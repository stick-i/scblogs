package cn.sticki.user.listener;

import cn.sticki.blog.sdk.BlogOperateDTO;
import cn.sticki.comment.sdk.CommentDTO;
import cn.sticki.user.mapper.UserGeneralMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.sticki.blog.sdk.BlogMqConstants.*;
import static cn.sticki.comment.sdk.MqConstants.BLOG_COMMENT_INCREASE_KEY;

/**
 * @author durance
 * @version 1.0
 * @date 2022/10/5 14:20
 */
@Slf4j
@Component
public class UserListener {

	public static final String USER_SEE_QUEUE = "user.operate.see";

	public static final String USER_COLLECT_QUEUE = "user.operate.collect";

	public static final String USER_LIKE_QUEUE = "user.operate.like";

	public static final String USER_COMMENT_QUEUE = "user.operate.comment";

	@Resource
	private UserGeneralMapper userGeneralMapper;

	/**
	 * 用户访问博客
	 *
	 * @param blogOperateDTO 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = USER_SEE_QUEUE),
			key = BLOG_OPERATE_READ_KEY
	))
	public void seeAddUserGeneral(BlogOperateDTO blogOperateDTO) {
		log.debug("用户 {} 访问加1", blogOperateDTO.getAuthorId());
		// 增加 统计表 用户访问数据
		userGeneralMapper.updateViewNumByUserId(blogOperateDTO.getAuthorId());

	}

	/**
	 * 用户收藏博客
	 *
	 * @param blogOperateDTO 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = USER_COLLECT_QUEUE),
			key = BLOG_OPERATE_COLLECT_KEY
	))
	public void collectAddUserGeneral(BlogOperateDTO blogOperateDTO) {
		log.debug("用户 {} 收藏加1", blogOperateDTO.getBlogId());
		userGeneralMapper.updateCollectNumByUserId(blogOperateDTO.getAuthorId(), 1);

	}

	/**
	 * 用户点赞博客
	 *
	 * @param blogOperateDTO 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = USER_LIKE_QUEUE),
			key = BLOG_OPERATE_LIKE_KEY
	))
	public void likeAddUserGeneral(BlogOperateDTO blogOperateDTO) {
		log.debug("用户 {} 点赞加1", blogOperateDTO.getBlogId());
		userGeneralMapper.updateLikeNumByUserId(blogOperateDTO.getAuthorId(), 1);

	}

	/**
	 * 用户评论博客
	 *
	 * @param commentDTO 评论操作操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = USER_COMMENT_QUEUE),
			key = BLOG_COMMENT_INCREASE_KEY
	))
	public void likeAddUserGeneral(CommentDTO commentDTO) {
		log.debug("用户 {} 点赞加1", commentDTO.getBlogId());
		userGeneralMapper.updateCommentNumByUserId(commentDTO.getBlogId(), 1);

	}

}