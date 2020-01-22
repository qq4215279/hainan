package com.gobestsoft.core.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * javaBean转换工具类类
 * @author zhangdaowei
 * @date 2018年07月16日 下午1:10
 */
public class TransBeanUtil {
	
	/**
     * 将Object对象转成Map
     * @param obj
     * @return
     */
    public static Map<String, Object> throwExceptionByTransBean2Map(Object obj) {
        Map<String, Object> map = null;
        try {
            map = transBean2Map(obj);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | IntrospectionException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将javabean转换成Map
     * @param obj
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static Map<String, Object> transBean2Map(Object obj)
            throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            String key = propertyDescriptors[i].getName();
            if (!key.equals("class")) {
                Method getter = propertyDescriptors[i].getReadMethod();
                Object value = getter.invoke(obj);
                map.put(key, value);
            }
        }
        return map;
    }
}
