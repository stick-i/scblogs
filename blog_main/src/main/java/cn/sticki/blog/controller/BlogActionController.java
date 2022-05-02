package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.security.AuthenticationFacade;
import cn.sticki.blog.service.BlogActionService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogActionController {

	@Resource
	private AuthenticationFacade authenticationFacade;

	@Resource
	private BlogActionService blogActionService;

	@PostMapping("/like")
	public RestTemplate likeBlog(@NotNull Integer blogId) {
		User user = authenticationFacade.getUser();
		boolean status = blogActionService.likeBlog(user.getId(), blogId);
		Map<String, Object> map = new HashMap<>();
		map.put("num", blogActionService.getLikeNum(blogId));
		map.put("status", status);
		return new RestTemplate(map);
	}

	@PostMapping("/collect")
	public RestTemplate collectBlog(@NotNull Integer blogId) {
		User user = authenticationFacade.getUser();
		boolean status = blogActionService.collectBlog(user.getId(), blogId);
		Map<String, Object> map = new HashMap<>();
		map.put("num", blogActionService.getCollectNum(blogId));
		map.put("status", status);
		return new RestTemplate(map);
	}

}
