package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogContent;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;

	@Autowired
	private User user;

	@PostMapping("/blog")
	public RestTemplate createBlog(Blog blog, BlogContent content) {
		blog.setAuthor(user.getUsername());

		return new RestTemplate();
	}

}
