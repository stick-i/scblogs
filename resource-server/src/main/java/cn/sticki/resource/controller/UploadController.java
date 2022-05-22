package cn.sticki.resource.controller;

import cn.sticki.common.enumeration.type.FileType;
import cn.sticki.common.pojo.RestResult;
import cn.sticki.common.utils.FileUtils;
import cn.sticki.resource.service.UploadService;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestController
public class UploadController {

	@Resource
	private UploadService uploadService;

	/**
	 * 上传图片
	 *
	 * @param file 图片文件
	 * @return 图片链接
	 */
	@PostMapping("/image")
	public RestResult<String> uploadBlogImage(@NotNull MultipartFile file) throws MinioException, IOException {
		log.debug("uploadBlogImage, fileName->{}", file.getOriginalFilename());
		FileUtils.checkFile(file, 1024 * 1024L, FileType.JPEG, FileType.PNG);
		String url = uploadService.uploadBlogImage(file);
		return new RestResult<>(url);
	}

	/**
	 * 上传头像
	 *
	 * @param file 头像文件
	 * @return 图片链接
	 */
	@PostMapping("/avatar")
	public RestResult<Boolean> uploadAvatar(@NotNull MultipartFile file, @NotNull String name) {
		log.debug("uploadAvatar, fileName->{}", file.getOriginalFilename());
		FileUtils.checkFile(file, 1024 * 1024L, FileType.JPEG, FileType.PNG);
		return new RestResult<>(uploadService.uploadAvatar(file, name));
	}

}
