package cn.sticki.blink.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 阿杆
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlinkGeneral {

	/**
	 * 动态id
	 */
	@TableId
	Integer blinkId;

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

	/**
	 * 是否已经删除，0未删除，1已删除
	 */
	Integer isDeleted;

}
