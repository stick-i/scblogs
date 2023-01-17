package cn.sticki.gateway.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/17 19:55
 */
@Data
public class VisitRecord {

	String ip;

	String method;

	String uri;

	String queryParam;

	String status;

	Integer userId;

	LocalDateTime creatTime;

}
