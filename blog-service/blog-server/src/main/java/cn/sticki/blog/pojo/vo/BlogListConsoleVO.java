package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.bo.BlogCountBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 阿杆
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogListConsoleVO extends BlogListVO {

	/**
	 * 各状态博客数量
	 */
	BlogCountBO count;

}
