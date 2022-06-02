package cn.sticki.resource.service;

import cn.sticki.resource.pojo.Image;
import com.baomidou.mybatisplus.extension.service.IService;
import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ImageService extends IService<Image> {

	void getAvatarImage(String file, HttpServletResponse response);

	void getGeneralImage(String file, HttpServletResponse response);

	String uploadAvatar(MultipartFile image, String name);

	String uploadBlogImage(MultipartFile image) throws MinioException, IOException;

}
