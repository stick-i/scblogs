package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.dto.BlogCountDTO;
import lombok.Data;

import java.util.List;

@Data
public class BlogListVO {

	BlogCountDTO count;// 各状态博客数量

	List<Blog> blogList;// 博客信息列表

	Integer page;// 当前页

	Integer pageSize;// 页大小

	Integer total;// 博客总数

}
