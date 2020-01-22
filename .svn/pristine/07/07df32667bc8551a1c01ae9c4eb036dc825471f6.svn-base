package com.gobestsoft.mamage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * create by gutongwei
 * on 2018/9/14 下午8:12
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserDept implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工会ID
     */
    private Integer deptId;

    /**
     * 父级Id
     */
    private Integer pId;


    /**
     * 所有父级Id
     */
    private String pIds;

    /**
     * 工会名称
     */
    private String deptName;

    /**
     * 工会等级
     */
    private Integer level;


    /**
     * 工会地址
     */
    private String address;


    /**
     * 获取父级工会名
     *
     * @return
     */
    @JsonIgnore
    public String parentDeptName() {
        if (parentDept != null) {
            return parentDept.getDeptName();
        }
        return "";
    }

    /**
     * @return
     */
    @JsonIgnore
    public String allParentDeptName() {
        String allParentName = "";
        if (parentDept != null) {
            allParentName += parentDept.getDeptName() + "-" + parentDept.allParentDeptName();
        }
        if (allParentName.endsWith("-")) {
            allParentName = allParentName.substring(0, allParentName.length() - 1);
        }
        return allParentName;
    }

    /**
     * 父级工会
     */
    private LoginUserDept parentDept;


    /**
     * 公司信息
     */
    private LoginUserDeptUnit unit;

}
