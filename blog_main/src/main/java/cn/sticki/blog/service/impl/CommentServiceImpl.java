package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.userException.UserIllegalException;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.mapper.CommentMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.Comment;
import cn.sticki.blog.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Override
	public void create(Comment comment) {
		// 检查博客是否存在，检查父评论id是否在该博客下
		boolean exists;
		if (comment.getParentId() != null) {
			// 判断博客id和父评论id是否正确
			LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Comment::getBlogId, comment.getBlogId()).eq(Comment::getId, comment.getParentId());
			exists = commentMapper.exists(wrapper);
		} else {
			// 判断博客id是否存在
			LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Blog::getId, comment.getBlogId());
			exists = blogMapper.exists(wrapper);
		}
		if (!exists) throw new UserIllegalException("数据异常");
		comment.setId(null);
		comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		commentMapper.insert(comment);
		blogGeneralMapper.increaseCommentNum(comment.getId());
	}

	@Override
	public void delete(int id) {
		int count = commentMapper.deleteById(id);
		if (count == 0)
			log.warn("comment 删除异常，id->{}", id);
		else {
			// 减少数量
			blogGeneralMapper.decreaseCommentNum(id);
		}
	}

	@Override
	public boolean checkCommentPublisher(int userId, int commentId) {
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getUserId, userId).eq(Comment::getId, commentId);
		return commentMapper.exists(wrapper);
	}

	@Override
	public void checkAndDelete(int userId, int commentId) {
		if (!checkCommentPublisher(userId, commentId)) {
			log.warn("非法删除评论，id->{},commentId->{}", userId, commentId);
			throw new UserIllegalException();
		} else {
			delete(commentId);
		}
	}

}
