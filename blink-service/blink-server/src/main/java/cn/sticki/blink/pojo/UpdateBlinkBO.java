package cn.sticki.blink.pojo;

import lombok.Data;

/**
 * @author 阿杆
 */
@Data
public class UpdateBlinkBO {

	/**
	 * 动态id
	 */
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 动态内容
	 */
	String content;

}
