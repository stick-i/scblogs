package cn.sticki.comment.pojo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVO {

	/**
	 * 父评论信息
	 */
	CommentView info;

	/**
	 * 子评论信息列表
	 */
	List<CommentView> sub;

	/**
	 * 子评论信息条数
	 */
	long subCount;

}
