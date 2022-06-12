package cn.sticki.user.pojo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 阿杆
 */
@Data
public class FansView {

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
	 * 粉丝院校代码
	 */
	Integer schoolCode;

	/**
	 * 粉丝头像链接
	 */
	String avatarUrl;

	/**
	 * 粉丝注册时间
	 */
	Timestamp registerTime;

}
