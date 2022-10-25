package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.bo.BlogSaveBO;
import cn.sticki.blog.pojo.domain.BlogContent;
import cn.sticki.blog.pojo.domain.BlogView;
import cn.sticki.blog.pojo.vo.BlogListConsoleVO;
import cn.sticki.blog.pojo.vo.BlogStatisticsDataVO;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.service.BlogViewService;
import cn.sticki.common.result.RestResult;
import cn.sticki.resource.type.FileType;
import cn.sticki.resource.utils.FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 博客控制台相关接口
 *
 * @author 阿杆
 */
@Slf4j
@RestController
@RequestMapping("/blog/console")
public class BlogConsoleController {

	@Resource
	private BlogService blogService;

	@Resource
	private BlogViewService blogViewService;

	/**
	 * 获取创作信息
	 */
	@GetMapping("/statistics-data")
	public RestResult<BlogStatisticsDataVO> getStatisticsData() {
		return new RestResult<>(new BlogStatisticsDataVO());
	}

	/**
	 * 获取博客列表
	 *
	 * @param page     当前页
	 * @param pageSize 页大小
	 * @param status   博客状态码
	 */
	@GetMapping("/list")
	public RestResult<BlogListConsoleVO> getBlogList(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int pageSize,
			@RequestParam(defaultValue = "0") int status,
			@RequestHeader Integer id) {
		if (pageSize > 200 || pageSize < 1 || status > 10 || status < 0) {
			return new RestResult<>(400, "参数异常");
		}
		// 获取博客列表
		LambdaQueryWrapper<BlogView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(BlogView::getAuthorId, id);
		// 若status为0，则查找显示全部博客，否则查找某部分博客
		if (status != 0) {
			wrapper.eq(BlogView::getStatus, status);
		}
		// 使用mybatis进行分页
		IPage<BlogView> blogIPage = new Page<>(page, pageSize);
		blogViewService.page(blogIPage, wrapper);
		BlogListConsoleVO blogListConsoleVO = new BlogListConsoleVO();
		BeanUtils.copyProperties(blogIPage, BlogListConsoleVO.class);
		// 获取博客统计数据
		blogListConsoleVO.setCount(blogService.getBlogCount(id));
		return new RestResult<>(blogListConsoleVO);
	}

	/**
	 * 保存博客
	 *
	 * @param blog 要保存的博客内容
	 */
	@PostMapping("/blog")
	public RestResult<Object> saveBlog(BlogSaveBO blog, MultipartFile coverImage, @RequestHeader Integer id) {

		// 进入入参校验
		blog.paramCheck(true);

		// 设置其他参数，保存博客
		blog.setCoverImageFile(coverImage);
		blog.setAuthorId(id);
		blogService.saveBlog(blog);

		return new RestResult<>();
	}

	/**
	 * 删除博客，将博客放入回收站
	 *
	 * @param id 博客id
	 */
	@DeleteMapping("/blog")
	public RestResult<Boolean> recoveryBlog(@NotNull Integer id, @RequestHeader(value = "id") Integer userId) {
		return new RestResult<>(blogService.deleteBlog(id, userId));
	}

	/**
	 * 彻底删除博客，只有回收站中的博客可以被彻底删除
	 *
	 * @param id 博客id
	 */
	@DeleteMapping("/blog/delete")
	public RestResult<Boolean> completelyDeleteBlog(@NotNull Integer id, @RequestHeader(value = "id") Integer userId) {
		Boolean result = blogService.completelyDeleteBlog(id, userId);
		return new RestResult<>(result);
	}

	/**
	 * 上传图片
	 *
	 * @param file 图片文件
	 * @return 图片链接
	 */
	@PostMapping("/image")
	public RestResult<String> uploadBlogImg(@NotNull MultipartFile file, @RequestHeader Integer id) {
		log.debug("uploadBlogImg, fileName->{}, userId->{}", file.getOriginalFilename(), id);
		FileUtils.checkFile(file, 1024 * 1024L, FileType.JPEG, FileType.PNG);
		String url = blogService.uploadImage(file);
		return new RestResult<>(url);
	}

	/**
	 * 获取博客可编辑内容，用于作者编辑博客时调用
	 *
	 * @param blogId 博客id
	 */
	@GetMapping("/content")
	public RestResult<BlogContent> getBlogContent(@RequestParam Integer blogId, @RequestHeader Integer id) {
		return new RestResult<>(blogService.getBlogContent(blogId, id));
	}

}
