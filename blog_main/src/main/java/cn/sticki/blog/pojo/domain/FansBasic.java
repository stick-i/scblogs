package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FansBasic {

	/**
	 * id
	 */
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 粉丝id
	 */
	Integer fansId;

	/**
	 * 备注
	 */
	String note;

	/**
	 * 状态码
	 */
	Integer status;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

	/**
	 * 粉丝用户名
	 */
	String username;

	/**
	 * 粉丝昵称
	 */
	String nickname;

	/**
	 * 粉丝头像链接
	 */
	String avatarUrl;

	/**
	 * 粉丝注册时间
	 */
	Timestamp registerTime;

}
