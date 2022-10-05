package cn.sticki.blog.listener;

import cn.sticki.blog.sdk.BlogReadDTO;
import cn.sticki.blog.utils.RankKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static cn.sticki.blog.constants.RedisConstants.RANK_HOT_DAY_KEY;
import static cn.sticki.blog.constants.RedisConstants.RANK_HOT_DAY_TTL;
import static cn.sticki.blog.sdk.BlogMqConstants.BLOG_EXCHANGE;
import static cn.sticki.blog.sdk.BlogMqConstants.BLOG_OPERATE_READ_KEY;

/**
 * @author durance
 * @version 1.0
 * @date 2022/10/5 14:20
 */
@Slf4j
@Component
public class BlogListener {

	public static final String COMMENT_RANK_QUEUE = "blog.rank.add-hot";

	@Resource
	private RedisTemplate<String, Integer> redisTemplate;

	/**
	 * 用户浏览博客对博客热度进行增加
	 *
	 * @param blogReadDTO 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE),
			value = @Queue(name = COMMENT_RANK_QUEUE),
			key = BLOG_OPERATE_READ_KEY
	))
	public void addRankHotScore(BlogReadDTO blogReadDTO) {
		log.debug("{} 热度加1", blogReadDTO.getBlogId());
		// 获取dayKey
		long dayKey = RankKeyUtils.getDayKey();
		//封装 redis key
		String key = RANK_HOT_DAY_KEY + dayKey;
		//进行判断，是否已经创建 该redis
		if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
			//如果没有创建，就执行创建并设置 TTL 为40天
			redisTemplate.opsForZSet().incrementScore(key, blogReadDTO.getBlogId(), 1d);
			redisTemplate.expire(key, RANK_HOT_DAY_TTL, TimeUnit.SECONDS);
		}
		//已创建，将对应博客热度增加 1
		redisTemplate.opsForZSet().incrementScore(key, blogReadDTO.getBlogId(), 1d);
	}

}
