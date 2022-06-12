package cn.sticki.comment.pojo;

import cn.sticki.common.result.ListVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 阿杆
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentListVO extends ListVO<CommentVO> {

	/**
	 * 总评论数量（包括二级评论）
	 */
	@SuppressWarnings("AlibabaPojoNoDefaultValue")
	Long allCount = 0L;

}
