package cn.sticki.resource.mapper;

import cn.sticki.resource.pojo.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author 阿杆
 */
@Mapper
public interface ImageMapper extends BaseMapper<Image> {

	/**
	 * 浏览量 +1
	 *
	 * @param url 访问的资源
	 */
	@Update("update resource.image set visit = visit + 1 where url = #{blogId};")
	void increaseVisit(String url);

}
