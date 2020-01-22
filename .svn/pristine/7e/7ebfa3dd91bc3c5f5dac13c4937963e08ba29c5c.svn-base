package com.gobestsoft.api.modular.configurer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gobestsoft.api.modular.basic.BaseResult;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 拦截器基类
 * Create by gutongwei
 * 2018/4/16
 */
public class BaseHandlerInterceptor implements HandlerInterceptor {

    /**
     * 返回json字符串
     *
     * @param response
     * @param code
     * @throws IOException
     */
    protected void writeResultCode(HttpServletResponse response, BaseResult.ResultCode code) throws IOException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new BaseResult(code, new Object()),
                SerializerFeature.WriteMapNullValue));
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(response.getStatus()!=200){
            return  false;
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
