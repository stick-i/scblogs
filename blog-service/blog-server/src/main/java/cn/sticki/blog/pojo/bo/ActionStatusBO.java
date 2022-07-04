package cn.sticki.blog.pojo.bo;

import lombok.Data;

/**
 * 用户操作博客的状态，是否点赞，是否收藏
 *
 * @author 阿杆
 */
@Data
public class ActionStatusBO {

	/**
	 * 是否点赞
	 */
	Boolean isLike;

	/**
	 * 是否收藏
	 */
	Boolean isCollect;

}
