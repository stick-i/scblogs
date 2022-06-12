package cn.sticki.user.service;

import cn.sticki.user.pojo.FansViewListVO;
import cn.sticki.user.pojo.FollowViewListVO;

import java.util.List;

/**
 * @author 阿杆
 */
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
	 * @param userId   用户od
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 关注列表
	 */
	FollowViewListVO getFollowList(int userId, int page, int pageSize);

	/**
	 * 获取粉丝列表
	 *
	 * @param userId   用户od
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 粉丝列表
	 */
	FansViewListVO getFansList(int userId, int page, int pageSize);

	/**
	 * 获取关注列表id
	 *
	 * @param userId 用户id
	 * @return id列表
	 */
	List<Integer> getFollowIdList(int userId);

}
