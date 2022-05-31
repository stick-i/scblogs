package cn.sticki.comment.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentView {

	/**
	 * 评论id
	 */
	Integer id;

	/**
	 * 博客id
	 */
	Integer blogId;

	/**
	 * 发表评论的用户id
	 */
	Integer userId;

	/**
	 * 用户昵称
	 */
	String nickname;

	/**
	 * 头像链接
	 */
	String avatarUrl;

	/**
	 * 评论内容
	 */
	String content;

	/**
	 * 发表时间
	 */
	Timestamp createTime;

	/**
	 * 父评论id
	 */
	Integer parentId;

	/**
	 * 回复的用户id
	 */
	Integer parentUserId;

	/**
	 * 回复的用户昵称
	 */
	String parentNickname;

}
