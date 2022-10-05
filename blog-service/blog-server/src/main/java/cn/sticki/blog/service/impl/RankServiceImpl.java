package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.vo.RankHotVO;
import cn.sticki.blog.service.RankService;
import cn.sticki.common.result.RestResult;
import cn.sticki.user.client.UserClient;
import cn.sticki.user.dto.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author durance
 */
@Service
public class RankServiceImpl implements RankService {

    public static final String DAY_KEY_PREFIX = "rank:hot:day:";

    public static final String WEEK_KEY_PREFIX = "rank:hot:week:";

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    UserClient userClient;

    @Resource
    BlogMapper blogMapper;

    @Override
    public List<RankHotVO> getRankHotToday() {
        // 1 进行判断 redis 是否已经创建了 今天的redis 排行榜缓存的key
        // 1.1 获取 day key
        long dayKey = getDayKey();
        // 获取redis数据
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet().reverseRangeWithScores(DAY_KEY_PREFIX + dayKey, 0, -1);
        return getRankHotVOList(typedTuples);
    }

    @Override
    public List<RankHotVO> getRankHotWeek() {
        long weekkey = getWeekKey();
        // 拿到数据集合
        Set<ZSetOperations.TypedTuple<String>> weekrank = stringRedisTemplate.opsForZSet().reverseRangeWithScores(WEEK_KEY_PREFIX + weekkey, 0, -1);
        // 进行判断查看有没有数据
        if (!(weekrank.size() == 0 || weekrank == null)) {
            // 如果拿到数据直接进行查询并返回
            return getRankHotVOList(weekrank);
        }
        // 如果没有拿到数据集，进行合并操作并存入缓存
        // 获取 day key
        long daykey = getDayKey();
        // 计算需要进行聚合的上周一的 day key
        long mondayKey = daykey % 7;
        if (mondayKey >= 4) {
            mondayKey = daykey - (3 + mondayKey);
        } else {
            mondayKey = daykey - (10 + mondayKey);
        }
        // 聚合上周的day key,存入集合
        ArrayList<String> daykeys = new ArrayList<>();
        for (long i = mondayKey; i < mondayKey + 7; i++) {
            daykeys.add(DAY_KEY_PREFIX + i);
        }
        // 合并操作获取合并结果
        stringRedisTemplate.opsForZSet().unionAndStore("COUNT_WEEK", daykeys, WEEK_KEY_PREFIX + weekkey);
        // 设置过期时间为 一星期
        stringRedisTemplate.expire(WEEK_KEY_PREFIX + weekkey, 60 * 60 * 24 * 7, TimeUnit.SECONDS);
        weekrank = stringRedisTemplate.opsForZSet().reverseRangeWithScores(WEEK_KEY_PREFIX + weekkey, 0, -1);
        return getRankHotVOList(weekrank);
    }

    /**
     * 拿到博客id及热度后，设置 热度排行榜显示 的相关参数
     *
     * @param typedTuples
     * @return
     */
    private List<RankHotVO> getRankHotVOList(Set<ZSetOperations.TypedTuple<String>> typedTuples){
        if(typedTuples==null || typedTuples.size() == 0){
            // 如果没有创建，说明今天暂无热榜相关的信息，返回空信息
            return null;
        }
        // 2 如果创建了，封装响应信息
        //拿到set集合迭代器
        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
        List<RankHotVO> result = new ArrayList<>();
        while(iterator.hasNext()){
            //拿到这项信息
            ZSetOperations.TypedTuple<String> tuple = iterator.next();
            // 拿到 blodid
            String blogid = tuple.getValue();
            // 2.1 查询并设置博客信息
            Blog blog = blogMapper.selectById(blogid);
            RankHotVO rankHotVO = BeanUtil.copyProperties(blog, RankHotVO.class);
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
