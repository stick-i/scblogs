package cn.sticki.resource.service;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.web.utils.ResponseUtils;
import cn.sticki.resource.mapper.ImageMapper;
import io.minio.errors.MinioException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class DownloadService {

	@Resource
	private MinioService minioService;

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

}
