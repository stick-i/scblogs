package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.dto.CommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentListVO extends ListVO<CommentDTO> {

	/**
	 * 总评论数量（包括二级评论）
	 */
	long allCount = 0;

}
