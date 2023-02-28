package cn.sticki.gateway.service;

import cn.sticki.gateway.mapper.VisitLogMapper;
import cn.sticki.gateway.pojo.VisitRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 访问日志Service类
 *
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/19 22:25
 */
@Service
public class VisitLogService extends ServiceImpl<VisitLogMapper, VisitRecord> {

}
