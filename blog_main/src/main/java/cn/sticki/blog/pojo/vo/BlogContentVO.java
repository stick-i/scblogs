package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.domain.BlogContent;
import lombok.Data;

@Data
public class BlogContentVO {

	BlogBasic info;

	BlogContent content;

}
