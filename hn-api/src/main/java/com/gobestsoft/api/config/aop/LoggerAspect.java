package com.gobestsoft.api.config.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gobestsoft.core.basic.HttpBasic;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class LoggerAspect extends HttpBasic{

    @Pointcut("execution(public * com.gobestsoft.api.*..controller.*.*(..))")
    public void log() {
    }


    @Around("log()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("访问路径为及参数：{}", getServletAndQueryString());
        log.debug("执行方法为：{}", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result = null;
        result = pjp.proceed(pjp.getArgs());
        log.debug("返回数据为：{}", JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
        long endTime = System.currentTimeMillis();
        log.debug("执行时间为：{}", (endTime - startTime) + "ms");
        return result;
    }


    /**
     * 获取ServletPath和调用参数
     *
     * @return
     */
    private String getServletAndQueryString() {
        HttpServletRequest request = getHttpServletRequest();
        Enumeration em = request.getParameterNames();
        StringBuilder queryString = new StringBuilder("?");
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            queryString.append(name + "=" + value);
        }
        return request.getServletPath() + queryString.toString();
    }


}
