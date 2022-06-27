package cn.sticki.blog.listener;

import cn.sticki.blog.mapper.BlogGeneralMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.sticki.comment.sdk.MqConstants.*;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/26 11:52
 */
@Slf4j
@Component
public class BlogListener {

	private static final String COMMENT_INCREASE_QUEUE = "blog.comment.increase";

	private static final String COMMENT_DECREASE_QUEUE = "blog.comment.decrease";

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	/**
	 * 博客评论数量增加
	 *
	 * @param blogId 博客id
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = COMMENT_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COMMENT_INCREASE_QUEUE),
			key = BLOG_COMMENT_INCREASE_KEY
	))
	public void commentNumberIncreaseListener(int blogId) {
		// 增加博客的评论数量
		log.debug("{} 评论数量+1", blogId);
		blogGeneralMapper.increaseCommentNum(blogId);
	}

	/**
	 * 博客评论数量减少
	 *
	 * @param blogId 博客id
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = COMMENT_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COMMENT_DECREASE_QUEUE),
			key = BLOG_COMMENT_DECREASE_KEY
	))
	public void commentNumberDecreaseListener(int blogId) {
		// 减少博客的评论数量
		log.debug("{} 评论数量-1", blogId);
		blogGeneralMapper.decreaseCommentNum(blogId);
	}

}
