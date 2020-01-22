package com.gobestsoft.common.modular.dept.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DemoCarry;
import com.gobestsoft.common.modular.dept.model.DeptExt;
import com.gobestsoft.common.modular.system.model.Dept;

/**
 * 民主管理dao
 *
 * @author gaoquan
 */
public interface DemoCarryDao {

    /**
     * 条件查询，无分页（状态，标题，开始日期，结束日期）
     *
     * @param code
     * @return
     * @date 2017-11-24
     */
    public List<Map<String, Object>> selectByCondition(
            @Param("page") Page<DemoCarry> page,
            @Param("deptNo") String deptNo,
            @Param("simplename") String simplename,
            @Param("orgCode") String orgCode);


    /**
     * 条件查询，无分页（状态，标题，开始日期，结束日期）
     *
     * @param code
     * @return
     * @date 2017-11-24
     */
    public Map<String, String> selectById(@Param("deptId") String deptId);

    /**
     * 修改民主表
     *
     * @param democracyId
     * @return
     */
    public void updDemoCarry(
            DemoCarry demoCarry
    );


}
