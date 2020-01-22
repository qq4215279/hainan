package com.gobestsoft.api.modular.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.ToolUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictService {

    @Resource
    DictMapper dictMapper;

    /**
     * 根据字典项的groupCode，code；查询该字典项指定的name名称
     */
    public Dict getDicByGroupCodeAndCode(String groupCode, Object code) {
        List<Dict> dicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("group_code", groupCode).and().eq("code", code));
        if (ToolUtil.isNotEmpty(dicts) && dicts.size() > 0) {
            return dicts.get(0);
        }
        return new Dict();
    }

}
