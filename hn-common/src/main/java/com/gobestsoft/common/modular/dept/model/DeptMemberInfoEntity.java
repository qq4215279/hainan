package com.gobestsoft.common.modular.dept.model;

import lombok.Data;

/**
 * create by gutongwei
 * on 2018/8/27 上午11:10
 */
@Data
public class DeptMemberInfoEntity {

    /**
     * 会员ID
     */
    private Integer member_id;

    /**
     * 组织工会ID
     */
    private Integer dept_id;

    /**
     * 组织工会名称
     */
    private String dept_name;


    /**
     * 会员卡号
     */
    private String member_card;

    /**
     * 站内卡号（内部自用）
     */
    private String station_card;
    /**
     * 性别
     */
    private String sex;
    /**
     * 会员名称
     */
    private String name;


    /**
     * 生日YYYYMMdd
     */
    private String birthday;
    /**
     * 民族
     */
    private String nation;
    /**
     * 就业情况
     */
    private String work_situation;
    /**
     * 证件类型
     */
    private String certificate_type;
    /**
     * 证件号
     */
    private String certificate_num;
    /**
     * 学历
     */
    private String education;
    /**
     * 技能等级
     */
    private String technology_level;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 户籍类型
     */
    private String household;
    /**
     * 户籍所在地
     */
    private String domicile;
    /**
     * 政治面貌
     */
    private String political_status;
    /**
     * 工作单位
     */
    private String work_unit;
    /**
     * 籍贯
     */
    private String native_place;
    /**
     * 出生地
     */
    private String homeplace;

    private String member_change_date;

    private String member_change;

    private String member_change_reason;

    private String is_farmer;

    private Integer isBind;

}
