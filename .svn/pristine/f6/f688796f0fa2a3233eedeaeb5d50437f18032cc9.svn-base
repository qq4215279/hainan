package com.gobestsoft.mamage.config.aop;

import com.alibaba.fastjson.JSON;
import com.gobestsoft.core.basic.HttpBasic;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class LoggerAspect extends HttpBasic {

    @Autowired
    private ManageProperties manageProperties;


    private final String executName = "执行方法为：{}";

    private final String excuteTime = "执行时间为：{}";

    private final String excuteArgs = "执行参数为：{}";

    @Pointcut("execution(public * com.gobestsoft.*.*.*.controller.*.*(..))")
    public void log() {
    }


    @Around("log()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        Object result = null;
        result = pjp.proceed(args);
        long endTime = System.currentTimeMillis();
        if (manageProperties.isWriteLog()) {
            log.info(excuteArgs, args);
            log.debug("访问路径为及参数：{}", getServletAndQueryString());
            log.info(executName, pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
            log.info(excuteTime, (endTime - startTime) + "ms");
            log.info("返回结果：{}", JSON.toJSONString(result));
        }
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
