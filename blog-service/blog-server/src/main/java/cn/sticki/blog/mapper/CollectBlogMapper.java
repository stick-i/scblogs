package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.BlogView;
import cn.sticki.blog.pojo.domain.CollectBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CollectBlogMapper extends BaseMapper<CollectBlog> {

	@MapKey("value")
	Map<Integer, Integer> selectMapByUserIdAndBlogIdList(Integer userId, Integer[] blogIdList);

	List<BlogView> selectUserLikeBlogList(Integer userId, String lastSql);

}
