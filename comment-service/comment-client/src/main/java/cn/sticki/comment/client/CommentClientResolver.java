package cn.sticki.comment.client;

import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 评论模块接口客户端熔断器
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/12 9:44
 */
@Slf4j
@Component
public class CommentClientResolver implements CommentClient {

	/**
	 * 获取评论列表
	 *
	 * @param blogId   博客id
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 评论列表
	 */
	@Override
	public RestResult<Object> getCommentList(Integer blogId, Integer page, Integer pageSize) {
		log.error("Comment 服务异常：获取评论列表 getCommentList 请求失败");
		return RestResult.fail("request fail");
	}

}
