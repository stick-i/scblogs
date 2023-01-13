package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 阿杆
 */
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

	/**
	 * 是否已经删除，0未删除，1已删除
	 */
	Integer isDeleted;

}
