package cn.sticki.blog.pojo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 博客收藏信息类
 */
@Data
public class CollectBlog {

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
