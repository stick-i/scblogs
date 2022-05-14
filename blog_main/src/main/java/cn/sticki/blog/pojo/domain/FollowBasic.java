package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FollowBasic {

	Integer id;

	Integer userId;

	Integer followId;

	String note;

	Integer status;

	Timestamp createTime;

	String username;

	String nickname;

	String avatarUrl;

	Timestamp registerTime;

}
