package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_vo")
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
	 * 头像链接
	 */
	String avatarUrl;

	/**
	 * 注册时间
	 */
	Timestamp registerTime;

}
