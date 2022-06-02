package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.BlogViewMapper;
import cn.sticki.blog.mapper.LikeBlogMapper;
import cn.sticki.blog.pojo.domain.BlogView;
import cn.sticki.blog.pojo.domain.LikeBlog;
import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.service.LikeBlogService;
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
public class LikeBlogServiceImpl extends ServiceImpl<LikeBlogMapper, LikeBlog> implements LikeBlogService {

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private LikeBlogMapper likeBlogMapper;

	@Resource
	private BlogViewMapper blogViewMapper;

	@Override
	public boolean likeBlog(Integer userId, Integer blogId) {
		// 查询是否已经点赞，若已点赞则取消点赞
		LambdaQueryWrapper<LikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(LikeBlog::getUserId, userId);
		wrapper.eq(LikeBlog::getBlogId, blogId);
		LikeBlog selectOne = likeBlogMapper.selectOne(wrapper);
		if (selectOne != null) {
			// 点赞已经存在
			likeBlogMapper.deleteById(selectOne);
			blogGeneralMapper.decreaseLikeNum(blogId);
			return false;
		} else {
			// 点赞不存在
			LikeBlog likeBlog = new LikeBlog();
			likeBlog.setBlogId(blogId);
			likeBlog.setUserId(userId);
			likeBlog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			likeBlogMapper.insert(likeBlog);
			blogGeneralMapper.increaseLikeNum(blogId);
			return true;
		}
	}

	@Override
	public Long getLikeNum(Integer blogId) {
		LambdaQueryWrapper<LikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(LikeBlog::getBlogId, blogId);
		return likeBlogMapper.selectCount(wrapper);
	}

	@Override
	public BlogListVO getLikeBlogList(@NotNull Integer userId, int page, int pageSize) {
		// 先查收藏表，获取收藏的博客id
		LambdaQueryWrapper<LikeBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(LikeBlog::getUserId, userId);
		IPage<LikeBlog> iPage = new Page<>(page, pageSize);
		likeBlogMapper.selectPage(iPage, wrapper);
		BlogListVO blogListVO = BeanUtil.copyProperties(iPage, BlogListVO.class);
		List<LikeBlog> records = iPage.getRecords();
		// 若为空，则直接返回
		if (records.isEmpty()) {
			return blogListVO;
		}
		ArrayList<Integer> blogIdList = new ArrayList<>();
		for (LikeBlog blog : records) {
			blogIdList.add(blog.getBlogId());
		}
		// 查询blog表，把之前获取的博客id列表传入，获取blog数据
		LambdaQueryWrapper<BlogView> blogWrapper = new LambdaQueryWrapper<>();
		blogWrapper.in(BlogView::getId, blogIdList);
		List<BlogView> blogViewList = blogViewMapper.selectList(blogWrapper);

		blogListVO.setRecords(blogViewList);
		return blogListVO;
	}

}
