package cn.sticki.blog.pojo.bo;

import cn.sticki.blog.pojo.domain.BlogView;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 阿杆
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogInfoBO extends BlogView {

	/**
	 * 作者昵称
	 */
	String authorName;

}
