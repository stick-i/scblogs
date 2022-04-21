package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogBasic {

	Integer id; //   博客id

	String author; //   作者

	String title; //   标题

	String description; //   描述

	String coverImage; // 封面图

	Timestamp createTime; //   创建时间

	Timestamp releaseTime; //  发表时间

	Timestamp modifiedTime; //  修改时间

	Integer status; // 发表状态（1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）

	Integer viewNum; // 浏览量

	Integer likeNum; // 点赞量

	Integer commentNum; // 评论量

	Integer collectionNum; // 收藏量

	Integer score; // 评分

}
