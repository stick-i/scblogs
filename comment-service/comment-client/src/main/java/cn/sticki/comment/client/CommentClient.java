package cn.sticki.comment.client;

import cn.sticki.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("comment-server")
public interface CommentClient {

	@GetMapping("/comment/list")
		// todo 这里的类是用的object
	RestResult<Object> getCommentList(@RequestParam Integer blogId, @RequestParam Integer page, @RequestParam(defaultValue = "3") Integer pageSize);

}
