package cn.sticki.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.dto.UserBlogActionStatusDTO;
import cn.sticki.blog.pojo.vo.*;
import cn.sticki.blog.security.AuthenticationFacade;
import cn.sticki.blog.service.BlogActionService;
import cn.sticki.blog.service.BlogBasicService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	private AuthenticationFacade authenticationFacade;

	@Resource
	private BlogActionService blogActionService;

	/**
	 * 获取推荐博客列表
	 *
	 * @return 博客列表
	 */
	@GetMapping("/list")
	public RestTemplate recommendBlog(@RequestParam(defaultValue = "1") int page) {
		log.debug("searchBlog,page->{}", page);
		// todo 最好根据用户标签来推
		BlogListVO blogListVO = blogBasicService.getRecommendBlogList(page, pageSize);
		HotBlogListVO hotBlogListVO = BeanUtil.copyProperties(blogListVO, HotBlogListVO.class);
		User user = authenticationFacade.getUser();
		if (user != null) {
			List<BlogBasic> blogList = blogListVO.getRecords();
			List<Integer> blogIdList = new ArrayList<>();
			for (BlogBasic blogBasic : blogList) {
				blogIdList.add(blogBasic.getId());
			}
			Map<Integer, UserBlogActionStatusDTO> userBlogActionStatus = blogActionService.getUserBlogActionStatus(user.getId(), blogIdList);
			hotBlogListVO.setUserAction(userBlogActionStatus);
		}
		return new RestTemplate(hotBlogListVO);
	}

	/**
	 * 搜索博客
	 *
	 * @param key 搜索内容
	 * @return 博客列表
	 */
	@GetMapping("/search")
	public RestTemplate searchBlog(@NotNull String key, @RequestParam(defaultValue = "1") int page) {
		log.debug("searchBlog,search->{},page->{}", key, page);
		BlogListVO blogListVO = blogBasicService.searchBlog(key, page, pageSize);
		IListVO<BlogBasic> blogList = new ListVO<>();
		blogList.setRecords(blogListVO.getRecords());
		return new RestTemplate(blogList);
	}

	/**
	 * 获取博客内容
	 *
	 * @param id 博客id
	 */
	@GetMapping("/blog")
	public RestTemplate getBlog(@RequestParam Integer id) {
		return new RestTemplate(blogBasicService.getBlogContent(id));
	}

}
