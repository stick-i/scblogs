package cn.sticki.blog.client;

import cn.sticki.blog.dto.BlogDTO;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * BlogClient请求失败时的熔断处理类
 *
 * @author 阿杆
 */
@Slf4j
@Component
public class BlogClientResolver implements BlogClient {

	/**
	 * 获取博客基本信息
	 *
	 * @param id 博客id
	 * @return 博客信息
	 */
	@Override
	public RestResult<BlogDTO> getBlogInfo(Integer id) {
		log.error("Blog 服务异常：getBlogInfo 请求失败");
		return RestResult.fail("request fail");
	}

}
