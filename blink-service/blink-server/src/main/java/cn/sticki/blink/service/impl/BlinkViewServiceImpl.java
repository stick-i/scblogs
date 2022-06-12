package cn.sticki.blink.service.impl;

import cn.sticki.blink.mapper.BlinkViewMapper;
import cn.sticki.blink.pojo.BlinkView;
import cn.sticki.blink.pojo.BlinkViewListVO;
import cn.sticki.blink.service.BlinkViewService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 阿杆
 */
@Service
public class BlinkViewServiceImpl extends ServiceImpl<BlinkViewMapper, BlinkView> implements BlinkViewService {

	@Resource
	private BlinkViewMapper blinkViewMapper;

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
		IPage<BlinkView> iPage = new Page<>(page, pageSize);
		blinkViewMapper.selectPage(iPage, wrapper);
		BlinkViewListVO blinkViewListVO = new BlinkViewListVO();
		BeanUtils.copyProperties(iPage, blinkViewListVO);
		return blinkViewListVO;
	}

}
