package cn.sticki.blink.pojo;

import cn.sticki.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/20 16:42
 */
@Data
public class BlinkVO {

	@JsonUnwrapped
	BlinkView blink;

	UserDTO user;

}
