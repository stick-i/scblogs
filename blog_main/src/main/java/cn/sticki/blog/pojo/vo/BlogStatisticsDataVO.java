package cn.sticki.blog.pojo.vo;

import lombok.Data;

@Data
public class BlogStatisticsDataVO {

	/**
	 * 粉丝数
	 */
	Integer allFansCount;

	/**
	 * 收藏数
	 */
	Integer allFavoriteCount;

	/**
	 * 总阅读量
	 */
	Integer allViewCount;

	/**
	 * 昨日新增粉丝
	 */
	Integer yesterdayFansCount;

	/**
	 * 昨天新增收藏
	 */
	Integer yesterdayFavoriteCount;

	/**
	 * 昨日阅读量
	 */
	Integer yesterdayViewCount;

}
