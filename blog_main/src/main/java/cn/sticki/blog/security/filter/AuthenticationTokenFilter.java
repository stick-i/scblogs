package cn.sticki.blog.security.filter;

import cn.hutool.jwt.JWT;
import cn.sticki.blog.config.JwtConfig;
import cn.sticki.blog.enumeration.CacheSpace;
import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.util.JwtUtils;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Resource
	private JwtUtils jwtUtils;

	@Resource
	private UserMapper userMapper;

	@CreateCache(name = CacheSpace.Login_UserID, expire = 30, timeUnit = TimeUnit.MINUTES)
	private Cache<Integer, User> cache;

	/**
	 * 认证过滤器
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//获取token
		String token = request.getHeader(JwtConfig.headerName);
		// 有则验证，验证成功则给权限，验证不成功或无token则不给权限，让后续的处理器判断是否允许操作
		// 此处只是给有token的用户授权而已
		if (StringUtils.hasText(token)) {
			// 验证、解析token
			JWT jwt = jwtUtils.validateAndParse(token);
			Object object = null;
			if (jwt != null) {
				object = jwt.getPayload("id");
			}
			if (object instanceof Integer) {
				Integer id = (Integer) object;
				// 获取用户数据
				User user = cache.get(id);
				if (user == null) {
					user = userMapper.selectById(id);
					cache.put(id, user);
				}
				log.debug("Token validate successful,user->{}", user);
				// TODO 获取权限信息封装到Authentication中
				// 存入SecurityContextHolder，这里构造一个已认证的 authenticationToken ，之后就不用再认证了。
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		//放行
		filterChain.doFilter(request, response);
	}

}