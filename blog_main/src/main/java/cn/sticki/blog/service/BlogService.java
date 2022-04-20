package cn.sticki.blog.service;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.systemException.DAOException;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.dto.BlogCountDTO;
import cn.sticki.blog.pojo.dto.BlogSaveDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BlogService extends IService<Blog> {

	/**
	 * 保存博客
	 *
	 * @param blog 博客
	 */
	void saveBlog(BlogSaveDTO blog) throws UserException, DAOException;

	BlogCountDTO getBlogCount(String author);

	Blog getBlog(int id);

}
