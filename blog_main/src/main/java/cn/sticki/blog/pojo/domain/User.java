package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	@TableId
	Integer id;

	String username;

	String nickname;

	String avatar;

	Timestamp registerTime;

	public User(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.avatar = user.getAvatar();
		this.registerTime = user.getRegisterTime();
	}

}
