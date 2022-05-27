package cn.sticki.blog.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogStatusBO extends BlogBasic {

	/**
	 * 点赞收藏等状态
	 */
	ActionStatusBO actionStatus;

}
