package cn.sticki.blog.service;

import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.vo.BlogContentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BlogBasicService extends IService<BlogBasic> {

	/**
	 * 获取推荐博客列表
	 */
	List<BlogBasic> getRecommendBlogList(int page, int pageSize);

	/**
	 * 获取搜索博客列表
	 *
	 * @param search 搜索值
	 */
	List<BlogBasic> searchBlog(String search, int page, int pageSize);

	/**
	 * 获取博客内容 todo 临时放一下
	 *
	 * @param id 博客id
	 */
	BlogContentVO getBlogContent(int id);

}
