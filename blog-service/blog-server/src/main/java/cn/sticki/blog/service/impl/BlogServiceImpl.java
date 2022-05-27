package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.BlogException;
import cn.sticki.blog.exception.BlogMapperException;
import cn.sticki.blog.mapper.BlogContentMapper;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.pojo.*;
import cn.sticki.blog.service.BlogService;
import cn.sticki.blog.type.BlogStatusType;
import cn.sticki.common.result.RestResult;
import cn.sticki.resource.client.ResourceClient;
import cn.sticki.resource.utils.FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private BlogContentMapper blogContentMapper;

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private ResourceClient resourceClient;

	@Override
	public void saveBlog(BlogSaveDTO blogDTO) {
		log.debug("saveBlog, id->{}", blogDTO.getId());
		Blog blog = new Blog();
		// BeanUtil.copyProperties(blogDTO, blog);  // 使用hutool工具直接复制属性
		blog.setAuthorId(blogDTO.getAuthorId());
		blog.setTitle(blogDTO.getTitle());
		blog.setDescription(blogDTO.getDescription());
		blog.setStatus(blogDTO.getStatus());

		BlogContent blogContent = new BlogContent();
		blogContent.setContent(blogDTO.getContent());

		if (blogDTO.getId() != null) {
			// 博客是已经存在的，直接进行更新操作，需要先进行作者身份核实
			LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Blog::getId, blogDTO.getId()).eq(Blog::getAuthorId, blogDTO.getAuthorId()).last("limit 1");
			Blog blogSelect = blogMapper.selectOne(wrapper);
			if (blogSelect == null) {
				// 身份核实失败，拒绝操作
				log.debug("blogId is error,refuse update,id->{}", blogDTO.getId());
				throw new BlogException("用户非法修改他人博客, blogId->" + blogDTO.getId() + ",userId->" + blogDTO.getAuthorId());
			}
			blog.setId(blogDTO.getId());
			blogContent.setBlogId(blogDTO.getId());
			// 更新封面图
			if (FileUtils.isNotEmpty(blogDTO.getCoverImageFile())) {
				String image = this.uploadImage(blogDTO.getCoverImageFile());
				blog.setCoverImage(image);
			}
			// 更新数据库，设置条件
			if (blog.getStatus() != null || blog.getDescription() != null || blog.getTitle() != null || blog.getCoverImage() != null) {
				// 更新发表时间，若发表时间为空且当前修改状态不为 未发表
				if (blogSelect.getReleaseTime() == null && BlogStatusType.DRAFT.getValue().equals(blog.getStatus())) {
					blog.setReleaseTime(new Timestamp(System.currentTimeMillis()));
				}
				blog.setAuthorId(null); // 作者不更新
				blogMapper.updateById(blog);
			}
			if (blogContent.getContent() != null) {
				blogContentMapper.updateById(blogContent);
			}
			return;
		}
		// 博客不存在，此处应新建博客，并保存
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		blog.setCreateTime(timestamp);
		// 上传封面图
		if (FileUtils.isNotEmpty(blogDTO.getCoverImageFile())) {
			String image = this.uploadImage(blogDTO.getCoverImageFile());
			blog.setCoverImage(image);
		}
		// 添加发表时间，若未发表则不添加
		if (!BlogStatusType.DRAFT.getValue().equals(blogDTO.getStatus())) blog.setReleaseTime(timestamp);
		// 插入数据库
		if (blogMapper.insert(blog) != 1) throw new BlogMapperException();
		log.debug("insert blog success,id->{}", blog.getId());
		// 新建博客内容
		blogContent.setBlogId(blog.getId());
		if (blogContentMapper.insert(blogContent) != 1) {
			blogMapper.deleteById(blog.getId());
			throw new BlogMapperException();
		}
		// 插入博客浏览量、收藏量、评分等数据
		BlogGeneral blogGeneral = new BlogGeneral();
		blogGeneral.setBlogId(blog.getId());
		// 设置初始评分
		blogGeneral.setScore(ratingBlog(blogDTO.getContent()));
		if (blogGeneralMapper.insert(blogGeneral) != 1) throw new BlogMapperException("blog_general insert error!");
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
		return result.getData();
	}

}
