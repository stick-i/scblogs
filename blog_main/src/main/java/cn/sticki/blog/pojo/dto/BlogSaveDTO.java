package cn.sticki.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogSaveDTO {

	Integer id; // 博客id(若无则不填)

	String author; // 作者

	String title; // 标题

	String description; // 描述

	String content; // 内容(md格式)

	Integer status; // 博客状态码

	MultipartFile coverImageFile; // 封面图

}
