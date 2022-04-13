package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

	@Select("select * from user where username = #{username} ;")
	User selectByUsername(String username);

	@Delete("delete from user where username = #{username} ;")
	int deleteByUsername(String username);

}
