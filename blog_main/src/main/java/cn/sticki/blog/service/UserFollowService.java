package cn.sticki.blog.service;

import cn.sticki.blog.pojo.domain.FansBasic;
import cn.sticki.blog.pojo.domain.FollowBasic;
import cn.sticki.blog.pojo.vo.ListVO;

public interface UserFollowService {

	/**
	 * 关注用户，已关注的状态下则取消关注
	 *
	 * @param userId   用户id（操作者）
	 * @param followId 关注者的id
	 * @return 返回调用后是否为关注状态，若为关注则返回true、未关注则返回false
	 */
	boolean follow(int userId, int followId);

	/**
	 * 获取关注列表
	 *
	 * @return 关注列表
	 */
	ListVO<FollowBasic> getFollowList(int userId, int page, int pageSize);

	/**
	 * 获取粉丝列表
	 *
	 * @return 粉丝列表
	 */
	ListVO<FansBasic> getFansList(int userId, int page, int pageSize);

}
