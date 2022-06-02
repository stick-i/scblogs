package cn.sticki.resource.service.impl;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.web.utils.ResponseUtils;
import cn.sticki.resource.config.ResourcePath;
import cn.sticki.resource.exception.UploadException;
import cn.sticki.resource.mapper.ImageMapper;
import cn.sticki.resource.pojo.Image;
import cn.sticki.resource.service.ImageService;
import cn.sticki.resource.service.MinioService;
import cn.sticki.resource.service.QiNiuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.errors.MinioException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

@Slf4j
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

	@Resource
	private MinioService minioService;

	@Resource
	private QiNiuService qiNiuService;

	@Resource
	private ImageMapper imageMapper;

	public void getAvatarImage(String file, HttpServletResponse response) {
		// todo 这里做一个可以减小尺寸的
		getImage(file, "avatar", response);
	}

	public void getGeneralImage(String file, HttpServletResponse response) {
		boolean image = getImage(file, "image", response);
		// 访问成功的话，图片访问量加1
		if (image) imageMapper.increaseVisit(file);
	}

	@SneakyThrows
	private boolean getImage(String file, String bucketName, @NotNull HttpServletResponse response) {
		log.debug("getImg, fileName->{}, bucketName->{}", file, bucketName);
		try {
			minioService.download(file, bucketName, response);
			response.setContentType("image/jpeg");
			return true;
		} catch (MinioException | IOException e) {
			try {
				ResponseUtils.objectToJson(response, new RestResult<>(404, "资源不存在"));
			} catch (IllegalStateException ignored) {
				// 忽略连接状态异常引起的报错，由用户多次重复刷新引起
			}
		}
		return false;
	}

	public String uploadAvatar(MultipartFile image, String name) {
		try {
			// 上传图片
			return qiNiuService.upload(image, name, "scblogs-avatar");
		} catch (Exception e) {
			log.error("头像上传失败:{},{}", name, e.getMessage());
			throw new UploadException("头像上传失败");
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
