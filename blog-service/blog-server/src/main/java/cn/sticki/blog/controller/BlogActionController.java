package cn.sticki.blog.controller;

import cn.sticki.blog.service.UserCollectBlogService;
import cn.sticki.blog.service.UserLikeBlogService;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/blog/action")
public class BlogActionController {

	@Resource
	private UserLikeBlogService userLikeBlogService;

	@Resource
	private UserCollectBlogService userCollectBlogService;

	@PostMapping("/like")
	public RestResult<Object> likeBlog(@NotNull Integer blogId, @RequestHeader int id) {
		boolean status = userLikeBlogService.likeBlog(id, blogId);
		Map<String, Object> map = new HashMap<>();
		map.put("num", userLikeBlogService.getLikeNum(blogId));
		map.put("status", status);
		return new RestResult<>(map);
	}

	@PostMapping("/collect")
	public RestResult<Object> collectBlog(@NotNull Integer blogId, @RequestHeader int id) {
		boolean status = userCollectBlogService.collectBlog(id, blogId);
		Map<String, Object> map = new HashMap<>();
		map.put("num", userCollectBlogService.getCollectNum(blogId));
		map.put("status", status);
		return new RestResult<>(map);
	}

}
