package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.user.dto.UserDTO;
import lombok.Data;

/**
 * @author durance
 */
@Data
public class RankHotVO {

	/**
	 * 博客信息
	 */
	Blog blog;

	/**
	 * 用户信息
	 */
	UserDTO author;

	/**
	 * 排行榜热度
	 */
	Double hot;

}
