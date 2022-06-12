package cn.sticki.resource.service.impl;

import cn.sticki.resource.mapper.CityMapper;
import cn.sticki.resource.mapper.ProvinceMapper;
import cn.sticki.resource.mapper.UniversityMapper;
import cn.sticki.resource.pojo.City;
import cn.sticki.resource.pojo.Province;
import cn.sticki.resource.pojo.University;
import cn.sticki.resource.service.UniversityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 阿杆
 */
@Slf4j
@Service
public class UniversityServiceImpl extends ServiceImpl<UniversityMapper, University> implements UniversityService {

	@Resource
	private ProvinceMapper provinceMapper;

	@Resource
	private CityMapper cityMapper;

	@Resource
	private UniversityMapper universityMapper;

	@Override
	public List<Province> getProvinceList() {
		return provinceMapper.selectList(null);
	}

	@Override
	public List<City> getCityList(int provinceId) {
		LambdaQueryWrapper<City> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(City::getProvinceId, provinceId);
		return cityMapper.selectList(wrapper);
	}

	@Override
	public List<University> getUniversityList(int cityId) {
		LambdaQueryWrapper<University> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(University::getCityId, cityId);
		return universityMapper.selectList(wrapper);
	}

	@Override
	public String getUniversityName(int schoolCode) {
		LambdaQueryWrapper<University> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(University::getCode, schoolCode);
		University university = universityMapper.selectOne(wrapper);
		return university == null ? null : university.getName();
	}

}
