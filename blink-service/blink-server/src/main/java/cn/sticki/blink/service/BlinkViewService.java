package cn.sticki.blink.service;

import cn.sticki.blink.pojo.BlinkView;
import cn.sticki.blink.pojo.BlinkViewListVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 阿杆
 */
public interface BlinkViewService extends IService<BlinkView> {

	/**
	 * 获取动态列表，以创建时间排序，获取最新的
	 *
	 * @param page       第几页
	 * @param pageSize   页大小
	 * @param schoolCode 院校代码
	 * @return 动态列表数据
	 */
	BlinkViewListVO getListByTime(int page, int pageSize, Integer schoolCode);

	/**
	 * 获取个人的动态列表
	 *
	 * @param userId   用户id
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 动态列表数据
	 */
	BlinkViewListVO getSelfList(int userId, int page, int pageSize);

}
