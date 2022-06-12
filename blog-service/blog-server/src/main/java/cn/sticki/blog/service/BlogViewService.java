package cn.sticki.blog.service;

import cn.sticki.blog.pojo.bo.ActionStatusBO;
import cn.sticki.blog.pojo.domain.BlogView;
import cn.sticki.blog.pojo.vo.BlogContentVO;
import cn.sticki.blog.pojo.vo.BlogInfoListVO;
import cn.sticki.blog.pojo.vo.BlogStatusListVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 阿杆
 */
public interface BlogViewService extends IService<BlogView> {

	/**
	 * 获取推荐博客列表
	 *
	 * @param userId   用户id
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 博客（含用户状态）列表
	 */
	BlogStatusListVO getRecommendBlogList(Integer userId, int page, int pageSize);

	/**
	 * 获取最新博客列表
	 *
	 * @param userId     用户id
	 * @param schoolCode 院校代码
	 * @param page       第几页
	 * @param pageSize   页大小
	 * @return 博客（含用户状态）列表
	 */
	BlogStatusListVO getNewBlogList(Integer userId, Integer schoolCode, int page, int pageSize);

	/**
	 * 获取最新博客列表
	 *
	 * @param userId   用户id
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 博客（含用户状态）列表
	 */
	BlogStatusListVO getFollowBlogList(Integer userId, int page, int pageSize);

	/**
	 * 获取搜索博客列表
	 *
	 * @param search   搜索值
	 * @param page     第几页
	 * @param pageSize 页大小
	 * @return 博客列表
	 */
	BlogInfoListVO searchBlog(String search, int page, int pageSize);

	/**
	 * 获取用户对该博客的状态，是否点赞或者是否收藏
	 *
	 * @param userId  用户id
	 * @param blogIds 博客id数组
	 * @return 一个map，key为博客id，value为状态
	 */
	Map<Integer, ActionStatusBO> getBlogActionStatus(Integer userId, Integer... blogIds);

	/**
	 * 获取博客内容的页面
	 *
	 * @param id     博客id
	 * @param userId 查看的用户id（如果有）
	 * @return 获取博客内容（html）
	 */
	BlogContentVO getBlogContentHtml(Integer id, Integer userId);

}
