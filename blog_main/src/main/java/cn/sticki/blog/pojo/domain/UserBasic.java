package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserBasic {

	String username; //  COMMENT '用户名'

	String realname; //  COMMENT '真实姓名'

	Timestamp birthday; //  COMMENT '生日时间戳'

	String intro; //  DEFAULT NULL COMMENT '个人简介'

	String gender; //  COMMENT '性别'

	Integer cityId; //  COMMENT '城市id'

	Integer provinceId; //  COMMENT '省份id'

	Timestamp modifiedTime; //  COMMENT '信息修改时间'

	Timestamp nameModifyTime; //  COMMENT '昵称修改时间'

	Timestamp startWorkTime; //  COMMENT '开始工作的时间'

}
