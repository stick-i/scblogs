package cn.sticki.blog.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@ConfigurationProperties(prefix = "my-redis")
@Data
public class RedisUtils {

	private static JedisPool jedisPool;

	private String host;

	private Integer port;

	private Integer maxTotal;

	private Integer maxIdle;

	@PostConstruct
	private void jedisInit() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		jedisPool = new JedisPool(config, host, port);
		log.debug("jedisInit");
	}

	public Jedis getJedis() {
		return jedisPool.getResource();
	}

	public String setex(String key, long seconds, String value) {
		Jedis jedis = getJedis();
		String setex = jedis.setex(key, seconds, value);
		jedis.close();
		return setex;
	}

	public String get(String key) {
		Jedis jedis = getJedis();
		String s = jedis.get(key);
		jedis.close();
		return s;
	}

	public Long del(String key) {
		Jedis jedis = getJedis();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}

}
