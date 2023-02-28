package cn.sticki.user.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 阿杆
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/**
	 * 用户id
	 */
	@TableId
	Integer id;

	/**
	 * 用户名
	 */
	String username;

	/**
	 * 用户昵称
	 */
	String nickname;

	/**
	 * 院校代码
	 */
	Integer schoolCode;

	/**
	 * 头像链接
	 */
	String avatarUrl;

	/**
	 * 注册时间
	 */
	Timestamp registerTime;

	/**
	 * 是否已经删除，0未删除，1已删除
	 */
	Integer deleted;

}
