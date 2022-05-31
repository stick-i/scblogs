package cn.sticki.blog.pojo;

import lombok.Data;

@Data
public class BlogCountBO {

	/**
	 * 全部的
	 */
	int all;

	/**
	 * 公开的，已发表的
	 */
	int publish;

	/**
	 * 未发表的（草稿箱）
	 */
	int draft;

	/**
	 * 私有的，仅自己可见
	 */
	int personal;

	/**
	 * 被删除的（回收站里的）
	 */
	int deleted;

	/**
	 * 审核中的
	 */
	int audit;

	// （1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）

}
