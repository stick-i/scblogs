package cn.sticki.user.mapper;

import cn.sticki.user.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * @author 阿杆
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据 ID 删除
	 *
	 * @param id 主键ID
	 * @return 影响数据行
	 */
	@Override
	int deleteById(Serializable id);

}
