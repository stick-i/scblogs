package cn.sticki.common.redis.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/20 20:29
 */
@Component
public class RedisIdGenerator {

	/**
	 * 开始时间戳
	 */
	private static final long BEGIN_TIMESTAMP = 1640995200L;

	/**
	 * 序列号的位数
	 */
	private static final int COUNT_BITS = 32;

	private final RedisTemplate<String, Long> redisTemplate;

	public RedisIdGenerator(RedisTemplate<String, Long> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@SuppressWarnings("ConstantConditions")
	public long nextId(String keyPrefix) {
		// 1. 生成时间戳
		LocalDateTime now = LocalDateTime.now();
		long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
		long timestamp = nowSecond - BEGIN_TIMESTAMP;
		// 2. 生成序列号
		// 2.1 获取当前日期，精确到天
		String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
		// 2.2 获取redis自增长值
		long increment = redisTemplate.opsForValue().increment("id:" + keyPrefix + ":" + date);

		// 3. 拼接并返回
		return increment << COUNT_BITS | timestamp;
	}

}
