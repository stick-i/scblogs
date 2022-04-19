package cn.sticki.blog.service;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.pojo.domain.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BlogService extends IService<Blog> {

	void saveBlog(Blog blog) throws UserException;

	Blog getBlog(int id);

}
