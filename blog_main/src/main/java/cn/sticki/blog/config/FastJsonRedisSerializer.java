package cn.sticki.blog.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 使Redis使用FastJson序列化配置
 *
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private Class<T> clazz;

	static {
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
	}

	public FastJsonRedisSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, DEFAULT_CHARSET);

		return JSON.parseObject(str, clazz);
	}

	protected JavaType getJavaType(Class<?> clazz) {
		return TypeFactory.defaultInstance().constructType(clazz);
	}

}
