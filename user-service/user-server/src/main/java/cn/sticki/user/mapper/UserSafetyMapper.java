package cn.sticki.user.mapper;

import cn.sticki.user.pojo.UserSafety;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;

/**
 * @author 阿杆
 */
@Mapper
public interface UserSafetyMapper extends BaseMapper<UserSafety> {

	/**
	 * 通过邮箱查找用户账号密码信息
	 *
	 * @param mail 邮箱
	 * @return 用户账号安全数据
	 */
	@Select("select * from `user`.`user_safety` where `mail` = #{mail}")
	UserSafety selectByMail(String mail);

	/**
	 * 更新密码
	 *
	 * @param id       用户id
	 * @param password 密码
	 * @return 影响的行数
	 */
	@Update("update `user`.`user_safety` set `password` = #{password} where `user_id` = #{id};")
	int updatePasswordById(Integer id, String password);

	/**
	 * 更新邮箱
	 *
	 * @param id   用户id
	 * @param mail 新邮箱
	 * @return 影响的行数
	 */
	@Update("update `user`.`user_safety` set `mail` = #{mail} where `user_id` = #{id} ")
	int updateMailById(Integer id, String mail);

	/**
	 * 根据 ID 删除
	 *
	 * @param id 主键ID
	 * @return 影响的行数
	 */
	@Override
	@Update("update `user_safety` set `deleted` = #{id} where `user_id` = #{id} and `deleted` = 0")
	int deleteById(Serializable id);

}
