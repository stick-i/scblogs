package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.BlogBasic;
import cn.sticki.blog.pojo.CollectBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CollectBlogMapper extends BaseMapper<CollectBlog> {

	@MapKey("value")
	Map<Integer, Integer> selectMapByUserIdAndBlogIdList(Integer userId, Integer[] blogIdList);

	List<BlogBasic> selectUserLikeBlogList(Integer userId, String lastSql);

}
