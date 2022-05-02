package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

	@Select("select * from user_vo where username = #{username};")
	User selectByUsername(String username);

	@Delete("delete from user where username = #{username} ;")
	int deleteByUsername(String username);

	@Update("update user set avatar_url = #{avatar} where id = #{id}")
	int updateAvatarById(Integer id, String avatar);

	@Update("update user set nickname = #{nickname} where id = #{id}")
	int updateNicknameById(Integer id, String nickname);

	int updateUserById(User user);

	int insert(User user);

	@Delete("delete from user where id = #{id};")
	int deleteById(Integer id);

}
