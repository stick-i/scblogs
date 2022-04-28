package cn.sticki.blog.util;

import cn.sticki.blog.enumeration.type.FileType;
import cn.sticki.blog.exception.userException.FileMaxSizeException;
import cn.sticki.blog.exception.userException.FileTypeException;
import lombok.SneakyThrows;
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
		try (InputStream inputStream = multipartFile.getInputStream()) {
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

	/**
	 * 获取文件后缀名
	 *
	 * @param fileName 文件名
	 * @return 后缀名，如 .png .jpg 等
	 */
	public String getExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) return fileName.substring(fileName.lastIndexOf("."));
		else return "";
	}

	/**
	 * 检查文件是否符合要求，若不符合要求则抛出异常
	 *
	 * @param file      被检查的文件
	 * @param maxSize   max size in byte
	 * @param ableTypes 允许的文件类型
	 */
	@SneakyThrows
	public void checkFile(MultipartFile file, Long maxSize, FileType... ableTypes) {
		if (maxSize != null) this.checkFileSize(file, maxSize);
		if (ableTypes.length > 0) this.checkFileTypes(file, ableTypes);
	}

	/**
	 * 检查文件大小
	 *
	 * @param file    被检查的文件
	 * @param maxSize max size in byte
	 */
	@SneakyThrows
	public void checkFileSize(MultipartFile file, Long maxSize) {
		if (file.getSize() > maxSize) {
			throw new FileMaxSizeException();
		}
	}

	/**
	 * 检查文件类型
	 *
	 * @param file      被检查的文件
	 * @param ableTypes 支持的文件类型
	 */
	@SneakyThrows
	public void checkFileTypes(MultipartFile file, FileType... ableTypes) {
		FileType fileType = this.getType(file);
		for (FileType ableType : ableTypes) {
			if (fileType == ableType) return;
		}
		throw new FileTypeException();
	}

}
