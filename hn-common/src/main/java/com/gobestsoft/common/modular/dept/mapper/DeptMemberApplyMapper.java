package com.gobestsoft.common.modular.dept.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DeptMemberApply;

@Repository
public interface DeptMemberApplyMapper extends BaseMapper<DeptMemberApply> {

	/**
	 * 入会、办卡审核页多条件分页查询
	 * @param page
     * @param status 审批状态
	 * @param orgId 当前登录用户的orgid
	 * @return
	 */
    List<Map<String, Object>> selectByCondition(@Param("page") Page<Map<String, Object>> page
    		, @Param("status") String status
    		, @Param("deptId") Integer deptId
    		, @Param("name") String name);

    /**
     * 转会第一级工会审核页多条件分页查询
     * @param page
     * @param status
     * @param createTime
     * @param orgId
     * @param name
     * @return
     */
	List<Map<String, Object>> selectFirstCheckByCondition(@Param("page") Page<Map<String, Object>> page,
            @Param("status") String status,
            @Param("createTime") String createTime,
            @Param("orgId") Integer orgId,
            @Param("name") String name);
	
	/**
     * 转会第二级工会审核页多条件分页查询
     * @param page
     * @param status
     * @param createTime
     * @param orgId
     * @param name
     * @return
     */
	List<Map<String, Object>> selectTwoCheckByCondition(@Param("page") Page<Map<String, Object>> page,
            @Param("status") String status,
            @Param("createTime") String createTime,
            @Param("orgId") Integer orgId,
            @Param("name") String name);

	/**
	 * 预备会员多条件分页查询
	 * @param page
	 * @param unitName
	 * @param name
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> selectPrePareMemberPageByCondition(@Param("page") Page<Map<String, Object>> page
			, @Param("unitName") String unitName
			, @Param("name") String name
			, @Param("type") String type
			, @Param("certificateNum") String certificateNum);
	
	/**
	 * 根据单位名称，会员类型；查询会员表中的预备会员信息
	 * @param unitName
	 * @return
	 */
	List<Map<String,Object>> selectListByUnitName(@Param("unitName") String unitName
			, @Param("unionName") String unionName
			, @Param("unionSimpleName") String unionSimpleName
			, @Param("type") String type);
	
	/**
	 * 上级工会报备多条件查询
	 * @param page
	 * @param unionName
	 * @param name
	 * @param applyType
	 * @param applyStatus
	 * @param orgId
	 * @return
	 */
	List<Map<String, Object>> selectPreparationMemberPageByCondition(@Param("page") Page<Map<String, Object>> page
			, @Param("unionName") String unionName
			, @Param("name") String name
			, @Param("applyType") Integer applyType
			, @Param("applyStatus") String applyStatus
			, @Param("orgId") Integer orgId
			, @Param("certificateNum") String certificateNum);

	/**
	 * 转办前修改上一条日志的状态
	 * @param id
	 * @param deptId 旧的部门流水号 用于兼容之前的逻辑
	 * @param transferReason
	 * @return
	 */
	int updateAuditDeptId(@Param("id") Integer id,@Param("deptId") Integer deptId,@Param("transfer_reason") String transferReason);
//	修改申请的部门流水号
	int updateDeptId(@Param("id") Integer id,@Param("deptId")Integer deptId);
    
}