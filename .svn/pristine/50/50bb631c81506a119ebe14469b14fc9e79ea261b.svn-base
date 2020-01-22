package com.gobestsoft.mamage.basic;

import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/8/3 上午8:58
 */
public abstract class BaseControllerWrapper {

    public Object obj = null;

    public BaseControllerWrapper(Object obj) {
        this.obj = obj;
    }

    @SuppressWarnings("unchecked")
    public Object wrap() {
        if (this.obj instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) this.obj;
            for (Map<String, Object> map : list) {
                wrapTheMap(map);
            }
            return list;
        } else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            wrapTheMap(map);
            return map;
        } else {
            return this.obj;
        }
    }

    protected abstract void wrapTheMap(Map<String, Object> map);
}
