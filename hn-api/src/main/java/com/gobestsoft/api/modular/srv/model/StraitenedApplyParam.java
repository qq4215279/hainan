package com.gobestsoft.api.modular.srv.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * create by gutongwei
 * on 2018/7/16 下午4:31
 */
@Data
public class StraitenedApplyParam {

    private String id;
    /**
     * 初审主键
     */
    private Integer straitenedFirstId;

    /**
     * 帮扶类型
     */
    @NotEmpty(message = "帮扶类型不能为空")
    private String straitenedType;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 民族；字典：lib_ethnic_group
     */
    @NotEmpty(message = "民族不能为空")
    private String nation;

    /**
     * 性别：字典：lib_sex
     */
    @NotEmpty(message = "性别不能为空")
    private String sex;

    private Integer age;

    /**
     * 身份证人像面
     */
    private String identityFace;

    /**
     * 身份证国徽面
     */
    private String cardNationalEmblem;

    /**
     * 承诺书
     */
    private String commitLetter;

    /**
     * 所在单位证明
     */
    private String unitProve;

    /**
     * 附件材料
     */
    private String attachments;

    /**
     * 政治面貌，字典：lib_political_status
     */
    @NotEmpty(message = "政治面貌不能为空")
    private String politicalStatus;

    /**
     * 身份证
     */
    @NotEmpty(message = "身份证不能为空")
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
    @NotEmpty(message = "健康情况不能为空")
    private String health;

    /**
     * 残疾类别：字典：lib_disability
     */
//    @NotEmpty(message = "残疾类型")
    private String disability;
    /**
     * 疾病类型 字典：lib_disease
     */
    private String disease;

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
     * 工作状态
     */
    private String workStatus;

    /**
     * 工作时间
     */
    private String workTime;

    /**
     * 所属行业：字典：lib_industry_type
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
    private String address;

    /**
     * 居住地址
     */
    private String liveAddress;

    /**
     * 工作单位
     */
    private String workUnit;

    /**
     * 工作单位性质：字典：lib_unit_type
     */
    private String unitType;

    /**
     * 企业状况
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
     * 医保情况
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
     * 劳动合同结束日期
     */
    private String laborContractEnd;

    /**
     * 是否家庭零就业：字典：lib_is_not
     */
    private String familyNoWorker;


    /**
     * 工会会员服务卡（大海惠工卡）卡号
     */
    private String memberCardNo;

    /**
     * 致困原因
     */
    private String reason;

    /**
     * 联系人名单
     */
    private List<ApplyContacts> contacts;


    /**
     *医疗帮扶
     */
    private List<ApplyMedical> medicals;

    /**
     * 子女助学
     */
    private List<ApplyStudy> studies;
}
