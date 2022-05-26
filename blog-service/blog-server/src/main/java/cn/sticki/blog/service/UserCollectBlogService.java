package cn.sticki.blog.service;

import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.pojo.UserCollectBlog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserCollectBlogService extends IService<UserCollectBlog> {

	/**
	 * 收藏博客
	 *
	 * @param userId 用户id
	 * @param blogId 博客id
	 * @return 收藏状态（false为取消收藏
	 */
	boolean collectBlog(Integer userId, Integer blogId);

	Long getCollectNum(Integer blogId);

	BlogListVO getCollectBlogList(Integer userId, int page, int pageSize);

}
