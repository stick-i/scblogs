package cn.sticki.blog.pojo;

import lombok.Data;

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
