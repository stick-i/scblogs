package cn.sticki.user.mapper;

import cn.sticki.user.pojo.UserSafety;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserSafetyMapper extends BaseMapper<UserSafety> {

	/**
	 * 通过邮箱查找用户账号密码信息
	 *
	 * @param mail 邮箱
	 */
	@Select("select * from user_safety where mail = #{mail}")
	UserSafety selectByMail(String mail);

	@Update("update user_safety set `password` = #{password} where user_id = #{id};")
	int updatePasswordById(Integer id, String password);

	@Update("update user_safety set `mail` = #{mail} where user_id = #{id} ")
	int updateMailById(Integer id, String mail);

}
