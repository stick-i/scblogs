package cn.sticki.blog.content.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 17:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogDocAndAuthorName extends BlogDoc {

	/**
	 * 作者昵称
	 */
	String authorName;

}
