package cn.sticki.blog.client;

import cn.sticki.blog.dto.BlogDTO;
import cn.sticki.blog.dto.BlogGeneralDTO;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * BlogClient请求失败时的熔断处理类
 *
 * @author 阿杆
 */
@Slf4j
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

	/**
	 * 获取用户所发表的博客各项数据
	 *
	 * @param userIds 用户id
	 * @return 用户发表博客数据统计
	 */
	@Override
	public RestResult<List<BlogGeneralDTO>> getBlogUserGeneral(Integer[] userIds) {
		log.error("Blog 服务异常：getBlogUserGeneral 请求失败");
		return RestResult.fail("request fail");
	}

}
