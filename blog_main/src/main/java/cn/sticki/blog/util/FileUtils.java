package cn.sticki.blog.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileUtils {
	/**
	 * 判断文件类型
	 */
	public FileType getType(MultipartFile multipartFile) throws IOException {
		String fileHead;
		try (
				InputStream inputStream = multipartFile.getInputStream()
		) {
			fileHead = getFileHeader(inputStream);
		}

		if (fileHead == null || fileHead.length() <= 0) return null;

		fileHead = fileHead.toUpperCase();
		for (FileType type : FileType.values()) {
			if (fileHead.startsWith(type.getValue())) {
				return type;
			}
		}
		return null;
	}

	/**
	 * 读取文件头
	 */
	private String getFileHeader(InputStream inputStream) throws IOException {
		byte[] b = new byte[28];
		inputStream.read(b, 0, 28);
		return bytesToHex(b);
	}

	/**
	 * 将字节数组转换成16进制字符串
	 */
	public String bytesToHex(byte[] bytes) {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : bytes) {
			int v = b & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

}
