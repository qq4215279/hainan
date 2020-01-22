package com.gobestsoft.api.modular.srv.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/26 下午3:23
 */
@Data
public class LawSupportParam {

    private String id;

    /**
     * 法律援助申请类型：0：中央专项彩票公益金法律援助项目法律援助申请。1：海南省职工服务中心法律援助申请。
     */
    @NotNull(message = "申请类型不能为空")
    private Integer type;

    /**
     * 法律援助事由 字典：lib_cause
     */
    @NotNull(message = "法律援助事由不能为空")
    private String cause;
    
    /**
     * 法律援助形式 字典：lib_support_shape
     */
    @NotNull(message = "法律援助形式不能为空")
    private String shape;

    /**
     * 申请人
     */
    @NotEmpty(message = "申请人不能为空")
    private String name;

    /**
     * 人员所属类型 
     */
    @NotEmpty(message = "申请人员类型不能为空")
    private String personType;
    
    /**
     * 性别：字典：lib_sex
     */
    private String sex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 身份证号
     */
    private String certificateNum;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 户籍地
     */
    private String domicilePlace;


    /**
     * 工作单位
     */
    private String workUnit;
    
    /**
     * 单位地址 unit_address
     */
    private String unitAddress;

    /**
     * 住所地（经常居住地）
     */
    private String address;
    
    /**
     * 家庭住址 home_address
     */
    private String homeAddress;


    /**
     * 家庭邮编
     */
    private String zipCode;

    /**
     * 家庭联系电话
     */
    private String tel;
    
    /**
     * 家庭人数 family_number
     */
    private Integer familyNumber;

    /**
     * 代理人姓名
     */
    private String agentName;

    /**
     * 代理人身份证
     */
    private String agentCertificateNum;

    /**
     * 申请人月收入
     */
    private Double monthlyIncome;


    /**
     * 申请人家庭人均月收入
     */
    private Double familyMonthlyIncome;

    /**
     * 申请人人均月收入
     */
    private Double familyMonthlyAvgIncome;


    /**
     * 家庭收入情况
     */
    private String familyIncomeCondition;


    /**
     * 案件类型:字典：lib_case_type
     */
    private String caseType;

    /**
     * 案件原由
     */
    private String caseReason;

    /**
     * 涉案金额
     */
    private Double caseMoney;

    /**
     * 案件情况
     */
    private String caseSituation;


    /**
     * 证据文件。相对路径或绝对路径，以“,”分割。
     */
    private String evidence;

    private String evidencePath;

    /**
     * 收入证明。相对路径或绝对路径，以“,”分割。
     */
    private String income;

    private String incomePath;

    /**
     * 户口本。相对路径或绝对路径，以“,”分割。
     */
    private String residenceBooklet;

    private String residenceBookletPath;

    /**
     * 家庭联系人
     */
    private List<ApplyContacts> contacts;

    /**
     *申请人所选的工会级别（省总：0，市总：1，区县总：2，街道：3 ）
     */
    private Integer deptLevel;
}
