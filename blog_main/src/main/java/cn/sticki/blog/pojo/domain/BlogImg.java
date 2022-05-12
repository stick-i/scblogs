package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogImg {

	Integer id;

	String img;

	Integer visit;

	Timestamp modified_time;

	Timestamp create_time;

}
