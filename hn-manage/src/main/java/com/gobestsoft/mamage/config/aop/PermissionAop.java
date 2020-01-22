/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gobestsoft.mamage.config.aop;

import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.basic.tips.Tip;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * AOP 权限自定义检查
 */
@Aspect
@Component
public class PermissionAop extends ManageBasic {

    @Pointcut(value = "@annotation(com.gobestsoft.mamage.config.aop.Permission)")
    private void cutPermission() {

    }

    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Permission permission = method.getAnnotation(Permission.class);

        if (!isAdmin() && StringUtils.isNotEmpty(permission.value()) && !hasPermission(permission.value())) {
            return new Tip(500, "当前无此权限", null);
        }
        return point.proceed();
    }

}
