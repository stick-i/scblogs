package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 博客点赞信息类
 */
@Data
public class LikeBlog {

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
