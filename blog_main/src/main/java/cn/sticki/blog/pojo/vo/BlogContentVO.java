package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.domain.BlogContent;
import cn.sticki.blog.pojo.domain.User;
import lombok.Data;

@Data
public class BlogContentVO {

	/**
	 * 博客基本信息
	 */
	BlogBasic info;

	/**
	 * 博客内容信息
	 */
	BlogContent content;

	/**
	 * 作者信息
	 */
	User author;

	/**
	 * 第一页评论信息
	 */
	CommentListVO comment;

}
