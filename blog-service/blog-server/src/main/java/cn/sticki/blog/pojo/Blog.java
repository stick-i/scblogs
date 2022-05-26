package cn.sticki.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Blog {

	/**
	 * 博客id
	 */
	@TableId
	Integer id;

	/**
	 * 作者
	 */
	Integer authorId;

	/**
	 * 标题
	 */
	String title;

	/**
	 * 描述
	 */
	String description;

	/**
	 * 封面图
	 */
	String coverImage;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

	/**
	 * 发表时间
	 */
	Timestamp releaseTime;

	/**
	 * 修改时间
	 */
	Timestamp modifiedTime;

	/**
	 * 发表状态（1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）
	 */
	Integer status;

}
