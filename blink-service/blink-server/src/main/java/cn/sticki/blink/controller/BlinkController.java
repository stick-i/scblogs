package cn.sticki.blink.controller;

import cn.sticki.blink.pojo.BlinkView;
import cn.sticki.blink.pojo.BlinkViewListVO;
import cn.sticki.blink.pojo.SaveBlinkBO;
import cn.sticki.blink.pojo.UpdateBlinkBO;
import cn.sticki.blink.service.BlinkService;
import cn.sticki.blink.service.BlinkViewService;
import cn.sticki.common.result.RestResult;
import cn.sticki.common.web.auth.AuthHelper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 动态（表白墙）相关接口
 *
 * @author 阿杆
 */
@Slf4j
@RestController
@RequestMapping("/blink")
public class BlinkController {

	private static final int PAGE_SIZE = 20;

	@Resource
	private BlinkViewService blinkViewService;

	@Resource
	private BlinkService blinkService;

	/**
	 * 获取动态内容
	 *
	 * @param id 动态id
	 */
	@GetMapping
	public RestResult<BlinkView> getBlink(Integer id) {
		BlinkView blinkView = blinkViewService.getById(id);
		return new RestResult<>(blinkView);
	}

	/**
	 * 创建动态
	 *
	 * @param content    动态内容
	 * @param schoolCode 院校代码，从cookie中获取
	 */
	@PostMapping
	public RestResult<Object> createBlink(@NotNull String content, @CookieValue(required = false) Integer schoolCode) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		SaveBlinkBO saveBlinkBO = new SaveBlinkBO();
		saveBlinkBO.setContent(content);
		saveBlinkBO.setUserId(id);
		saveBlinkBO.setSchoolCode(schoolCode);
		blinkService.create(saveBlinkBO);
		return new RestResult<>();
	}

	/**
	 * 修改动态内容
	 */
	@PutMapping
	public RestResult<Object> updateBlink(UpdateBlinkBO blinkBO) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		blinkBO.setUserId(id);
		blinkService.update(blinkBO);
		return new RestResult<>();
	}

	/**
	 * 删除动态
	 *
	 * @param blinkId 动态id
	 */
	@DeleteMapping
	public RestResult<Object> deleteBlink(int blinkId) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		blinkService.remove(blinkId, id);
		return new RestResult<>();
	}

	/**
	 * 获取最新动态列表
	 *
	 * @param page       第几页
	 * @param schoolCode 院校代码，哪个学校的
	 */
	@GetMapping("/list")
	public RestResult<BlinkViewListVO> getList(@RequestParam(defaultValue = "1") Integer page, Integer schoolCode) {
		BlinkViewListVO viewList = blinkViewService.getListByTime(page, PAGE_SIZE, schoolCode);
		return new RestResult<>(viewList);
	}

	/**
	 * 获取自己的动态列表
	 *
	 * @param page 第几页
	 */
	@GetMapping("/list/self")
	public RestResult<BlinkViewListVO> getSelfList(@RequestParam(defaultValue = "1") Integer page) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		BlinkViewListVO viewList = blinkViewService.getSelfList(id, page, PAGE_SIZE);
		return new RestResult<>(viewList);
	}

}
