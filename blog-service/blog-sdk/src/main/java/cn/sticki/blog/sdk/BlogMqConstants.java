package cn.sticki.blog.sdk;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/25 21:59
 */
public class BlogMqConstants {

	/**
	 * 博客服务交互机
	 */
	public static final String BLOG_EXCHANGE = "blog";

	/**
	 * 博客插入
	 */
	public static final String BLOG_INSERT_KEY = "blog.insert";

	/**
	 * 博客更新
	 */
	public static final String BLOG_UPDATE_KEY = "blog.update";

	/**
	 * 博客删除
	 */
	public static final String BLOG_DELETE_KEY = "blog.delete";

	/**
	 * 用户浏览博客
	 */
	public static final String BLOG_OPERATE_READ_KEY = "blog.read";

	/**
	 * 用户点赞博客
	 */
	public static final String BLOG_OPERATE_LIKE_KEY = "blog.like";

	/**
	 * 用户取消点赞博客
	 */
	public static final String BLOG_OPERATE_LIKE_CANCEL_KEY = "blog.like.cancel";

	/**
	 * 用户收藏博客
	 */
	public static final String BLOG_OPERATE_COLLECT_KEY = "blog.collect";

	/**
	 * 用户收藏博客
	 */
	public static final String BLOG_OPERATE_COLLECT_CANCEL_KEY = "blog.collect.cancel";

	/**
	 * 用户转发博客
	 */
	public static final String BLOG_OPERATE_RELAY_KEY = "blog.relay";

}
