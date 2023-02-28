package cn.sticki.blink.controller;

import cn.sticki.blink.pojo.BlinkView;
import cn.sticki.blink.pojo.BlinkViewListVO;
import cn.sticki.blink.pojo.SaveBlinkBO;
import cn.sticki.blink.pojo.UpdateBlinkBO;
import cn.sticki.blink.service.BlinkService;
import cn.sticki.blink.service.BlinkViewService;
import cn.sticki.common.web.auth.AuthHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 动态（表白墙）相关接口
 *
 * @author 阿杆
 */
@Slf4j
@RestController
@RequestMapping("/blink")
@Validated
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
	public BlinkView getBlink(Integer id) {
		return blinkViewService.getById(id);
	}

	/**
	 * 创建动态
	 *
	 * @param content    动态内容
	 * @param schoolCode 院校代码，从cookie中获取
	 */
	@PostMapping
	public void createBlink(@NotNull String content, @CookieValue(required = false) Integer schoolCode) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		SaveBlinkBO saveBlinkBO = new SaveBlinkBO();
		saveBlinkBO.setContent(content);
		saveBlinkBO.setUserId(id);
		saveBlinkBO.setSchoolCode(schoolCode);
		blinkService.create(saveBlinkBO);
	}

	/**
	 * 修改动态内容
	 */
	@PutMapping
	public void updateBlink(UpdateBlinkBO blinkBO) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		blinkBO.setUserId(id);
		blinkService.update(blinkBO);
	}

	/**
	 * 删除动态
	 *
	 * @param blinkId 动态id
	 */
	@DeleteMapping
	public void deleteBlink(@NotNull Integer blinkId) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		blinkService.remove(blinkId, id);
	}

	/**
	 * 获取最新动态列表
	 *
	 * @param page       第几页
	 * @param schoolCode 院校代码，哪个学校的
	 */
	@GetMapping("/list")
	public BlinkViewListVO getList(@RequestParam(defaultValue = "1") Integer page, Integer schoolCode) {
		return blinkViewService.getListByTime(page, PAGE_SIZE, schoolCode);
	}

	/**
	 * 获取自己的动态列表
	 *
	 * @param page 第几页
	 */
	@GetMapping("/list/self")
	public BlinkViewListVO getSelfList(@RequestParam(defaultValue = "1") Integer page) {
		Integer id = AuthHelper.getCurrentUserIdOrExit();
		return blinkViewService.getSelfList(id, page, PAGE_SIZE);
	}

}
