package cn.sticki.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 阿杆
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginBO extends User {

	/**
	 * 院校名称
	 */
	String schoolName;

}
