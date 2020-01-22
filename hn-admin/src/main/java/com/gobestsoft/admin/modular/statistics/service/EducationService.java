package com.gobestsoft.admin.modular.statistics.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.common.modular.statistics.mapper.EducationCountMapper;
import com.gobestsoft.common.modular.statistics.mapper.EducationKeyMapper;
import com.gobestsoft.common.modular.statistics.model.SysEducationCount;
import com.gobestsoft.common.modular.statistics.model.SysEducationKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教育统计
 */
@Service
public class EducationService {

    @Resource
    private EducationKeyMapper educationKeyMapper;
    @Resource
    private EducationCountMapper educationCountMapper;

    /**
     * 获取key
     */
    public List<SysEducationKey> getKeyName(Integer pid) {
        Wrapper<SysEducationKey> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",pid);
        return educationKeyMapper.selectList(wrapper);
    }


    /**
     * 获取count
     */
    public SysEducationCount getKeyCount(Integer id) {
        return educationCountMapper.selectById(id);
    }
}
