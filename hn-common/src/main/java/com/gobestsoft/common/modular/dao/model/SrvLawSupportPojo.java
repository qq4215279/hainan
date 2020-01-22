package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 法律援助表
 */
@TableName("srv_law_support")
@Data
public class SrvLawSupportPojo extends Model<SrvLawSupportPojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;
    /**
     * 法律援助申请类型：0：中央专项彩票公益金法律援助项目法律援助申请。2：海南省职工服务中心法律援助申请。
     */
    private Integer type;

    /**
     * 法律援助事由 字典：lib_cause
     */
    private String cause;
    
    /**
     * 法律援助形式 字典：lib_support_shape
     */
    private String shape;
    
    /**
     * 申请人
     */
    private String name;


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
     * 人员所属类型
     */
    private String personType;

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
    /**
     * 收入证明。相对路径或绝对路径，以“,”分割。
     */
    private String income;
    /**
     * 户口本。相对路径或绝对路径，以“,”分割。
     */
    private String residenceBooklet;

    /**
     * 创建人
     */
    private String createUid;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 审查状态【0：待查看，可撤回状态】【1：待处理】【2：通过】【3：拒绝】
     */
    private Integer status;
    
    /**
     * 审核过期时间 expire_time（默认自申请后的十天过期）
     */
    private String expireTime;
    
    /**
     * 后台上传的附件sys_attachments
     */
    private String sysAttachments;

    /**
     *申请人所选的工会级别（省总：1，市总：2，区县总：3，街道：4 ）

    private Integer deptLevel;
     */
}
