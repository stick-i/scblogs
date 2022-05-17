package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogImg {

	/**
	 * 图片id
	 */
	Integer id;

	/**
	 * 图片链接
	 */
	String img;

	/**
	 * 访问次数
	 */
	Integer visit;

	/**
	 * 修改时间
	 */
	Timestamp modified_time;

	/**
	 * 创建时间
	 */
	Timestamp create_time;

}
