package com.gobestsoft.common.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Data
@TableName("sys_dept")
public class Dept extends Model<Dept> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父部门id
     */
    private Integer pid;
    /**
     * 父级ids（格式：0,1,2,3）
     */
    private String pids;
    /**
     * 简称
     */
    private String simplename;
    /**
     * 全称
     */
    private String fullname;
    /**
     * 提示
     */
    private String tips;
    private Integer level;//工会级别（省总：0，市总：1，区县总：2，街道：3, 基层：4）;
    /**
     * 工会地址
     */
    private String addr;
    /**
     * 0：组织  1：部门
     */
    @TableField(value = "dept_type")
    private Integer deptType;
    @TableField(value = "dept_no")
    private String deptNo;//工会编号
    @TableField(value = "p_dept_no")
    private String pDeptNo;//上级工会编号
    @TableField(value = "p_dept_name")
    private String pDeptName;//上级工会名称
    @TableField(value = "pic_name")
    private String picName;//负责人
    @TableField(value = "chairman")
    private String chairman;//主席
    /**
     * 建会日期
     */
    private String establishDate;
    @TableField(value = "org_type")
    private Integer orgType;//0地区工会，1行业产业工会
    @TableField(value = "special_flg")
    private Integer specialFlg;//是否特区 0 否 1 是
    @TableField(value = "root_flg")
    private Integer rootFlg;//是否特区 0 否 1 是


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Dept [id=" + id + ", sort=" + sort + ", pid=" + pid + ", pids=" + pids + ", simplename=" + simplename
                + ", fullname=" + fullname + ", tips=" + tips + ", level=" + level + ", addr=" + addr + ", deptType=" + deptType
                + ", deptNo=" + deptNo + ", pDeptNo=" + pDeptNo + ", pDeptName=" + pDeptName + ", picName=" + picName 
                + ", chairman=" + chairman + ", establishDate=" + establishDate + "]";
    }

}
