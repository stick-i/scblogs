package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.BlogException;
import cn.sticki.blog.exception.BlogMapperException;
import cn.sticki.blog.mapper.*;
import cn.sticki.blog.pojo.bo.BlogCountBO;
import cn.sticki.blog.pojo.bo.BlogSaveBO;
import cn.sticki.blog.pojo.domain.*;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.result.RestResult;
import cn.sticki.resource.client.ResourceClient;
import cn.sticki.resource.utils.FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private BlogContentMapper blogContentMapper;

	@Resource
	private BlogContentHtmlMapper blogContentHtmlMapper;

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private BlogViewMapper blogViewMapper;

	@Resource
	private ResourceClient resourceClient;

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Override
	public void saveBlog(BlogSaveBO blogDTO) {
		log.debug("saveBlog, id->{}", blogDTO.getId());
		// 1. 判断是新建博客还是修改博客
		if (blogDTO.getId() == null) {
			// 1.1 博客不存在，新建博客
			createBlog(blogDTO);
			return;
		}
		// 1.2 博客存在，修改博客
		// 2. 先进行作者身份核实
		LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
		// 根据 博客id 和 作者id 查询原始数据
		wrapper.eq(Blog::getId, blogDTO.getId()).eq(Blog::getAuthorId, blogDTO.getAuthorId()).last("limit 1");
		Blog originalBlog = blogMapper.selectOne(wrapper);
		if (originalBlog == null) {
			// 3.1 身份核实失败，拒绝操作
			log.warn("blogId is error,refuse update,id->{}", blogDTO.getId());
			throw new BlogException("非法修改他人博客");
		}
		// 3. 核实通过，整理数据
		Blog blog = new Blog();
		// 3.1 拷贝属性到blog实体类
		BeanUtils.copyProperties(blogDTO, blog);
		// 4. 更新博客封面图
		if (FileUtils.isNotEmpty(blogDTO.getCoverImageFile())) {
			// 4.1 将封面图上传到服务器
			String image = this.uploadImageSubLastString(blogDTO.getCoverImageFile());
			// 4.2 设置属性，如果上传成功则返回图片url，否则为null，为null时不会更新数据库
			blog.setCoverImage(image);
		}
		// 5. 更新 blog 数据库
		// 5.1 将实体类中的空属性用从数据库中取出来的补全
		copyOnlyNullProperties(blog, originalBlog);
		// 5.2 更新发表时间，若发表时间为空 且 当前修改的状态**不为“未发表”**
		Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
		if (originalBlog.getReleaseTime() == null && !BlogStatusType.DRAFT.getValue().equals(blog.getStatus())) {
			blog.setReleaseTime(nowTimestamp);
		}
		// 5.3 更新修改时间
		blog.setModifiedTime(nowTimestamp);
		// 5.4 更新数据库
		if (blogMapper.updateById(blog) != 1) {
			throw new BlogMapperException("博客更新失败，id->" + blog.getId());
		}
		// 6. 更新内容 html和md
		if (blogDTO.getContent() != null) {
			// 6.1 md内容
			BlogContent blogContent = new BlogContent();
			blogContent.setBlogId(blog.getId());
			blogContent.setContent(blogDTO.getContent());
			// 6.2 html内容
			BlogContentHtml blogHtml = new BlogContentHtml();
			blogHtml.setBlogId(blog.getId());
			blogHtml.setContent(blogDTO.getContentHtml());
			// 6.3 更新到数据库
			boolean success = blogContentMapper.updateById(blogContent) == 1
					&& blogContentHtmlMapper.updateById(blogHtml) == 1;
			if (!success) {
				throw new BlogMapperException("博客内容更新失败，id->" + blog.getId());
			}
		}
		// 7. 发送MQ消息
		rabbitTemplate.convertAndSend(BLOG_EXCHANGE, BLOG_UPDATE_KEY, blog);
	}

	/**
	 * 创建新博客
	 *
	 * @param blogDTO 博客内容
	 */
	private void createBlog(BlogSaveBO blogDTO) {
		// 1. 拷贝属性到blog实体类
		Blog blog = new Blog();
		BeanUtils.copyProperties(blogDTO, blog);
		// 1.2 添加创建时间
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		blog.setCreateTime(timestamp);
		// 2. 上传封面图
		if (FileUtils.isNotEmpty(blogDTO.getCoverImageFile())) {
			String image = this.uploadImageSubLastString(blogDTO.getCoverImageFile());
			blog.setCoverImage(image);
		}
		// 3. 添加发表时间，若未发表则不添加
		if (!BlogStatusType.DRAFT.getValue().equals(blogDTO.getStatus())) {
			blog.setReleaseTime(timestamp);
		}
		// 4. 插入数据库
		if (blogMapper.insert(blog) != 1) {
			throw new BlogMapperException();
		}
		log.debug("insert blog success,id->{}", blog.getId());
		// 5. 新建博客内容
		BlogContent blogContent = new BlogContent();
		blogContent.setBlogId(blog.getId());
		blogContent.setContent(blogDTO.getContent());
		// 5.2 html部分
		BlogContentHtml blogHtml = new BlogContentHtml();
		blogHtml.setBlogId(blog.getId());
		blogHtml.setContent(blogDTO.getContentHtml());
		// 6. 插入博客内容数据
		if (blogContentMapper.insert(blogContent) != 1 || blogContentHtmlMapper.insert(blogHtml) != 1) {
			throw new BlogMapperException("content insert error!");
		}
		// 7. 插入博客浏览量、收藏量、评分等数据
		BlogGeneral blogGeneral = new BlogGeneral();
		blogGeneral.setBlogId(blog.getId());
		// 8. 设置初始评分
		blogGeneral.setScore(ratingBlog(blogDTO.getContent()));
		if (blogGeneralMapper.insert(blogGeneral) != 1) {
			throw new BlogMapperException("blog_general insert error!");
		}
		// 9. 发送MQ消息
		rabbitTemplate.convertAndSend(BLOG_EXCHANGE, BLOG_INSERT_KEY, blog);
	}

	private int ratingBlog(@NotNull String blog) {
		return blog.length();
	}

	@Override
	public BlogCountBO getBlogCount(int authorId) {
		List<BlogCount> blogCountList = blogMapper.selectBlogCountListByAuthorId(authorId);
		BlogCountBO blogCountBO = new BlogCountBO();
		// 0表示全部，1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中
		int all = 0;
		for (BlogCount count : blogCountList) {
			switch (count.getStatus()) {
				case 0:
					blogCountBO.setAll(count.getNumber());
					break;
				case 1:
					blogCountBO.setPublish(count.getNumber());
					break;
				case 2:
					blogCountBO.setDraft(count.getNumber());
					break;
				case 3:
					blogCountBO.setPersonal(count.getNumber());
					break;
				case 4:
					blogCountBO.setDeleted(count.getNumber());
					break;
				case 5:
					blogCountBO.setAudit(count.getNumber());
					break;
				default:
			}
			all += count.getNumber();
		}
		blogCountBO.setAll(all);
		return blogCountBO;
	}

	@Override
	public String uploadImage(MultipartFile coverImage) {
		log.debug("uploadCoverImage,pic name->{}", coverImage.getOriginalFilename());
		RestResult<String> result = resourceClient.uploadBlogImage(coverImage);
		if (result.getStatus() && result.getData() != null && result.getData().length() > 0) {
			log.debug("uploadCoverImage OK, url:{}", result.getData());
			return result.getData();
		}
		return null;
	}

	/**
	 * 上传图片并截取最后一段字符串
	 *
	 * @param coverImage 图片
	 * @return 截取后的字符串
	 */
	public String uploadImageSubLastString(MultipartFile coverImage) {
		String image = this.uploadImage(coverImage);
		if (image == null) {
			return null;
		}
		String[] imageSplit = image.split("/");
		return imageSplit[imageSplit.length - 1];
	}

	/**
	 * 删除博客（将博客存入回收站）
	 *
	 * @param blogId 博客id
	 * @param userId 用户id
	 * @return 操作是否成功
	 */
	@Override
	public Boolean deleteBlog(Integer blogId, Integer userId) {
		Blog blog = getById(blogId);
		// 权限校验
		if (blog == null || !blog.getAuthorId().equals(userId)) {
			throw new BlogException("非法删除他人博客");
		}
		// 判断博客当前状态,是否已经是存在回收站里了
		if (BlogStatusType.DELETED.getValue().equals(blog.getStatus())) {
			log.info("博客删除失败：已经存入回收站，id {}", blogId);
			return false;
		}
		// 更新数据库
		blog.setStatus(BlogStatusType.DELETED.getValue());
		blog.setModifiedTime(new Timestamp(System.currentTimeMillis()));
		boolean isSuccess = lambdaUpdate().eq(Blog::getId, blogId).eq(Blog::getAuthorId, userId)
				.set(Blog::getStatus, blog.getStatus()).update();
		if (isSuccess) {
			// 发送消息到MQ
			rabbitTemplate.convertAndSend(BLOG_EXCHANGE, BLOG_UPDATE_KEY, blog);
		}
		return isSuccess;
	}

	/**
	 * 完全删除博客
	 *
	 * @param blogId 博客id
	 * @param userId 用户id
	 * @return 操作是否成功
	 */
	@Override
	public Boolean completelyDeleteBlog(Integer blogId, Integer userId) {
		Blog blog = getById(blogId);
		// 权限校验，博客不是属于该用户
		if (blog == null || !blog.getAuthorId().equals(userId)) {
			throw new BlogException("非法删除他人博客");
		}
		// 判断博客当前状态,是否已经是存在回收站里了
		if (!BlogStatusType.DELETED.getValue().equals(blog.getStatus())) {
			log.info("彻底删除操作失败，只有在回收站里的博客可以删除，id {}", blogId);
			return false;
		}
		// 从数据库删除博客
		boolean isSuccess = removeById(blogId);
		if (isSuccess) {
			// 发送删除的消息到MQ
			rabbitTemplate.convertAndSend(BLOG_EXCHANGE, BLOG_DELETE_KEY, blogId);
			try {
				// 删除其他相应的信息表
				blogGeneralMapper.deleteById(blogId);
				blogContentMapper.deleteById(blogId);
				blogContentHtmlMapper.deleteById(blogId);
			} catch (Exception e) {
				log.error("博客内容删除异常. {}", e.getMessage());
			}
		}
		return isSuccess;
	}

	@Override
	public List<BlogUserGeneral> getUserBlogGeneral(Integer[] userIds) {
		List<BlogUserGeneral> result = new ArrayList<>();
		// 查询博客数据
		List<BlogView> blogViews = blogViewMapper.selectBlogViewsByUserIds(userIds);
		for (BlogView blogView : blogViews) {
			BlogUserGeneral blogUserGeneral = new BlogUserGeneral();
			if (blogView != null) {
				BeanUtils.copyProperties(blogView, blogUserGeneral);
				blogUserGeneral.setUserId(blogView.getAuthorId());
				result.add(blogUserGeneral);
			}
		}
		return result;
	}

	@Override
	public BlogContent getBlogContent(@NotNull Integer blogId, @NotNull Integer userId) {
		LambdaQueryWrapper<Blog> blogWrapper = new LambdaQueryWrapper<>();
		blogWrapper.eq(Blog::getId, blogId).eq(Blog::getAuthorId, userId);
		// 判断是否存在（该博客是否为该用户发表的）
		if (!blogMapper.exists(blogWrapper)) {
			return null;
		}
		return blogContentMapper.selectById(blogId);
	}

	/**
	 * 将targetBean中为空的属性，从originalBean中获取并填充，<br>
	 * targetBean中不为空的属性不会被改变
	 *
	 * @param targetBean   目标bean，must not null
	 * @param originalBean 源bean，must not null
	 * @param <T>          bean的类型，两个bean类型必须一致
	 */
	public <T> void copyOnlyNullProperties(T targetBean, T originalBean) {
		BeanMap targetMap = BeanMap.create(targetBean);
		BeanMap originMap = BeanMap.create(originalBean);
		for (Object name : targetMap.keySet()) {
			if (targetMap.get(name) == null) {
				targetMap.put(name, originMap.get(name));
			}
		}
	}

}
