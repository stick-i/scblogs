package cn.sticki.blog.pojo.vo;

import lombok.Data;

@Data
public class BlogStatisticsDataVO {

	Integer allFansCount; //  粉丝数

	Integer allFavoriteCount; //  收藏数

	Integer allViewCount; //  总阅读量

	Integer yesterdayFansCount; //  昨日新增粉丝

	Integer yesterdayFavoriteCount; //  昨天新增收藏

	Integer yesterdayViewCount; //  昨日阅读量

}
