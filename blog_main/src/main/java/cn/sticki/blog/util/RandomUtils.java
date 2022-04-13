package cn.sticki.blog.util;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class RandomUtils {

	public String generator() {
		return generator(6);
	}

	public String generator(int capacity) {
		return generator(capacity, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}

	public String generator(int capacity, String randomString) {
		StringBuilder builder = new StringBuilder(capacity);
		for (int i = 0; i < capacity; i++) {
			char ch = randomString.charAt(new Random().nextInt(randomString.length()));
			builder.append(ch);
		}
		return String.valueOf(builder);
	}

	public String uuid() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

}
