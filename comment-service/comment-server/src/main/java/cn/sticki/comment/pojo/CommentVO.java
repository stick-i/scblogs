package cn.sticki.comment.pojo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVO {

	/**
	 * 父评论信息
	 */
	CommentBO info;

	/**
	 * 子评论信息列表
	 */
	List<CommentBO> sub;

	/**
	 * 子评论信息条数
	 */
	long subCount;

}
