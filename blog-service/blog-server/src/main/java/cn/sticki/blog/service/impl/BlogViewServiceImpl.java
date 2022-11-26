package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.mapper.*;
import cn.sticki.blog.pojo.bo.ActionStatusBO;
import cn.sticki.blog.pojo.bo.BlogInfoBO;
import cn.sticki.blog.pojo.bo.BlogStatusBO;
import cn.sticki.blog.pojo.domain.BlogView;
import cn.sticki.blog.pojo.vo.BlogContentVO;
import cn.sticki.blog.pojo.vo.BlogInfoListVO;
import cn.sticki.blog.pojo.vo.BlogStatusListVO;
import cn.sticki.blog.sdk.BlogOperateDTO;
import cn.sticki.blog.service.BlogViewService;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.result.RestResult;
import cn.sticki.user.client.UserClient;
import cn.sticki.user.dto.UserDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.sticki.blog.sdk.BlogMqConstants.BLOG_EXCHANGE;
import static cn.sticki.blog.sdk.BlogMqConstants.BLOG_OPERATE_READ_KEY;

/**
 * @author 阿杆
 */
@Slf4j
@Service
public class BlogViewServiceImpl extends ServiceImpl<BlogViewMapper, BlogView> implements BlogViewService {

	@Resource
	private BlogViewMapper blogViewMapper;

	@Resource
	private LikeBlogMapper likeBlogMapper;

	@Resource
	private CollectBlogMapper collectBlogMapper;

	@Resource
	private BlogContentHtmlMapper blogContentHtmlMapper;

	@Resource
	private UserClient userClient;

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Resource
	private BlogMapper blogMapper;

	@Override
	public BlogStatusListVO getRecommendBlogList(Integer userId, int page, int pageSize) {
		// todo 最好根据用户标签来推
		BlogInfoListVO blogInfoListVO = searchBlog(null, page, pageSize);
		BlogStatusListVO blogStatusListVO = BeanUtil.copyProperties(blogInfoListVO, BlogStatusListVO.class);
		addActionStatusToList(blogStatusListVO.getRecords(), userId);
		return blogStatusListVO;
	}

	@Override
	public BlogStatusListVO getNewBlogList(Integer userId, Integer schoolCode, int page, int pageSize) {
		// 设置查询条件：schoolCode相同、状态为公开，并以id倒序（表示发布时间最新）
		LambdaQueryWrapper<BlogView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(schoolCode != null, BlogView::getSchoolCode, schoolCode);
		wrapper.eq(BlogView::getStatus, BlogStatusType.PUBLISH.getValue()).orderByDesc(BlogView::getId);
		// 设置分页
		IPage<BlogView> iPage = new Page<>(page, pageSize);
		blogViewMapper.selectPage(iPage, wrapper);
		// 整合信息
		return viewListVoToActionStatusListVO(iPage, userId);
	}

	@Override
	public BlogStatusListVO getFollowBlogList(@NotNull Integer userId, int page, int pageSize) {
		// 设置查询条件：作者为关注的对象，状态为公开，并以id倒序（表示发布时间最新）
		LambdaQueryWrapper<BlogView> wrapper = new LambdaQueryWrapper<>();
		RestResult<List<Integer>> result = userClient.getFollowIdList(userId);
		wrapper.in(result.getStatus() && result.getData().size() > 0, BlogView::getAuthorId, result.getData());
		wrapper.eq(BlogView::getStatus, BlogStatusType.PUBLISH.getValue()).orderByDesc(BlogView::getId);
		IPage<BlogView> iPage = new Page<>(page, pageSize);
		blogViewMapper.selectPage(iPage, wrapper);
		return viewListVoToActionStatusListVO(iPage, userId);
	}

	@Override
	public BlogInfoListVO searchBlog(String search, int page, int pageSize) {
		// 设置查询条件：公开的，并以得分排序
		LambdaQueryWrapper<BlogView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(BlogView::getStatus, BlogStatusType.PUBLISH.getValue()).orderByDesc(BlogView::getScore);
		// 设置模糊查询
		wrapper.like(search != null, BlogView::getTitle, search);
		// 设置分页
		IPage<BlogView> iPage = new Page<>(page, pageSize);
		blogViewMapper.selectPage(iPage, wrapper);
		// 开始整合信息
		return viewListToInfoListVO(iPage);
	}

