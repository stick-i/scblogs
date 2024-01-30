package cn.sticki.comment.client;

import cn.sticki.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 评论模块接口客户端
 *
 * @author 阿杆
 */
@Import(CommentClientResolver.class)
@FeignClient(value = "comment-server", fallback = CommentClientResolver.class)
public interface CommentClient {

	/**
	 * 获取评论列表<br>
	 * todo 这里的类是用的object
	 *
	 * @param blogId   博客id
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 评论列表
	 */
	@GetMapping("/comment/list")
	RestResult<Object> getCommentList(@RequestParam Integer blogId, @RequestParam Integer page, @RequestParam(defaultValue = "3") Integer pageSize);

}
