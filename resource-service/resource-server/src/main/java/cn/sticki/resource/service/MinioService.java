package cn.sticki.resource.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MinioService {

	/**
	 * 判断bucket是否存在
	 */
	boolean existBucket(String name) throws MinioException;

	/**
	 * 上传文件，请手动关闭inputStream
	 */
	void upload(String filePath, String bucketName, InputStream inputStream, long objectSize, long partSize, String contentType) throws MinioException;

	/**
	 * 上传文件
	 *
	 * @param multipartFile 文件
	 * @param bucketName    桶名称
	 * @throws MinioException minio异常
	 * @throws IOException    字节流为空
	 */
	void upload(MultipartFile multipartFile, String bucketName) throws MinioException, IOException;

	void upload(MultipartFile multipartFile, String filePath, String bucketName) throws MinioException, IOException;

	/**
	 * 下载文件，需要自己关闭outputStream
	 *
	 * @param filePath     文件名(包括路径)
	 * @param bucketName   储存桶
	 * @param outputStream 输出流
	 * @deprecated 请使用 {@link MinioService#download(java.lang.String, java.lang.String, javax.servlet.http.HttpServletResponse) }
	 */
	void download(String filePath, String bucketName, ServletOutputStream outputStream) throws MinioException, IOException;

	/**
	 * 下载文件,使用response
	 *
	 * @param filePath   文件路径
	 * @param bucketName 桶名称
	 * @param response   输出的响应体
	 */
	void download(String filePath, String bucketName, HttpServletResponse response) throws MinioException, IOException;

	/**
	 * 下载文件，需要手动关闭流，否则会一直占用资源
	 *
	 * @param filePath 文件名(包括路径)
	 */
	InputStream download(String filePath, String bucketName) throws MinioException;

	void removeFile(String fileName, String bucketName) throws MinioException;

	void getObjectList(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

}
