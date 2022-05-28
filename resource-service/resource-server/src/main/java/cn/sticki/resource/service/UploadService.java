package cn.sticki.resource.service;

import cn.sticki.resource.config.ResourcePath;
import cn.sticki.resource.mapper.ImageMapper;
import cn.sticki.resource.pojo.Image;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

@Slf4j
@Service
public class UploadService {

	@Resource
	private ImageMapper imageMapper;

	@Resource
	private MinioService minioService;

	public boolean uploadAvatar(MultipartFile image, String name) {
		try {
			// 上传图片
			minioService.upload(image, name, "avatar");
			// 返回结果
			return true;
		} catch (Exception e) {
			log.error("头像上传失败:{}", e.getMessage());
			return false;
		}
	}

	public String uploadBlogImage(MultipartFile image) throws MinioException, IOException {
		return uploadImage(image, "image");
	}

	private String uploadImage(MultipartFile image, String bucketName) throws IOException, MinioException {
		try (InputStream stream = image.getInputStream()) {
			String md5 = DigestUtils.md5DigestAsHex(stream);
			// 存数据库，上传图片
			// 先判断数据库是否存在这条记录，存在的话直接返回链接就行了
			LambdaQueryWrapper<Image> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Image::getUrl, md5);
			if (!imageMapper.exists(wrapper)) {
				// 不存在，则新增
				Image img = new Image();
				img.setUrl(md5);
				img.setCreateTime(new Timestamp(System.currentTimeMillis()));
				imageMapper.insert(img);
				// 上传图片
				minioService.upload(image, md5, bucketName);
			}
			// 返回访问链接
			return ResourcePath.imageUrlBase + md5;
		}
	}

}
