package cn.sticki.blog.service;

import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.pojo.UserLikeBlog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserLikeBlogService extends IService<UserLikeBlog> {

	/**
	 * 点赞博客，若点赞不存在，则添加，若已存在，则取消点赞
	 *
	 * @param userId 用户id
	 * @param blogId 博客id
	 * @return 点赞状态（false意为未点赞，即取消点赞
	 */
	boolean likeBlog(Integer userId, Integer blogId);

	Long getLikeNum(Integer blogId);

	BlogListVO getLikeBlogList(Integer userId, int page, int pageSize);

}
