package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.service.UserCollectBlogService;
import cn.sticki.blog.service.UserLikeBlogService;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/blog")
public class UserHistoryController {

	private final int pageSize = 20;

	@Resource
	private UserLikeBlogService userLikeBlogService;

	@Resource
	private UserCollectBlogService userCollectBlogService;

	@GetMapping("/like")
	public RestResult<BlogListVO> getLikeList(@RequestParam(defaultValue = "1") int page, @RequestHeader Integer id) {
		BlogListVO blogList = userLikeBlogService.getLikeBlogList(id, page, pageSize);
		return new RestResult<>(blogList);
	}

	@GetMapping("/collect")
	public RestResult<BlogListVO> getCollectList(@RequestParam(defaultValue = "1") int page, @RequestHeader Integer id) {
		BlogListVO blogList = userCollectBlogService.getCollectBlogList(id, page, pageSize);
		return new RestResult<>(blogList);
	}

}
