package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.UserLikeBlogMapper;
import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.domain.UserLikeBlog;
import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.service.UserLikeBlogService;
import cn.sticki.blog.util.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class UserLikeBlogServiceImpl extends ServiceImpl<UserLikeBlogMapper, UserLikeBlog> implements UserLikeBlogService {

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private UserLikeBlogMapper userLikeBlogMapper;

	@Override
	public boolean likeBlog(Integer userId, Integer blogId) {
		// 查询是否已经点赞，若已点赞则取消点赞
		LambdaQueryWrapper<UserLikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserLikeBlog::getUserId, userId);
		wrapper.eq(UserLikeBlog::getBlogId, blogId);
		UserLikeBlog selectOne = userLikeBlogMapper.selectOne(wrapper);
		if (selectOne != null) {
			// 点赞已经存在
			userLikeBlogMapper.deleteById(selectOne);
			blogGeneralMapper.decreaseLikeNum(blogId);
			return false;
		} else {
			// 点赞不存在
			UserLikeBlog likeBlog = new UserLikeBlog();
			likeBlog.setBlogId(blogId);
			likeBlog.setUserId(userId);
			likeBlog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			userLikeBlogMapper.insert(likeBlog);
			blogGeneralMapper.increaseLikeNum(blogId);
			return true;
		}
	}

	@Override
	public Long getLikeNum(Integer blogId) {
		LambdaQueryWrapper<UserLikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserLikeBlog::getBlogId, blogId);
		return userLikeBlogMapper.selectCount(wrapper);
	}

	@Override
	public BlogListVO getLikeBlogList(@NotNull Integer userId, int page, int pageSize) {
		BlogListVO blogListVO = new BlogListVO();
		LambdaQueryWrapper<UserLikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserLikeBlog::getUserId, userId);
		Long count = userLikeBlogMapper.selectCount(wrapper);
		blogListVO.setTotal(count);
		if (count != 0 && count >= (long) (page - 1) * pageSize) {
			String limit = SqlUtils.limit(count, (long) page, (long) pageSize);
			List<BlogBasic> blogList = userLikeBlogMapper.selectUserLikeBlogList(userId, limit);
			blogListVO.setBlogList(blogList);
		}
		return blogListVO;
	}

}
