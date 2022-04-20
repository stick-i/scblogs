package cn.sticki.blog.controller;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.systemException.DAOException;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.dto.BlogSaveDTO;
import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.pojo.vo.BlogStatisticsDataVO;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.type.BlogStatusType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/blog-console")
public class BlogConsoleController {

	@Resource
	private BlogService blogService;

	@Autowired
	private User user;

	/**
	 * 获取创作信息
	 */
	@GetMapping("/blog-statistics-data")
	public RestTemplate getStatisticsData() {
		return new RestTemplate(new BlogStatisticsDataVO());
	}

	/**
	 * 获取博客列表
	 *
	 * @param page     当前页
	 * @param pageSize 页大小
	 * @param status   博客状态码
	 */
	@GetMapping("/blog-list")
	public RestTemplate getBlogList(
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(defaultValue = "20", required = false) int pageSize,
			@RequestParam(defaultValue = "0", required = false) int status) {
		if (pageSize > 200 || pageSize < 1 || status > 10 || status < 0)
			return new RestTemplate(400, "参数异常");
		BlogListVO blogListVO = new BlogListVO();
		// 获取博客统计数据
		blogListVO.setCount(blogService.getBlogCount(user.getUsername()));
		// 获取博客列表
		LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Blog::getAuthor, user.getUsername());
		// 若status为0，则查找显示全部博客，否则查找某部分博客
		if (status != 0) wrapper.eq(Blog::getStatus, status);
		// 使用mybatis进行分页
		IPage<Blog> blogIPage = new Page<>(page, pageSize);
		blogService.page(blogIPage, wrapper);
		blogListVO.setBlogList(blogIPage.getRecords());
		blogListVO.setTotal((int) blogIPage.getTotal());
		blogListVO.setPage(page);
		blogListVO.setPageSize(pageSize);
		return new RestTemplate(blogListVO);
		// return new RestTemplate();
	}

	/**
	 * 保存博客
	 *
	 * @param blog 要保存的博客内容
	 */
	@PostMapping("/blog")
	public RestTemplate saveBlog(BlogSaveDTO blog) throws UserException, DAOException {
		// 如果为新增博客，则需要全部参数
		if (blog.getId() == null && (blog.getContent() == null || blog.getTitle() == null || blog.getDescription() == null || blog.getStatus() == null))
			return new RestTemplate(400, "参数异常");
		// 如果是修改博客，则需要有至少一个参数
		if (blog.getId() != null && blog.getContent() == null && blog.getTitle() == null && blog.getDescription() == null && blog.getStatus() == null)
			return new RestTemplate(400, "参数异常");
		blog.setAuthor(user.getUsername());
		blogService.saveBlog(blog);
		return new RestTemplate();
	}

	/**
	 * 删除博客，将博客放入回收站
	 *
	 * @param id 博客id
	 */
	@DeleteMapping("/blog")
	public RestTemplate recoveryBlog(@NotNull Integer id) {
		Blog blog = blogService.getBlog(id);
		// 权限校验
		if (blog == null || !blog.getAuthor().equals(user.getUsername()))
			return new RestTemplate(402, "非法操作！");
		// 判断博客当前状态,是否已经是存在草稿箱里了
		if (BlogStatusType.DELETED.getValue().equals(blog.getStatus()))
			return new RestTemplate(400, "操作失败，博客已经存入回收站");
		// 更新数据库
		LambdaUpdateWrapper<Blog> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Blog::getId, id).set(Blog::getStatus, BlogStatusType.DELETED.getValue());
		return new RestTemplate(blogService.update(wrapper));
	}

	/**
	 * 彻底删除博客，只有回收站中的博客可以被彻底删除
	 *
	 * @param id 博客id
	 */
	@DeleteMapping("/blog/delete")
	public RestTemplate completelyDeleteBlog(int id) {
		return new RestTemplate();
	}

}
