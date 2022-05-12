package cn.sticki.blog.util;

import cn.sticki.blog.exception.userException.FileException;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EncryptUtils {

	/**
	 * MD5加密
	 *
	 * @param str 待加密字符串
	 * @return 16进制加密字符串
	 */
	public static String toMD5(String str) {
		return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
	}

	public static String toMD5(byte[] bytes) {
		return DigestUtils.md5DigestAsHex(bytes);
	}

	public static String toMD5(InputStream inputStream) {
		try {
			return DigestUtils.md5DigestAsHex(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileException("空文件");
		}
	}

}
