package cn.sticki.blog.pojo.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 阿杆
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogStatusBO extends BlogInfoBO {

	/**
	 * 点赞收藏等状态
	 */
	ActionStatusBO actionStatus;

}
