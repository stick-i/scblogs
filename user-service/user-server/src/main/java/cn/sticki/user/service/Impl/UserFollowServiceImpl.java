package cn.sticki.user.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.common.result.ListVO;
import cn.sticki.user.exception.UserIllegalException;
import cn.sticki.user.mapper.FansBasicMapper;
import cn.sticki.user.mapper.FollowBasicMapper;
import cn.sticki.user.mapper.UserFollowMapper;
import cn.sticki.user.pojo.FansBasic;
import cn.sticki.user.pojo.FollowBasic;
import cn.sticki.user.pojo.UserFollow;
import cn.sticki.user.service.UserFollowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Slf4j
@Service
public class UserFollowServiceImpl implements UserFollowService {

	@Resource
	private UserFollowMapper userFollowMapper;

	@Resource
	private FollowBasicMapper followBasicMapper;

	@Resource
	private FansBasicMapper fansBasicMapper;

	@Override
	public boolean follow(int userId, int followId) {
		if (userId == followId) {
			log.warn("User follow oneself,userId->{}", userId);
			throw new UserIllegalException("Error for follow oneself");
		}
		// 关注用户，查询是否存在关注记录，若不存在，则添加记录，若存在，则取消记录
		LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserFollow::getFansId, userId).eq(UserFollow::getFollowId, followId);
		if (userFollowMapper.exists(wrapper)) {
			// 若存在，则删除记录，返回false
			userFollowMapper.delete(wrapper);
			return false;
		} else {
			UserFollow userFollow = new UserFollow();
			userFollow.setFansId(userId);
			userFollow.setFollowId(followId);
			userFollow.setCreateTime(new Timestamp(System.currentTimeMillis()));
			// 不存在，添加记录，返回true
			userFollowMapper.insert(userFollow);
			return true;
		}
	}

	@Override
	public ListVO<FollowBasic> getFollowList(int userId, int page, int pageSize) {
		// 获取关注列表
		LambdaQueryWrapper<FollowBasic> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(FollowBasic::getUserId, userId);
		IPage<FollowBasic> iPage = new Page<>(page, pageSize);
		followBasicMapper.selectPage(iPage, wrapper);
		// 赋值返回
		ListVO<FollowBasic> listVO = new ListVO<>();
		BeanUtil.copyProperties(iPage, listVO);
		return listVO;
	}

	@Override
	public ListVO<FansBasic> getFansList(int userId, int page, int pageSize) {
		// 获取粉丝列表
		LambdaQueryWrapper<FansBasic> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(FansBasic::getUserId, userId);
		IPage<FansBasic> iPage = new Page<>(page, pageSize);
		fansBasicMapper.selectPage(iPage, wrapper);
		ListVO<FansBasic> listVO = new ListVO<>();
		BeanUtil.copyProperties(iPage, listVO);
		return listVO;
	}

}
