package cn.sticki.blog.listener;

import cn.sticki.blog.sdk.BlogOperateDTO;
import cn.sticki.blog.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.sticki.blog.sdk.BlogMqConstants.*;

/**
 * @author durance
 * @version 1.0
 * @date 2022/10/5 14:20
 */
@Slf4j
@Component
public class BlogListener {

	public static final String SEE_RANK_QUEUE = "blog.rank.see";

	public static final String COLLECT_RANK_QUEUE = "blog.rank.collect";

	public static final String LIKE_RANK_QUEUE = "blog.rank.like";

	@Resource
	private RankService rankService;

	/**
	 * 用户浏览博客对博客热度进行增加
	 *
	 * @param blogReadDTO 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = SEE_RANK_QUEUE),
			key = BLOG_OPERATE_READ_KEY
	))
	public void seeAddRankHotScore(BlogOperateDTO blogReadDTO) {
		log.debug("{} 被浏览热度加1", blogReadDTO.getBlogId());
		// 博客热度加 1
		rankService.addRankHotScore(blogReadDTO.getBlogId(), 1d);
		// 作者热度加 1
		rankService.addRankAuthorScore(blogReadDTO.getBlogId(), 1d);
	}

	/**
	 * 用户收藏博客对博客热度进行增加
	 *
	 * @param blogReadDTO 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = COLLECT_RANK_QUEUE),
			key = BLOG_OPERATE_COLLECT_KEY
	))
	public void collectAddRankHotScore(BlogOperateDTO blogReadDTO) {
		log.debug("{} 被收藏热度加3", blogReadDTO.getBlogId());
		// 博客热度加 3
		rankService.addRankHotScore(blogReadDTO.getBlogId(), 3d);
		// 作者热度加 3
		rankService.addRankAuthorScore(blogReadDTO.getBlogId(), 3d);
	}

	/**
	 * 用户点赞博客对博客热度进行增加
	 *
	 * @param blogReadDTO 用户操作消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange(name = BLOG_EXCHANGE, type = ExchangeTypes.TOPIC),
			value = @Queue(name = LIKE_RANK_QUEUE),
			key = BLOG_OPERATE_LIKE_KEY
	))
	public void likeAddRankHotScore(BlogOperateDTO blogReadDTO) {
		log.debug("{} 被点赞热度加3", blogReadDTO.getBlogId());
		// 执行热度加 3
		rankService.addRankHotScore(blogReadDTO.getBlogId(), 3d);
		// 作者热度加 3
		rankService.addRankAuthorScore(blogReadDTO.getBlogId(), 3d);
	}

}
