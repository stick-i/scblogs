package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogUserGeneral;
import cn.sticki.blog.pojo.vo.BlogContentVO;
import cn.sticki.blog.pojo.vo.BlogStatusListVO;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.service.BlogViewService;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.result.RestResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取博客信息的相关接口
 *
 * @author 阿杆
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
	@GetMapping("/list/recommend")
	public RestResult<BlogStatusListVO> getRecommendBlog(
			@RequestParam(defaultValue = "1") int page,
			@RequestHeader(required = false) Integer id) {
		log.debug("searchBlog,page->{}", page);
		BlogStatusListVO recommendBlogList = blogViewService.getRecommendBlogList(id, page, pageSize);
		return new RestResult<>(recommendBlogList);
	}

	/**
	 * 获取最新博客列表
	 */
	@GetMapping("/list/new")
	public RestResult<BlogStatusListVO> getNewBlog(
			@RequestParam(defaultValue = "1") int page,
			@RequestHeader(required = false) Integer id,
			@CookieValue(required = false) Integer schoolCode) {
		log.debug("searchBlog,page->{}", page);
		BlogStatusListVO recommendBlogList = blogViewService.getNewBlogList(id, schoolCode, page, pageSize);
		return new RestResult<>(recommendBlogList);
	}

	/**
	 * 获取关注用户发表的博客列表，这个必须得登录才行
	 */
	@GetMapping("/list/follow")
	public RestResult<BlogStatusListVO> getFollowBlog(
			@RequestParam(defaultValue = "1") int page,
			@RequestHeader Integer id) {
		log.debug("searchBlog,page->{}", page);
		BlogStatusListVO recommendBlogList = blogViewService.getFollowBlogList(id, page, pageSize);
		return new RestResult<>(recommendBlogList);
	}

	/**
	 * 获取博客内容
	 *
	 * @param id 博客id
	 */
	@GetMapping("/detail")
	public RestResult<BlogContentVO> getBlogContentVO(@RequestParam Integer id, @RequestHeader(value = "id", required = false) Integer userId) {
		BlogContentVO blogContent = blogViewService.getBlogContentHtml(id, userId);
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

	/**
	 * 获取用户所发表的博客各项数据
	 *
	 * @param userIds 用户id
	 * @return 用户发表博客数据统计
	 */
	@GetMapping("/general")
	public RestResult<List<BlogUserGeneral>> getBlogUserGeneral(@RequestParam Integer[] userIds) {
		List<BlogUserGeneral> userBlogGeneral = blogService.getUserBlogGeneral(userIds);
		return new RestResult<>(userBlogGeneral);
	}

}
