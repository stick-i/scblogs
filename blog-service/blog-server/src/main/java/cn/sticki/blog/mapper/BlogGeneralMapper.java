package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.BlogGeneral;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author 阿杆
 */
@Mapper
public interface BlogGeneralMapper extends BaseMapper<BlogGeneral> {

	/**
	 * 博客点赞数+1
	 *
	 * @param blogId 博客id
	 */
	@Update("update blog_general set like_num = like_num + 1 where blog_id = #{blogId};")
	void increaseLikeNum(Integer blogId);

	/**
	 * 博客点赞数-1
	 *
	 * @param blogId 博客id
	 */
	@Update("update blog_general set like_num = like_num - 1 where blog_id = #{blogId};")
	void decreaseLikeNum(Integer blogId);

	/**
	 * 博客收藏数+1
	 *
	 * @param blogId 博客id
	 */
	@Update("update blog_general set collection_num = collection_num + 1 where blog_id = #{blogId};")
	void increaseCollectionNum(Integer blogId);

	/**
	 * 博客收藏数-1
	 *
	 * @param blogId 博客id
	 */
	@Update("update blog_general set collection_num = collection_num - 1 where blog_id = #{blogId};")
	void decreaseCollectionNum(Integer blogId);

	/**
	 * 博客评论数+1
	 *
	 * @param blogId 博客id
	 */
	@Update("update blog_general set comment_num = comment_num + 1 where blog_id = #{blogId};")
	void increaseCommentNum(Integer blogId);

	/**
	 * 博客评论数-1
	 *
	 * @param blogId 博客id
	 */
	@Update("update blog_general set comment_num = comment_num - 1 where blog_id = #{blogId};")
	void decreaseCommentNum(Integer blogId);

}
