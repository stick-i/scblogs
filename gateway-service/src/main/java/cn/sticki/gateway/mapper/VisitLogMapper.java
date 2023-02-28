package cn.sticki.gateway.mapper;

import cn.sticki.gateway.pojo.VisitRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/18 19:47
 */
@Mapper
public interface VisitLogMapper extends BaseMapper<VisitRecord> {

}
