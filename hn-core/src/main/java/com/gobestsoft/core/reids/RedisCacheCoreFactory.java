package com.gobestsoft.core.reids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * create by gutongwei
 * on 2018/10/24 下午2:47
 */
@Service
public class RedisCacheCoreFactory {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 进行缓存---有 过期时间
     */
    public void cacheModel(String cacheKey, Object cacheData, int expiredTime) {
        redisUtils.set(cacheKey, new RedisCacheModel(cacheData, expiredTime));
    }

    /**
     * 进行缓存---无 过期时间
     */
    public void cacheModel(String cacheKey, Object cacheData) {
        RedisCacheModel model = new RedisCacheModel();
        model.setData(cacheData);
        redisUtils.set(cacheKey, model);
    }

    /**
     * 获取缓存
     *
     * @param cacheKey  redis-key
     * @return
     */
    public RedisCacheModel getCacheModel(String cacheKey) {
        RedisCacheModel cacheModel = null;
        if (redisUtils.exists(cacheKey)) {
            try {
                cacheModel = (RedisCacheModel) redisUtils.get(cacheKey);
                if (cacheModel.isExpired()) {
                    cacheModel = null;
                }
            } catch (Exception ex) {
                cacheModel = null;
            }
        }
        return cacheModel;
    }

    /**
     * 循环 获取 key 对应的值，有则返回
     */
    public RedisCacheModel getCacheModel(Set<String> keys){
        RedisCacheModel model = null;
        for(String key : keys){
            model = this.getCacheModel(key);
            if(model != null){
                break;
            }
        }
        return model;
    }
}
