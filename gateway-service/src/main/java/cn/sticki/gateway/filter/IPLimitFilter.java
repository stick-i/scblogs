package cn.sticki.gateway.filter;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.tool.utils.RequestUtils;
import cn.sticki.common.tool.utils.ResponseUtils;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class IPLimitFilter extends OncePerRequestFilter {

	private final static int time = 5;

	private final static int count = 30;

	@CreateCache(name = "gateway:ipLimit:", expire = time)
	private Cache<String, Integer> cache;

	/**
	 * ip访问限制过滤器
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// todo 有待优化，目前逻辑为，连续访问30次，需要冷却5s
		// 获取当前ip
		String ip = RequestUtils.getIPAddress(request);
		// ip计数
		Integer ipCount = cache.get(ip);
		if (ipCount == null) ipCount = 0;
		if (ipCount > count) {
			// ip访问超过限制
			ResponseUtils.objectToJson(response, new RestResult<>(408, "访问频繁，请稍后再试"));
			response.sendError(403, "Reject request");
		} else {
			ipCount++;
			cache.put(ip, ipCount);
			//放行
			filterChain.doFilter(request, response);
		}
		// 打印访问情况
		log.info("{} {} {} {}", request.getRemoteAddr(), request.getMethod(), response.getStatus(), request.getServletPath());
	}

}