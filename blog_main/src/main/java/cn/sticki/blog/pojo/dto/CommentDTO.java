package cn.sticki.blog.pojo.dto;

import cn.sticki.blog.pojo.domain.CommentBasic;
import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {

	CommentBasic info;

	List<CommentBasic> sub;

	long subCount;

}
