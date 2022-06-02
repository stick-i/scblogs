package cn.sticki.resource.pojo;

import lombok.Data;

@Data
public class UniversityView {

	/**
	 * id
	 */
	Integer id;

	/**
	 * 学校名称
	 */
	String name;

	/**
	 * 学校标识码
	 */
	Integer identifier;

	/**
	 * 院校代码
	 */
	Integer code;

	/**
	 * 所属城市
	 */
	String city;

	/**
	 * 所属省份
	 */
	String province;

	/**
	 * 办学层次（1本科，2专科）
	 */
	Integer rank;

	/**
	 * 备注
	 */
	String remark;

}
