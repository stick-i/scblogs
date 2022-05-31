package cn.sticki.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BlogContent {

	/**
	 * 博客id
	 */
	@TableId
	Integer blogId;

	/**
	 * 博客内容
	 */
	String content;

	/**
	 * 修改时间
	 */
	Timestamp modifiedTime;

}
