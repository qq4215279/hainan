package com.gobestsoft.common.modular.dept.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dept.model.DeptOrganizationLog;

/**
 * 申请建会操作日志
 * @author zhangdaowei
 * @date 2018-08-16
 */
@Repository
public interface DeptOrganizationLogMapper extends BaseMapper<DeptOrganizationLog>{

	/**
	 * 根据申请id，查询审核日志
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> selectListMapByRefId(@Param("id") Integer id);
	
}
