package cn.sticki.blog.dto;

import lombok.Data;

/**
 * @author durance
 */
@Data
public class BlogGeneralDTO {

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 博客id
	 */
	Integer blogId;

	/**
	 * 浏览量
	 */
	Integer viewNum;

	/**
	 * 点赞量
	 */
	Integer likeNum;

	/**
	 * 评论量
	 */
	Integer commentNum;

	/**
	 * 收藏量
	 */
	Integer collectionNum;

}
