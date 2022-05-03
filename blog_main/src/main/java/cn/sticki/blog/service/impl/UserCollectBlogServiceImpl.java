package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.UserCollectBlogMapper;
import cn.sticki.blog.pojo.domain.BlogBasic;
import cn.sticki.blog.pojo.domain.UserCollectBlog;
import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.service.UserCollectBlogService;
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
public class UserCollectBlogServiceImpl extends ServiceImpl<UserCollectBlogMapper, UserCollectBlog> implements UserCollectBlogService {

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private UserCollectBlogMapper userCollectBlogMapper;

	@Override
	public boolean collectBlog(Integer userId, Integer blogId) {
		LambdaQueryWrapper<UserCollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserCollectBlog::getUserId, userId);
		wrapper.eq(UserCollectBlog::getBlogId, blogId);
		UserCollectBlog selectOne = userCollectBlogMapper.selectOne(wrapper);
		if (selectOne != null) {
			// 内容已经存在
			userCollectBlogMapper.deleteById(selectOne);
			blogGeneralMapper.decreaseCollectionNum(blogId);
			return false;
		} else {
			UserCollectBlog collectBlog = new UserCollectBlog();
			collectBlog.setBlogId(blogId);
			collectBlog.setUserId(userId);
			collectBlog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			userCollectBlogMapper.insert(collectBlog);
			blogGeneralMapper.increaseCollectionNum(blogId);
			return true;
		}
	}

	@Override
	public Long getCollectNum(Integer blogId) {
		LambdaQueryWrapper<UserCollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserCollectBlog::getBlogId, blogId);
		return userCollectBlogMapper.selectCount(wrapper);
	}

	@Override
	public BlogListVO getCollectBlogList(@NotNull Integer userId, int page, int pageSize) {
		BlogListVO blogListVO = new BlogListVO();
		LambdaQueryWrapper<UserCollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserCollectBlog::getUserId, userId);
		Long count = userCollectBlogMapper.selectCount(wrapper);
		blogListVO.setTotal(count);
		if (count != 0 && count >= (long) (page - 1) * pageSize) {
			String limit = SqlUtils.limit(count, (long) page, (long) pageSize);
			List<BlogBasic> blogList = userCollectBlogMapper.selectUserLikeBlogList(userId, limit);
			blogListVO.setBlogList(blogList);
		}
		return blogListVO;
	}

}
