package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.mapper.BlogBasicMapper;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.UserLikeBlogMapper;
import cn.sticki.blog.pojo.BlogBasic;
import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.pojo.UserLikeBlog;
import cn.sticki.blog.service.UserLikeBlogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserLikeBlogServiceImpl extends ServiceImpl<UserLikeBlogMapper, UserLikeBlog> implements UserLikeBlogService {

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private UserLikeBlogMapper userLikeBlogMapper;

	@Resource
	private BlogBasicMapper blogBasicMapper;

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
		// 先查收藏表，获取收藏的博客id
		LambdaQueryWrapper<UserLikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserLikeBlog::getUserId, userId);
		IPage<UserLikeBlog> iPage = new Page<>(page, pageSize);
		userLikeBlogMapper.selectPage(iPage, wrapper);
		BlogListVO blogListVO = BeanUtil.copyProperties(iPage, BlogListVO.class);
		List<UserLikeBlog> records = iPage.getRecords();
		// 若为空，则直接返回
		if (records.isEmpty()) {
			return blogListVO;
		}
		ArrayList<Integer> blogIdList = new ArrayList<>();
		for (UserLikeBlog blog : records) {
			blogIdList.add(blog.getBlogId());
		}
		// 查询blog表，把之前获取的博客id列表传入，获取blog数据
		LambdaQueryWrapper<BlogBasic> blogWrapper = new LambdaQueryWrapper<>();
		blogWrapper.in(BlogBasic::getId, blogIdList);
		List<BlogBasic> blogBasicList = blogBasicMapper.selectList(blogWrapper);

		blogListVO.setRecords(blogBasicList);
		return blogListVO;
	}

}
