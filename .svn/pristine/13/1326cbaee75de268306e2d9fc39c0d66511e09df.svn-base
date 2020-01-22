package com.gobestsoft.common.modular.mst.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.mst.model.MstOrganization;

/**
 * 组织信息Dao层接口
 * @author zhangdw
 * 2018-05-11 下午3点 46分
 */
@Repository
public interface MstOrganizationDao extends MstBaseDao<MstOrganization>{
	
	/**
	 * 多条件分页查询方法
	 * @param page
	 * @param unionName 工会名称
     * @param unionNumber 工会编号
     * @param pId 上级工会id（sys_dept表id）
	 * @return
	 */
	public List<MstOrganization> selectList(
            @Param("page") Page<MstOrganization> page,
            @Param("unionName") String unionName,
            @Param("unionNumber") String unionNumber,
            @Param("pId") Integer pId);

	/**
	 * 根据deptId（即：sys_dept表id）,查询关联的工会组织对象
	 * @param deptId
	 * @return
	 */
	public MstOrganization getBeanByDeptId(@Param("deptId") Integer deptId);

	/**
	 * 根据sys_dept表id数组,查询出对应的工会信息集合
	 * @param deptIds
	 * @return
	 */
	public List<MstOrganization> getAllParentMstOrganizationBydeptIds(String[] deptIds);

	/**
	 * 根据sys_dept表id,查询直接下级工会List集合
	 * @param deptId
	 * @return
	 */
	public List<MstOrganization> getChildMstOrganizationByDeptId(Integer deptId);

	/**
	 * 根据sys_dept表id,查询全部下级工会List集合
	 * @param deptId
	 * @return
	 */
	public List<MstOrganization> getAllChildMstOrganizationByDeptId(Integer deptId);
	
	/**
	 * 根据单位名称查询该信息是否存在
	 * @return
	 */
	public MstOrganization isExistByUnitNameOrUnitOrgCode(
            @Param("unitName") String unitName,
            @Param("unitOrgCode") String unitOrgCode);
}
