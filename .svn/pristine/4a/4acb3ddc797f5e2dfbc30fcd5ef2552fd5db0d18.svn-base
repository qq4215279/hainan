package com.gobestsoft.admin.core.aop;

import com.gobestsoft.admin.common.annotion.RedisCache;
import com.gobestsoft.core.reids.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by duanmu on 2019/1/25.
 */
@Aspect
@Component
public class RedisCacheAop {

    @Autowired
    RedisUtils redisUtils;

    @Pointcut(value = "@annotation(com.gobestsoft.admin.common.annotion.RedisCache)")
    public void cutPoint(){}


    @Around(value = "cutPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature ms = (MethodSignature)joinPoint.getSignature();
        Method method = ms.getMethod();
        RedisCache redisCache = method.getAnnotation(RedisCache.class);
        String key = redisCache.key();
        if(StringUtils.isEmpty(key)){
            return null;
        } else {
            if(!redisUtils.exists(key)){
                Object processd = joinPoint.proceed();

                redisUtils.set(key, processd, redisCache.expireSeconds());
                return processd;
            }

            return redisUtils.get(key);
        }

    }
}