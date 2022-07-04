package cn.sticki.blink.service;

import cn.sticki.blink.pojo.Blink;
import cn.sticki.blink.pojo.SaveBlinkBO;
import cn.sticki.blink.pojo.UpdateBlinkBO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 阿杆
 */
public interface BlinkService extends IService<Blink> {

	/**
	 * 新建动态
	 *
	 * @param blinkBO 博客数据
	 */
	void create(SaveBlinkBO blinkBO);

	/**
	 * 删除动态
	 *
	 * @param id     动态id
	 * @param userId 用户id
	 */
	void remove(int id, int userId);

	/**
	 * 更新动态
	 *
	 * @param blinkBO 动态数据
	 */
	void update(UpdateBlinkBO blinkBO);

}
