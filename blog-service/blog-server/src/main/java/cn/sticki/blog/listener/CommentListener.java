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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static cn.sticki.blog.sdk.BlogMqConstants.BLOG_EXCHANGE;
import static cn.sticki.blog.sdk.BlogMqConstants.BLOG_OPTION_SEE_KEY;
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

	public static final String REDIS_KEY_PREFIX = "rank:hot:day:";

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 用户浏览博客对博客热度进行增加
	 *
	 * @param hotMessage 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COMMENT_RANK_QUEUE),
			key = BLOG_OPTION_SEE_KEY
	))
	public void addRankHotScore(String hotMessage) {
		log.debug("{} 热度加1", hotMessage);
		// 传过来的数据是一下序列化数据，解序列化
		RankSendBO rankSendBO = JSON.parseObject(hotMessage, RankSendBO.class);
		// 获取daykey
		long daykey = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
		//封装 redis key
		String key = REDIS_KEY_PREFIX + daykey;
		//进行判断，是否已经创建 改redis
		Set<String> rank = stringRedisTemplate.opsForZSet().reverseRange(key, 1, -1);
		if (rank == null || rank.size() == 0) {
			//如果没有创建，就执行创建并设置 TTL 为40天
			stringRedisTemplate.opsForZSet().incrementScore(key, rankSendBO.getBlogId().toString(), 1d);
			stringRedisTemplate.expire(key, 60 * 60 * 24 * 40, TimeUnit.SECONDS);
		}
		//已创建，将对应博客热度增加 1
		stringRedisTemplate.opsForZSet().incrementScore(key, rankSendBO.getBlogId().toString(), 1d);

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
