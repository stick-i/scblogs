package cn.sticki.resource.pojo;

import lombok.Data;

@Data
public class City {

	/**
	 * 城市id
	 */
	Integer id;

	/**
	 * 城市名称
	 */
	String name;

	/**
	 * 所属省份的id
	 */
	Integer provinceId;

}
