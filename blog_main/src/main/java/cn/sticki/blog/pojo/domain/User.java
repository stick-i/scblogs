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

	@TableId
	Integer id;

	String username;

	String nickname;

	String avatarUrl;

	Timestamp registerTime;

	public User(User user) {
		if (user == null) return;
		this.id = user.getId();
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.avatarUrl = user.getAvatarUrl();
		this.registerTime = user.getRegisterTime();
	}

}
