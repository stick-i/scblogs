package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.service.CollectBlogService;
import cn.sticki.blog.service.LikeBlogService;
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
	private LikeBlogService likeBlogService;

	@Resource
	private CollectBlogService collectBlogService;

	/**
	 * 获取点赞列表
	 *
	 * @param page 第几页（默认每页20条）
	 */
	@GetMapping("/like")
	public RestResult<BlogListVO> getLikeList(@RequestParam(defaultValue = "1") int page, @RequestHeader Integer id) {
		BlogListVO blogList = likeBlogService.getLikeBlogList(id, page, pageSize);
		return new RestResult<>(blogList);
	}

	/**
	 * 获取收藏列表
	 *
	 * @param page 第几页（默认每页20条）
	 */
	@GetMapping("/collect")
	public RestResult<BlogListVO> getCollectList(@RequestParam(defaultValue = "1") int page, @RequestHeader Integer id) {
		BlogListVO blogList = collectBlogService.getCollectBlogList(id, page, pageSize);
		return new RestResult<>(blogList);
	}

}
