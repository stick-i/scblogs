package cn.sticki.blog.content.controller;

import cn.sticki.blog.content.pojo.BlogListVO;
import cn.sticki.blog.content.pojo.SearchQuery;
import cn.sticki.blog.content.service.BlogContentService;
import cn.sticki.common.result.RestResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 17:28
 */
@RestController
@RequestMapping("/blog/content")
public class BlogContentController {

	@Resource
	private BlogContentService blogContentService;

	/**
	 * 搜索博客
	 *
	 * @return 博客列表
	 */
	@GetMapping("/search")
	public RestResult<BlogListVO> searchBlog(@Validated SearchQuery searchQuery) {
		return blogContentService.searchBlog(searchQuery);
	}

}
