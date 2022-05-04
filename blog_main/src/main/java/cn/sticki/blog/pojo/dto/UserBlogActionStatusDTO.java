package cn.sticki.blog.pojo.dto;

import lombok.Data;

/**
 * 用户操作博客的状态，是否点赞，是否收藏
 */
@Data
public class UserBlogActionStatusDTO {

	boolean isLike;

	boolean isCollect;

}
