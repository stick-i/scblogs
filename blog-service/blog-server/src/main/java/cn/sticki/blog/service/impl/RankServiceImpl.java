package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.vo.RankHotVO;
import cn.sticki.blog.service.RankService;
import cn.sticki.blog.utils.RankKeyUtils;
import cn.sticki.common.result.RestResult;
import cn.sticki.user.client.UserClient;
import cn.sticki.user.dto.UserDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static cn.sticki.blog.constants.RedisConstants.*;

/**
 * @author durance
 */
@Service
public class RankServiceImpl implements RankService {

	@Resource
	RedisTemplate<String, Integer> redisTemplate;

	@Resource
	UserClient userClient;

	@Resource
	BlogMapper blogMapper;

	@Override
	public List<RankHotVO> getTodayHotRank() {
		// 1 进行判断 redis 是否已经创建了 今天的redis 排行榜缓存的key
		// 1.1 获取 day key
		long dayKey = RankKeyUtils.getDayKey();
		// 获取redis数据
		Set<ZSetOperations.TypedTuple<Integer>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(RANK_HOT_DAY_KEY + dayKey, 0, -1);
		return getRankHotVOList(typedTuples);
	}

	@SuppressWarnings("AlibabaUndefineMagicConstant")
	@Override
	public List<RankHotVO> getWeekHotRank() {
		long weekKey = RankKeyUtils.getWeekKey();
		// 拿到数据集合
		Set<ZSetOperations.TypedTuple<Integer>> weekRank = redisTemplate.opsForZSet().reverseRangeWithScores(RANK_HOT_WEEK_KEY + weekKey, 0, -1);
		// 进行判断查看有没有数据
		if (!(weekRank == null || weekRank.size() == 0)) {
			// 如果拿到数据直接进行查询并返回
			return getRankHotVOList(weekRank);
		}
		// 如果没有拿到数据集，进行合并操作并存入缓存
		// 获取 day key
		long dayKey = RankKeyUtils.getDayKey();
		// 计算需要进行聚合的上周一的 day key
		long mondayKey = dayKey % 7;
		if (mondayKey >= 4) {
			mondayKey = dayKey - (3 + mondayKey);
		} else {
			mondayKey = dayKey - (10 + mondayKey);
		}
		// 聚合上周的day key,存入集合
		ArrayList<String> dayKeys = new ArrayList<>();
		for (long i = mondayKey; i < mondayKey + 7; i++) {
			dayKeys.add(RANK_HOT_DAY_KEY + i);
		}
		// 合并操作获取合并结果
		redisTemplate.opsForZSet().unionAndStore("COUNT_WEEK", dayKeys, RANK_HOT_WEEK_KEY + weekKey);
		// 设置过期时间为 一星期
		redisTemplate.expire(RANK_HOT_WEEK_KEY + weekKey, RANK_HOT_WEEK_TTL, TimeUnit.SECONDS);
		weekRank = redisTemplate.opsForZSet().reverseRangeWithScores(RANK_HOT_WEEK_KEY + weekKey, 0, -1);
		return getRankHotVOList(weekRank);
	}

	/**
	 * 拿到博客id及热度后，设置 热度排行榜显示 的相关参数
	 *
	 * @param typedTuples 排行数据
	 * @return 热点排行数据列表
	 */
	private List<RankHotVO> getRankHotVOList(Set<ZSetOperations.TypedTuple<Integer>> typedTuples) {
		if (typedTuples == null || typedTuples.size() == 0) {
			// 如果没有创建，说明今天暂无热榜相关的信息，返回空信息
			return null;
		}
		// 2 如果创建了，封装响应信息
		//拿到set集合迭代器
		Iterator<ZSetOperations.TypedTuple<Integer>> iterator = typedTuples.iterator();
		List<RankHotVO> result = new ArrayList<>();
		// todo 可以优化，数据库查询可以改成批量查询，速度会快很多，还需要注意有些博客状态
		while (iterator.hasNext()) {
			//拿到这项信息
			ZSetOperations.TypedTuple<Integer> tuple = iterator.next();
			// 拿到 blogId
			Integer blogId = tuple.getValue();
			// 2.1 查询并设置博客信息
			Blog blog = blogMapper.selectById(blogId);
			RankHotVO rankHotVO = new RankHotVO();
			rankHotVO.setBlog(blog);
			// 2.2 查询并设置用户信息
			RestResult<UserDTO> user = userClient.getByUserId(blog.getAuthorId());
			rankHotVO.setAuthor(user.getData());
			// 2.3 设置热度信息
			rankHotVO.setHot(tuple.getScore());
			result.add(rankHotVO);
		}

		return result;
	}

    /**
     * 获取 day key
     */
    private long getDayKey() {
        return System.currentTimeMillis() / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取上一周的 week key
     */
    private long getWeekKey() {
        // 拿到 week key,和day key
        long week = System.currentTimeMillis() / (1000 * 60 * 60 * 24 * 7);
        long day = getDayKey();
        // 由于原来的week key并不是从周一开始算起，所以进行移位计算，拿到上周的week key
        long key = day % 7;
        if (key >= 4) {
            // 经计算，取模值大于等于 4 时 进入新的一周，将week key-1
            return week - 1;
        }
        //否则，在新的一周的周四时，week值将再加1，所以要拿到上一周的week key需要-2
        return week - 2;
    }


}
