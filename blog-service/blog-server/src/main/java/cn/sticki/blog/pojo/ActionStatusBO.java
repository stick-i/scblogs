package cn.sticki.blog.pojo;

import lombok.Data;

/**
 * 用户操作博客的状态，是否点赞，是否收藏
 */
@Data
public class ActionStatusBO {

	/**
	 * 是否点赞
	 */
	boolean isLike;

	/**
	 * 是否收藏
	 */
	boolean isCollect;

}
