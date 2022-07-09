package cn.sticki.blog.content.service;

import cn.sticki.blog.content.pojo.BlogListVO;
import cn.sticki.blog.content.pojo.SearchQuery;
import cn.sticki.common.result.RestResult;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 17:39
 */
public interface BlogContentService {

	/**
	 * 搜索博客
	 *
	 * @param searchQuery 搜索条件
	 * @return 搜索到的结果
	 */
	RestResult<BlogListVO> searchBlog(SearchQuery searchQuery);

}
