package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentBasic {

	Integer id; // 评论id

	Integer blogId; // 博客id

	Integer userId; // 发表评论的用户id

	String nickname; // 用户昵称

	String avatarUrl; // 头像链接

	String content; // 评论内容

	Timestamp createTime; // 发表时间

	Integer parentId; // 父评论id

	Integer parentUserId; // 回复的用户id

	String parentNickname; // 回复的用户昵称

}
