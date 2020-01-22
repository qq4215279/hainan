package com.gobestsoft.common.modular.dept.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dept.model.DeptMemberApplyLog;

@Repository
public interface DeptMemberApplyLogMapper extends BaseMapper<DeptMemberApplyLog> {

	/**
	 * 查看流程：根据申请id，查询审核日志
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> selectMapsByApplyId(@Param("id") Integer id);

	/**
	 * 根据申请id，获取第一条log日志；即：app用户申请入会、转会、办卡时添加的数据
	 * @param string
	 * @return
	 */
	List<DeptMemberApplyLog> selectFirstLogDataByApplyId(@Param("id") Integer id);

	/**
	 * 根据申请id，获取对应的日志条数
	 * @param applyId
	 * @return
	 */
	Integer selectCountByApplyId(@Param("applyId") Integer applyId);

	/**
	 * 批量插入
	 * @param deptMemberApplyLogs
	 */
	void insertList(@Param("logs") List<DeptMemberApplyLog> logs);


	Map selectLastLogByDept(@Param("id")Integer id,@Param("deptId")Integer deptId);
}