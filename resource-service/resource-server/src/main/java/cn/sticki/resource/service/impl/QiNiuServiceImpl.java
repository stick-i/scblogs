package cn.sticki.resource.service.impl;

import cn.sticki.resource.config.QiNiuConfig;
import cn.sticki.resource.service.QiNiuService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class QiNiuServiceImpl implements QiNiuService {

	@Resource
	private QiNiuConfig qiNiuConfig;

	@Override
	public String upload(InputStream inputStream, String fileName, String bucketName) throws QiniuException {
		// 构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(Region.region0());
		// 其他参数参考类注释

		UploadManager uploadManager = new UploadManager(cfg);
		// 生成上传凭证，然后准备上传
		Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
		// 可覆盖上传
		String upToken = auth.uploadToken(bucketName, fileName);

		try {
			Response response = uploadManager.put(inputStream, fileName, upToken, null, null);
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			log.info("QiNiuService upload ok, key {}", putRet);
			return qiNiuConfig.getPrefixUrl() + putRet.key;
		} catch (QiniuException ex) {
			Response r = ex.response;
			log.error("QiNiuService upload error,{}", r.toString());
			try {
				log.error("error body,{}", r.bodyString());
			} catch (QiniuException ex2) {
				//ignore
			}
			throw ex;
		}

	}

	@Override
	public String upload(MultipartFile multipartFile, String bucketName) throws IOException {
		return upload(multipartFile, null, bucketName);
	}

	@Override
	public String upload(MultipartFile multipartFile, String fileName, String bucketName) throws IOException {
		try (InputStream inputStream = multipartFile.getInputStream()) {
			return upload(inputStream, fileName, bucketName);
		}
	}

}
