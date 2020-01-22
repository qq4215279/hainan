package com.gobestsoft.common.modular.legal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.legal.model.DeptLegal;

/**
 *  法人基本信息
 */
@Repository
public interface DeptLegalMapper extends BaseMapper<DeptLegal> {

	/**
	 * 根据deptId,查询法人基本信息
	 * @param deptId
	 * @return
	 */
	List<DeptLegal> selectDeptLegalByDeptId(@Param("deptId") Integer deptId);
}