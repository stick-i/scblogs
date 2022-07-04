package cn.sticki.common.redis.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/21 21:57
 */
public class RedisSimpleLock implements ILock {

	private final static String KEY_PREFIX = "lock:";

	private final static String KEY_UUID = UUID.randomUUID() + ":";

	private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;

	static {
		// 加载lua文件
		UNLOCK_SCRIPT = new DefaultRedisScript<>();
		UNLOCK_SCRIPT.setScriptText(
				"if(redis.call('get', KEYS[1]) == ARGV[1]) then" +
						"return redis.call('del', KEYS[1]);" +
						"end;" +
						"return 0;");
		UNLOCK_SCRIPT.setResultType(Long.class);
	}

	private final StringRedisTemplate stringRedisTemplate;

	private final String name;

	public RedisSimpleLock(StringRedisTemplate stringRedisTemplate, String name) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.name = name;
	}

	@Override
	public boolean tryLock(long timeout) {
		// 1. 生成key，通过拼接前缀和业务名
		String key = KEY_PREFIX + name;
		// 2. 生成value，用于判断该锁是不是当前线程生成的。使用随机的UUID+当前线程id，防止集群时value碰撞。
		String value = KEY_UUID + Thread.currentThread().getId();
		// 3. 尝试写入redis
		Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
		// 4. 返回写入情况，成功即获取到锁
		return Boolean.TRUE.equals(success);
	}

	@Override
	public void unlock() {
		String key = KEY_PREFIX + name;
		String value = KEY_UUID + Thread.currentThread().getId();
		// 调用lua脚本
		stringRedisTemplate.execute(
				UNLOCK_SCRIPT,
				Collections.singletonList(key),
				value);
	}

}
