package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserFollow {

	Integer id;

	Integer fansId;

	Integer followId;

	String note;

	Integer status;

	Timestamp createTime;

}
