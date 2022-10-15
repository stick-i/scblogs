package cn.sticki.common.web.anno;

import cn.sticki.common.web.exception.FrequentVisitsException;
import cn.sticki.common.web.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/31 20:24
 */
@Aspect
@Component
@Slf4j
public class RequestLimitAspect {

	@Resource
	private RedisTemplate<String, Integer> redisTemplate;

	private static final String IPLIMIT_KEY = "ipLimit:";

	/**
	 * 拦截有 {@link RequestLimit}注解的方法
	 */
	@Around("@annotation(cn.sticki.common.web.anno.RequestLimit)")
	public Object before(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		// 1. 获取被拦截的方法和方法名
		Method method = signature.getMethod();
		String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
		log.debug("拦截方法{}", methodName);
		// 1.2 获取注解参数
		RequestLimit limit = method.getAnnotation(RequestLimit.class);
		// 2. 获取当前线程的请求
		ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attribute == null) {
			log.warn(this.getClass().getName() + "只能用于web controller方法");
			return pjp.proceed();
		}
		HttpServletRequest request = attribute.getRequest();
		// 2.2 获取当前请求的ip
		String ip = RequestUtils.getIpAddress(request);
		// 3. 生成key
		String key = IPLIMIT_KEY + methodName + ":" + ip;
		// 4. 获取Redis中的数据
		Integer count = redisTemplate.opsForValue().get(key);
		int nowCount = count == null ? 0 : count;
		if (nowCount >= limit.count()) {
			// 5. 超出限制，拒绝访问
			log.info("访问频繁被拒绝访问，ip:{}，method:{}", ip, signature.getName());
			if (nowCount == limit.count()) {
				// 5.2 重置Redis时间为设定的等待值
				log.debug("重置redis值为{}，等待{}", nowCount + 1, limit.waits());
				redisTemplate.opsForValue().set(key, nowCount + 1, limit.waits(), TimeUnit.SECONDS);
			}
			// aop执行顺序在advice之前，故执行完当前aop方法后还会继续执行ResponseAdvice
			// 但我并不希望程序继续执行ResponseAdvice，所以抛出异常，返回值交给ExceptionAdvice处理
			throw new FrequentVisitsException();
		}

		Boolean isReset = false;
		if (count == null) {
			// 6. 重置计数器
			log.debug("重置计数器");
			isReset = redisTemplate.opsForValue().setIfAbsent(key, 1, limit.time(), TimeUnit.SECONDS);
		}
		if (count != null || !Boolean.TRUE.equals(isReset)) {
			// 计数器 +1，不重置TTL
			redisTemplate.opsForValue().increment(key);
			// 防止GC时key过期后然插入数据，导致数据TTL为-1
			Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
			if (Objects.isNull(expire) || expire.equals(-1L) || expire > limit.time()) {
				redisTemplate.expire(key, limit.time(), TimeUnit.SECONDS);
			}
		}
		log.debug("方法放行");
		return pjp.proceed();
	}

}
