package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.UserCollectBlogMapper;
import cn.sticki.blog.mapper.UserLikeBlogMapper;
import cn.sticki.blog.pojo.domain.UserCollectBlog;
import cn.sticki.blog.pojo.domain.UserLikeBlog;
import cn.sticki.blog.pojo.dto.UserBlogActionStatusDTO;
import cn.sticki.blog.service.BlogActionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BlogActionServiceImpl implements BlogActionService {

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private UserLikeBlogMapper likeBlogMapper;

	@Resource
	private UserCollectBlogMapper collectBlogMapper;

	@Override
	public boolean likeBlog(Integer userId, Integer blogId) {
		// 查询是否已经点赞，若已点赞则取消点赞
		LambdaQueryWrapper<UserLikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserLikeBlog::getUserId, userId);
		wrapper.eq(UserLikeBlog::getBlogId, blogId);
		UserLikeBlog selectOne = likeBlogMapper.selectOne(wrapper);
		if (selectOne != null) {
			// 点赞已经存在
			likeBlogMapper.deleteById(selectOne);
			blogGeneralMapper.decreaseLikeNum(blogId);
			return false;
		} else {
			// 点赞不存在
			UserLikeBlog likeBlog = new UserLikeBlog();
			likeBlog.setBlogId(blogId);
			likeBlog.setUserId(userId);
			likeBlog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			likeBlogMapper.insert(likeBlog);
			blogGeneralMapper.increaseLikeNum(blogId);
			return true;
		}
	}

	@Override
	public boolean collectBlog(Integer userId, Integer blogId) {
		LambdaQueryWrapper<UserCollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserCollectBlog::getUserId, userId);
		wrapper.eq(UserCollectBlog::getBlogId, blogId);
		UserCollectBlog selectOne = collectBlogMapper.selectOne(wrapper);
		if (selectOne != null) {
			// 内容已经存在
			collectBlogMapper.deleteById(selectOne);
			blogGeneralMapper.decreaseCollectionNum(blogId);
			return false;
		} else {
			UserCollectBlog collectBlog = new UserCollectBlog();
			collectBlog.setBlogId(blogId);
			collectBlog.setUserId(userId);
			collectBlog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			collectBlogMapper.insert(collectBlog);
			blogGeneralMapper.increaseCollectionNum(blogId);
			return true;
		}
	}

	@Override
	public Long getLikeNum(Integer blogId) {
		LambdaQueryWrapper<UserLikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserLikeBlog::getBlogId, blogId);
		return likeBlogMapper.selectCount(wrapper);
	}

	@Override
	public Long getCollectNum(Integer blogId) {
		LambdaQueryWrapper<UserCollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserCollectBlog::getBlogId, blogId);
		return collectBlogMapper.selectCount(wrapper);
	}

	@Override
	public Map<Integer, UserBlogActionStatusDTO> getUserBlogActionStatus(Integer userId, List<Integer> blogIdList) {
		Map<Integer, Integer> likeMap = likeBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIdList);
		Map<Integer, Integer> collectMap = collectBlogMapper.selectMapByUserIdAndBlogIdList(userId, blogIdList);
		HashMap<Integer, UserBlogActionStatusDTO> map = new HashMap<>();
		for (Integer blogId : blogIdList) {
			UserBlogActionStatusDTO statusDTO = new UserBlogActionStatusDTO();
			statusDTO.setLike(likeMap.containsKey(blogId));
			statusDTO.setCollect(collectMap.containsKey(blogId));
			map.put(blogId, statusDTO);
		}
		return map;
	}

}
