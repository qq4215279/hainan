package com.gobestsoft.api.config.cache;

import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.core.reids.RedisCacheModel;
import com.gobestsoft.core.reids.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/10/24 下午2:47
 */
@Service
public class RedisCacheFactory {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DictMapper dictMapper;

    /**
     * 根据groupCodes查询字典
     *
     * @param groupCodes 多个以逗号分割
     * @return
     */
    public List<DictDto> getDictsByGroupCodes(String groupCodes) {
        String redisKey = CacheConstant.APP_DICT_CACHE + groupCodes;
        RedisCacheModel cacheModel = (RedisCacheModel) redisUtils.get(redisKey);
        if (cacheModel != null && !cacheModel.isExpired()) {
            return (List<DictDto>) cacheModel.getData();
        }
        String[] groupCode = groupCodes.split(",");
        List<DictModel> dictModels = new ArrayList<>();
        Arrays.asList(groupCode).forEach(g -> {
            DictModel model = dictMapper.getDictionary(g, 0);
            if (model != null) {
                dictModels.add(model);
            }
        });
        List<DictDto> dtos = new ArrayList<>();
        dictModels.forEach(d -> {
            dtos.add(convertDict(d));
        });
        redisUtils.set(redisKey, new RedisCacheModel(dtos, 200));
        return dtos;
    }


    /**
     * 转换字典
     *
     * @param dictModel
     * @return
     */
    private DictDto convertDict(DictModel dictModel) {
        if (dictModel == null) {
            return null;
        }
        DictDto dto = new DictDto();
        dto.setGroup_code(dictModel.getGroup_code());
        dto.setName(dictModel.getName());
        dto.setCode(dictModel.getCode());
        if (dictModel.getDict() != null && dictModel.getDict().size() > 0) {
            dto.setDict(new ArrayList<>());
            for (DictModel m : dictModel.getDict()) {
                dto.getDict().add(convertDict(m));
            }
        }
        return dto;
    }


    /**
     * 进行缓存
     *
     * @param cacheKey    redis-key
     * @param cacheData   redis-值
     * @param expiredTime 缓存时间
     */
    public void cacheModel(String cacheKey, Object cacheData, int expiredTime) {
        redisUtils.set(cacheKey, new RedisCacheModel(cacheData, expiredTime));
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

}
