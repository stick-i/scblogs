package cn.sticki.resource.pojo;

import lombok.Data;

@Data
public class University {

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
	Long identifier;

	/**
	 * 院校代码
	 */
	Integer code;

	/**
	 * 所属城市id
	 */
	Integer cityId;

	/**
	 * 办学层次（1本科，2专科）
	 */
	Integer rank;

	/**
	 * 备注
	 */
	String remark;

}
