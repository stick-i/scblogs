package cn.sticki.user.pojo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 阿杆
 */
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
	 * 状态码 0->未关注;1->已关注
	 */
	Integer status;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

}
