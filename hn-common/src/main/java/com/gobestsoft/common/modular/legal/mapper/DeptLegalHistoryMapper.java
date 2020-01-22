package com.gobestsoft.common.modular.legal.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.legal.model.DeptLegalHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 备份法人信息
 */
@Repository
public interface DeptLegalHistoryMapper extends BaseMapper<DeptLegalHistory> {
    public DeptLegalHistory selectOneByApplyId(@Param("applyId") Integer applyId);
}
