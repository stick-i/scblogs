package cn.sticki.resource.controller;

import cn.sticki.common.result.RestResult;
import cn.sticki.resource.pojo.City;
import cn.sticki.resource.pojo.Province;
import cn.sticki.resource.pojo.University;
import cn.sticki.resource.service.UniversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 选择高校信息相关
 */
@Slf4j
@RestController
@RequestMapping("/resource")
public class UniversityController {

	@Resource
	private UniversityService universityService;

	/**
	 * 获取省份信息
	 */
	@GetMapping("/province")
	public RestResult<List<Province>> getProvinceList() {
		return new RestResult<>(universityService.getProvinceList());
	}

	/**
	 * 获取城市信息
	 *
	 * @param provinceId 省份id
	 */
	@GetMapping("/city")
	public RestResult<List<City>> getCityList(@RequestParam int provinceId) {
		return new RestResult<>(universityService.getCityList(provinceId));
	}

	/**
	 * 获取高校信息
	 *
	 * @param cityId 城市id
	 */
	@GetMapping("/university")
	public RestResult<List<University>> getUniversityList(@RequestParam int cityId) {
		return new RestResult<>(universityService.getUniversityList(cityId));
	}

}
