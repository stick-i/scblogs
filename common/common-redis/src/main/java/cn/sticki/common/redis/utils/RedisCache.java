package cn.sticki.common.redis.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static cn.sticki.common.redis.utils.RedisConstants.CACHE_LOCK_KEY;
import static cn.sticki.common.redis.utils.RedisConstants.CACHE_NULL_TTL;

/**
 * Redis缓存工具类，可以解决缓存穿透和缓存击穿问题<br>
 * 学习自黑马程序员虎老师: <a href="https://www.bilibili.com/video/BV1cr4y1671t?p=46">视频链接</a>
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/19 20:39
 */
@Slf4j
@Component
public class RedisCache {

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings("AlibabaThreadShouldSetName")
	private static final ExecutorService CACHE_REBUILD_EXECUTOR = new ThreadPoolExecutor(
			5, 10,
			0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<>());

	/**
	 * 存入redis，以json形式
	 *
	 * @param key   key
	 * @param value value
	 * @param time  过期时间
	 * @param unit  时间单位
	 */
	public void set(String key, Object value, Long time, TimeUnit unit) {
		stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
	}

	/**
	 * 存入redis，并设置逻辑过期策略，用于解决缓存击穿问题，一般不在程序中单独调用
	 *
	 * @param key   key
	 * @param value value
	 * @param time  过期时间
	 * @param unit  时间单位
	 */
	public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
		// 设置逻辑过期
		RedisData redisData = new RedisData();
		redisData.setData(value);
		redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
		// 写入Redis，不设置TTL
		stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
	}

	/**
	 * 从redis获取数据，并基于缓存空对象来解决缓存穿透问题
	 *
	 * @param keyPrefix  key前缀
	 * @param id         id
	 * @param type       bean类型
	 * @param dbFallback 可执行的查询数据库语句
	 * @param time       若从数据库查找，存入redis的缓存时间
	 * @param unit       时间单位
	 * @param <R>        bean类型
	 * @param <ID>       id类型
	 * @return 缓存对象
	 */
	public <R, ID> R getWithPassThrough(
			String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
		String key = keyPrefix + id;
		// 1.从redis查询缓存
		String json = stringRedisTemplate.opsForValue().get(key);
		// 2.判断是否存在
		if (StrUtil.isNotBlank(json)) {
			// 3.1 存在，直接返回
			return JSONUtil.toBean(json, type);
		}
		// 3.2 判断命中的是否是为null
		if (json != null) {
			// 不为null，说明该值缓存了空对象，返回null
			return null;
		}

		// 4.缓存不存在也不为空对象，则根据id查询数据库
		R r = dbFallback.apply(id);
		// 5.数据库不存在
		if (r == null) {
			// 将空值写入redis
			stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.SECONDS);
			// 返回null
			return null;
		}
		// 6.存在，写入redis
		this.set(key, r, time, unit);
		return r;
	}

	/**
	 * 从redis获取数据，并基于逻辑过期的方式来解决缓存击穿的问题
	 *
	 * @param keyPrefix  key前缀
	 * @param id         id
	 * @param type       bean类型
	 * @param dbFallback 可执行的查询数据库语句
	 * @param time       若从数据库查找，存入redis的缓存时间
	 * @param unit       时间单位
	 * @param <R>        bean类型
	 * @param <ID>       id类型
	 * @return 缓存对象
	 */
	public <R, ID> R getWithLogicalExpire(
			String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
		String key = keyPrefix + id;
		// 1.从redis查询缓存
		String json = stringRedisTemplate.opsForValue().get(key);
		// 2.判断是否存在
		if (StrUtil.isBlank(json)) {
			// 3.不存在，直接返回null
			return null;
		}
		// 4.命中，需要先把json反序列化为对象
		RedisData redisData = JSONUtil.toBean(json, RedisData.class);
		R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
		LocalDateTime expireTime = redisData.getExpireTime();
		// 5.判断是否过期
		if (expireTime.isAfter(LocalDateTime.now())) {
			// 5.1.未过期，直接返回店铺信息
			return r;
		}
		// 5.2.已过期，需要缓存重建
		// 6.缓存重建
		// 6.1.获取互斥锁
		String lockKey = CACHE_LOCK_KEY + key;
		boolean isLock = tryLock(lockKey);
		// 6.2.判断是否获取锁成功
		if (isLock) {
			// 6.3.成功，开启独立线程，实现缓存重建
			CACHE_REBUILD_EXECUTOR.submit(() -> {
				try {
					// 查询数据库
					R newR = dbFallback.apply(id);
					// 重建缓存
					this.setWithLogicalExpire(key, newR, time, unit);
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					// 释放锁
					unlock(lockKey);
				}
			});
		}
		// 6.4.返回当前的信息
		return r;
	}

	/**
	 * 从redis获取数据，并基于互斥锁来解决缓存击穿问题
	 *
	 * @param keyPrefix  key前缀
	 * @param id         id
	 * @param type       bean类型
	 * @param dbFallback 可执行的查询数据库语句
	 * @param time       若从数据库查找，存入redis的缓存时间
	 * @param unit       时间单位
	 * @param <R>        bean类型
	 * @param <ID>       id类型
	 * @return 缓存对象
	 */
	public <R, ID> R getWithMutex(
			String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
		String key = keyPrefix + id;
		// 1.从redis查询缓存
		String shopJson = stringRedisTemplate.opsForValue().get(key);
		// 2.判断是否存在
		if (StrUtil.isNotBlank(shopJson)) {
			// 3.存在，直接返回
			return JSONUtil.toBean(shopJson, type);
		}
		// 判断命中的是否是空值
		if (shopJson != null) {
			// 返回一个错误信息
			return null;
		}

		// 4.实现缓存重建
		// 4.1.获取互斥锁
		String lockKey = CACHE_LOCK_KEY + key;
		R r;
		try {
			boolean isLock = tryLock(lockKey);
			// 4.2.判断是否获取成功
			if (!isLock) {
				// 4.3.获取锁失败，休眠并重试
				Thread.sleep(50);
				return getWithMutex(keyPrefix, id, type, dbFallback, time, unit);
			}
			// 4.4.获取锁成功，根据id查询数据库
			r = dbFallback.apply(id);
			// 5.不存在，返回空值
			if (r == null) {
				// 将空值写入redis
				stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
				// 返回null
				return null;
			}
			// 6.存在，写入redis
			this.set(key, r, time, unit);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			// 7.释放锁
			unlock(lockKey);
		}
		// 8.返回
		return r;
	}

	private boolean tryLock(String key) {
		Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
		return BooleanUtil.isTrue(flag);
	}

	private void unlock(String key) {
		stringRedisTemplate.delete(key);
	}

}
