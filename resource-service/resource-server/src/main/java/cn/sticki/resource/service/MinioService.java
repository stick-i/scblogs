package cn.sticki.resource.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioService {

	@Resource
	private MinioClient minioClient;

	/**
	 * 判断bucket是否存在
	 */
	public boolean existBucket(String name) throws MinioException {
		try {
			return minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
			// minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MinioException();
		}
	}

	/**
	 * 创建存储bucket
	 *
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public boolean makeBucket(String bucketName) throws MinioException {
		try {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MinioException();
		}
	}

	/**
	 * 删除存储bucket
	 *
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public Boolean removeBucket(String bucketName) throws MinioException {
		try {
			minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MinioException();
		}
	}

	/**
	 * 上传文件，请手动关闭inputStream
	 */
	public void upload(String filePath, String bucketName, InputStream inputStream, long objectSize, long partSize, String contentType)
			throws MinioException {
		try {
			minioClient.putObject(PutObjectArgs
					.builder()
					.bucket(bucketName)
					.object(filePath)
					// 文件大小和分片大小，填-1默认为5Mib
					.stream(inputStream, objectSize, partSize)
					.contentType(contentType)
					.build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MinioException("文件上传异常");
		}
	}

	/**
	 * 上传文件
	 *
	 * @param multipartFile 文件
	 * @param bucketName    桶名称
	 * @throws MinioException minio异常
	 * @throws IOException    字节流为空
	 */
	public void upload(MultipartFile multipartFile, String bucketName) throws MinioException, IOException {
		try (
				InputStream inputStream = multipartFile.getInputStream()
		) {
			this.upload(
					multipartFile.getOriginalFilename(),
					bucketName,
					inputStream,
					multipartFile.getSize(),
					-1,
					multipartFile.getContentType()
			);
		}
	}

	public void upload(MultipartFile multipartFile, String filePath, String bucketName) throws MinioException, IOException {
		try (
				InputStream inputStream = multipartFile.getInputStream()
		) {
			this.upload(
					filePath,
					bucketName,
					inputStream,
					multipartFile.getSize(),
					-1,
					multipartFile.getContentType()
			);
		}
	}

	/**
	 * 下载文件，需要自己关闭outputStream
	 *
	 * @param filePath     文件名(包括路径)
	 * @param bucketName   储存桶
	 * @param outputStream 输出流
	 * @deprecated 请使用 {@link MinioService#download(java.lang.String, java.lang.String, javax.servlet.http.HttpServletResponse) }
	 */
	public void download(String filePath, String bucketName, ServletOutputStream outputStream) throws MinioException, IOException {
		InputStream inputStream = this.download(filePath, bucketName);
		IOUtils.copy(inputStream, outputStream);
		inputStream.close();
	}

	/**
	 * 下载文件,使用response
	 *
	 * @param filePath   文件路径
	 * @param bucketName 桶名称
	 * @param response   输出的响应体
	 */
	public void download(String filePath, String bucketName, HttpServletResponse response) throws MinioException, IOException {
		try (InputStream inputStream = this.download(filePath, bucketName)) {
			try (ServletOutputStream outputStream = response.getOutputStream()) {
				IOUtils.copy(inputStream, outputStream);
			}
		}
	}

	/**
	 * 下载文件，需要手动关闭流，否则会一直占用资源
	 *
	 * @param filePath 文件名(包括路径)
	 */
	public InputStream download(String filePath, String bucketName) throws MinioException {
		try {
			return minioClient.getObject(GetObjectArgs
					.builder()
					.bucket(bucketName)
					.object(filePath)
					.build());
		} catch (Exception e) {
			throw new MinioException("文件下载异常," + e.getMessage());
		}
	}

	public void removeFile(String fileName, String bucketName) throws MinioException {
		try {
			minioClient.removeObject(
					RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build()
			);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MinioException("文件删除异常," + e.getMessage());
		}
	}

	public void getObjectList(String bucketName)
			throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
		Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
		for (Result<Item> result : results) {
			Item item = result.get();
			System.out.println(item.objectName());
		}
	}

}
