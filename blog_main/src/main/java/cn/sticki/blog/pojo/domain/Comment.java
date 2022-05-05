package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

	Integer id; // 评论id

	Integer userId; // 用户id

	Integer blogId; // 博客id

	String content; // 评论内容

	Integer parentId; // 父评论id

	Timestamp createTime; // 创建时间

}
