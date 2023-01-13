package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 博客收藏信息类
 *
 * @author 阿杆
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

	/**
	 * 是否已经删除，0未删除，1已删除
	 */
	Integer isDeleted;

}
