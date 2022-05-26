package cn.sticki.blog.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotBlogListVO extends BlogListVO {

	/**
	 * 用户行为字典
	 */
	Map<Integer, UserBlogActionStatusDTO> userAction;

}
