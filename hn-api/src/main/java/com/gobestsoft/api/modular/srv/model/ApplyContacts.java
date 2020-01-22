package com.gobestsoft.api.modular.srv.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 困难帮扶联系人
 * create by gutongwei
 * on 2018/8/24 下午3:22
 */
@Data
public class ApplyContacts implements Serializable {

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
     * 政治面貌
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
     * 医保情况
     */
    private String medicalInsurance;


    /**
     * 健康情况
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
     * 学校或单位
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
