package cn.sticki.message.client;

import cn.sticki.common.result.RestResult;
import cn.sticki.message.pojo.MailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 消息服务模块接口客户端
 *
 * @author 阿杆
 */
@FeignClient(value = "message-server", fallback = MessageClientResolver.class)
public interface MessageClient {

	/**
	 * 发送邮件
	 *
	 * @param mail 邮件信息
	 * @return 发送情况
	 */
	@PostMapping(value = "/send/mail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<Object> sendMail(MailDTO mail);

}
