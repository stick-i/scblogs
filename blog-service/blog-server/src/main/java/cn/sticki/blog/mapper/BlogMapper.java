package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 阿杆
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

	/**
	 * 获取各状态下的博客数量
	 *
	 * @param authorId 作者id
	 * @return 博客数量列表
	 */
	@Select("select `status`,count(*) as number from blog where author_id = #{authorId} group by `status` order by status;")
	List<BlogCount> selectBlogCountListByAuthorId(int authorId);

}
