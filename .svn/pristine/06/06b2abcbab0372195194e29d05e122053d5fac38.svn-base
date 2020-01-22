package com.gobestsoft.mamage.config.interceptor;


import com.gobestsoft.mamage.basic.ManageBasic;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器基类
 * Create by gutongwei
 * 2018/4/16
 */
public class BaseHandlerInterceptor extends ManageBasic implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (response.getStatus() == 404 ) {
            response.sendRedirect(request.getContextPath() + "/notFind");
            return false;
        }
        if (response.getStatus() == 500) {
            response.sendRedirect(request.getContextPath() + "/wrong");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
