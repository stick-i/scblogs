package cn.sticki.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.exception.BlogException;
import cn.sticki.blog.pojo.*;
import cn.sticki.blog.service.BlogBasicService;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.result.RestResult;
import cn.sticki.resource.type.FileType;
import cn.sticki.resource.utils.FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 博客控制台相关接口
 */
@Slf4j
@RestController
@RequestMapping("/blog/console")
public class BlogConsoleController {

	@Resource
	private BlogService blogService;

	@Resource
	private BlogBasicService blogBasicService;

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
		if (pageSize > 200 || pageSize < 1 || status > 10 || status < 0) return new RestResult<>(400, "参数异常");
		// 获取博客列表
		LambdaQueryWrapper<BlogBasic> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(BlogBasic::getAuthorId, id);
		// 若status为0，则查找显示全部博客，否则查找某部分博客
		if (status != 0) wrapper.eq(BlogBasic::getStatus, status);
		// 使用mybatis进行分页
		IPage<BlogBasic> blogIPage = new Page<>(page, pageSize);
		blogBasicService.page(blogIPage, wrapper);
		BlogListConsoleVO blogListConsoleVO = BeanUtil.copyProperties(blogIPage, BlogListConsoleVO.class);
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
	public RestResult<Object> saveBlog(BlogSaveDTO blog, MultipartFile coverImage, @RequestHeader Integer id) {
		blog.setCoverImageFile(coverImage);
		blog.setAuthorId(id);
		// 如果为新增博客，则需要全部参数
		if (blog.getId() == null && (blog.getContent() == null || blog.getTitle() == null || blog.getDescription() == null || blog.getStatus() == null))
			return new RestResult<>(400, "参数异常");
		// 如果是修改博客，则需要有至少一个参数
		if (blog.getId() != null && blog.getContent() == null && blog.getTitle() == null && blog.getDescription() == null && blog.getStatus() == null)
			return new RestResult<>(400, "参数异常");
		// 检查封面图
		if (FileUtils.isNotEmpty(blog.getCoverImageFile())) {
			FileUtils.checkFile(blog.getCoverImageFile(), 1024 * 1024L, FileType.JPEG, FileType.PNG);
		}
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
		Blog blog = blogService.getById(id);
		// 权限校验
		if (blog == null || !blog.getAuthorId().equals(userId)) throw new BlogException("非法删除他人博客");
		// 判断博客当前状态,是否已经是存在回收站里了
		if (BlogStatusType.DELETED.getValue().equals(blog.getStatus())) return new RestResult<>(400, "操作失败，博客已经存入回收站");
		// 更新数据库
		LambdaUpdateWrapper<Blog> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Blog::getId, id).eq(Blog::getAuthorId, userId).set(Blog::getStatus, BlogStatusType.DELETED.getValue());
		return new RestResult<>(blogService.update(wrapper));
	}

	/**
	 * 彻底删除博客，只有回收站中的博客可以被彻底删除
	 *
	 * @param id 博客id
	 */
	@DeleteMapping("/blog/delete")
	public RestResult<Boolean> completelyDeleteBlog(@NotNull Integer id, @RequestHeader(value = "id") Integer userId) {
		Blog blog = blogService.getById(id);
		// 权限校验，博客不是属于该用户
		if (blog == null || !blog.getAuthorId().equals(userId)) throw new BlogException("非法删除他人博客");
		// 判断博客当前状态,是否已经是存在回收站里了
		if (!BlogStatusType.DELETED.getValue().equals(blog.getStatus())) return new RestResult<>(400, "操作失败，只有在回收站里的博客可以删除");
		LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Blog::getId, id);
		// todo 这里删除博客应该连带另外两张表的数据一起删除
		return new RestResult<>(blogService.remove(wrapper));
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

}
