package com.gobestsoft.admin.modular.system.controller;

import com.gobestsoft.common.modular.system.dao.RoleDao;
import com.gobestsoft.common.modular.system.mapper.RoleMapper;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.Role;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.node.ZTreeNode;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.constant.Const;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.system.service.RoleService;
import com.gobestsoft.mamage.moudle.system.wrapper.RoleWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 *
 * @author gobestsoft
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController {

    private static String PREFIX = "/system/role";

    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleDao roleDao;

    @Resource
    RoleService roleService;

    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/role";
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add")
    public String roleAdd() {
        return PREFIX + "/role_add";
    }

    /**
     * 跳转到修改角色
     */
    @RequestMapping(value = "/role_edit/{roleId}")
    public String roleEdit(@PathVariable Integer roleId, Model model) throws BusinessException {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleMapper.selectById(roleId);
        model.addAttribute(role);
        model.addAttribute("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
//        model.addAttribute("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        return PREFIX + "/role_edit";
    }

    /**
     * 跳转到角色分配
     */
    @RequestMapping(value = "/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Integer roleId, Model model) throws BusinessException {
        if (roleId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return PREFIX + "/role_assign";
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false, value = "roleName") String roleName) {
        List<Map<String, Object>> roles = this.roleDao.selectRoles(roleName);
        return super.warpObject(new RoleWrapper(roles));
    }

    /**
     * 角色新增
     */
    @RequestMapping(value = "/renew", method = RequestMethod.POST)
    @ResponseBody
    public Tip add(@Valid Role role, BindingResult result) throws BusinessException {
        if (result.hasErrors()) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (role.getId() == null) {
            this.roleMapper.insert(role);
        } else {
            this.roleMapper.updateById(role);
        }
        return success();
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Tip remove(@RequestParam Integer roleId) throws BusinessException {
        if (roleId == null) {
            return fail();
        }

        //不能删除超级管理员角色
        if (roleId.equals(Const.ADMIN_ROLE_ID)) {
            return tip(500, "超级管理员不能删除", null);
        }

        roleService.delRoleById(roleId);
        return success();
    }

    /**
     * 配置权限
     */
    @RequestMapping("/setAuthority")
    @ResponseBody
    public Tip setAuthority(@RequestParam("roleId") Integer roleId, @RequestParam("ids") String ids) throws BusinessException {
        if (roleId == null) {
            return fail();
        }
        roleService.setAuthority(roleId, ids);
        return success();
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList = this.roleDao.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        filterRole(roleTreeList);
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable String userId) {
        User theUser = this.userMapper.selectById(userId);
        String roleid = theUser.getRoleid();
        List<ZTreeNode> roleTreeList = this.roleDao.roleTreeList();
        filterRole(roleTreeList);
        roleTreeList.add(ZTreeNode.createParent());
        Arrays.asList(roleid.split(",")).forEach(i -> {
            roleTreeList.forEach(t -> {
                if (StringUtils.isNumeric(i) && t.getId() == Integer.valueOf(i)) {
                    t.setChecked(true);
                }
            });
        });
        return roleTreeList;
    }


    private void filterRole(List<ZTreeNode> list) {
        if (isAdmin()) return;

        if (getLoginUser().getDept().getLevel() == 1) {
            list.removeIf(record -> ToolUtil.equals(record.getId(), 1));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 24));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 19));
        }
        if (getLoginUser().getDept().getLevel() == 2) {
            list.removeIf(record -> ToolUtil.equals(record.getId(), 1));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 24));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 25));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 18));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 19));
        }
        if (getLoginUser().getDept().getLevel() == 3) {
            list.removeIf(record -> ToolUtil.equals(record.getId(), 1));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 24));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 25));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 26));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 17));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 18));
            list.removeIf(record -> ToolUtil.equals(record.getId(), 19));
        }
    }
}
