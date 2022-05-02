package cn.sticki.blog.security;

import cn.sticki.blog.pojo.domain.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

	Authentication getAuthentication();

	User getUser();

}
