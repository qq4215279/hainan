package com.gobestsoft.common.modular.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.system.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 删除角色的所有权限
     *
     * @param roleId 角色id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    int deleteRelationByRoleId(@Param("roleId") Integer roleId);


    /**
     * 添加角色权限
     *
     * @param roleId
     * @return
     */
    int addRelationByRoleId(@Param("roleId") Integer roleId,@Param("menuIds") String[] menuIds);
}