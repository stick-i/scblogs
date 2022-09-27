package cn.sticki.blog.service;


import cn.sticki.blog.pojo.bo.RankHotBO;

import java.util.List;

/**
 * @author durance
 */
public interface RankService {

    /**
     * 获取当日博客热度排行榜
     *
     * @return
     */
    List<RankHotBO> getRankHotToday();
}
