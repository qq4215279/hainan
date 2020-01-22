package com.gobestsoft.common.modular.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.system.model.Relation;

/**
 * <p>
  * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
public interface RelationMapper extends BaseMapper<Relation> {

	/**
	 * 批量插入角色、人员关联表
	 * @param relations
	 */
	void insertList(@Param("relations") List<Relation> relations);

}