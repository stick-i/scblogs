package cn.sticki.resource.mapper;

import cn.sticki.resource.pojo.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {

	@Update("update image set visit = visit + 1 where url = #{blogId};")
	void increaseVisit(String url);

}
