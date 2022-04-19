package cn.sticki.blog.pojo.dto;

import lombok.Data;

@Data
public class BlogCountDTO {

	int all; // 全部的

	int publish; // 公开的，已发表的

	int draft; // 未发表的（草稿箱）

	int personal; // 私有的，仅自己可见

	int deleted; // 被删除的（回收站里的）

	int audit; // 审核中的

	// （1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）

}
