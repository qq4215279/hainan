package com.gobestsoft.common.modular.legal.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.legal.model.DeptLegalApproveLog;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 法人申请日志
 */
@Repository
public interface DeptLegalApproveLogMapper extends BaseMapper<DeptLegalApproveLog> {

	/**
	 * 审核日志状态 0:待处理；1:审核通过；2:审核拒绝
	 */
	final static String[] A_STATUS = {"0","1","2"};
	
	/**
	 * 查看流程：根据申请id，查询审核日志
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> selectMapsByApplyId(@Param("id") Integer id);
	
}

