package cn.sticki.blink.pojo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 阿杆
 */
@Data
public class BlinkView {

	/**
	 * 动态id
	 */
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 学校代码
	 */
	Integer schoolCode;

	/**
	 * 动态内容
	 */
	String content;

	/**
	 * 发表时间
	 */
	Timestamp createTime;

	/**
	 * 修改时间
	 */
	Timestamp modifiedTime;

	/**
	 * 浏览量
	 */
	Integer viewNum;

	/**
	 * 点赞量
	 */
	Integer likesNum;

	/**
	 * 评论量
	 */
	Integer commentNum;

	/**
	 * 评分
	 */
	Double score;

}
