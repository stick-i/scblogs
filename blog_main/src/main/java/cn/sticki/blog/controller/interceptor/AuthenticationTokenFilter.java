package cn.sticki.blog.controller.interceptor;

import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.util.JwtUtils;
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

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Resource
	private JwtUtils jwtUtils;

	/**
	 * 认证过滤器
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//获取token
		String token = request.getHeader("token");
		if (!StringUtils.hasText(token)) {
			//放行，因为这里无法进行认证，等于没有给权限，后面还是会经过认证接口的。
			filterChain.doFilter(request, response);
			return;
		}
		// 验证、解析token
		User user = jwtUtils.validateAndParse(token, User.class);
		if (user == null) throw new RuntimeException("token异常");

		// TODO 获取权限信息封装到Authentication中
		// 存入SecurityContextHolder，这里构造一个已认证的 authenticationToken ，之后就不用在认证了。
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		//放行
		filterChain.doFilter(request, response);
	}

}