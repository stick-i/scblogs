package cn.sticki.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sticki.blog.exception.BlogException;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.mapper.BlogViewMapper;
import cn.sticki.blog.mapper.CollectBlogMapper;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogView;
import cn.sticki.blog.pojo.domain.CollectBlog;
import cn.sticki.blog.pojo.vo.BlogListVO;
import cn.sticki.blog.sdk.BlogOperateDTO;
import cn.sticki.blog.service.CollectBlogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static cn.sticki.blog.sdk.BlogMqConstants.*;

/**
 * @author 阿杆
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CollectBlogServiceImpl extends ServiceImpl<CollectBlogMapper, CollectBlog> implements CollectBlogService {

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private CollectBlogMapper collectBlogMapper;

	@Resource
	private BlogViewMapper blogViewMapper;

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Override
	public boolean collectBlog(Integer userId, Integer blogId) {
		// 进行判断博客是否存在
		Blog blog = blogMapper.selectById(blogId);
		if (blog == null) {
			log.warn("当前博客不存在,blogId={}", blogId);
			throw new BlogException("当前博客不存在");
		}
		LambdaQueryWrapper<CollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CollectBlog::getUserId, userId);
		wrapper.eq(CollectBlog::getBlogId, blogId);
		CollectBlog selectOne = collectBlogMapper.selectOne(wrapper);
		if (selectOne != null) {
			// 内容已经存在
			collectBlogMapper.deleteById(selectOne);
			blogGeneralMapper.decreaseCollectionNum(blogId);
			// 向rabbitMQ发送 取消收藏博客消息
			rabbitTemplate.convertAndSend(BLOG_TOPIC_EXCHANGE, BLOG_OPERATE_COLLECT_CANCEL_KEY, new BlogOperateDTO(blogId, userId, blog.getAuthorId()));
			return false;
		} else {
			CollectBlog collectBlog = new CollectBlog();
			collectBlog.setBlogId(blogId);
			collectBlog.setUserId(userId);
			collectBlog.setCreateTime(new Timestamp(System.currentTimeMillis()));
			collectBlogMapper.insert(collectBlog);
			blogGeneralMapper.increaseCollectionNum(blogId);
			// 向rabbitMQ发送 收藏博客消息
			rabbitTemplate.convertAndSend(BLOG_TOPIC_EXCHANGE, BLOG_OPERATE_COLLECT_KEY, new BlogOperateDTO(blogId, userId, blog.getAuthorId()));
			return true;
		}
	}

	@Override
	public Long getCollectNum(Integer blogId) {
		LambdaQueryWrapper<CollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CollectBlog::getBlogId, blogId);
		return collectBlogMapper.selectCount(wrapper);
	}

	@Override
	public BlogListVO getCollectBlogList(@NotNull Integer userId, int page, int pageSize) {
		// 先查收藏表，获取收藏的博客id
		LambdaQueryWrapper<CollectBlog> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CollectBlog::getUserId, userId);
		IPage<CollectBlog> iPage = new Page<>(page, pageSize);
		collectBlogMapper.selectPage(iPage, wrapper);
		BlogListVO blogListVO = BeanUtil.copyProperties(iPage, BlogListVO.class);
		List<CollectBlog> collectBlogList = iPage.getRecords();
		// 若为空，则直接返回
		if (collectBlogList.isEmpty()) {
			return blogListVO;
		}
		ArrayList<Integer> blogIdList = new ArrayList<>();
		for (CollectBlog blog : collectBlogList) {
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
