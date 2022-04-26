package cn.sticki.blog.controller.interceptor;

import cn.sticki.blog.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ResponseUtils responseUtils;

    // @Override
    // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //     log.debug("UserLoginInterceptor,url->{}", request.getRequestURL());
    //     HttpSession session = request.getSession();
    //     if (session.getAttribute("user") != null) {
    //         return true;
    //     }
    //     responseUtils.objectToJson(response, new RestTemplate(401, "未登录"));
    //     return false;
    // }

}
