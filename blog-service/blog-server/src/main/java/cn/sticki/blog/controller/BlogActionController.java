package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.service.CollectBlogService;
import cn.sticki.blog.service.LikeBlogService;
import cn.sticki.common.web.auth.AuthHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户对博客的行为相关接口
 *
 * @author 阿杆
 */
@Slf4j
@RestController
@RequestMapping("/blog/action")
@Validated
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
	 */
	@PostMapping("/like")
	public Map<String, Object> likeBlog(@NotNull Integer blogId) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		boolean status = likeBlogService.likeBlog(id, blogId);
		Map<String, Object> map = new HashMap<>(1);
		map.put("num", likeBlogService.getLikeNum(blogId));
		map.put("status", status);
		return map;
	}

	/**
	 * 收藏博客
	 *
	 * @param blogId 博客id
	 */
	@PostMapping("/collect")
	public Map<String, Object> collectBlog(@NotNull Integer blogId) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		boolean status = collectBlogService.collectBlog(id, blogId);
		Map<String, Object> map = new HashMap<>(1);
		map.put("num", collectBlogService.getCollectNum(blogId));
		map.put("status", status);
		return map;
	}

	/**
	 * 获取点赞列表
	 *
	 * @param page 第几页（默认每页20条）
	 */
	@GetMapping("/like")
	public BlogListVO getLikeList(@RequestParam(defaultValue = "1") int page) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		return likeBlogService.getLikeBlogList(id, page, pageSize);
	}

	/**
	 * 获取收藏列表
	 *
	 * @param page 第几页（默认每页20条）
	 */
	@GetMapping("/collect")
	public BlogListVO getCollectList(@RequestParam(defaultValue = "1") int page) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		return collectBlogService.getCollectBlogList(id, page, pageSize);
	}

}
