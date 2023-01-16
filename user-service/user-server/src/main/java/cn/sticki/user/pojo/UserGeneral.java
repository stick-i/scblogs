package cn.sticki.user.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author durancer
 */
@Data
public class UserGeneral {

	/**
	 * id
	 */
	@TableId
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 累计点赞数
	 */
	Integer likeNum;

	/**
	 * 粉丝数
	 */
	Integer fansNum;

	/**
	 * 评论数
	 */
	Integer commentNum;

	/**
	 * 收藏数
	 */
	Integer collectNum;

	/**
	 * 访问数
	 */
	Integer viewNum;

	/**
	 * 博客数
	 */
	Integer blogNum;

	/**
	 * 作者周排行
	 */
	Integer weekRank;

	/**
	 * 作者总排行
	 */
	Integer totalRank;

	/**
	 * 是否已经删除，0未删除，1已删除
	 */
	Integer deleted;

}
