package cn.sticki.blink.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Blink {

	/**
	 * 动态id
	 */
	@TableId
	Integer id;

	/**
	 * 用户id
	 */
	Integer userId;

	/**
	 * 学校代码
	 */
	Integer schoolCode;

	/**
	 * 动态内容
	 */
	String content;

	/**
	 * 发表时间
	 */
	Timestamp createTime;

	/**
	 * 修改时间
	 */
	Timestamp modifiedTime;

}
