package cn.sticki.blog.pojo.vo;

import cn.sticki.user.dto.UserDTO;
import lombok.Data;

/**
 * @author durance
 * @date 2022-10-10 22:40
 */
@Data
public class RankAuthorVO {

	/**
	 * 用户信息
	 */
	UserDTO author;

	/**
	 * 排行榜热度
	 */
	Double hot;

	/**
	 * 粉丝数
	 */
	Integer fansNum;

}
