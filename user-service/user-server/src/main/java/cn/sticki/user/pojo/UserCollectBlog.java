package cn.sticki.user.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserCollectBlog {

	/**
	 * id
	 */
	Integer id;

	/**
	 * 博客id
	 */
	Integer blogId;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

}
