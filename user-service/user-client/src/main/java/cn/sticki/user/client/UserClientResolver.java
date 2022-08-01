package cn.sticki.user.client;

import cn.sticki.common.result.RestResult;
import cn.sticki.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/12 9:56
 */
@Slf4j
@Component
public class UserClientResolver implements UserClient {

	/**
	 * 获取用户公开信息
	 *
	 * @param id 用户id
	 * @return 用户信息
	 */
	@Override
	public RestResult<UserDTO> getByUserId(Integer id) {
		log.error("User 服务异常：getByUserId 请求失败");
		return RestResult.fail("request fail");
	}

	/**
	 * 批量获取用户信息
	 *
	 * @param userIdList 用户id列表
	 * @return key为用户id，value为用户信息
	 */
	@Override
	public RestResult<Map<Integer, UserDTO>> getUserList(List<Integer> userIdList) {
		log.error("User 服务异常：getUserList 请求失败");
		return RestResult.fail("request fail");
	}

	/**
	 * 批量获取用户信息
	 *
	 * @param userIdList 用户id列表
	 * @return key为用户id，value为用户信息
	 */
	@Override
	public RestResult<Map<Integer, UserDTO>> getUserList(Set<Integer> userIdList) {
		return getUserList((List<Integer>) null);
	}

	/**
	 * 获取用户关注列表
	 *
	 * @param userId 用户id
	 * @return 关注列表
	 */
	@Override
	public RestResult<List<Integer>> getFollowIdList(Integer userId) {
		log.error("User 服务异常：getFollowIdList 请求失败");
		return RestResult.fail("request fail");
	}

}
