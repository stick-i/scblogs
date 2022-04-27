package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class User implements Serializable {

	@TableId
	Integer id;

	String username;

	String nickname;

	String avatar;

	Timestamp registerTime;

}
