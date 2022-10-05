package cn.sticki.blog.sdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用 rabbitMQ 传输具体用户浏览了某篇博客的事件实体类
 *
 * @author durance
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogReadDTO {

	/**
	 * 被浏览的博客id
	 */
	Integer blogId;

	/**
	 * 执行的用户id，可能为空
	 */
	Integer userId;

}