	@Override
	public Map<Integer, ActionStatusBO> getBlogActionStatus(Integer userId, Integer... blogIds) {
		HashMap<Integer, ActionStatusBO> map = new HashMap<>(blogIds.length);
		// 空列表不查数据，会报错
		if (blogIds.length == 0) {
			return map;
		}
		Map<Integer, Integer> likeMap = likeBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIds);
		Map<Integer, Integer> collectMap = collectBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIds);
		for (Integer blogId : blogIds) {
			ActionStatusBO statusDTO = new ActionStatusBO();
			statusDTO.setIsLike(likeMap.containsKey(blogId));
			statusDTO.setIsCollect(collectMap.containsKey(blogId));
			map.put(blogId, statusDTO);
		}
		return map;
	}

	@Override
	public BlogContentVO getBlogContentHtml(Integer id, Integer userId) {
		BlogContentVO blog = new BlogContentVO();
		// 博客内容
		blog.setContent(blogContentHtmlMapper.selectById(id));
		// 博客基本信息
		BlogView blogView = blogViewMapper.selectById(id);
		if (blogView == null) {
			return null;
		}
		BlogStatusBO blogStatusBO = BeanUtil.copyProperties(blogView, BlogStatusBO.class);
		if (userId != null) {
			// 获取该用户对该博客的状态
			Map<Integer, ActionStatusBO> status = getBlogActionStatus(userId, id);
			blogStatusBO.setActionStatus(status.get(id));
		}
		blog.setInfo(blogStatusBO);
		// 博客作者信息
		RestResult<UserDTO> result = userClient.getByUserId(blogView.getAuthorId());
		blog.setAuthor(result.getData());

		// 封装好请求体后，发送到MQ
		BlogOperateDTO blogOperateDTO = new BlogOperateDTO();
		blogOperateDTO.setBlogId(id);
		blogOperateDTO.setAuthorId(blogView.getAuthorId());
		blogOperateDTO.setUserId(userId);
		rabbitTemplate.convertAndSend(BLOG_EXCHANGE, BLOG_OPERATE_READ_KEY, blogOperateDTO);
		return blog;
	}

	/**
	 * 将查询出来的数据添加上其他信息（此处只有作者信息）
	 */
	private BlogInfoListVO viewListToInfoListVO(IPage<BlogView> blogListVO) {
		BlogInfoListVO blogInfoListVO = BeanUtil.copyProperties(blogListVO, BlogInfoListVO.class);
		addAuthorNameToList(blogInfoListVO.getRecords());
		return blogInfoListVO;
	}

	/**
	 * 将查询出来的数据添加上作者信息和用户点赞的状态
	 */
	private BlogStatusListVO viewListVoToActionStatusListVO(IPage<BlogView> blogListVO, Integer userId) {
		BlogInfoListVO blogInfoListVO = viewListToInfoListVO(blogListVO);
		BlogStatusListVO blogStatusListVO = BeanUtil.copyProperties(blogInfoListVO, BlogStatusListVO.class);
		if (userId != null) {
			addActionStatusToList(blogStatusListVO.getRecords(), userId);
		}
		return blogStatusListVO;
	}

	/**
	 * 批量添加作者信息数据
	 */
	private void addAuthorNameToList(List<BlogInfoBO> blogInfoList) {
		// 获取用户id列表
		Set<Integer> userIdList = blogInfoList.stream().map(BlogInfoBO::getAuthorId).collect(Collectors.toSet());
		// 查询用户信息
		RestResult<Map<Integer, UserDTO>> result = userClient.getUserList(userIdList);
		// 判断信息是否正确
		if (result.getStatus()) {
			// 添加用户昵称
			Map<Integer, UserDTO> userMap = result.getData();
			for (BlogInfoBO blogInfoBO : blogInfoList) {
				String nickname = userMap.get(blogInfoBO.getAuthorId()).getNickname();
				blogInfoBO.setAuthorName(nickname);
			}
		}
		// return blogInfoList;
	}

	/**
	 * 批量添加用户点赞、收藏状态信息
	 *
	 * @param blogList 博客列表
	 * @param userId   用户id
	 */
	private void addActionStatusToList(List<BlogStatusBO> blogList, Integer userId) {
		// 构造博客id列表
		Integer[] blogIdList = blogList.stream().map(BlogStatusBO::getId).toArray(Integer[]::new);
		// 查询这些博客的点赞状态
		Map<Integer, ActionStatusBO> blogActionStatus = getBlogActionStatus(userId, blogIdList);

		for (BlogStatusBO record : blogList) {
			ActionStatusBO actionStatus = blogActionStatus.get(record.getId());
			record.setActionStatus(actionStatus);
		}
		// return blogList;
	}

}
