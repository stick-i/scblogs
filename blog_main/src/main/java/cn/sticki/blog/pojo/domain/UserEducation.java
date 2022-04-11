package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserEducation {

	String username; //  '用户名'

	Timestamp createTime; //  '创建时间'

	String school; //  '学校名称'

	Integer schoolId; //  '学校id'

	String profession; //  '专业'

	String degree; //  '学位程度'

	Timestamp startTime; //  '入学时间'

	Timestamp endTime; //  '毕业时间'

	String auditImgUrl; // '图片链接'

	String safetyAuditStatus; //   '安全审核状态'

	String status; // '状态码'

	Timestamp modifiedTime; //  '更新时间'

}
