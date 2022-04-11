package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSafety {

	@TableId
	Integer userId;

	String username;

	String password;

	String mail;

	String mobile;

}
