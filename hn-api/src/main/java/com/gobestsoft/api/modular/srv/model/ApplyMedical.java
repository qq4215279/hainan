package com.gobestsoft.api.modular.srv.model;

import lombok.Data;

/**
 * 困难帮扶附件属性：医疗帮扶
 * create by gutongwei
 * on 2018/10/18 下午5:14
 */
@Data
public class ApplyMedical {

    /**
     * 姓名
     */
    private String name;

    /**
     * 与建档者关系：字典：lib_family_relationship
     */
    private String relation;


    /**
     * 身份证号
     */
    private String certificateNum;


    /**
     * 年龄
     */
    private Integer age;


    /**
     * 性别：字典：lib_sex
     */
    private String sex;


    /**
     * 工作单位
     */
    private String workUnit;


    /**
     * 手机号
     */
    private String mobile;


    /**
     * 病种/疾病类型：字典：lib_disease
     */
    private String disease;


    /**
     *住院次数
     */
    private Integer hospitalizationNum;


    /**
     * 本单位救助金额
     */
    private Double unitDonationMoney;
    /**
     * 社会捐助金额
     */
    private Double sociologyDonationMoney;

    /**
     * 住院时间及后续治疗情况
     */
    private String remark;

}
