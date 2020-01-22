package com.gobestsoft.common.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 日志记录dao
 */
public interface LogDao {

    /**
     * 按照表名获取日志列表
     * @param page
     * @param name
     * @param status
     * @param tableName
     * @return
     */
    List<Map<String, Object>> selectByCondition(
            @Param("page")Page<Map<String, Object>> page
            ,@Param("name") String name
            ,@Param("status") String status
            ,@Param("table") String tableName
    );
}
