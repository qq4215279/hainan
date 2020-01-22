package com.gobestsoft.common.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import com.gobestsoft.core.node.ZTreeNode;

import java.util.List;
import java.util.Map;

/**
 * 角色相关的dao
 *
 * @author gobestsoft
 * @date 2017年2月12日 下午8:43:52
 */
public interface RoleDao {

	/**
	 * 根据条件查询角色列表
	 *
	 * @return
	 * @date 2017年2月12日 下午9:14:34
	 */
	public List<Map<String, Object>> selectRoles(@Param("condition") String condition);



	/**
	 * 获取角色列表树
	 *
	 * @return
	 * @date 2017年2月18日 上午10:32:04
	 */
	public List<ZTreeNode> roleTreeList();

	/**
	 * 获取角色列表树
	 *
	 * @return
	 * @date 2017年2月18日 上午10:32:04
	 */
	public List<ZTreeNode> roleTreeListByRoleId(Integer[] roleId);


}
