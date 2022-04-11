package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogContent {

	Integer blogId; // '博客id'

	String content; // '博客内容'

	Timestamp createTime; //  '创建时间'

	Timestamp modifiedTime; // '修改时间'

}
