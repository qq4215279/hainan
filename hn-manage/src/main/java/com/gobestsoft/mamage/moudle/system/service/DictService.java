package com.gobestsoft.mamage.moudle.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.modular.system.dao.DictDao;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseControllerWrapper;
import com.gobestsoft.mamage.moudle.system.parameter.DictEntry;
import com.gobestsoft.mamage.moudle.system.parameter.DictParameter;
import com.gobestsoft.mamage.moudle.system.wrapper.DictWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 字典服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
@Service
@Transactional
public class DictService {

    @Resource
    DictDao dictDao;

    @Resource
    DictMapper dictMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 添加字典
     *
     * @author fengshuonan
     * @Date 2017/4/27 17:01
     */
    public void addDict(DictParameter parameter) {
        dictMapper.delete(new EntityWrapper().eq("group_code", parameter.getGroupCode()));

        //添加字典
        Dict dict = new Dict();
        dict.setName(parameter.getDictName());
        dict.setGroupCode(parameter.getGroupCode());
        dict.setPid(0);
        dict.setCode("");
        this.dictMapper.insert(dict);

        //添加字典条目
        for (DictEntry entry : parameter.getEntrys()) {
            Dict itemDict = new Dict();
            itemDict.setGroupCode(parameter.getGroupCode());
            itemDict.setPid(dict.getId());
            itemDict.setName(entry.getName());
            itemDict.setCode(entry.getCode());
            this.dictMapper.insert(itemDict);

            for (DictEntry third : entry.getEntrys()) {
                Dict thiredDict = new Dict();
                thiredDict.setGroupCode(parameter.getGroupCode());
                thiredDict.setPid(itemDict.getId());
                thiredDict.setName(third.getName());
                thiredDict.setCode(third.getCode());
                this.dictMapper.insert(thiredDict);
            }
        }
    }

    /**
     * 删除字典
     *
     * @author fengshuonan
     * @Date 2017/4/28 11:39
     */
    public void deleteDict(String groupCode) {
        Wrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("group_code", groupCode);
        dictMapper.delete(dictEntityWrapper);
    }

    /**
     * 根据字典项的groupCode，查询该字典项全部选项集合
     *
     * @param groupCode
     * @return
     */
    public List<Map<String, Object>> getDicByGroupCode(String groupCode) {
        Wrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("group_code", groupCode);
        return dictMapper.selectMaps(dictEntityWrapper);
    }

    /**
     * 根据字典项的groupCode，code；查询该字典项指定的name名称
     *
     * @param groupCode
     * @param code
     * @return
     */
    public Dict getDicByGroupCodeAndCode(String groupCode, Object code) {
        List<Dict> dicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("group_code", groupCode).and().eq("code", code));
        if (ToolUtil.isNotEmpty(dicts) && dicts.size() > 0) {
            return dicts.get(0);
        }
        return new Dict();
    }

    /**
     * 根据字典项的groupCode,name;查询该字典项指定的code
     *
     * @param groupCode
     * @param name
     * @return
     */
    public Dict getDicByGroupCodeAndName(String groupCode, String name) {
        List<Dict> dicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("group_code", groupCode).and().eq("name", name));
        if (ToolUtil.isNotEmpty(dicts) && dicts.size() > 0) {
            return dicts.get(0);
        }
        return new Dict();
    }

    /**
     * 根据字典groupCode,查询出字典list集合
     *
     * @param groupCodes
     * @return
     */
    public List<Dict> getDicListByGroupCodes(String[] groupCodes) {
        return dictMapper.getDicListByGroupCodes(groupCodes);
    }

    /**
     * 获取所有字典
     *
     * @param condition
     * @return
     */
    public Object getAllDict(String condition) {
        if (StringUtils.isEmpty(condition) && redisUtils.exists(CacheConstant.getAllDict())) {
            return redisUtils.get(CacheConstant.getAllDict());
        }
        List<Map<String, Object>> list = this.dictDao.list(condition);
        BaseControllerWrapper wrapper = new DictWrapper(list);
        Object result = wrapper.wrap();
        if (StringUtils.isEmpty(condition)) {
            redisUtils.set(CacheConstant.getAllDict(), result);
        }
        return result;
    }


    /**
     * 获取字典
     *
     * @param groupCode
     * @return
     */
    public DictModel getDictionary(String groupCode) {
        return dictMapper.getDictionary(groupCode, 0);
    }
}
