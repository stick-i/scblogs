package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FollowBasic {

	/**
	 * id
	 */
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 关注者id
	 */
	Integer followId;

	/**
	 * 备注
	 */
	String note;

	/**
	 * 关注状态码
	 */
	Integer status;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

	/**
	 * 关注者用户名
	 */
	String username;

	/**
	 * 关注者昵称
	 */
	String nickname;

	/**
	 * 关注者头像链接
	 */
	String avatarUrl;

	/**
	 * 关注者注册时间
	 */
	Timestamp registerTime;

}
