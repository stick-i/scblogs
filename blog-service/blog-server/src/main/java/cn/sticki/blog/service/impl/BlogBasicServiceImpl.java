package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.mapper.BlogBasicMapper;
import cn.sticki.blog.pojo.BlogBasic;
import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.service.BlogBasicService;
import cn.sticki.blog.type.BlogStatusType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class BlogBasicServiceImpl extends ServiceImpl<BlogBasicMapper, BlogBasic> implements BlogBasicService {

	@Resource
	private BlogBasicMapper blogBasicMapper;

	@Override
	public BlogListVO getRecommendBlogList(int page, int pageSize) {
		return searchBlog(null, page, pageSize);
	}

	@Override
	public BlogListVO searchBlog(String search, int page, int pageSize) {
		// 设置查询条件：公开的，并以得分排序
		LambdaQueryWrapper<BlogBasic> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(BlogBasic::getStatus, BlogStatusType.PUBLISH.getValue()).orderByDesc(BlogBasic::getScore);
		// 设置模糊查询
		wrapper.like(search != null, BlogBasic::getTitle, search);
		// 设置分页
		IPage<BlogBasic> iPage = new Page<>(page, pageSize);
		blogBasicMapper.selectPage(iPage, wrapper);
		return BeanUtil.copyProperties(iPage, BlogListVO.class);
	}

	@Override
	public void getBlogContent(int id) {
	}

	// @Override todo 先空着
	// public BlogContentVO getBlogContent(int id) {
	// 	BlogContentVO blog = new BlogContentVO();
	// 	blog.setInfo(blogBasicMapper.selectById(id));
	// 	blog.setContent(blogContentMapper.selectById(id));
	// 	blog.setAuthor(userMapper.selectByUsername(blog.getInfo().getAuthor()));
	// 	blog.setComment(commentService.getList(id, 1, 3));
	// 	return blog;
	// }

}
