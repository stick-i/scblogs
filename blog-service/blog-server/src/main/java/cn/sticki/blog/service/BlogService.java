package cn.sticki.blog.service;

import cn.sticki.blog.pojo.Blog;
import cn.sticki.blog.pojo.BlogCountBO;
import cn.sticki.blog.pojo.BlogSaveDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService extends IService<Blog> {

	/**
	 * 保存博客
	 *
	 * @param blog 博客
	 */
	void saveBlog(BlogSaveDTO blog);

	/**
	 * 获取作者博客数量
	 *
	 * @param authorId 作者id
	 */
	BlogCountBO getBlogCount(int authorId);

	/**
	 * 上传博客图片，自动命名为文件的md5值，返回图片路径
	 *
	 * @param coverImage 封面图
	 */
	String uploadImage(MultipartFile coverImage);

}
