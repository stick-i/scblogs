package cn.sticki.resource.service;

import io.minio.errors.MinioException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 阿杆
 */
public interface MinioService {

	/**
	 * 判断bucket是否存在
	 *
	 * @param name bucket名
	 * @return 是否存在
	 * @throws MinioException 连接失败
	 */
	boolean existBucket(String name) throws MinioException;

	/**
	 * 上传文件，请手动关闭inputStream
	 *
	 * @param filePath    文件名，包含上传路径
	 * @param bucketName  桶名称
	 * @param inputStream 输入流
	 * @param objectSize  文件大小
	 * @param partSize    分段大小
	 * @param contentType 文件类型
	 * @throws MinioException 上传失败
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

	/**
	 * 上传文件，请手动关闭inputStream
	 *
	 * @param multipartFile 文件
	 * @param filePath      文件名，包含上传路径
	 * @param bucketName    桶名称
	 * @throws MinioException 上传失败
	 * @throws IOException    文件为空
	 */
	void upload(MultipartFile multipartFile, String filePath, String bucketName) throws MinioException, IOException;

	/**
	 * 下载文件，需要自己关闭outputStream
	 *
	 * @param filePath     文件名(包括路径)
	 * @param bucketName   储存桶
	 * @param outputStream 输出流
	 * @throws MinioException 文件不存在
	 * @throws IOException    outputStream为空
	 * @deprecated 请使用 {@link MinioService#download(java.lang.String, java.lang.String, jakarta.servlet.http.HttpServletResponse) }
	 */
	void download(String filePath, String bucketName, ServletOutputStream outputStream) throws MinioException, IOException;

	/**
	 * 下载文件,使用response
	 *
	 * @param filePath   文件路径
	 * @param bucketName 桶名称
	 * @param response   输出的响应体
	 * @throws MinioException 文件不存在
	 * @throws IOException    response为空
	 */
	void download(String filePath, String bucketName, HttpServletResponse response) throws MinioException, IOException;

	/**
	 * 下载文件，需要手动关闭流，否则会一直占用资源
	 *
	 * @param filePath   文件名(包括路径)
	 * @param bucketName 桶名称
	 * @return 输入流，直接复制给响应体就可以了
	 * @throws MinioException 获取失败，文件可能不存在
	 */
	InputStream download(String filePath, String bucketName) throws MinioException;

	/**
	 * 删除文件
	 *
	 * @param fileName   文件名
	 * @param bucketName 桶名称
	 * @throws MinioException 文件不存在
	 */
	void removeFile(String fileName, String bucketName) throws MinioException;

	/**
	 * 获取桶内文件列表
	 *
	 * @param bucketName 桶名称
	 * @throws Exception 获取失败，可能是桶不存在
	 */
	void getObjectList(String bucketName) throws Exception;

}
