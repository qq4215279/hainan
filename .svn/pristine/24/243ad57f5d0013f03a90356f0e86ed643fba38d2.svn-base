package com.gobestsoft.common.modular.opinion.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.opinion.model.Opinion;

import java.util.List;
import java.util.Map;

/**
 * 建言献策
 */
@Repository
public interface OpinionMapper extends BaseMapper<Opinion> {


    /**
     * 列表
     */
    List<Map<String, String>> selectByCondition(
            @Param("page") RowBounds rowBounds
            ,@Param("startTime") String startTime
            ,@Param("endTime") String endTime
            ,@Param("type") Integer type
            ,@Param("status") Integer status
            ,@Param("orgId") Integer orgId
    );
}
