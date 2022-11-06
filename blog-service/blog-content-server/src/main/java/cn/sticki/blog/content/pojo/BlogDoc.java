package cn.sticki.blog.content.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Blog ES文档类型
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/8 15:24
 */
@Data
@Document(indexName = "blog")
public class BlogDoc {

	/**
	 * 博客id
	 */
	@Id
	Integer id;

	/**
	 * 作者id
	 */
	@Field(type = FieldType.Integer)
	Integer authorId;

	/**
	 * 标题
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = "descriptiveContent")
	String title;

	/**
	 * 描述
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = "descriptiveContent")
	String description;

	/**
	 * 院校代码
	 */
	@Field(type = FieldType.Integer)
	Integer schoolCode;

	/**
	 * 封面图
	 */
	@Field(type = FieldType.Keyword, index = false)
	String coverImage;

	/**
	 * 创建时间
	 */
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss")
	Date createTime;

	/**
	 * 发表时间
	 */
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss")
	Date releaseTime;

	/**
	 * 修改时间
	 */
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss")
	Date modifiedTime;

	/**
	 * 发表状态（1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）
	 */
	@Field(type = FieldType.Integer)
	Integer status;

	/**
	 * 博客创作类型：1. 原创; 2. 转载
	 */
	@Field(type = FieldType.Integer)
	Integer writeType;

	/**
	 * 由其他属性copy而来，主要用于搜索功能，并非该实体类中的成员
	 */
	@JsonIgnore
	@Field(type = FieldType.Text, analyzer = "ik_max_word", ignoreFields = "descriptiveContent", excludeFromSource = true)
	String descriptiveContent;

}
