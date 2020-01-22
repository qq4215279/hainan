package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.StaffSkillPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface SrvTjMapper extends BaseMapper<StaffSkillPojo> {
    List<LinkedHashMap<String,Object>> getBfType();
    List<LinkedHashMap<String,Object>> getRegion();
    List<LinkedHashMap<String,Object>> getBfaAge();
    List<LinkedHashMap<String,Object>> getBfPass();
    List<Map<String,Object>> getlist(@Param("page") Page<Map<String,Object>> page,
                                     @Param("name") String name,
                                     @Param("mobile") String mobile);
    List<Map<String,Object>> getStaffSkill(@Param("idNum") String idNum,
                                           @Param("mobile") String mobile);
}
