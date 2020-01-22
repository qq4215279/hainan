package com.gobestsoft.mamage.constant.factory;

import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.common.modular.dao.model.MstDistrictPojo;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.Role;

import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author gobestsoft
 * @date 2017-06-14 21:12
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     *
     * @author gobestsoft
     * @Date 2017/5/9 23:41
     */
    String getUserNameById(String userId);

    /**
     * 根据用户id获取用户账号
     *
     * @author gobestsoft
     * @date 2017年5月16日21:55:371
     */
    String getUserAccountById(Integer userId);


    /**
     * 获取角色
     *
     * @param roleId
     * @return
     */
    Role getSingleRole(Integer roleId);

    /**
     * 通过角色ids获取角色名称
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(Integer roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    String getSingleRoleTip(Integer roleId);

    /**
     * 获取部门名称
     */
    String getDeptName(Integer deptId);

    /**
     * 根据当前登录用户的orgid,获取上级工会对象
     */
    Dept getPUnionPojo(Integer deptId);

    /**
     * 获取菜单的名称们(多个)
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称
     */
    String getMenuName(Integer menuId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取字典名称
     */
    String getDictName(Integer dictId);

    /**
     * 获取通知标题
     */
    String getNoticeTitle(Integer dictId);

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, Integer val);

    /**
     * 获取性别名称
     */
    String getSexName(Integer sex);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(Integer status);

    /** 2017-11-30 caoy  modify ---- start */
    /**
     * 查询字典 (groupCode)
     */
    List<Dict> findInDict(String groupCode);

    /**
     * 查询字典 (groupCode + code)
     */
    Dict findInDictName(String groupCode, String code);

    /**
     * 根据categoryId 查询分类（工会资讯）
     */
    CmsCategoryPojo getCategoryById(Integer categoryId);

    /**
     * 查询所有资讯分类类别 （工会资讯）
     */
    List<CmsCategoryPojo> getCategoryList();
    /** 2017-11-30 caoy  modify ---- end */

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    String getCacheObject(String para);

    /**
     * 获取子部门id
     */
    List<Integer> getSubDeptId(Integer deptid);

    /**
     * 获取所有父部门id
     */
    List<Integer> getParentDeptIds(Integer deptid);

    /**
     * 根据层级获得区域集合
     */
    List<MstDistrictPojo> getDistrictByLevel(Integer level);

    /**
     * 根据父级ID获得区域集合
     */
    List<MstDistrictPojo> getDistrictByPid(String pid);

    /**
     * 根据父级ID获得区域集合
     */
    MstDistrictPojo getDistrictByCityCode(String pid);
}
