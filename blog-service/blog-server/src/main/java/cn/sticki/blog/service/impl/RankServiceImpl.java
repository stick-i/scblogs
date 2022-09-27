package cn.sticki.blog.service.impl;

import cn.sticki.blog.pojo.bo.RankHotBO;
import cn.sticki.blog.service.RankService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author durance
 */
public class RankServiceImpl implements RankService {

    public static final String KEY_PREFIX = "rank-hot:";

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public List<RankHotBO> getRankHotToday() {
        // 1 进行判断 redis 是否已经创建了 今天的redis 排行榜缓存的key
        // 1.1 获取 day key
        long dayKey = getDayKey();
        if(stringRedisTemplate.opsForValue().get(KEY_PREFIX+dayKey)==null){
            // 如果没有创建，说明今天暂无热榜相关的信息，返回空信息
            return null;
        }
        // 2 如果创建了，封装响应信息
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet().reverseRangeWithScores(KEY_PREFIX + dayKey, 0, -1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        return null;
    }



    /**
     * 获取 day key
     */
    public long getDayKey() {
        return System.currentTimeMillis() / (1000 * 60 * 60 * 24);
    }


}
