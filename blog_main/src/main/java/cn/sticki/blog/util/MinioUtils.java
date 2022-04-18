package cn.sticki.blog.util;

import cn.sticki.blog.exception.systemException.MinioException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioUtils {

	@Resource
	private MinioClient minioClient;

	@Value("${minio.bucket-name}")
	private String bucketName;

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
	public void upload(String objectName, InputStream inputStream, long objectSize, long partSize, String contentType)
			throws MinioException {
		try {
			minioClient.putObject(PutObjectArgs
					.builder()
					.bucket(bucketName)
					.object(objectName)
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
	 * @throws MinioException minio异常
	 * @throws IOException    字节流为空
	 */
	public void upload(MultipartFile multipartFile) throws MinioException, IOException {
		try (
				InputStream inputStream = multipartFile.getInputStream()
		) {
			this.upload(
					multipartFile.getOriginalFilename(),
					inputStream,
					multipartFile.getSize(),
					-1,
					multipartFile.getContentType()
			);
		}
	}

	/**
	 * 下载文件
	 *
	 * @param fileName     文件名
	 * @param outputStream 输出流
	 */
	public void download(String fileName, ServletOutputStream outputStream) throws MinioException, IOException {
		InputStream inputStream = this.download(fileName);
		IOUtils.copy(inputStream, outputStream);
		inputStream.close();
	}

	/**
	 * 下载文件，需要手动关闭流，否则会一直占用资源
	 *
	 * @param fileName 文件名
	 */
	public InputStream download(String fileName) throws MinioException {
		try {
			return minioClient.getObject(GetObjectArgs
					.builder()
					.bucket(bucketName)
					.object(fileName)
					.build());
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MinioException("文件下载异常");
		}
	}

	public void removeFile(String fileName) throws MinioException {
		try {
			minioClient.removeObject(
					RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build()
			);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new MinioException("文件删除异常");
		}
	}

	public void getObjectList()
			throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
		Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
		for (Result<Item> result : results) {
			Item item = result.get();
			System.out.println(item.objectName());
		}
	}

}
