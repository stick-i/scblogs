package cn.sticki.user.controller;

import cn.sticki.common.result.ListVO;
import cn.sticki.common.result.RestResult;
import cn.sticki.user.pojo.FansView;
import cn.sticki.user.pojo.FollowView;
import cn.sticki.user.service.UserFollowService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户关注接口
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class FollowController {

	// 默认每页20条
	private final int pageSize = 20;

	@Resource
	private UserFollowService userFollowService;

	/**
	 * 获取关注列表
	 *
	 * @param page 当前页
	 * @return 关注列表数据
	 */
	@GetMapping("/follow")
	public RestResult<ListVO<FollowView>> getFollowList(@RequestParam(defaultValue = "1") int page, @RequestHeader Integer id) {
		ListVO<FollowView> listVO = userFollowService.getFollowList(id, page, pageSize);
		return new RestResult<>(listVO);
	}

	/**
	 * 获取粉丝列表
	 *
	 * @param page 当前页
	 * @return 粉丝列表数据
	 */
	@GetMapping("/fans")
	public RestResult<ListVO<FansView>> getFansList(@RequestParam(defaultValue = "1") int page, @RequestHeader Integer id) {
		ListVO<FansView> listVO = userFollowService.getFansList(id, page, pageSize);
		return new RestResult<>(listVO);
	}

	/**
	 * 关注其他用户
	 *
	 * @param followId 被关注的用户id
	 * @return 关注状态，若成功则为true，取关则为false
	 */
	@PostMapping("/follow")
	public RestResult<Object> doFollow(@NotNull Integer followId, @RequestHeader Integer id) {
		try {
			boolean result = userFollowService.follow(id, followId);
			return new RestResult<>(200, "success", result, true);
		} catch (Exception e) {
			return new RestResult<>(200, "fail", null, false);
		}
	}

}
