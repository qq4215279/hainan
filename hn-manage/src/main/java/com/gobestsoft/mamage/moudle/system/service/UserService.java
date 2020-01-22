package com.gobestsoft.mamage.moudle.system.service;

import com.gobestsoft.common.modular.dept.mapper.DeptOrganizationMapper;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.common.modular.system.dao.MenuDao;
import com.gobestsoft.common.modular.system.dao.UserMgrDao;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.MD5Util;
import com.gobestsoft.core.util.NumUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.model.LoginUserDept;
import com.gobestsoft.mamage.model.LoginUserDeptUnit;
import com.gobestsoft.mamage.model.LoginUserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserMgrDao userMgrDao;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private DeptOrganizationMapper deptOrganizationMapper;

    /**
     * 获取系统用户
     * @param account 账号
     * @param password 密码
     * @param userType 0：admin。1：company
     * @return
     */
    public User user(String account, String password,String userType) {
        User user = userMgrDao.getByAccount(account,userType);
        if (user != null && user.getPassword().equals(MD5Util.encrypt(password + user.getSalt()))) {
            return user;
        }
        return null;
    }

    public LoginUser getLoginUser(User user) {
        LoginUser loginUser = new LoginUser();

        loginUser.setAvatar(user.getAvatar());
        loginUser.setId(user.getId());            // 账号id
        loginUser.setAccount(user.getAccount());// 账号
        loginUser.setName(user.getName());        // 用户名称
        loginUser.setDeptId(user.getDeptid());
        loginUser.setRoles(new HashSet<>());
        Integer[] roleArray = NumUtil.toIntegerArray(user.getRoleid().split(","));// 角色集合
        for (int roleId : roleArray) {
            LoginUserRole role = new LoginUserRole();
            role.setRoleId(roleId);
            role.setRoleName(ConstantFactory.me().getSingleRoleName(roleId));
            loginUser.getRoles().add(role);
        }

        loginUser.setPermissions(new HashSet<>());
        List<String> permissions = getMenuIdsByRoleIds(user.getRoleid());
        for (String permission : permissions) {
            if (StringUtils.isNotEmpty(permission)) {
                loginUser.getPermissions().add(permission);
            }
        }
        loginUser.setRoleIds(new ArrayList<>());
        Arrays.asList(NumUtil.toIntegerArray(user.getRoleid().split(","))).forEach(r -> {
            loginUser.getRoleIds().add(r);
        });

        loginUser.setDept(getLoginDept(user.getDeptid()));
        return loginUser;
    }

    /**
     * 获取登录的工会ID
     *
     * @param deptId 公会ID
     * @return
     */
    private LoginUserDept getLoginDept(Integer deptId) {
        if (deptId == null) {
            return null;
        }
        LoginUserDept loginUserDept = null;
        Dept dept = deptDao.selectById(deptId);
        if (dept != null) {
            loginUserDept = new LoginUserDept();
            loginUserDept.setDeptId(deptId);
            loginUserDept.setDeptName(dept.getSimplename());
            loginUserDept.setLevel(dept.getLevel());
            loginUserDept.setAddress(dept.getAddr());
            loginUserDept.setPId(dept.getPid());
            loginUserDept.setPIds(dept.getPids());
            //组织公司信息
            DeptOrganization organization = deptOrganizationMapper.selectByDeptId(deptId);
            if (organization != null) {
                LoginUserDeptUnit unit = new LoginUserDeptUnit();
                unit.setUnitDistrict(organization.getUnitDistrict());
                unit.setUnitName(organization.getUnitName());
                loginUserDept.setUnit(unit);
            }
            if (dept.getPid() != null) {
                loginUserDept.setParentDept(getLoginDept(dept.getPid()));
            }
        }
        return loginUserDept;
    }

    /**
     * 根据用户角色获取用户可操作的menuid
     *
     * @param roleIds
     * @return
     */
    public List<String> getMenuIdsByRoleIds(String roleIds) {
        if (StringUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        return menuDao.getMenuIdsByRoleIds(roleIds);
    }

    /**
     * 将token存入redis缓存中
     *
     * @param uid
     */
    public void setToken(String uid) {
        String token = UUIDUtil.getUUID();
        String tokenKey = "USER_" + uid;
        redisUtils.set(tokenKey, token);
    }

    public int queryUsername(String username) {

        return this.userMgrDao.queryAccountByUsername(username);

    }

    /**
    * 弱口令密码校验
    */
    // 至少8位，且包含数字，大小写字母
    private static final String REG1 =  "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9\\d$@$!%*?&]{8,}";
    // 相邻两位不重复
    private static final String REG2 =  ".*(.)\\1{1}.*";

    public boolean getIsWeak(String password) {
        if(!Pattern.matches(REG1, password)){
            return true;
        }
        if(Pattern.matches(REG2, password)){
            return true;
        }
        return false;
    }
}
