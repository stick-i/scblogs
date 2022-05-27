package cn.sticki.user.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {

	/**
	 * 用户id
	 */
	Integer id;

	/**
	 * 用户名
	 */
	String username;

	/**
	 * 用户昵称
	 */
	String nickname;

	/**
	 * 头像链接
	 */
	String avatarUrl;

	/**
	 * 注册时间
	 */
	Timestamp registerTime;

}
