package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Blog {

	@TableId
	Integer id; //   博客id

	String author; //   作者

	String title; //   标题

	String description; //   描述

	Timestamp createTime; //   创建时间

	Timestamp releaseTime; //  发表时间

	Timestamp modifiedTime; //  修改时间

	Integer status; // 发表状态（1表示已发表、2表示未发表、3为仅自己可见、4为回收站）

}
