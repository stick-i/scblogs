package cn.sticki.blog.pojo.bo;

import cn.sticki.blog.type.BlogCreateTypeEnum;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.result.RestResult;
import cn.sticki.resource.type.FileType;
import cn.sticki.resource.utils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

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
	String description;

	/**
	 * 内容(md格式)
	 */
	@NotNull("博客内容不能为空")
	String content;

	/**
	 * 内容(html格式)
	 */
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
	Integer createType;

	/**
	 * 前端入参校验
	 *
	 * @param saveFlag 是保存还是更新  true 保存， false 更新
	 */
	public void paramCheck(boolean saveFlag) {

		if (saveFlag) {
			Assert.isNull(this.id, "保存博客不应该有ID入参");
		}

		// 状态校验
		Assert.isTrue(Objects.equals(this.status, BlogStatusType.PUBLISH.getValue())
						|| Objects.equals(this.status, BlogStatusType.DRAFT.getValue())
						|| Objects.equals(this.status, BlogStatusType.PERSONAL.getValue())
				, "博客状态仅可以是草稿或发布状态或仅自己可见");

		// 创作类型校验
		Assert.isTrue(BlogCreateTypeEnum.getEnum(this.createType).isPresent(), "创作类型值非法");

		// 描述校验
		if (Objects.equals(this.status, BlogStatusType.PUBLISH.getValue())) {
			Assert.notNull(description, "发布状态，文章摘要不能为空");
		}

		// 检查封面图
		if (FileUtils.isNotEmpty(this.coverImageFile)) {
			FileUtils.checkFile(this.coverImageFile, 1024 * 1024L, FileType.JPEG, FileType.PNG);
		}
	}

}
