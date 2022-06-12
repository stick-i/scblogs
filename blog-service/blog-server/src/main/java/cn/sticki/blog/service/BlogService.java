package cn.sticki.blog.service;

import cn.sticki.blog.pojo.bo.BlogCountBO;
import cn.sticki.blog.pojo.bo.BlogSaveBO;
import cn.sticki.blog.pojo.domain.Blog;
import cn.sticki.blog.pojo.domain.BlogContent;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 阿杆
 */
public interface BlogService extends IService<Blog> {

	/**
	 * 保存博客
	 *
	 * @param blog 博客
	 */
	void saveBlog(BlogSaveBO blog);

	/**
	 * 获取作者博客数量
	 *
	 * @param authorId 作者id
	 * @return 博客数量信息
	 */
	BlogCountBO getBlogCount(int authorId);

	/**
	 * 上传博客图片，自动命名为文件的md5值，返回图片路径
	 *
	 * @param coverImage 封面图
	 * @return 资料链接
	 */
	String uploadImage(MultipartFile coverImage);

	/**
	 * 获取博客md，用于作者编辑博客内容
	 *
	 * @param blogId 博客id
	 * @param userId 用户id
	 * @return 博客内容
	 */
	BlogContent getBlogContent(Integer blogId, Integer userId);

}
