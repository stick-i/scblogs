package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.systemException.DAOException;
import cn.sticki.blog.mapper.BlogContentMapper;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogContent;
import cn.sticki.blog.pojo.domain.BlogCount;
import cn.sticki.blog.pojo.dto.BlogCountDTO;
import cn.sticki.blog.pojo.dto.BlogSaveDTO;
import cn.sticki.blog.service.BlogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private BlogContentMapper blogContentMapper;

	@Override
	public void saveBlog(BlogSaveDTO blogDTO) throws UserException, DAOException {
		log.debug("saveBlog, id->{}", blogDTO.getId());
		Blog blog = new Blog();
		blog.setAuthor(blogDTO.getAuthor());
		blog.setTitle(blogDTO.getTitle());
		blog.setDescription(blogDTO.getDescription());
		blog.setStatus(blogDTO.getStatus());

		BlogContent blogContent = new BlogContent();
		blogContent.setContent(blogDTO.getContent());

		if (blogDTO.getId() != null) {
			// 博客是已经存在的，直接进行更新操作，需要先进行作者身份核实
			LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Blog::getId, blogDTO.getId()).eq(Blog::getAuthor, blogDTO.getAuthor());
			Long count = blogMapper.selectCount(wrapper);
			if (count != 1) {
				// 身份核实失败，拒绝操作
				log.debug("blogId is false,refuse update,id->{}", blogDTO.getId());
				throw new UserException("非法操作");
			}
			// 身份核实完毕，可以更新数据库，设置条件
			if (blogDTO.getStatus() != null || blogDTO.getDescription() != null || blogDTO.getTitle() != null) {
				LambdaUpdateWrapper<Blog> blogUW = new LambdaUpdateWrapper<>();
				blogUW.eq(Blog::getId, blogDTO.getId());
				blogUW.set(blogDTO.getStatus() != null, Blog::getStatus, blogDTO.getStatus());
				blogUW.set(blogDTO.getDescription() != null, Blog::getDescription, blogDTO.getDescription());
				blogUW.set(blogDTO.getTitle() != null, Blog::getTitle, blogDTO.getTitle());
				blogMapper.update(blog, blogUW);
			}
			if (blogDTO.getContent() != null) {
				LambdaUpdateWrapper<BlogContent> blogContentUW = new LambdaUpdateWrapper<>();
				blogContentUW.eq(BlogContent::getBlogId, blogDTO.getId());
				blogContentUW.set(BlogContent::getContent, blogDTO.getContent());
				blogContentMapper.update(blogContent, blogContentUW);
			}
			return;
		}
		// 博客不存在，此处应新建博客，并保存
		// todo 博客时间有问题，需要修改
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		blog.setCreateTime(timestamp);
		// 添加发表时间，若未发表则不添加
		if (blogDTO.getStatus() != 2) blog.setReleaseTime(timestamp);
		// 插入数据库
		if (blogMapper.insert(blog) != 1)
			throw new DAOException();
		log.debug("insert blog success,id->{}", blog.getId());
		// 新建博客内容
		blogContent.setBlogId(blog.getId());
		if (blogContentMapper.insert(blogContent) != 1) {
			blogMapper.deleteById(blog.getId());
			throw new DAOException();
		}
	}

	@Override
	public BlogCountDTO getBlogCount(String author) {
		List<BlogCount> blogCountList = blogMapper.selectBlogCountListByAuthor(author);
		BlogCountDTO blogCountDTO = new BlogCountDTO();
		// 0表示全部，1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中
		int all = 0;
		for (BlogCount count : blogCountList) {
			switch (count.getStatus()) {
				case 0:
					blogCountDTO.setAll(count.getNumber());
					break;
				case 1:
					blogCountDTO.setPublish(count.getNumber());
					break;
				case 2:
					blogCountDTO.setDraft(count.getNumber());
					break;
				case 3:
					blogCountDTO.setPersonal(count.getNumber());
					break;
				case 4:
					blogCountDTO.setDeleted(count.getNumber());
					break;
				case 5:
					blogCountDTO.setAudit(count.getNumber());
					break;
			}
			all += count.getNumber();
		}
		blogCountDTO.setAll(all);
		return blogCountDTO;
	}

	@Override
	public Blog getBlog(int id) {
		return blogMapper.selectById(id);
	}

}
