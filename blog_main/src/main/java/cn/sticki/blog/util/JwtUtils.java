package cn.sticki.blog.util;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtUtils {

	//设置秘钥
	@Value("${security.jwt.key}")
	public byte[] key;

	@Value("${security.jwt.issuer}")
	public String issuer;

	/**
	 * 生成jwtToken，有效时间默认为 1天
	 *
	 * @param o 内容
	 * @return token
	 */
	@SneakyThrows
	public String createToken(Object o) {
		return createToken(o, 86400);
	}

	/**
	 * 生成token
	 *
	 * @param data      payload 内容
	 * @param validTime 有效时间，单位->秒
	 * @return token
	 */
	@SneakyThrows
	public String createToken(Object data, long validTime) {
		JWT token = JWT.create();
		token.setPayload("data", data);
		// 获取类的所有属性，包括public、protected、default、private
		// Field[] fields = o.getClass().getDeclaredFields();
		// for (Field field : fields) {
		// 	// 设置属性为可读
		// 	field.setAccessible(true);
		// 	// 将属性添加到payload
		// 	token.setPayload(field.getName(), field.get(o));
		// }
		// 设置失效时间和密钥、设置签发时间和签发人
		Date date = new Date(System.currentTimeMillis() + (validTime * 1000L));
		token.setIssuer(issuer).setExpiresAt(date).setKey(key);
		return token.sign();
	}

	/**
	 * 验证签名是否正确，以及是否过期
	 */
	public boolean validate(String token) {
		try {
			// 验证签名是否正确
			if (!JWT.of(token).setKey(key).validate(0)) return false;
			// 验证时间是否正常
			JWTValidator.of(token).validateDate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 解析data数据，若数据类型不一致，则会解析出属性为null，但对象不会为null
	 *
	 * @param jwt 要被解析的jwt
	 * @param clz 解析成的类型
	 * @param <T> 类型
	 * @return clz类型对象
	 */
	public <T> T parse(String jwt, Class<T> clz) {
		JSONObject data = (JSONObject) JWT.of(jwt).getPayload("data");
		return data.toBean(clz);
	}

	public <T> T validateAndParse(String token, Class<T> clz) {
		if (!validate(token)) return null;
		return parse(token, clz);
	}

}
