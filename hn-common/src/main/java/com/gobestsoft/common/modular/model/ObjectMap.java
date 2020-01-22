package com.gobestsoft.common.modular.model;

import com.gobestsoft.core.support.HttpKit;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 简单封装HashMap 用于存取http请求中的参数
 */
public class ObjectMap extends HashMap<String,Object> {

    private ObjectMap(){}

    private HttpServletRequest request;

    public static ObjectMap getInstance(){
        return new ObjectMap();
    }

    public Object getObject(String key){
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

    public void putRequest(String key){
        request = HttpKit.getRequest();
        this.put(key,request.getParameter(key));
    }
}
