package cn.sticki.blog.content.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 17:45
 */
@Data
public class SearchQuery {

	/**
	 * 搜索的内容
	 */
	@NotNull
	String key;

	/**
	 * 判断字段
	 */
	String sortBy;

	/**
	 * 当前页
	 */
	@Range(min = 1, max = 50)
	int page = 1;

	/**
	 * 页大小
	 */
	@Range(min = 1, max = 20)
	int size = 10;

}
