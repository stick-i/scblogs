package cn.sticki.blog.security.handler;

import cn.sticki.blog.config.JwtConfig;
import cn.sticki.blog.enumeration.CacheSpace;
import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.pojo.vo.UserVO;
import cn.sticki.blog.security.UserDetails;
import cn.sticki.blog.util.JwtUtils;
import cn.sticki.blog.util.ResponseUtils;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理器
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Resource
	private UserMapper userMapper;

	@Resource
	private JwtUtils jwtUtils;

	@Resource
	private ResponseUtils responseUtils;

	@CreateCache(name = CacheSpace.Login_UserID, expire = 30, timeUnit = TimeUnit.MINUTES)
	private Cache<String, User> cache;

	/**
	 * 登录成功处理逻辑
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		UserSafety userSafety = ((UserDetails) authentication.getPrincipal()).getUserSafety();
		User user = userMapper.selectById(userSafety.getUserId());
		// 存入redis
		cache.put(user.getId().toString(), user);
		log.debug("/login/login,user->{}", user);
		UserVO userVO = UserVO.userToVO(user);
		// 添加token
		response.setHeader(JwtConfig.headerName, jwtUtils.createToken("id", user.getId()));
		response.setHeader("Access-Control-Expose-Headers", JwtConfig.headerName);
		responseUtils.objectToJson(response, new RestTemplate(userVO));
	}

}