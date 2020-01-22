package com.gobestsoft.core.basic;

import com.gobestsoft.core.support.HttpKit;
import com.gobestsoft.core.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 简单封装HashMap 用于存取http请求中的参数
 */
public class ObjectMap extends HashMap<String,Object> {

    private ObjectMap(){}


    public static ObjectMap getInstance(){
        return new ObjectMap();
    }

    public Object get(String key){
        return this.get(key);
    }

    public String getStr(String key){
        return this.get(key)==null?null:this.get(key).toString();
    }

    public Integer getInt(String key){
        return this.get(key)==null?0:Integer.valueOf(this.get(key).toString());
    }

    public Double getDouble(String key){
        return this.get(key)==null?0d:Double.valueOf(this.get(key).toString());
    }

    public void putRequest(HttpServletRequest request,String key){
        this.put(key,request.getParameter(key));
    }
    public void putRequest(String key){
        this.put(key, HttpKit.getRequest().getParameter(key));
    }
}
