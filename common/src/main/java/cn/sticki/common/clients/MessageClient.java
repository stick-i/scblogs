package cn.sticki.common.clients;

import cn.sticki.common.pojo.MailDTO;
import cn.sticki.common.pojo.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "message-server")
public interface MessageClient {

	@PostMapping(value = "/send/mail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	RestResult<Object> sendMail(MailDTO mail);

}
