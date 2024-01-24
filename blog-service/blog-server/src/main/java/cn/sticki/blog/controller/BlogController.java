package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogUserGeneral;
import cn.sticki.blog.pojo.vo.BlogContentVO;
import cn.sticki.blog.pojo.vo.BlogStatusListVO;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.service.BlogViewService;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.web.auth.AuthHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
	public BlogStatusListVO getRecommendBlog(@RequestParam(defaultValue = "1") int page) {
		log.debug("searchBlog,page->{}", page);
		Integer id = AuthHelper.getCurrentUserId();
		return blogViewService.getRecommendBlogList(id, page, pageSize);
	}

	/**
	 * 获取最新博客列表
	 */
	@GetMapping("/list/new")
	public BlogStatusListVO getNewBlog(@RequestParam(defaultValue = "1") int page, @CookieValue(required = false) Integer schoolCode) {
		log.debug("searchBlog,page->{}", page);
		Integer id = AuthHelper.getCurrentUserId();
		return blogViewService.getNewBlogList(id, schoolCode, page, pageSize);
	}

	/**
	 * 获取关注用户发表的博客列表，这个必须得登录才行
	 */
	@GetMapping("/list/follow")
	public BlogStatusListVO getFollowBlog(@RequestParam(defaultValue = "1") int page) {
		log.debug("searchBlog,page->{}", page);
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		return blogViewService.getFollowBlogList(id, page, pageSize);
	}

	/**
	 * 获取博客内容
	 *
	 * @param id 博客id
	 */
	@GetMapping("/detail")
	public BlogContentVO getBlogContentVO(@RequestParam Integer id) {
		Integer userId = AuthHelper.getCurrentUserId();
		return blogViewService.getBlogContentHtml(id, userId);
	}

	/**
	 * 获取博客基本信息，适合后端内部调用，不合适前端使用
	 *
	 * @param id 博客id
	 */
	@GetMapping("/blog")
	public Blog getBlog(@RequestParam Integer id) {
		LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Blog::getId, id).eq(Blog::getStatus, BlogStatusType.PUBLISH.getValue());
		return blogService.getOne(wrapper);
	}

	/**
	 * 获取用户所发表的博客各项数据
	 *
	 * @param userIds 用户id
	 * @return 用户发表博客数据统计
	 */
	@GetMapping("/general")
	public List<BlogUserGeneral> getBlogUserGeneral(@RequestParam Integer[] userIds) {
		return blogService.getUserBlogGeneral(userIds);
	}

}
