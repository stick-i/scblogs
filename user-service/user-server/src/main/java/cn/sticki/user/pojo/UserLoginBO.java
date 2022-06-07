package cn.sticki.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginBO extends User {

	/**
	 * 院校名称
	 */
	String schoolName;

}
