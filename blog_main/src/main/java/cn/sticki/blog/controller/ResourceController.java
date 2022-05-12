package cn.sticki.blog.controller;

import cn.sticki.blog.config.ResourcePath;
import cn.sticki.blog.util.OssUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
	public void getAvatar(@PathVariable @NotNull String file, @NotNull HttpServletResponse response) {
		getImg(ResourcePath.avatar + file, response);
	}

	@GetMapping("/blog-cover/{file}")
	public void getBlogCover(@PathVariable @NotNull String file, @NotNull HttpServletResponse response) {
		getImg(ResourcePath.blogCoverImage + file, response);
	}

	@GetMapping("/blog-img/{file}")
	public void getBlogImg(@PathVariable @NotNull String file, @NotNull HttpServletResponse response) {
		getImg(ResourcePath.blogImage + file, response);
	}

	@SneakyThrows
	private void getImg(String filePath, HttpServletResponse response) {
		log.debug("getImg, fileName->{}", filePath);
		response.setContentType("image/jpeg");
		try (ServletOutputStream outputStream = response.getOutputStream()) {
			ossUtils.download(filePath, outputStream);
		}
	}

}
