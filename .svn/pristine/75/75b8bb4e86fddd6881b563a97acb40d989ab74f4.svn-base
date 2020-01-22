package com.gobestsoft.common.modular.smp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.smp.model.SmpGroup;

public interface GroupMapper {
	
	/**
	 * 多条件分页查询方法
	 * @param page
     * @param groupName 小组名称
     * @param orgId
	 * @return
	 */
	public List<Map<String,Object>> selectList(
            @Param("page") Page<Map<String, Object>> page,
            @Param("groupName") String groupName,
            @Param("orgId") Integer orgId);
	
	/**
	 * 查询方法
	 * @param id
	 * @return
	 */
	public SmpGroup selectById(Integer id);
	
	/**
	 * 伪删除一条数据
	 * @param id
	 */
	public void remove(Integer id);
	
	/**
	 * 更新审核状态方法
	 * @param id
	 * @return
	 */
	void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
	
}
