package com.gobestsoft.common.modular.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.DeptMemberApplyPojo;
import com.gobestsoft.common.modular.model.ObjectMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 仅仅用于操作没有关联公会组织的会员申请
 */

@Repository
public interface DeptMemApplyMapper extends BaseMapper<DeptMemberApplyPojo> {
    /**
     * 查询所有没有对应公会信息的用户数量和单位名称
     * @param page
     * @param objectMap
     * @return
     */
    public List<Map<String,Object>> selectList(@Param("page") Page page, ObjectMap objectMap);

    List<String> selectSameUnitById(@Param("id")String id);


    void updateMemberDept(@Param("deptId")String deptId,@Param("list")List<String> list);

}
