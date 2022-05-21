package cn.sticki.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.enumeration.type.BlogStatusType;
import cn.sticki.blog.enumeration.type.FileType;
import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.systemException.DAOException;
import cn.sticki.blog.exception.userException.UserIllegalException;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.dto.BlogSaveDTO;
import cn.sticki.blog.pojo.vo.BlogListConsoleVO;
import cn.sticki.blog.pojo.vo.BlogStatisticsDataVO;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.security.AuthenticationFacade;
import cn.sticki.blog.service.BlogBasicService;
import cn.sticki.blog.service.BlogConsoleService;
import cn.sticki.blog.util.FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/blog-console")
public class BlogConsoleController {

	@Resource
	private BlogConsoleService blogConsoleService;

	@Resource
	private BlogBasicService blogBasicService;

	@Resource
	private AuthenticationFacade authenticationFacade;

	@Resource
	private FileUtils fileUtils;

	/**
	 * 获取创作信息
	 */
	@GetMapping("/blog-statistics-data")
	public RestTemplate<BlogStatisticsDataVO> getStatisticsData() {
		return new RestTemplate<>(new BlogStatisticsDataVO());
	}

	/**
	 * 获取博客列表
	 *
	 * @param page     当前页
	 * @param pageSize 页大小
	 * @param status   博客状态码
	 */
	@GetMapping("/blog-list")
	public RestTemplate<BlogListConsoleVO> getBlogList(@RequestParam(defaultValue = "1", required = false) int page, @RequestParam(defaultValue = "20", required = false) int pageSize, @RequestParam(defaultValue = "0", required = false) int status) {
		User user = authenticationFacade.getUser();
		if (pageSize > 200 || pageSize < 1 || status > 10 || status < 0) return new RestTemplate<>(400, "参数异常");
		// 获取博客列表
		LambdaQueryWrapper<BlogBasic> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(BlogBasic::getAuthor, user.getUsername());
		// 若status为0，则查找显示全部博客，否则查找某部分博客
		if (status != 0) wrapper.eq(BlogBasic::getStatus, status);
		// 使用mybatis进行分页
		IPage<BlogBasic> blogIPage = new Page<>(page, pageSize);
		blogBasicService.page(blogIPage, wrapper);
		BlogListConsoleVO blogListConsoleVO = BeanUtil.copyProperties(blogIPage, BlogListConsoleVO.class);
		// 获取博客统计数据
		blogListConsoleVO.setCount(blogConsoleService.getBlogCount(user.getUsername()));
		return new RestTemplate<>(blogListConsoleVO);
	}

	/**
	 * 保存博客
	 *
	 * @param blog 要保存的博客内容
	 */
	@PostMapping("/blog")
	public RestTemplate<Object> saveBlog(BlogSaveDTO blog, MultipartFile coverImage) throws UserException, DAOException {
		User user = authenticationFacade.getUser();
		blog.setCoverImageFile(coverImage);
		blog.setAuthor(user.getUsername());
		// 如果为新增博客，则需要全部参数
		if (blog.getId() == null && (blog.getContent() == null || blog.getTitle() == null || blog.getDescription() == null || blog.getStatus() == null))
			return new RestTemplate<>(400, "参数异常");
		// 如果是修改博客，则需要有至少一个参数
		if (blog.getId() != null && blog.getContent() == null && blog.getTitle() == null && blog.getDescription() == null && blog.getStatus() == null)
			return new RestTemplate<>(400, "参数异常");
		// 检查封面图
		if (fileUtils.isNotEmpty(blog.getCoverImageFile())) {
			fileUtils.checkFile(blog.getCoverImageFile(), 1024 * 1024L, FileType.JPEG, FileType.PNG);
		}
		blogConsoleService.saveBlog(blog);
		return new RestTemplate<>();
	}

	/**
	 * 删除博客，将博客放入回收站
	 *
	 * @param id 博客id
	 */
	@DeleteMapping("/blog")
	public RestTemplate<Boolean> recoveryBlog(@NotNull Integer id) throws UserIllegalException {
		User user = authenticationFacade.getUser();
		Blog blog = blogConsoleService.getById(id);
		// 权限校验
		if (blog == null || !blog.getAuthor().equals(user.getUsername())) throw new UserIllegalException();
		// 判断博客当前状态,是否已经是存在回收站里了
		if (BlogStatusType.DELETED.getValue().equals(blog.getStatus())) return new RestTemplate<>(400, "操作失败，博客已经存入回收站");
		// 更新数据库
		LambdaUpdateWrapper<Blog> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Blog::getId, id).set(Blog::getStatus, BlogStatusType.DELETED.getValue());
		return new RestTemplate<>(blogConsoleService.update(wrapper));
	}

	/**
	 * 彻底删除博客，只有回收站中的博客可以被彻底删除
	 *
	 * @param id 博客id
	 */
	@DeleteMapping("/blog/delete")
	public RestTemplate<Boolean> completelyDeleteBlog(@NotNull Integer id) throws UserIllegalException {
		User user = authenticationFacade.getUser();
		Blog blog = blogConsoleService.getById(id);
		// 权限校验，博客不是属于该用户
		if (blog == null || !blog.getAuthor().equals(user.getUsername())) throw new UserIllegalException();
		// 判断博客当前状态,是否已经是存在回收站里了
		if (!BlogStatusType.DELETED.getValue().equals(blog.getStatus())) return new RestTemplate<>(400, "操作失败，只有在回收站里的博客可以删除");
		LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Blog::getId, id);
		// todo 这里删除博客应该连带另外两张表的数据一起删除
		return new RestTemplate<>(blogConsoleService.remove(wrapper));
	}

	/**
	 * 上传图片
	 *
	 * @param file 图片文件
	 * @return 图片链接
	 */
	@PostMapping("/img")
	public RestTemplate<String> uploadBlogImg(@NotNull MultipartFile file) {
		log.debug("uploadBlogImg, fileName->{}, userId->{}", file.getOriginalFilename(), authenticationFacade.getUser().getId());
		if (fileUtils.isNotEmpty(file)) {
			fileUtils.checkFile(file, 1024 * 1024L, FileType.JPEG, FileType.PNG);
		}
		String url = blogConsoleService.uploadImage(file);
		return new RestTemplate<>(url);
	}

}
