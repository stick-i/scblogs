package cn.sticki.blog.mapper;

import cn.sticki.blog.pojo.domain.BlogGeneral;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BlogGeneralMapper extends BaseMapper<BlogGeneral> {

	@Update("update blog_general set like_num = like_num + 1 where blog_id = #{blogId};")
	int increaseLikeNum(Integer blogId);

	@Update("update blog_general set like_num = like_num - 1 where blog_id = #{blogId};")
	int decreaseLikeNum(Integer blogId);

	@Update("update blog_general set collection_num = collection_num + 1 where blog_id = #{blogId};")
	int increaseCollectionNum(Integer blogId);

	@Update("update blog_general set collection_num = collection_num - 1 where blog_id = #{blogId};")
	int decreaseCollectionNum(Integer blogId);

	@Update("update blog_general set comment_num = comment_num + 1 where blog_id = #{blogId};")
	int increaseCommentNum(Integer blogId);

	@Update("update blog_general set comment_num = comment_num - 1 where blog_id = #{blogId};")
	int decreaseCommentNum(Integer blogId);

}
