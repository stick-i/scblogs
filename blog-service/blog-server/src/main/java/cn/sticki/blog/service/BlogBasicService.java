package cn.sticki.blog.service;

import cn.sticki.blog.pojo.BlogBasic;
import cn.sticki.blog.pojo.BlogListVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BlogBasicService extends IService<BlogBasic> {

	/**
	 * 获取推荐博客列表
	 */
	BlogListVO getRecommendBlogList(int page, int pageSize);

	/**
	 * 获取搜索博客列表
	 *
	 * @param search 搜索值
	 */
	BlogListVO searchBlog(String search, int page, int pageSize);

	/**
	 * 获取博客内容 todo 临时放一下
	 *
	 * @param id 博客id
	 */
	void getBlogContent(int id);

}
