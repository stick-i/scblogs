package cn.sticki.comment.service;

import cn.sticki.comment.pojo.Comment;
import cn.sticki.comment.pojo.CommentListVO;

public interface CommentService {

	/**
	 * 创建评论
	 */
	void create(Comment comment);

	/**
	 * 删除评论
	 *
	 * @param id 评论id
	 */
	void delete(int id);

	/**
	 * 检查评论发表人
	 *
	 * @param userId    用户id
	 * @param commentId 评论id
	 * @return 若该评论是该用户发表的，返回true
	 */
	boolean checkPublisher(int userId, int commentId);

	/**
	 * 检查发表人并删除评论
	 */
	void checkAndDelete(int userId, int commentId);

	/**
	 * 获取评论信息列表
	 *
	 * @param blogId   博客id
	 * @param page     当前页
	 * @param pageSize 页大小
	 */
	CommentListVO getList(int blogId, int page, int pageSize);

}
