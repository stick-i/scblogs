package cn.sticki.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.pojo.BlogBasic;
import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.pojo.HotBlogListVO;
import cn.sticki.blog.pojo.UserBlogActionStatusDTO;
import cn.sticki.blog.service.BlogActionService;
import cn.sticki.blog.service.BlogBasicService;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogBasicService blogBasicService;

	private final int pageSize = 20;

	@Resource
	private BlogActionService blogActionService;

	/**
	 * 获取推荐博客列表
	 *
	 * @return 博客列表
	 */
	@GetMapping("/list")
	public RestResult<HotBlogListVO> recommendBlog(
			@RequestParam(defaultValue = "1") int page,
			@RequestHeader(required = false) Integer id) {
		log.debug("searchBlog,page->{}", page);
		// todo 最好根据用户标签来推
		BlogListVO blogListVO = blogBasicService.getRecommendBlogList(page, pageSize);
		HotBlogListVO hotBlogListVO = BeanUtil.copyProperties(blogListVO, HotBlogListVO.class);
		if (id != null) {
			List<BlogBasic> blogList = blogListVO.getRecords();
			List<Integer> blogIdList = new ArrayList<>();
			for (BlogBasic blogBasic : blogList) {
				blogIdList.add(blogBasic.getId());
			}
			Map<Integer, UserBlogActionStatusDTO> userBlogActionStatus = blogActionService.getUserBlogActionStatus(id, blogIdList);
			hotBlogListVO.setUserAction(userBlogActionStatus);
		}
		return new RestResult<>(hotBlogListVO);
	}

	/**
	 * 搜索博客
	 *
	 * @param key 搜索内容
	 * @return 博客列表
	 */
	@GetMapping("/search")
	public RestResult<BlogListVO> searchBlog(@NotNull String key, @RequestParam(defaultValue = "1") int page) {
		log.debug("searchBlog,search->{},page->{}", key, page);
		BlogListVO blogListVO = blogBasicService.searchBlog(key, page, pageSize);
		return new RestResult<>(blogListVO);
	}

	/**
	 * 获取博客内容
	 *
	 * @param id 博客id
	 */
	@GetMapping("/blog")
	public RestResult<Object> getBlog(@RequestParam Integer id) {
		// todo 获取博客内容接口
		// blogBasicService.getBlogContent(id);
		return new RestResult<>();
	}

}
