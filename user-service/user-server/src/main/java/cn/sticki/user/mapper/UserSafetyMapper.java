package cn.sticki.user.mapper;

import cn.sticki.user.pojo.UserSafety;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserSafetyMapper extends BaseMapper<UserSafety> {

	/**
	 * 登录时使用，todo 后续可以改进成 邮箱、手机号均可登录
	 *
	 * @param username 用户名
	 * @param password 密码
	 */
	@Select("select * from user_safety where username = #{username} and password = #{password};")
	UserSafety selectByUsernameAndPassword(String username, String password);

	/**
	 * 通过邮箱查找用户账号密码信息
	 *
	 * @param mail 邮箱
	 */
	@Select("select * from user_safety where mail = #{mail}")
	UserSafety selectByMail(String mail);

	@Delete("delete from user_safety where username = #{username} ;")
	int deleteByUsername(String username);

	@Update("update user_safety set `password` = #{password} where user_id = #{id};")
	int updatePasswordById(Integer id, String password);

	@Update("update user_safety set `mail` = #{mail} where user_id = #{id} ")
	int updateMailById(Integer id, String mail);

}
