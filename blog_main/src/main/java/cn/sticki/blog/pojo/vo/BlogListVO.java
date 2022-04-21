package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.BlogBasic;
import lombok.Data;

import java.util.List;

@Data
public class BlogListVO {

	List<BlogBasic> blogList;// 博客信息列表

	Integer page;// 当前页

	Integer pageSize;// 页大小

	Integer total;// 博客总数

}
