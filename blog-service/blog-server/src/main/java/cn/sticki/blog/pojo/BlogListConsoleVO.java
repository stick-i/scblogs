package cn.sticki.blog.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogListConsoleVO extends BlogListVO {

	/**
	 * 各状态博客数量
	 */
	BlogCountBO count;

}
