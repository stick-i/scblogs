package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

	@Resource
	private BlogMapper blogMapper;

	@Override
	public void saveBlog(Blog blog) throws UserException {
		// todo 在控制层获取作者id 一并传入
		Integer id = blog.getId();
		if (id == null) {
			// todo 博客不存在，此处应新建博客，并保存
			blogMapper.insert(blog);
		}
		// todo 博客是已经存在的，直接进行更新操作，需要先进行作者id核实
		Blog blog1 = blogMapper.selectById(blog.getId());
		String author = blog1.getAuthor();
		if (!blog.getAuthor().equals(author)) {
			throw new UserException("非法操作");
		}
		blogMapper.updateById(blog);
	}

	@Override
	public Blog getBlog(int id) {
		return blogMapper.selectById(id);
	}

}
