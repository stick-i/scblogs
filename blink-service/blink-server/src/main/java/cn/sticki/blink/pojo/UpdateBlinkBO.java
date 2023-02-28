package cn.sticki.blink.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 阿杆
 */
@Data
public class UpdateBlinkBO {

	/**
	 * 动态id
	 */
	@NotNull
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 动态内容
	 */
	@NotNull
	String content;

}
