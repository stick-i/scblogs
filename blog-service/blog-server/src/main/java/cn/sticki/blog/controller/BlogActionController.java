package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.service.CollectBlogService;
import cn.sticki.blog.service.LikeBlogService;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户对博客的行为相关接口
 */
@Slf4j
@RestController
@RequestMapping("/blog/action")
public class BlogActionController {

	@Resource
	private LikeBlogService likeBlogService;

	@Resource
	private CollectBlogService collectBlogService;

	private final int pageSize = 20;

	/**
	 * 点赞博客
	 *
	 * @param blogId 博客id
	 * @param id     用户id
	 */
	@PostMapping("/like")
	public RestResult<Object> likeBlog(@NotNull Integer blogId, @RequestHeader int id) {
		boolean status = likeBlogService.likeBlog(id, blogId);
		Map<String, Object> map = new HashMap<>();
		map.put("num", likeBlogService.getLikeNum(blogId));
		map.put("status", status);
		return new RestResult<>(map);
	}

	/**
	 * 收藏博客
	 *
	 * @param blogId 博客id
	 * @param id     用户id
	 */
	@PostMapping("/collect")
	public RestResult<Object> collectBlog(@NotNull Integer blogId, @RequestHeader int id) {
		boolean status = collectBlogService.collectBlog(id, blogId);
		Map<String, Object> map = new HashMap<>();
		map.put("num", collectBlogService.getCollectNum(blogId));
		map.put("status", status);
		return new RestResult<>(map);
	}

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
