package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.vo.BlogStatisticsDataVO;
import cn.sticki.blog.pojo.vo.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/blog-console")
public class BlogConsoleController {

	/**
	 * 获取创作信息
	 */
	@GetMapping("/blog-statistics-data")
	public RestTemplate getStatisticsData() {
		return new RestTemplate();
	}

	/**
	 * 获取博客列表
	 *
	 * @param page     当前页
	 * @param pageSize 页大小
	 * @param status   博客状态码
	 */
	@GetMapping("/blog-list")
	public RestTemplate getBlogList(int page, int pageSize, int status) {
		return new RestTemplate(new BlogStatisticsDataVO());
	}

	@PostMapping("/blog")
	public RestTemplate saveBlog() {
		return new RestTemplate();
	}

	@DeleteMapping("/blog")
	public RestTemplate deleteBlog() {
		return new RestTemplate();
	}

	@DeleteMapping("/blog/delete")
	public RestTemplate completelyDeleteBlog() {
		return new RestTemplate();
	}

}
