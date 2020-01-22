package com.gobestsoft.company.interceptor;

import com.gobestsoft.mamage.config.interceptor.BaseHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户身份验证 验证token
 *
 * @author gutongwei
 * <p>
 * 2017年11月23日
 */
public class AuthInterceptor extends BaseHandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!isLogin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
