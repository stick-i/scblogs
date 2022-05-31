package cn.sticki.resource.service;

import cn.sticki.resource.pojo.City;
import cn.sticki.resource.pojo.Province;
import cn.sticki.resource.pojo.University;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UniversityService extends IService<University> {

	/**
	 * 获取省份信息
	 *
	 * @return 省份信息列表
	 */
	List<Province> getProvinceList();

	/**
	 * 获取城市信息
	 *
	 * @param provinceId 省份id
	 * @return 城市信息列表
	 */
	List<City> getCityList(int provinceId);

	/**
	 * 获取高校信息
	 *
	 * @param cityId 城市id
	 * @return 高校信息列表
	 */
	List<University> getUniversityList(int cityId);

}
