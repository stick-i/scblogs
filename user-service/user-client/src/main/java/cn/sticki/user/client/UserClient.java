package cn.sticki.user.client;

import cn.sticki.common.result.RestResult;
import cn.sticki.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-server")
public interface UserClient {

	/**
	 * 获取用户公开信息
	 *
	 * @param id 用户id
	 */
	@GetMapping("/user")
	RestResult<UserDTO> getByUserId(@RequestParam Integer id);

}
