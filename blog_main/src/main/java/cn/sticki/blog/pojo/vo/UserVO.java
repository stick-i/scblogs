package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.User;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;

@Data
public class UserVO {

	String username;

	String nickname;

	String avatarUrl;

	Timestamp registerTime;

	// @Value("${resource.url.avatar}")
	// private String avatarPrefix;

	@Value("${resource.url.avatar}")
	private @NotNull String avatarPrefix() {
		return "";
	}

	public UserVO(User user) {
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.avatarUrl = avatarPrefix() + user.getAvatar();
		this.registerTime = user.getRegisterTime();
	}

}
