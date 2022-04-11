package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {

	Integer id;

	String username;

	String nickname;

	String avatarUrl;

	Timestamp registerTime;

}
