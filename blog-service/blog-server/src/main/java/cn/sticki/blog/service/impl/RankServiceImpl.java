package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.vo.RankAuthorVO;
import cn.sticki.blog.pojo.vo.RankHotVO;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.service.RankService;
import cn.sticki.blog.utils.RankKeyUtils;
import cn.sticki.user.client.UserClient;
import cn.sticki.user.dto.UserDTO;
import cn.sticki.user.dto.UserGeneralDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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

	@Resource
	BlogService blogService;

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

	@Override
	public List<RankAuthorVO> getWeekAuthorRank() {
		//获取上周的周key
		long weekkey = RankKeyUtils.getWeekKey();
		return getAuthorRank(RANK_AUTHOR_WEEK_KEY + weekkey);
	}

	@Override
	public List<RankAuthorVO> getTotalAuthorRank() {
		return getAuthorRank(RANK_AUTHOR_TOTAL_KEY);
	}

	@Override
	public List<RankAuthorVO> getAuthorRank(String key) {
		// 查询总榜数据库信息
		Set<ZSetOperations.TypedTuple<Integer>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);
		// 进行判断，如果为空返回null
		if (typedTuples == null || typedTuples.size() == 0) {
			return null;
		}
		// 否则返回封装结果
		return getRankAuthorVOList(typedTuples);
	}

	@Override
	public void increaseRankHotScore(Integer blogId, Double score) {
		// 获取dayKey
		long dayKey = RankKeyUtils.getDayKey();
		//封装 redis key
		String key = RANK_HOT_DAY_KEY + dayKey;
		//进行判断，是否已经创建 该redis
		if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
			//如果没有创建，就执行创建并设置 TTL 为40天
			redisTemplate.opsForZSet().incrementScore(key, blogId, score);
			redisTemplate.expire(key, RANK_HOT_DAY_TTL, TimeUnit.SECONDS);
		}
		//已创建，修改对应的博客热度
		redisTemplate.opsForZSet().incrementScore(key, blogId, score);
	}

	@Override
	public void increaseRankAuthorScore(Integer blogId, Double score) {
		// 获取作者的id值
		LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Blog::getId, blogId);
		Integer authorId = blogService.getOne(wrapper).getAuthorId();
		String weekKey = RANK_AUTHOR_WEEK_KEY + RankKeyUtils.getWeekKey();
		// 周榜热度增加，进行判断，是否存在该键
		if (Boolean.FALSE.equals(redisTemplate.hasKey(weekKey))) {
			//如果没有创建，就执行创建并设置 TTL 为40天
			redisTemplate.opsForZSet().incrementScore(weekKey, blogId, score);
			redisTemplate.expire(weekKey, RANK_HOT_WEEK_TTL, TimeUnit.SECONDS);
		}
		// 进行判断 加减热度操作
		redisTemplate.opsForZSet().incrementScore(weekKey, blogId, score);
		// 作者排行榜总热度 加 score
		redisTemplate.opsForZSet().incrementScore(RANK_AUTHOR_WEEK_KEY, authorId, score);

	}

	/**
	 * 拿到博客id及热度后，设置作者排行榜相关参数
	 *
	 * @param typedTuples 作者id集合
	 * @return 作者排行榜信息
	 */
	private List<RankAuthorVO> getRankAuthorVOList(Set<ZSetOperations.TypedTuple<Integer>> typedTuples) {
		Iterator<ZSetOperations.TypedTuple<Integer>> iterator = typedTuples.iterator();
		List<Integer> userIdList = new ArrayList<>();
		List<RankAuthorVO> authorVOList = new ArrayList<>();
		while (iterator.hasNext()) {
			ZSetOperations.TypedTuple<Integer> tuple = iterator.next();
			// 将id存入
			userIdList.add(tuple.getValue());
			// 设置RankAuthorVO
			RankAuthorVO rankAuthorVO = new RankAuthorVO();
			rankAuthorVO.setHot(tuple.getScore());
			authorVOList.add(rankAuthorVO);
		}
		// 查询所有 作者信息
		Map<Integer, UserDTO> userMap = userClient.getUserList(userIdList).getData();
		// 查询作者获赞数等信息
		Map<Integer, UserGeneralDTO> userGeneralMap = userClient.getUserGeneralList(userIdList).getData();
		// 封装 authorList
		int len = authorVOList.size();
		for (int i = 0; i < len; i++) {
			// 依次设置作者信息
			authorVOList.get(i).setAuthor(userMap.get(userIdList.get(i)));
			// 根据相同的用户id，设置获赞数和粉丝数
			BeanUtils.copyProperties(userGeneralMap.get(userIdList.get(i)), authorVOList.get(i));
		}
		return authorVOList;
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
		// 创建博客id集合
		List<Integer> blogIdList = new ArrayList<>();
		while (iterator.hasNext()) {
			//拿到这项信息
			ZSetOperations.TypedTuple<Integer> tuple = iterator.next();
			// 拿到 blogId
			Integer blogId = tuple.getValue();
			// 2.1 将博客id存入集合
			blogIdList.add(blogId);
			// 2.2 设置热度信息
			RankHotVO rankHotVO = new RankHotVO();
			rankHotVO.setHot(tuple.getScore());
			result.add(rankHotVO);
		}
		List<Blog> blogList = blogMapper.selectBatchIds(blogIdList);
		// 创建 用户id集合
		List<Integer> userIdList = new ArrayList<>();
		for (Blog blog : blogList) {
			// 拿出 userId 存入集合
			userIdList.add(blog.getAuthorId());
		}
		// 查询并设置 user信息
		Map<Integer, UserDTO> userList = userClient.getUserList(userIdList).getData();
		for (int i = 0; i < blogList.size(); i++) {
			// 设置用户相关信息
			result.get(i).setBlog(blogList.get(i));
			result.get(i).setAuthor(userList.get(blogList.get(i).getAuthorId()));
		}

		return result;
	}

}
