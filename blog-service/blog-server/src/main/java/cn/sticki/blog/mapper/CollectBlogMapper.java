package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.CollectBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author 阿杆
 */
@Mapper
public interface CollectBlogMapper extends BaseMapper<CollectBlog> {

	/**
	 * 获取用户对某列表博客的收藏状态
	 *
	 * @param userId     用户id
	 * @param blogIdList 博客列表
	 * @return map key和value都是用户收藏的博客id
	 */
	@MapKey("value")
	Map<Integer, Integer> selectMapByUserIdAndBlogIdList(Integer userId, Integer[] blogIdList);

}
