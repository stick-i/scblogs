package cn.sticki.blog.listener;

import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.pojo.bo.RankSendBO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
public class CommentListener {

	private static final String COMMENT_INCREASE_QUEUE = "blog.comment.increase";

	private static final String COMMENT_DECREASE_QUEUE = "blog.comment.decrease";

	public static final String COMMENT_RANK_QUEUE = "blog.rank.addhot";

	private static final String KEY_PREFIX = "rank:hot:";

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 通过点赞、浏览、转发等博客相关操作对博客热度进行增加
	 *
	 * @param hotMessage
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = COMMENT_RANK_EXCHANGE, type = ExchangeTypes.FANOUT),
			value = @Queue(name = COMMENT_RANK_QUEUE)
	))
	public void addRankHotScore(String hotMessage){
		System.out.println(hotMessage);
		// 传过来的数据是一下序列化数据，解序列化
		RankSendBO rankSendBO = JSON.parseObject(hotMessage, RankSendBO.class);
		// 获取daykey
		long daykey = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
		//封装 redis key
		String key = KEY_PREFIX+daykey;
		stringRedisTemplate.opsForZSet().incrementScore(key,rankSendBO.getBlogId(),rankSendBO.getScore());

	}

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
