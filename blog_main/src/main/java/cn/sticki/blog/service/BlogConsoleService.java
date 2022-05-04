package cn.sticki.blog.service;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.systemException.DAOException;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.dto.BlogCountDTO;
import cn.sticki.blog.pojo.dto.BlogSaveDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface BlogConsoleService extends IService<Blog> {

	/**
	 * 保存博客
	 *
	 * @param blog 博客
	 */
	void saveBlog(BlogSaveDTO blog) throws UserException, DAOException;

	/**
	 * 获取作者博客数据
	 *
	 * @param author 作者用户名
	 */
	BlogCountDTO getBlogCount(String author);

	/**
	 * 上传封面图
	 *
	 * @param name       存储的文件名
	 * @param coverImage 封面图
	 */
	void uploadCoverImage(String name, MultipartFile coverImage);

}
