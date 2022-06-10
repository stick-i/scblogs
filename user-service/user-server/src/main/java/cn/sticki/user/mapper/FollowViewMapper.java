package cn.sticki.user.mapper;

import cn.sticki.user.pojo.FollowView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowViewMapper extends BaseMapper<FollowView> {

	@Select("select follow_id from user.follow_view where user_id = #{userId};")
	List<Integer> selectFollowIdByUserId(int userId);

}
