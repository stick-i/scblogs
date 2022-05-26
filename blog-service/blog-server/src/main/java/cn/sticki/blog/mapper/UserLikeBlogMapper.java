package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.BlogBasic;
import cn.sticki.blog.pojo.UserLikeBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserLikeBlogMapper extends BaseMapper<UserLikeBlog> {

	@MapKey("value")
	Map<Integer, Integer> selectMapByUserIdAndBlogIdList(Integer userId, List<Integer> blogIdList);

	/**
	 * 查询用户点赞博客列表的信息
	 *
	 * @param userId  用户id
	 * @param lastSql 添加到末尾的sql
	 */
	List<BlogBasic> selectUserLikeBlogList(Integer userId, String lastSql);

}
