package cn.sticki.blog.service;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.pojo.domain.Blog;

public interface BlogService {

	void saveBlog(Blog blog) throws UserException;

	Blog getBlog(int id);

}
