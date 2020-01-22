package com.gobestsoft.mamage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * create by gutongwei
 * on 2018/7/30 下午2:00
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;          // 主键ID
    private String account;      // 账号
    private String avatar;      // 头像
    private String name;         // 姓名
    private Integer deptId;
    private LoginUserDept dept;

    /**
     * 权限数组
     */
    private Set<String> permissions;
    /**
     * 角色数组
     */
    private Set<LoginUserRole> roles;


    /**
     * 角色IDS
     */
    private List<Integer> roleIds;


    /**
     * 获取父级工会名
     *
     * @return
     */
    public String getDeptName() {
        if (dept != null) {
            return dept.getDeptName();
        }
        return "";
    }

}
