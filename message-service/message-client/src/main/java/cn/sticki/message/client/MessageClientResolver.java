package cn.sticki.message.client;

import cn.sticki.common.result.RestResult;
import cn.sticki.message.pojo.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 消息服务模块接口客户端熔断器
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/12 9:48
 */
@Slf4j
@Component
public class MessageClientResolver implements MessageClient {

	/**
	 * 发送邮件
	 *
	 * @param mail 邮件信息
	 * @return 发送情况
	 */
	@Override
	public RestResult<Object> sendMail(MailDTO mail) {
		log.error("Message 服务异常：sendMail(MailDTO mail) 请求失败");
		return RestResult.fail("request fail");
	}

}
