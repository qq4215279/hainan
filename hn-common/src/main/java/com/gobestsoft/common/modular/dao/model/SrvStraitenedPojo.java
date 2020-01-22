package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 帮助互助表
 *
 * @author gutongwei
 */
@TableName("srv_straitened")
@Data
public class SrvStraitenedPojo extends Model<SrvStraitenedPojo> {


    /**
     * 困难帮扶申请ID
     */
    private Integer id;
    /**
     * 初审外键
     */
    private Integer straitenedFirstId;
    private String auid;
    /**
     * 审查状态【0：待查看，可撤回状态】【1：待处理】【2：通过】【3：拒绝】'
     */
    private Integer status;

    /**
     * 困难类别，字典：lib_difficult
     */
    private String srvCategory;
    /**
     * 建档标准,字典：lib_filing_standard
     */
    private String filingStandard;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 民族；字典：lib_ethnic_group
     */
    private String nation;
    /**
     * 性别：字典：lib_sex
     */
    private String sex;
    /**
     * 政治面貌，字典：lib_political_status
     */
    private String politicalStatus;
    /**
     * 身份证号
     */
    private String certificateNum;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 其他联系方式
     */
    private String otherContactTel;
    /**
     * 邮政编码
     */
    private String zipCode;
    /**
     * 健康情况，字典：lib_health
     */
    private String health;
    /**
     * 残疾类别：字典：lib_disability
     */
    private String disability;
    /**
     * 劳模类型：字典：lib_worker
     */
    private String modelWorker;
    /**
     * 住房类型：字典，lib_house_type
     */
    private String houseType;
    /**
     * 住房面积
     */
    private Double houseArea;

    /**
     * 工作时间
     */
    private String workTime;
    /**
     * 工作状态
     */
    private String workStatus;
    /**
     * 所属行业：字典：lib_industry_typ
     */
    private String industryType;
    /**
     * 婚姻状况：字典：lib_marital_status
     */
    private String maritalStatus;
    /**
     * 户口类型:字典：lib_household
     */
    private String householdType;

    /**
     * 居住地址
     */
    private String liveAddress;

    /**
     * 居住地址
     */
    private String address;
    /**
     * 工作单位
     */
    private String workUnit;
    /**
     * 工作单位性质：字典：lib_unit_type
     */
    private String unitType;
    /**
     *企业状况:字典：lib_enterprise_situation
     */
    private String enterpriseSituation;
    /**
     * 是否单亲：字典：lib_is_not
     */
    private String isSingleParent;
    /**
     * 月收入
     */
    private Double monthlyIncome;
    /**
     * 家庭其他年收入
     */
    private Double familyYearIncomeOther;
    /**
     * 家庭年度总收入
     */
    private Double familyYearIncome;
    /**
     * 家庭人口
     */
    private Integer familyPopulation;
    /**
     * 家庭月人均收入
     */
    private Double familyMonthIncomeAvg;
    /**
     * 户口所在地行政区划
     */
    private String householeAddress;
    /**
     * 医保状况
     */
    private String medicalInsurance;
    /**
     * 是否具有自救能力：字典：lib_is_not
     */
    private String hasSaveOneself;
    /**
     * 劳动合同开始日期
     */
    private String laborContractStart;
    /**
     *劳动合同结束日期
     */
    private String laborContractEnd;
    /**
     * 是否家庭零就业：字典：lib_is_not
     */
    private String familyNoWorker;
    /**
     *工会会员服务卡（大海惠工卡）卡号
     */
    private String memberCardNo;
    /**
     * 致困原因
     */
    private String reason;
    /**
     * @return
     */
    private String createTime;
    
    /**
     * 后台上传的附件sys_attachments
     */
    private String sysAttachments;

    private String disease;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
