package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FansBasic {

	Integer id;

	Integer userId;

	Integer fansId;

	String note;

	Integer status;

	Timestamp createTime;

	String username;

	String nickname;

	String avatarUrl;

	Timestamp registerTime;

}
