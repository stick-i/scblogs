package cn.sticki.resource.controller;

import cn.sticki.resource.pojo.City;
import cn.sticki.resource.pojo.Province;
import cn.sticki.resource.pojo.University;
import cn.sticki.resource.service.UniversityService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 选择高校信息相关
 *
 * @author 阿杆
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
	public List<Province> getProvinceList() {
		return universityService.getProvinceList();
	}

	/**
	 * 获取城市信息
	 *
	 * @param provinceId 省份id
	 */
	@GetMapping("/city")
	public List<City> getCityList(@RequestParam int provinceId) {
		return universityService.getCityList(provinceId);
	}

	/**
	 * 获取高校信息
	 *
	 * @param cityId 城市id
	 */
	@GetMapping("/university")
	public List<University> getUniversityList(@RequestParam int cityId) {
		return universityService.getUniversityList(cityId);
	}

	/**
	 * 通过院校代码获取名称
	 *
	 * @param schoolCode 院校代码
	 */
	@GetMapping("/university/name")
	public String getUniversityName(@RequestParam Integer schoolCode) {
		return universityService.getUniversityName(schoolCode);
	}

}
