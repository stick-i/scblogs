package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

	@Select("select * from user where username = #{username} ;")
	User selectByUsername(String username);

	@Delete("delete from user where username = #{username} ;")
	int deleteByUsername(String username);

	@Update("update user set avatar = #{avatar} where id = #{id}")
	int updateAvatarById(Integer id, String avatar);

}
