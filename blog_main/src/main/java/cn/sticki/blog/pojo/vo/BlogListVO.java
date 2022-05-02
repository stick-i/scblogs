package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.BlogBasic;
import lombok.Data;

import java.util.List;

@Data
public class BlogListVO {

	List<BlogBasic> blogList;// 博客信息列表

	Long page;// 当前页

	Long pageSize;// 页大小

	Long total;// 博客总数

}
