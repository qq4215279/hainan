package com.gobestsoft.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: gutongwei
 * @Date: 2019/5/14 9:36 PM
 * @Description:
 **/
public interface StatisticsBaseDao<T> {

    /**
     * 根据日期查询
     *
     * @param deptId
     * @param startTime
     * @param endTime
     * @return
     */
    T statisticsOne(@Param("deptId") Integer deptId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 根据时间范围来统计
     *
     * @param deptId
     * @param startTime
     * @param endTime
     * @return
     */
    List<T> statistics(@Param("deptId") Integer deptId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
