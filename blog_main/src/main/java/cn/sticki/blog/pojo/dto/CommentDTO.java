package cn.sticki.blog.pojo.dto;

import cn.sticki.blog.pojo.domain.CommentBasic;
import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {

	/**
	 * 父评论信息
	 */
	CommentBasic info;

	/**
	 * 子评论信息列表
	 */
	List<CommentBasic> sub;

	/**
	 * 子评论信息条数
	 */
	long subCount;

}
