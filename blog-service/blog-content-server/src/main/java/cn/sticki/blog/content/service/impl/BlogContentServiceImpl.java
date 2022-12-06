package cn.sticki.blog.content.service.impl;

import cn.sticki.blog.content.mapper.BlogRepository;
import cn.sticki.blog.content.pojo.BlogDoc;
import cn.sticki.blog.content.pojo.BlogListVO;
import cn.sticki.blog.content.pojo.SearchQuery;
import cn.sticki.blog.content.service.BlogContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 17:46
 */
@Slf4j
@Service
public class BlogContentServiceImpl implements BlogContentService {

	@Resource
	private BlogRepository blogRepository;

	/**
	 * 搜索博客
	 *
	 * @param searchQuery 搜索条件
	 * @return 搜索到的结果
	 */
	@Override
	public BlogListVO searchBlog(SearchQuery searchQuery) {
		log.debug("searchQuery:{}", searchQuery);
		// 1. 获取数据
		SearchPage<BlogDoc> searchPage = blogRepository.findByDescriptiveContent(
				// 1.1 设置key
				searchQuery.getKey(),
				// 1.2 设置分页，这里是从第0页开始的
				PageRequest.of(searchQuery.getPage() - 1, searchQuery.getSize()));
		log.debug("result number:{}", searchPage.getTotalElements());
		// 2. 数据解析
		List<SearchHit<BlogDoc>> searchHitList = searchPage.getContent();
		ArrayList<BlogDoc> blogDocList = new ArrayList<>(searchHitList.size());
		for (SearchHit<BlogDoc> blogHit : searchHitList) {
			// 2.1 获取博客数据
			BlogDoc blogDoc = blogHit.getContent();
			// 2.2 获取高亮数据
			Map<String, List<String>> fields = blogHit.getHighlightFields();
			if (fields.size() > 0) {
				// 2.3 通过反射，将高亮数据替换到原来的博客数据中
				BeanMap beanMap = BeanMap.create(blogDoc);
				for (String name : fields.keySet()) {
					beanMap.put(name, fields.get(name).get(0));
				}
			}
			// 2.4 博客数据插入列表
			blogDocList.add(blogDoc);
		}
		// 3. 设置返回值信息
		BlogListVO blogListVO = new BlogListVO();
		blogListVO.setRecords(blogDocList);
		blogListVO.setCurrent(searchPage.getNumber() + 1);
		blogListVO.setTotal(searchPage.getTotalElements());
		blogListVO.setSize(searchQuery.getSize());
		return blogListVO;
	}

}
