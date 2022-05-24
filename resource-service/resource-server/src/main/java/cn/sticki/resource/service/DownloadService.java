package cn.sticki.resource.service;

import io.minio.errors.MinioException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class DownloadService {

	@Resource
	private MinioService minioService;

	public void getAvatarImage(String file, HttpServletResponse response) {
		getImage(file, "avatar", response);
	}

	public void getGeneralImage(String file, HttpServletResponse response) {
		getImage(file, "image", response);
	}

	@SneakyThrows
	private void getImage(String file, String bucketName, @NotNull HttpServletResponse response) {
		log.debug("getImg, fileName->{}, bucketName->{}", file, bucketName);
		try (ServletOutputStream outputStream = response.getOutputStream()) {
			try {
				minioService.download(file, bucketName, outputStream);
				response.setContentType("image/jpeg");
			} catch (MinioException | IOException e) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "资源不存在");
			}
		}
	}

}
