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
	 * 增加访问量
	 *
	 * @param userId 用户id
	 * @return 影响行数
	 */
	Integer updateViewNumByUserId(@Param("userId") Integer userId);

	/**
	 * 修改访问量
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateLikeNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改访问量
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateFansNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改访问量
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateCommentNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改访问量
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateCollectNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

	/**
	 * 修改访问量
	 *
	 * @param userId 用户id
	 * @param num    修改的量
	 * @return 影响行数
	 */
	Integer updateBlogNumByUserId(@Param("userId") Integer userId, @Param("num") Integer num);

}
