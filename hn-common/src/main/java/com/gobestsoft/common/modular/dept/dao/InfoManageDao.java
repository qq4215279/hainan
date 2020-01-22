package com.gobestsoft.common.modular.dept.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.InfoManage;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.core.node.ZTreeNode;

/**
 * 工会信息管理的dao
 *
 * @author cxl
 * @date 2017-11-24
 */
public interface InfoManageDao {

	/**
	 * 根据条件查询工会信息管理列表
	 *
	 * @return
	 * @date 2017年11月26日 21:50:34
	 */
	public List<Map<String, Object>> selectByCondition(@Param("page") Page<Map<String, Object>> page, @Param("deptName") String deptName,
                                                       @Param("orgCode") String orgCode);

	/**
	 * 根据条件查询工会信息管理
	 *
	 * @param deptId
	 * @return
	 * @date 2017年11月26日 21:50:34
	 */
	public InfoManage getDetail(@Param("deptId") Integer deptId);
	
	/**
	 * 根据条件查询工会信息是否存在
	 *
	 * @param deptNo
	 * @return
	 * @date 2017年11月26日 21:50:34
	 */
	public Dept isExist(@Param("deptNo") String deptNo);

	/**
	 * 增加部门信息
	 *
	 * @param infoManage
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer addDept(InfoManage infoManage);

	/**
	 * 增加工会补足表（基层）
	 *
	 * @param infoManage
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer addInfoManage(InfoManage infoManage);

	/**
	 * 删除部门信息
	 *
	 * @param deptId
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer deleteDept(@Param("deptId") Integer deptId);

	/**
	 * 删除工会补足表（基层）
	 *
	 * @param deptId
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer deleteInfoManage(@Param("deptId") Integer deptId);

	/**
	 * 关闭工会
	 *
	 * @param deptId
	 * @return
	 * @date 2017年11月28日 09:36:11
	 */
	public Integer closeInfoManage(@Param("deptId") Integer deptId);

	/**
	 * 修改部门信息
	 *
	 * @param infoManage
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer editDept(InfoManage infoManage);

	/**
	 * 修改工会补足表（基层）
	 *
	 * @param infoManage
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer editInfoManage(InfoManage infoManage);

	/**
	 * 获取ztree的节点列表
	 *
	 * @return
	 * @date 2017年2月17日 下午8:28:43
	 */
	public List<ZTreeNode> tree();

	/**
	 * 转移部门
	 *
	 * @param deptId
	 * @param pid
	 * @param pDeptNo
	 * @param pDeptName
	 * @return
	 * @date 2017年11月27日 14:30:32
	 */
	public Integer transferDept(@Param("deptId") Integer deptId, @Param("pid") Integer pid,
                                @Param("pDeptNo") String pDeptNo, @Param("pDeptName") String pDeptName);
	
	/**
	 * 根据id查询工会编号
	 *
	 * @param id
	 * @return
	 * @date 2017年11月26日 21:50:34
	 */
	public Dept getDeptInfo(@Param("id") Integer id);
	
}
