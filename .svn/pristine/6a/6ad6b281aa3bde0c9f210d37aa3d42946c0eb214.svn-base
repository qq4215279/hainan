package com.gobestsoft.common.modular.contract.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.contract.model.CollectiveContractMaster;

/**
 * 集体合同母版接口
 */
@Repository
public interface CollectiveContractMasterDao extends BaseMapper<CollectiveContractMaster> {

    /**
     * 母版列表
     */
    List<Map<String, Object>> selectByCondition(@Param("page") Page<Map<String, Object>> page,
                                             @Param("name") String name);

}
