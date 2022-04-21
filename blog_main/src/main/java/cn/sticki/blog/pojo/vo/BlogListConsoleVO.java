package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.dto.BlogCountDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BlogListConsoleVO extends BlogListVO {

	BlogCountDTO count;// 各状态博客数量

}
