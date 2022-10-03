package cn.sticki.blog.service;


import cn.sticki.blog.pojo.vo.RankHotVO;

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
    List<RankHotVO> getRankHotToday();

    /**
     * 获取近七日博客热度排行榜
     *
     * @return
     */
    List<RankHotVO> getRankHotWeek();
}
