package cn.sticki.blog.service;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.systemException.DAOException;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.dto.BlogSaveDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BlogService extends IService<Blog> {

	void saveBlog(BlogSaveDTO blog) throws UserException, DAOException;

	Blog getBlog(int id);

}
