package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.mapper.BlogBasicMapper;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.UserCollectBlogMapper;
import cn.sticki.blog.pojo.BlogBasic;
import cn.sticki.blog.pojo.BlogListVO;
import cn.sticki.blog.pojo.UserCollectBlog;
import cn.sticki.blog.service.UserCollectBlogService;
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
public class UserCollectBlogServiceImpl extends ServiceImpl<UserCollectBlogMapper, UserCollectBlog> implements UserCollectBlogService {

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private UserCollectBlogMapper userCollectBlogMapper;

	@Resource
	private BlogBasicMapper blogBasicMapper;

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
		// 先查收藏表，获取收藏的博客id
		LambdaQueryWrapper<UserCollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserCollectBlog::getUserId, userId);
		IPage<UserCollectBlog> iPage = new Page<>(page, pageSize);
		userCollectBlogMapper.selectPage(iPage, wrapper);
		BlogListVO blogListVO = BeanUtil.copyProperties(iPage, BlogListVO.class);
		List<UserCollectBlog> userCollectBlogList = iPage.getRecords();
		// 若为空，则直接返回
		if (userCollectBlogList.isEmpty()) {
			return blogListVO;
		}
		ArrayList<Integer> blogIdList = new ArrayList<>();
		for (UserCollectBlog blog : userCollectBlogList) {
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
