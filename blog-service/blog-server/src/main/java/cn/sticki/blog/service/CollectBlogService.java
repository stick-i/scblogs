package cn.sticki.blog.service;

import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.pojo.CollectBlog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CollectBlogService extends IService<CollectBlog> {

	/**
	 * 收藏博客
	 *
	 * @param userId 用户id
	 * @param blogId 博客id
	 * @return 收藏状态（false为取消收藏
	 */
	boolean collectBlog(Integer userId, Integer blogId);

	/**
	 * 获取博客的收藏数量
	 *
	 * @param blogId 博客id
	 * @return 数量
	 */
	Long getCollectNum(Integer blogId);

	/**
	 * 获取用户收藏的博客列表
	 *
	 * @param userId   用户id
	 * @param page     第几页
	 * @param pageSize 页大小
	 */
	BlogListVO getCollectBlogList(Integer userId, int page, int pageSize);

}
