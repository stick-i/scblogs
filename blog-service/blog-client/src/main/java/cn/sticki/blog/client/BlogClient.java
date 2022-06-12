package cn.sticki.blog.client;

import cn.sticki.blog.dto.BlogDTO;
import cn.sticki.common.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 阿杆
 */
@FeignClient(value = "blog-server")
public interface BlogClient {

	/**
	 * 获取博客基本信息
	 *
	 * @param id 博客id
	 * @return 博客信息
	 */
	@GetMapping("/blog/blog")
	RestResult<BlogDTO> getBlogInfo(@RequestParam Integer id);

}
