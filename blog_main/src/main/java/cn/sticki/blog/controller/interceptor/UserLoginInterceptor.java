package cn.sticki.blog.controller.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("UserLoginInterceptor,url->{}", request.getRequestURL());
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return true;
        }
        response.getWriter().println("未登录");
        return false;
    }

}
