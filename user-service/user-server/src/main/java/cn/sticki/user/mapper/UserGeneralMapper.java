package cn.sticki.user.mapper;

import cn.sticki.user.pojo.UserGeneral;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author durance
 */
@Mapper
public interface UserGeneralMapper extends BaseMapper<UserGeneral> {

	/**
	 * 批量添加用户统计信息
	 *
	 * @param userIds 用户id数组
	 * @return 影响行数
	 */
	Integer insertAllUser(@Param("userIds") Integer[] userIds);

	/**
	 * 增加访问数
	 *
	 * @param userId 用户id
	 * @return 影响行数
	 */
	Integer updateViewNumByUserId(@Param("userId") Integer userId);

	/**
	 * 修改点赞数
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateLikeNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改粉丝数
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateFansNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改评论数
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateCommentNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改收藏数
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateCollectNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改博客数
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateBlogNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

}
