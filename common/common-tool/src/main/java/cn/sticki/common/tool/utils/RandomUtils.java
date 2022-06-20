package cn.sticki.common.tool.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author 阿杆
 */
public class RandomUtils {

	public static Integer randomNumber(int size) {
		return Integer.valueOf(generator(size, "0123456789"));
	}

	public static String generator() {
		return generator(6);
	}

	public static String generator(int capacity) {
		return generator(capacity, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}

	public static String generator(int capacity, String randomString) {
		StringBuilder builder = new StringBuilder(capacity);
		for (int i = 0; i < capacity; i++) {
			char ch = randomString.charAt(new Random().nextInt(randomString.length()));
			builder.append(ch);
		}
		return String.valueOf(builder);
	}

	public static String uuid() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

}
