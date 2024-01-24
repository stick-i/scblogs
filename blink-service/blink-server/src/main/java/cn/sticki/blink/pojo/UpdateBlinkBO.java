package cn.sticki.blink.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
