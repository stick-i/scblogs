package cn.sticki.blog.controller;

import cn.sticki.blog.config.ResourcePath;
import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.util.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/resource")
public class ResourceController {

	@Resource
	private OssUtils ossUtils;

	/**
	 * 获取头像资源
	 *
	 * @param file     文件名
	 * @param response 响应头
	 */
	@GetMapping("/avatar/{file}")
	public void getAvatar(@PathVariable @NotNull String file, @NotNull HttpServletResponse response)
			throws IOException, MinioException {
		log.debug("getAvatar, fileName->{}", file);
		response.setContentType("image/jpeg");
		try (
				ServletOutputStream outputStream = response.getOutputStream()
		) {
			ossUtils.download(ResourcePath.avatar + file, outputStream);
		}
	}

	@GetMapping("/blog-cover/{file}")
	public void getBlogCover(@PathVariable @NotNull String file, @NotNull HttpServletResponse response)
			throws IOException, MinioException {
		log.debug("getBlogCover, fileName->{}", file);
		response.setContentType("image/jpeg");
		try (
				ServletOutputStream outputStream = response.getOutputStream()
		) {
			ossUtils.download(ResourcePath.blogCoverImage + file, outputStream);
		}
	}

}
