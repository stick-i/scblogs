package cn.sticki.blog.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
	@NotNull("博客标题不能为空")
	String title;

	/**
	 * 描述
	 */
	@NotNull("博客描述不能为空")
	String description;

	/**
	 * 内容(md格式)
	 */
	@NotNull("博客内容不能为空")
	String content;

	/**
	 * 内容(html格式)
	 */
	@NotNull("博客内容不能为空")
	String contentHtml;

	/**
	 * 博客状态码 发表状态（1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）
	 */
	@NotNull("博客状态码不能为空")
	Integer status;

	/**
	 * 封面图
	 */
	MultipartFile coverImageFile;

	/**
	 * 博客创作类型：1. 原创; 2. 转载
	 */
	Integer writeType;

}
