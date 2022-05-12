package cn.sticki.blog.service.impl;

import cn.sticki.blog.config.ResourcePath;
import cn.sticki.blog.enumeration.type.BlogStatusType;
import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.systemException.DAOException;
import cn.sticki.blog.exception.userException.UserIllegalException;
import cn.sticki.blog.mapper.BlogContentMapper;
import cn.sticki.blog.mapper.BlogGeneralMapper;
import cn.sticki.blog.mapper.BlogImgMapper;
import cn.sticki.blog.mapper.BlogMapper;
import cn.sticki.blog.pojo.domain.*;
import cn.sticki.blog.pojo.dto.BlogCountDTO;
import cn.sticki.blog.pojo.dto.BlogSaveDTO;
import cn.sticki.blog.service.BlogConsoleService;
import cn.sticki.blog.util.EncryptUtils;
import cn.sticki.blog.util.FileUtils;
import cn.sticki.blog.util.OssUtils;
import cn.sticki.blog.util.RandomUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class BlogConsoleServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogConsoleService {

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private BlogContentMapper blogContentMapper;

	@Resource
	private BlogGeneralMapper blogGeneralMapper;

	@Resource
	private OssUtils ossUtils;

	@Resource
	private RandomUtils randomUtils;

	@Resource
	private FileUtils fileUtils;

	@Resource
	private BlogImgMapper blogImgMapper;

	@Override
	public void saveBlog(BlogSaveDTO blogDTO) throws UserException, DAOException {
		log.debug("saveBlog, id->{}", blogDTO.getId());
		Blog blog = new Blog();
		// BeanUtil.copyProperties(blogDTO, blog);  // 使用hutool工具直接复制属性
		blog.setAuthor(blogDTO.getAuthor());
		blog.setTitle(blogDTO.getTitle());
		blog.setDescription(blogDTO.getDescription());
		blog.setStatus(blogDTO.getStatus());

		BlogContent blogContent = new BlogContent();
		blogContent.setContent(blogDTO.getContent());

		if (blogDTO.getId() != null) {
			// 博客是已经存在的，直接进行更新操作，需要先进行作者身份核实
			LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Blog::getId, blogDTO.getId()).eq(Blog::getAuthor, blogDTO.getAuthor()).last("limit 1");
			Blog blogSelect = blogMapper.selectOne(wrapper);
			if (blogSelect == null) {
				// 身份核实失败，拒绝操作
				log.debug("blogId is error,refuse update,id->{}", blogDTO.getId());
				throw new UserIllegalException();
			}
			blog.setId(blogDTO.getId());
			blogContent.setBlogId(blogDTO.getId());
			// 更新封面图，直接通过原有的名称，进行替换(如果有)
			if (fileUtils.isNotEmpty(blogDTO.getCoverImageFile())) {
				if (blogSelect.getCoverImage() != null) {
					this.uploadCoverImage(blogSelect.getCoverImage(), blogDTO.getCoverImageFile());
				} else {
					String imageUrl = randomUtils.uuid();
					this.uploadCoverImage(imageUrl, blogDTO.getCoverImageFile());
					blog.setCoverImage(imageUrl);
				}
			}
			// 更新数据库，设置条件
			if (blog.getStatus() != null || blog.getDescription() != null || blog.getTitle() != null || blog.getCoverImage() != null) {
				// 更新发表时间，若发表时间为空且当前修改状态不为 未发表
				if (blogSelect.getReleaseTime() == null && BlogStatusType.DRAFT.getValue().equals(blog.getStatus())) {
					blog.setReleaseTime(new Timestamp(System.currentTimeMillis()));
				}
				blog.setAuthor(null); // 作者不更新
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
		if (fileUtils.isNotEmpty(blogDTO.getCoverImageFile())) {
			String imageUrl = randomUtils.uuid();
			this.uploadCoverImage(imageUrl, blogDTO.getCoverImageFile());
			blog.setCoverImage(imageUrl);
		}
		// 添加发表时间，若未发表则不添加
		if (!BlogStatusType.DRAFT.getValue().equals(blogDTO.getStatus())) blog.setReleaseTime(timestamp);
		// 插入数据库
		if (blogMapper.insert(blog) != 1)
			throw new DAOException();
		log.debug("insert blog success,id->{}", blog.getId());
		// 新建博客内容
		blogContent.setBlogId(blog.getId());
		if (blogContentMapper.insert(blogContent) != 1) {
			blogMapper.deleteById(blog.getId());
			throw new DAOException();
		}
		// 插入博客浏览量、收藏量、评分等数据
		BlogGeneral blogGeneral = new BlogGeneral();
		blogGeneral.setBlogId(blog.getId());
		// 设置初始评分
		blogGeneral.setScore(ratingBlog(blogDTO.getContent()));
		if (blogGeneralMapper.insert(blogGeneral) != 1)
			throw new DAOException("blog_general insert error!");
	}

	private int ratingBlog(@NotNull String blog) {
		return blog.length();
	}

	@Override
	public BlogCountDTO getBlogCount(String author) {
		List<BlogCount> blogCountList = blogMapper.selectBlogCountListByAuthor(author);
		BlogCountDTO blogCountDTO = new BlogCountDTO();
		// 0表示全部，1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中
		int all = 0;
		for (BlogCount count : blogCountList) {
			switch (count.getStatus()) {
				case 0:
					blogCountDTO.setAll(count.getNumber());
					break;
				case 1:
					blogCountDTO.setPublish(count.getNumber());
					break;
				case 2:
					blogCountDTO.setDraft(count.getNumber());
					break;
				case 3:
					blogCountDTO.setPersonal(count.getNumber());
					break;
				case 4:
					blogCountDTO.setDeleted(count.getNumber());
					break;
				case 5:
					blogCountDTO.setAudit(count.getNumber());
					break;
			}
			all += count.getNumber();
		}
		blogCountDTO.setAll(all);
		return blogCountDTO;
	}

	@SneakyThrows
	@Override
	public void uploadCoverImage(String name, MultipartFile coverImage) {
		log.debug("uploadCoverImage,name->{}", name);
		try (
				InputStream inputStream = coverImage.getInputStream()
		) {
			// 上传文件
			ossUtils.upload(
					ResourcePath.blogCoverImage + name,
					inputStream,
					coverImage.getSize(),
					-1,
					coverImage.getContentType()
			);
		}
	}

	@SneakyThrows
	@Override
	public String uploadImage(MultipartFile coverImage) {
		try (InputStream stream = coverImage.getInputStream()) {
			String md5 = EncryptUtils.toMD5(stream);
			// 存数据库，上传图片
			// 先判断数据库是否存在这条记录，存在的话直接返回链接就行了
			LambdaQueryWrapper<BlogImg> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(BlogImg::getImg, md5);
			if (!blogImgMapper.exists(wrapper)) {
				// 不存在，则新增
				BlogImg blogImg = new BlogImg();
				blogImg.setImg(md5);
				blogImg.setCreate_time(new Timestamp(System.currentTimeMillis()));
				blogImgMapper.insert(blogImg);
				// 上传图片
				ossUtils.upload(coverImage, ResourcePath.blogImage + md5);
			}
			// 返回访问链接
			return ResourcePath.blogImageUrlBase + md5;
		}
	}

}
