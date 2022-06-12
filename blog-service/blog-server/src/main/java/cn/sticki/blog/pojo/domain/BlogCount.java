package cn.sticki.blog.pojo.domain;

import lombok.Data;

/**
 * @author 阿杆
 */
@Data
public class BlogCount {

	/**
	 * 状态码
	 */
	Integer status;

	/**
	 * 当前状态对应的数量
	 */
	Integer number;

}
