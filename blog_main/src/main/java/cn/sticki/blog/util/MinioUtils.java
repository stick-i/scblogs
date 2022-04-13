package cn.sticki.blog.util;

import cn.sticki.blog.exception.SystemException;
import io.minio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

@Component
public class MinioUtils {

	@Resource
	private MinioClient minioClient;

	@Value("${minio.bucket-name}")
	private String bucketName;

	/**
	 * 判断bucket是否存在，不存在则创建
	 */
	public void existBucket(String name) {
		try {
			boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
			if (!exists) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建存储bucket
	 *
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public Boolean makeBucket(String bucketName) {
		try {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除存储bucket
	 *
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public Boolean removeBucket(String bucketName) {
		try {
			minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public InputStream download(String fileName) throws SystemException {

		try (InputStream stream =
				     minioClient.getObject(
						     GetObjectArgs.builder()
								     .bucket(bucketName)
								     .object(fileName)
								     .build()
				     )) {
			// Read data from stream
			return stream;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("文件下载失败");
		}

		// try {
		// 	 = minioClient.getObject(
		// 			GetObjectArgs.builder()
		// 					.bucket(bucketName)
		// 					.object(fileName)
		// 					.build()
		// 	);
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// 	throw new SystemException("文件下载失败");
		// }
	}

	/**
	 * 上传文件
	 */
	public void upload(MultipartFile multipartFile) throws SystemException {
		try {
			minioClient.putObject(
					PutObjectArgs.builder()
							.bucket(bucketName)
							.object(multipartFile.getOriginalFilename())
							// 文件大小和分片大小，填-1默认为5Mib
							.stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
							.contentType(multipartFile.getContentType())

							.build()
			);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("文件上传失败");
		}

		// minioClient.putObject(bucketName, )
		// minioClient.putObject(bucketName, multipartFile.getName(), multipartFile.getInputStream(), multipartFile.getSize(), multipartFile.getContentType());

		// List<String> names = new ArrayList<>(multipartFile.length);
		// for (MultipartFile file : multipartFile) {
		// 	String fileName = file.getOriginalFilename();
		// 	String[] split = fileName.split("\\.");
		// 	if (split.length > 1) {
		// 		fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
		// 	} else {
		// 		fileName = fileName + System.currentTimeMillis();
		// 	}
		// 	InputStream in = null;
		// 	try {
		// 		in = file.getInputStream();
		// 		minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(in, in.available(), -1).contentType(file.getContentType()).build());
		// 	} catch (Exception e) {
		// 		e.printStackTrace();
		// 	} finally {
		// 		if (in != null) {
		// 			try {
		// 				in.close();
		// 			} catch (IOException e) {
		// 				e.printStackTrace();
		// 			}
		// 		}
		// 	}
		// 	names.add(fileName);
		// }
		// return names;
	}

}
