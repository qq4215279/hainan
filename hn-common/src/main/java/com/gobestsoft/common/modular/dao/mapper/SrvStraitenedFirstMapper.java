package com.gobestsoft.common.modular.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedFirstPojo;
import com.gobestsoft.common.modular.srv.StraitenedEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 相互帮扶初审
 */
@Repository
public interface SrvStraitenedFirstMapper extends BaseMapper<SrvStraitenedFirstPojo> {

	/**
	 * 根据条件查询困难帮扶初审列表
	 * @param page
	 * @param name
	 * @param status
	 * @param type
	 * @param workUnit
	 * @param deptId
	 * @return
	 */
	public List<Map<String,Object>> selectByCondition(
			@Param("page") Page<Map<String,Object>> page, 
			@Param("name") String name,
			@Param("status") Integer status,
			@Param("type") Integer type,
			@Param("workUnit") String workUnit,
			@Param("deptId") Integer deptId);

	/**
	 * 我的申请(初审)
	 */
	public List<StraitenedEntity> getStraitenedFirst(@Param("page") RowBounds rowBounds, @Param("auid") String auid);

	/**
	 * 包含初审和终审
	 * @param rowBounds
	 * @param auid
	 * @return
	 */
	public List<StraitenedEntity> getStraitenedAll(@Param("page") RowBounds rowBounds, @Param("auid") String auid);



	public Map<String,Object> selectOneById(@Param("id") String id);



	/**
	 * 更新待审核审核人流水号，转办
	 * @param straitened_id
	 * @param transfer_dept_id
	 * @param transfer_reason
	 */
	int updateAuditDeptId(@Param("straitened_id")Integer straitened_id
			,@Param("transfer_dept_id")Integer transfer_dept_id
			,@Param("transfer_reason")String transfer_reason
	,@Param("type")Integer type);



	/**
	 * 查询指定天数前申请而且至今没有操作审核的记录
	 * @param day
	 * @return
	 */
	List<Map<String,Object>> selectExpireAudit(@Param("day") int day);

	void updateExpireAudit(List<String> list);

	void updateExpireLog(List<String> list);

	/**
	 * 查询下级部门困难帮扶初审的申请和审核情况
	 * @return
	 */
	public List<Map<String,Object>> selectTotalCount();
}

