package cn.sticki.common.redis.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis默认序列化配置
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/17 22:22
 */
@Configuration
public class RedisSerializeDefaultConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		// 创建RedisTemplate对象
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		// 设置连接工厂
		template.setConnectionFactory(connectionFactory);
		// 创建JSON序列化工具
		GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		// 设置Key的序列化
		template.setKeySerializer(RedisSerializer.string());
		template.setHashKeySerializer(RedisSerializer.string());
		// 设置Value的序列化
		template.setValueSerializer(jsonRedisSerializer);
		template.setHashValueSerializer(jsonRedisSerializer);
		// 返回
		return template;
	}

}
