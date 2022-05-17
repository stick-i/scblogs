package cn.sticki.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "resource-path")
public class ResourcePath {

	public static String avatar;

	public static String blogCoverImage;

	public static String defaultAvatar;

	public static String blogImage;

	public static String blogImageUrlBase;

	public void setAvatar(String avatar) {
		ResourcePath.avatar = avatar;
	}

	public void setBlogCoverImage(String blogCoverImage) {
		ResourcePath.blogCoverImage = blogCoverImage;
	}

	public void setDefaultAvatar(String defaultAvatar) {
		ResourcePath.defaultAvatar = defaultAvatar;
	}

	public void setBlogImage(String blogImage) {
		ResourcePath.blogImage = blogImage;
	}

	public void setBlogImageUrlBase(String blogImageUrlBase) {
		ResourcePath.blogImageUrlBase = blogImageUrlBase;
	}

}