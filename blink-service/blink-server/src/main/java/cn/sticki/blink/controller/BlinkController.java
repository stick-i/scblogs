package cn.sticki.blink.controller;

import cn.sticki.blink.pojo.BlinkView;
import cn.sticki.blink.pojo.BlinkViewListVO;
import cn.sticki.blink.pojo.SaveBlinkBO;
import cn.sticki.blink.pojo.UpdateBlinkBO;
import cn.sticki.blink.service.BlinkService;
import cn.sticki.blink.service.BlinkViewService;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 动态（表白墙）相关接口
 */
@Slf4j
@RestController
@RequestMapping("/blink")
public class BlinkController {

	private static final int pageSize = 20;

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
	public RestResult<Object> createBlink(@NotNull String content, @RequestHeader Integer id, @CookieValue(required = false) Integer schoolCode) {
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
	public RestResult<Object> updateBlink(UpdateBlinkBO blinkBO, @RequestHeader Integer id) {
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
	public RestResult<Object> deleteBlink(int blinkId, @RequestHeader Integer id) {
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
		BlinkViewListVO viewList = blinkViewService.getListByTime(page, pageSize, schoolCode);
		return new RestResult<>(viewList);
	}

	/**
	 * 获取自己的动态列表
	 *
	 * @param page 第几页
	 */
	@GetMapping("/list/self")
	public RestResult<BlinkViewListVO> getSelfList(@RequestParam(defaultValue = "1") Integer page, @RequestHeader Integer id) {
		BlinkViewListVO viewList = blinkViewService.getSelfList(id, page, pageSize);
		return new RestResult<>(viewList);
	}

}
