package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

	/**
	 * 评论id
	 */
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 博客id
	 */
	Integer blogId;

	/**
	 * 评论内容
	 */
	String content;

	/**
	 * 父评论id
	 */
	Integer parentId;

	/**
	 * 回复的用户id
	 */
	Integer parentUserId;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

}
