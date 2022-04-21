package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.service.BlogBasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private User user;

	@Resource
	private BlogBasicService blogBasicService;

	private final int pageSize = 10;

	/**
	 * 获取推荐博客列表
	 *
	 * @return 博客列表
	 */
	@GetMapping("/list")
	public RestTemplate recommendBlog(@CookieValue(name = "recommend-page", defaultValue = "0") int page, HttpServletResponse response) {
		log.debug("searchBlog,page->{}", page);
		// todo 最好根据用户标签来推
		List<BlogBasic> blogList = blogBasicService.getRecommendBlogList(page, pageSize);
		ResponseCookie cookie = ResponseCookie.from("recommend-page", String.valueOf(page + 1))
				.sameSite("None").secure(true).maxAge(Duration.ofHours(1)).build();
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return new RestTemplate(blogList);
	}

	/**
	 * 搜索博客
	 *
	 * @param key 搜索内容
	 * @return 博客列表
	 */
	@GetMapping("/search")
	public RestTemplate searchBlog(String key, @CookieValue(name = "search-page", defaultValue = "0") int page, HttpServletResponse response) {
		log.debug("searchBlog,search->{},page->{}", key, page);
		List<BlogBasic> blogList = blogBasicService.searchBlog(key, page, pageSize);
		ResponseCookie cookie = ResponseCookie.from("search-page", String.valueOf(page + 1))
				.sameSite("None").secure(true).maxAge(Duration.ofHours(1)).build();
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return new RestTemplate(blogList);
	}

	/**
	 * 获取博客内容
	 *
	 * @param id 博客id
	 */
	@GetMapping("/blog")
	public RestTemplate getBlog(@RequestParam Integer id) {
		return new RestTemplate(blogBasicService.getBlogContent(id));
	}

}
