package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.security.AuthenticationFacade;
import cn.sticki.blog.service.UserCollectBlogService;
import cn.sticki.blog.service.UserLikeBlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserHistoryController {

	private final int pageSize = 20;

	@Resource
	private AuthenticationFacade authenticationFacade;

	@Resource
	private UserLikeBlogService userLikeBlogService;

	@Resource
	private UserCollectBlogService userCollectBlogService;

	@GetMapping("/like")
	public RestTemplate getLikeList(@RequestParam(defaultValue = "1") int page) {
		User user = authenticationFacade.getUser();
		BlogListVO blogList = userLikeBlogService.getLikeBlogList(user.getId(), page, pageSize);
		return new RestTemplate(blogList);
	}

	@GetMapping("/collect")
	public RestTemplate getCollectList(@RequestParam(defaultValue = "1") int page) {
		User user = authenticationFacade.getUser();
		BlogListVO blogList = userCollectBlogService.getCollectBlogList(user.getId(), page, pageSize);
		return new RestTemplate(blogList);
	}

}
