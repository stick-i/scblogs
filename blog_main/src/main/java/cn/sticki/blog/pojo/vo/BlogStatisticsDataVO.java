package cn.sticki.blog.pojo.vo;

import lombok.Data;

@Data
public class BlogStatisticsDataVO {

	int allFansCount; //  粉丝数

	int allFavoriteCount; //  收藏数

	int allViewCount; //  总阅读量

	int yesterdayFansCount; //  昨日新增粉丝

	int yesterdayFavoriteCount; //  昨天新增收藏

	int yesterdayViewCount; //  昨日阅读量

}
