package cn.sticki.blink.service.impl;

import cn.sticki.blink.mapper.BlinkViewMapper;
import cn.sticki.blink.pojo.BlinkVO;
import cn.sticki.blink.pojo.BlinkView;
import cn.sticki.blink.pojo.BlinkViewListVO;
import cn.sticki.blink.service.BlinkViewService;
import cn.sticki.user.client.UserClient;
import cn.sticki.user.dto.UserDTO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 阿杆
 */
@Service
public class BlinkViewServiceImpl extends ServiceImpl<BlinkViewMapper, BlinkView> implements BlinkViewService {

	@Resource
	private BlinkViewMapper blinkViewMapper;

	@Resource
	private UserClient userClient;

	@Override
	public BlinkViewListVO getListByTime(int page, int pageSize, Integer schoolCode) {
		LambdaQueryWrapper<BlinkView> wrapper = new LambdaQueryWrapper<>();
		// 通过id排序，即为通过时间排序，因为时间越后面的id就越大
		wrapper.orderByDesc(BlinkView::getId).eq(schoolCode != null, BlinkView::getSchoolCode, schoolCode);
		return getPage(wrapper, page, pageSize);
	}

	@Override
	public BlinkViewListVO getSelfList(int userId, int page, int pageSize) {
		LambdaQueryWrapper<BlinkView> wrapper = new LambdaQueryWrapper<>();
		// 通过id排序，即为通过时间排序，因为时间越后面的id就越大
		wrapper.orderByDesc(BlinkView::getId).eq(BlinkView::getUserId, userId);
		return getPage(wrapper, page, pageSize);
	}

	/**
	 * 用于设置分页查询的私有方法
	 *
	 * @param wrapper  条件
	 * @param page     第几页
	 * @param pageSize 页大小
	 */
	private BlinkViewListVO getPage(Wrapper<BlinkView> wrapper, int page, int pageSize) {
		BlinkViewListVO blinkViewListVO = new BlinkViewListVO();

		// 查询数据
		IPage<BlinkView> iPage = new Page<>(page, pageSize);
		blinkViewMapper.selectPage(iPage, wrapper);
		// 复制查询信息
		blinkViewListVO.setCurrent(iPage.getCurrent());
		blinkViewListVO.setPages(iPage.getPages());
		blinkViewListVO.setSize(iPage.getSize());
		blinkViewListVO.setTotal(iPage.getTotal());

		// 获取用户数据
		Set<Integer> userIdSet = iPage.getRecords().stream().map(BlinkView::getUserId).collect(Collectors.toSet());
		Map<Integer, UserDTO> userMap = userClient.getUserList(userIdSet).getData();
		if (userMap == null) {
			userMap = new HashMap<>(0);
		}

		// 填充动态数据和用户数据
		ArrayList<BlinkVO> blinkVOList = new ArrayList<>();
		for (BlinkView blink : iPage.getRecords()) {
			BlinkVO blinkVO = new BlinkVO();
			blinkVO.setBlink(blink);
			blinkVO.setUser(userMap.get(blink.getUserId()));
			blinkVOList.add(blinkVO);
		}

		blinkViewListVO.setRecords(blinkVOList);
		return blinkViewListVO;
	}

}
