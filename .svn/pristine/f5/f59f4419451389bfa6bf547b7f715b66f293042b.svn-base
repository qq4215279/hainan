package com.gobestsoft.mamage.model;

import lombok.Data;

import java.util.Objects;

/**
 * create by gutongwei
 * on 2018/8/17 上午9:13
 */
@Data
public class LoginUserRole {

    private Integer roleId;

    private String roleName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginUserRole that = (LoginUserRole) o;
        return Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return roleId;
    }
}
