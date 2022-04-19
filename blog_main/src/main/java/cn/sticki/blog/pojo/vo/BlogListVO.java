package cn.sticki.blog.pojo.vo;

import cn.sticki.blog.pojo.domain.Blog;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BlogListVO {

	Map count;// 各状态博客数量

	List<Blog> blogList;// 博客信息列表

	Integer page;// 当前页

	Integer pageSize;// 页大小

	Integer total;// 博客总数

}
