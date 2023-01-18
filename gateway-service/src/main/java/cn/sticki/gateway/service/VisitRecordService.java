package cn.sticki.gateway.service;

import cn.sticki.gateway.filter.AuthorizeFilter;
import cn.sticki.gateway.mapper.VisitRecordMapper;
import cn.sticki.gateway.pojo.VisitRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * 访问服务
 *
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/17 19:49
 */
@Slf4j
@Service
public class VisitRecordService extends ServiceImpl<VisitRecordMapper, VisitRecord> {

	private final String attributeKey = "visitRecord";

	public VisitRecord get(ServerWebExchange exchange) {
		return exchange.getAttribute(attributeKey);
	}

	public void put(ServerWebExchange exchange, VisitRecord visitRecord) {
		Map<String, Object> attributes = exchange.getAttributes();
		attributes.put(attributeKey, visitRecord);
	}

	public void saveRecord(ServerWebExchange exchange) {
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();
		VisitRecord visitRecord = get(exchange);

		// 设置访问状态和用户id（如果有）
		if (response.getStatusCode() != null) {
			visitRecord.setStatus(response.getStatusCode().value());
		}
		visitRecord.setUserId(AuthorizeFilter.getUserId(request));

		// 打印访问情况
		log.info(visitRecord.toString());

		// 插入数据库
		this.save(visitRecord);
	}

}
