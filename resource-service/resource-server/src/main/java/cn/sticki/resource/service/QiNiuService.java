package cn.sticki.resource.service;

import com.qiniu.common.QiniuException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface QiNiuService {

	/**
	 * 上传文件
	 *
	 * @param inputStream 数据流
	 * @param fileName    文件名，默认不指定name的情况下，以文件内容的hash值作为文件名
	 * @param bucketName  桶名称
	 */
	String upload(InputStream inputStream, String fileName, String bucketName) throws QiniuException;

	/**
	 * 上传文件
	 *
	 * @param multipartFile 文件
	 * @param bucketName    桶名称
	 */
	String upload(MultipartFile multipartFile, String bucketName) throws IOException;

	String upload(MultipartFile multipartFile, String fileName, String bucketName) throws IOException;

}
