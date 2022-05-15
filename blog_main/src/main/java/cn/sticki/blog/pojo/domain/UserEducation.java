package cn.sticki.blog.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserEducation {

	/**
	 * 用户名
	 */
	@TableId
	String username;

	/**
	 * 创建时间
	 */
	Timestamp createTime;

	/**
	 * 学校名称
	 */
	String school;

	/**
	 * 学校id
	 */
	Integer schoolId;

	/**
	 * 专业
	 */
	String profession;

	/**
	 * 学位程度
	 */
	String degree;

	/**
	 * 入学时间
	 */
	Timestamp startTime;

	/**
	 * 毕业时间
	 */
	Timestamp endTime;

	/**
	 * 图片链接
	 */
	String auditImgUrl;

	/**
	 * 安全审核状态
	 */
	String safetyAuditStatus;

	/**
	 * 状态码
	 */
	String status;

	/**
	 * 更新时间
	 */
	Timestamp modifiedTime;

}
