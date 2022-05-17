package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserFollow {

	/**
	 * id
	 */
	Integer id;

	/**
	 * 粉丝id
	 */
	Integer fansId;

	/**
	 * 关注者id
	 */
	Integer followId;

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

}
