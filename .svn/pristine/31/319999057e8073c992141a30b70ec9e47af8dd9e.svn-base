package com.gobestsoft.admin.common.annotion;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by duanmu on 2019/1/25.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * 传入需要获取的key的值
     */
    String key() default "";

    long expireSeconds() default 180L;
}
