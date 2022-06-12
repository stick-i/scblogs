package cn.sticki.blog.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 阿杆
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogSaveBO {

	/**
	 * 博客id(若无则不填)
	 */
	Integer id;

	/**
	 * 作者id
	 */
	Integer authorId;

	/**
	 * 标题
	 */
	String title;

	/**
	 * 描述
	 */
	String description;

	/**
	 * 内容(md格式)
	 */
	String content;

	/**
	 * 内容(html格式)
	 */
	String contentHtml;

	/**
	 * 博客状态码
	 */
	Integer status;

	/**
	 * 封面图
	 */
	MultipartFile coverImageFile;

}
