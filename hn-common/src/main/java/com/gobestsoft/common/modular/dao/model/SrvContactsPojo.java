package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 法律援助表
 */
@TableName("srv_contacts")
@Data
public class SrvContactsPojo extends Model<SrvContactsPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    /**
     * 指向ID
     */
    private Integer targetId;
    /**
     * 0:困难帮扶家庭联系人。1：法律援助家庭联系人。
     */
    private Integer type;

    /**
     * 姓名
     */
    private String name;

    /**
     * 关系
     */
    private String relation;

    /**
     * 性别：字典：lib_sex
     */
    private String sex;


    /**
     * 政治面貌，字典：lib_political_status
     */
    private String politicalStatus;

    /**
     * 文化程度/学历，字典：lib_education_code
     */
    private String education;


    /**
     * 身份证号
     */
    private String certificateNum;

    /**
     * 年龄
     */
    private Integer age;


    /**
     * 医保状况
     */
    private String medicalInsurance;
    /**
     * 健康情况，字典：lib_health
     */
    private String health;

    /**
     * 月收入
     */
    private Double monthlyIncome;

    /**
     * 身份
     */
    private String identity;

    /**
     * 工作单位或者学校
     */
    private String unit;
    
    /**
     * 联系方式
     */
    private String tel;


    /**
     * 在校类别：字典：lib_school_type
     */
    private String schoolType;

}

