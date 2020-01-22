package com.gobestsoft.mamage.moudle.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.common.modular.system.dao.MenuDao;
import com.gobestsoft.common.modular.system.dao.RoleDao;
import com.gobestsoft.common.modular.system.mapper.RelationMapper;
import com.gobestsoft.common.modular.system.mapper.RoleMapper;
import com.gobestsoft.common.modular.system.model.Relation;
import com.gobestsoft.core.node.ZTreeNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色相关业务
 *
 * @author gobestsoft
 * @Date 2017年1月10日 下午9:11:57
 */
@Service
public class RoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleDao roleDao;

    @Resource
    RelationMapper relationMapper;

    @Resource
    MenuDao menuDao;

    /**
     * 设置某个角色的权限
     *
     * @param roleId   角色id
     * @param menusIds 权限的id
     * @date 2017年2月13日 下午8:26:53
     */
    @Transactional
    public void setAuthority(Integer roleId, String menusIds) {
        roleMapper.deleteRelationByRoleId(roleId);
        roleMapper.addRelationByRoleId(roleId, menusIds.split(","));
    }

    /**
     * 删除用户的角色权限
     *
     * @param roleId
     */
    public void delRoleById(Integer roleId) {
        roleMapper.deleteRelationByRoleId(roleId);
        roleMapper.delete(new EntityWrapper().eq("id", roleId));
    }


    /**
     * 获取角色菜单列表
     */
    public List<ZTreeNode> menuTreeListByRoleId(Integer roleId) {
        List<ZTreeNode> roleTreeList = menuDao.menuTreeList();

        ZTreeNode parent = ZTreeNode.createParent();
        //TODO 超级管理员权限待配置
//        if (roleId != 1) {
        parent.setChecked(false);
//        }
        roleTreeList.add(parent);

        List<Relation> relations = relationMapper.selectList(new EntityWrapper().eq("roleid", roleId));

        roleTreeList.forEach(r -> {
            for (Relation relation : relations) {
//                if (r.getId().equals(relation.getMenuid()) || roleId == 1) {
//                    r.setChecked(true);
//                }
                if (r.getId().equals(relation.getMenuid())) {
                    r.setChecked(true);
                }

            }
        });

        return roleTreeList;
    }
}
