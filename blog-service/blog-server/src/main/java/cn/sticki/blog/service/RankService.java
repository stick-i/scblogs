package cn.sticki.blog.service;

import cn.sticki.blog.pojo.vo.RankAuthorVO;
import cn.sticki.blog.pojo.vo.RankHotVO;

import java.util.List;

/**
 * @author durance
 */
public interface RankService {

	/**
	 * 获取当日博客热度排行榜
	 *
	 * @return 当日热度排行信息
	 */
	List<RankHotVO> getTodayHotRank();

	/**
	 * 获取周博客热度排行榜
	 *
	 * @return 上周热度排行信息
	 */
	List<RankHotVO> getWeekHotRank();

	/**
	 * 获取周作者排行榜
	 *
	 * @return 上周作者排行信息
	 */
	List<RankAuthorVO> getWeekAuthorRank();

	/**
	 * 获取作者总榜
	 *
	 * @return 作者总排行榜
	 */
	List<RankAuthorVO> getTotalAuthorRank();

	/**
	 * 对相应博客执行热度加 3
	 *
	 * @param blogId 增加热度的博客
	 * @param score  增加的分数
	 */
	void addRankHotScore(Integer blogId, Double score);

	/**
	 * 提升作者排行榜 原力值
	 *
	 * @param blogId 博客id
	 * @param score  增加的分数
	 */
	void addRankAuthorScore(Integer blogId, Double score);

}
