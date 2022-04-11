package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Blog {

	Integer id; //  COMMENT '博客id'

	String author; //  COMMENT '作者'

	String title; //  COMMENT '标题'

	String description; //  COMMENT '描述'

	Timestamp createTime; //  CURRENT_TIMESTAMP(6) COMMENT '创建时间'

	Timestamp releaseTime; //  '发表时间'

	Timestamp modifiedTime; //  CURRENT_TIMESTAMP(6)

	String status; //  '发表状态（0表示已发表、2表示未发表、）'

}
