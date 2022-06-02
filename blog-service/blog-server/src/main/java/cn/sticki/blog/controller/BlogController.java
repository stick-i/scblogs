package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.vo.BlogContentVO;
import cn.sticki.blog.pojo.vo.BlogInfoListVO;
import cn.sticki.blog.pojo.vo.BlogStatusListVO;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.service.BlogViewService;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.result.RestResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 获取博客信息的相关接口
 */
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;

	@Resource
	private BlogViewService blogViewService;

	private final int pageSize = 20;

	/**
	 * 获取推荐博客列表
	 *
	 * @return 博客列表
	 */
	@GetMapping("/list")
	public RestResult<BlogStatusListVO> recommendBlog(
			@RequestParam(defaultValue = "1") int page,
			@RequestHeader(required = false) Integer id) {
		log.debug("searchBlog,page->{}", page);
		BlogStatusListVO recommendBlogList = blogViewService.getRecommendBlogList(id, page, pageSize);
		return new RestResult<>(recommendBlogList);
	}

	/**
	 * 搜索博客
	 *
	 * @param key 搜索内容
	 * @return 博客列表
	 */
	@GetMapping("/search")
	public RestResult<BlogInfoListVO> searchBlog(@NotNull String key, @RequestParam(defaultValue = "1") int page) {
		log.debug("searchBlog,search->{},page->{}", key, page);
		return new RestResult<>(blogViewService.searchBlog(key, page, pageSize));
	}

	/**
	 * 获取博客内容
	 *
	 * @param id 博客id
	 */
	@GetMapping("/content")
	public RestResult<BlogContentVO> getBlogContentVO(@RequestParam Integer id, @RequestHeader(value = "id", required = false) Integer userId) {
		BlogContentVO blogContent = blogViewService.getBlogContent(id, userId);
		return new RestResult<>(blogContent);
	}

	/**
	 * 获取博客基本信息，适合后端内部调用，不合适前端使用
	 *
	 * @param id 博客id
	 */
	@GetMapping("/blog")
	public RestResult<Blog> getBlog(@RequestParam Integer id) {
		LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Blog::getId, id).eq(Blog::getStatus, BlogStatusType.PUBLISH.getValue());
		return new RestResult<>(blogService.getOne(wrapper));
	}

}
