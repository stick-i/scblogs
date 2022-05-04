package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.dto.BlogCountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogListConsoleVO extends BlogListVO {

	BlogCountDTO count;// 各状态博客数量

}
