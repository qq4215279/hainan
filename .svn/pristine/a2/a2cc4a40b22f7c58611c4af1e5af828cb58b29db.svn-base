package com.gobestsoft.common.modular.mst.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.mst.model.Enterprise;
import com.gobestsoft.common.modular.mst.model.EnterpriseExt;
import com.gobestsoft.common.modular.mst.model.EnterpriseLog;
import com.gobestsoft.common.modular.system.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 申请建会企业信息表
 * @author ke
 *
 */

public interface EnterpriseDao {
	
	/**
	 * 查询企业是否申请建会
	 */
	public List<Map<String,Object>> selectByAccount(String account);
	
	/**
	 * 新增企业建会信息
	 */
	public void enterpriseBuild(Enterprise enterprise);
	
	/**
	 * 新增企业信息日志
	 */
	public void enterpriseBuildLog(EnterpriseLog enterpriseLog);
	
	/**
	 * 查看code
	 */
	public List<Map<String,Object>> enterpriseByCode();

	/**
	 * 根据用户的orgId通过pids查询可以审核的下级工会
	 */
	public List<Integer> auditByOrgId(String orgId);
	
	/**
	 * 查询上级可以审核的企业信息
	 */
	public List<Map<String,Object>> selectAuditEnterprise(@Param("page") Page<Map<String, Object>> page,
                                                          @Param("status") Integer status,
                                                          @Param("enterpriseName") String enterpriseName,
                                                          @Param("createTime") String createTime,
                                                          @Param("id") Integer id,
                                                          @Param("oid") String oid);
	
	/**
	 * 根据enterpriseId查看企业详细信息
	 */
	public List<Map<String,Object>> selectByEnterpriseId(Integer enterpriseId);
	
	/**
	 * 根据code更新企业信息状态
	 */
	public void updateByStatus(@Param("code") String code, @Param("status") Integer status);
	
	/**
	 * 查询审核工会的pids
	 */
	public Map<String,Object> deptByPids(Integer pid);
	
	/**
	 * 新增工会
	 */
	public void addDept(Dept dept);
	
	/**
	 * 根据code更新企业组织信息
	 */
	public void updateByDeptId(@Param("code") String code, @Param("deptId") Integer deptId);
	
	/**
	 * 根据企业帐号更新orgId
	 */
	public void updateByUserOrgId(@Param("account") String account, @Param("deptId") Integer deptId);
	
	/**
	 * 查看审核流程
	 */
	public List<Map<String,Object>> log(Integer companyId);
	
	/**
	 * 修改信息提交建会审核
	 */
	public void updateEnterprise(Enterprise enterprise);
	
	/**
	 * 查询用户类型
	 */
	public String userType(String account);
	
	/**
	 * 查找用户设置菜单信息
	 */
	public List<Map<String,Object>> selectByUserUid(String account);
	
	/**
	 * 添加完善信息
	 */
	public void addComplete(EnterpriseExt enterpriseExt);
	
	/**
	 * 根据orgId查询申请建会补充表信息
	 */
	public List<Map<String,Object>> selectEnterpriseExtByDeptId(Integer deptId);

}
