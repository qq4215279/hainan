package com.gobestsoft.core.util;

import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 操作实体的方法
 *
 * @author swt
 */
public class BeanUtil {

    /**
     * 将o1中名称与o2实体参数名称相同的值复制到o2中
     * @param o1 被复制的实体
     * @param o2 需要处理的实体
     * @return
     */
    public static  Object tranferValues(Object o1,Object o2) throws Exception {

        return tranferValues(o1,o2,null);
    }

    /**
     * 将o1中名称与o2实体参数名称相同的值复制到o2中
     * @param o1 被复制的实体
     * @param o2 需要处理的实体
     * @param excludeColumns 需要排除的字段
     * @return
     * @throws IllegalAccessException
     */
    public static  Object tranferValues(Object o1,Object o2,String[] excludeColumns )  throws Exception {

        Field[] fields1 = o1.getClass().getDeclaredFields();


        Field[] fields2 = o2.getClass().getDeclaredFields();

        List<String> list= null;

        if(excludeColumns!=null){
            list=Arrays.asList(excludeColumns);
        }

        for(Field f1: fields1){
            if(list!=null && list.size()!=0){
                if(list.contains(f1.getName())){
                    continue;
                }
            }

            for(Field  f2:fields2){
                if(f1.getName().equals(f2.getName())){
                    try {
                        f1.setAccessible(true);
                        f2.setAccessible(true);
                        f2.set(o2,f1.get(o1));
                        f2.setAccessible(false);
                        f1.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new Exception(f2.getName()+"参数类型可能不一致");
                    }
                    break;
                }
            }
        }
        return o2;
    }

}
