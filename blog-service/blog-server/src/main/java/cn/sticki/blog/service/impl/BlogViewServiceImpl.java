package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.mapper.BlogContentMapper;
import cn.sticki.blog.mapper.BlogViewMapper;
import cn.sticki.blog.mapper.CollectBlogMapper;
import cn.sticki.blog.mapper.LikeBlogMapper;
import cn.sticki.blog.pojo.bo.ActionStatusBO;
import cn.sticki.blog.pojo.bo.BlogInfoBO;
import cn.sticki.blog.pojo.bo.BlogStatusBO;
import cn.sticki.blog.pojo.domain.BlogView;
import cn.sticki.blog.pojo.vo.BlogContentVO;
import cn.sticki.blog.pojo.vo.BlogInfoListVO;
import cn.sticki.blog.pojo.vo.BlogStatusListVO;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private BlogContentMapper blogContentMapper;

	@Resource
	private UserClient userClient;

	@Override
	public BlogStatusListVO getRecommendBlogList(Integer userId, int page, int pageSize) {
		// todo 最好根据用户标签来推
		BlogInfoListVO blogInfoListVO = searchBlog(null, page, pageSize);
		List<BlogInfoBO> blogList = blogInfoListVO.getRecords();
		BlogStatusListVO blogStatusListVO = BeanUtil.copyProperties(blogInfoListVO, BlogStatusListVO.class);
		// 构造博客id列表
		// List<Integer> blogIdList = blogList.stream().map(BlogBasic::getId).collect(Collectors.toList());
		Integer[] blogIdList = blogList.stream().map(BlogView::getId).toArray(Integer[]::new);
		// 查询这些博客的点赞状态
		Map<Integer, ActionStatusBO> blogActionStatus = getBlogActionStatus(userId, blogIdList);

		for (BlogStatusBO record : blogStatusListVO.getRecords()) {
			ActionStatusBO actionStatus = blogActionStatus.get(record.getId());
			record.setActionStatus(actionStatus);
		}
		return blogStatusListVO;
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
		BlogInfoListVO blogListVO = BeanUtil.copyProperties(iPage, BlogInfoListVO.class);
		// 获取用户id列表
		List<Integer> userIdList = iPage.getRecords().stream().map(BlogView::getAuthorId).collect(Collectors.toList());
		// 查询用户信息
		RestResult<Map<Integer, UserDTO>> result = userClient.getUserList(userIdList);
		// 判断信息是否正确
		if (result.getStatus()) {
			// 添加用户昵称
			List<BlogInfoBO> blogInfoBOList = blogListVO.getRecords();
			Map<Integer, UserDTO> userMap = result.getData();
			for (BlogInfoBO blogInfoBO : blogInfoBOList) {
				String nickname = userMap.get(blogInfoBO.getAuthorId()).getNickname();
				blogInfoBO.setAuthorName(nickname);
			}
		}
		return blogListVO;
	}

	public Map<Integer, ActionStatusBO> getBlogActionStatus(Integer userId, Integer... blogIds) {
		HashMap<Integer, ActionStatusBO> map = new HashMap<>();
		// 空列表不走数据，会报错
		if (blogIds.length == 0) {
			return map;
		}
		Map<Integer, Integer> likeMap = likeBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIds);
		Map<Integer, Integer> collectMap = collectBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIds);
		for (Integer blogId : blogIds) {
			ActionStatusBO statusDTO = new ActionStatusBO();
			statusDTO.setLike(likeMap.containsKey(blogId));
			statusDTO.setCollect(collectMap.containsKey(blogId));
			map.put(blogId, statusDTO);
		}
		return map;
	}

	@Override
	public BlogContentVO getBlogContent(Integer id, Integer userId) {
		BlogContentVO blog = new BlogContentVO();
		// 博客内容
		blog.setContent(blogContentMapper.selectById(id));
		// 博客基本信息
		BlogView blogView = blogViewMapper.selectById(id);
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
		// 博客评论信息
		// blog.setComment(commentService.getList(id, 1, 3));
		return blog;
	}

}
