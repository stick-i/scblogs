package cn.sticki.blog.service;

public interface BlogActionService {

	/**
	 * 点赞博客，若点赞不存在，则添加，若已存在，则取消点赞
	 *
	 * @param userId 用户id
	 * @param blogId 博客id
	 * @return 点赞状态（false意为未点赞，即取消点赞
	 */
	boolean likeBlog(Integer userId, Integer blogId);

	/**
	 * 收藏博客
	 *
	 * @param userId 用户id
	 * @param blogId 博客id
	 * @return 收藏状态（false为取消收藏
	 */
	boolean collectBlog(Integer userId, Integer blogId);

	Long getLikeNum(Integer blogId);

	Long getCollectNum(Integer blogId);

}
