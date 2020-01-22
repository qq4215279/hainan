package com.gobestsoft.common.modular.legal.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 法人申请表
 */
@Data
@TableName("dept_legal_apply")
public class DeptLegalApply extends Model<DeptLegalApply> {

    private Integer id;
    /**
     * 工会id
     */
    private Integer deptId;
    /**
     * 法人姓名
     */
    private String name;

    /**
     * 法人性别：字典：lib_sex
     */
    private String sex;
    /**
     * 法人民族：字典lib_nation
     */
    private String nation;
    /**
     * 法人-生日
     */
    private String birthday;
    /**
     * 法人-学历：字典：lib_education
     */
    private String education;
    /**
     * 法人-政治面貌：字典：lib_political_outlook
     */
    private String politicalOutlook;

    /**
     * 法人-籍贯
     */
    private String nativePlace;

    /**
     * 法人-现任工会职务
     */
    private String workPosition;
    /**
     * 法人-任职开始时间
     */
    private String tnureStartDate;
    /**
     * 法人-任职结束时间
     */
    private String tnureEndData;

    /**
     * 法人-是否是兼职0：否。1：是
     */
    private String partTimeJob;
    /**
     * 法人-身份证号
     */
    private String certificateNo;

    /**
     * 法人-其他职务
     */
    private String otherPosition;
    /**
     * 法人-入会时间
     *
     * @return
     */
    private String joinTime;
    
    /**
     * 法人联系方式
     */
    private String legalMobile;
    /**
     * 收入情况-上年结余累计（万元）
     */
    private Double incomeAccumulativeLastYear;
    /**
     * 收入情况-年会员缴纳会费收入（万元）
     */
    private Double incomeAnnualDues;
    /**
     * 收入情况-年2%拨交工会经费本级留成收入（万元）
     */
    private Double incomeTradeUnionFunds;
    /**
     * 收入情况-其他收入（万元）
     */
    private Double incomeOther;
    /**
     * 资金情况-合计
     */
    private Double capitalTotal;
    /**
     * 资金情况-固定资金（万元）
     */
    private Double capitalFixedFunds;
    /**
     * 资金情况-流动资金
     */
    private Double capitalWorking;

    /**
     * 资金情况-其他
     *
     * @return
     */
    private Double capitalOther;

    /**
     * 场所情况-合计
     */
    private Double placeTotal;

    /**
     * 场所情况-办公场所（平方米）m2
     */
    private Double placeOfficeArea;

    /**
     * 场所情况-活动场所（平方米）m2
     */
    private Double placeActivity;
    /**
     * 场所情况-其他场所
     */
    private Double placeOther;
    /**
     * 承担民事责任能力状况
     */
    private String abilityToBear;

    /**
     * 工会名称
     */
    private String deptName;

    /**
     * 办公室电话
     */
    private String unitTel;

    /**
     * 单位地址
     */
    private String unitAddress;

    /**
     * 工会地址邮编
     */
    private String unitZipCode;

    /**
     * 建会时间
     */
    private String createunionTime;

    /**
     * 选举日期
     */
    private String electionDate;

    /**
     * 审批文号
     */
    private String approveNo;

    /**
     * 职工人数
     */
    private Integer unitNumber;

    /**
     * 会员人数
     */
    private Integer membership;

    /**
     * 专职干部人数
     */
    private Integer cadresNumber;

    /**
     * 主席名称
     */
    private String chairmanName;

    /**
     * 现任XX届主席
     */
    private String chairmanSession;

    /**
     * 主席手机号
     */
    private String chairmanMobile;

    /**
     * 证书有效期
     */
    private String cerTime;
    /**
     * 证书编码
     */
    private String cerNo;
    /**
     * 经办人姓名
     */
    private String agentName;
    /**
     * 经办人身份证号
     */
    private String agentCertificateNo;

    /**
     * 经办人手机号
     */
    private String agentMobile;

    /**
     * 注销原因
     */
    private String cancellationReason;

    /**
     * 注销依据/批准文件文号
     */
    private String cacellationBasis;

    /**
     * 上级工会全称
     */
    private String pDeptFullname;

    /**
     * 资产处理情况
     */
    private String assetsHanding;

    /**
     * 遗失声明公示情况
     */
    private String lossNatice;

    /**
     * 遗失原因
     */
    private String lossReason;

    /**
     * 申请类型：0：申请法人资格。1：变更法人资格。2:注销法人资格。3：遗失补办法人资格',
     */
    private String type;

    /**
     * 0:未审核。1：审核中。2：审核通过。3：审核拒绝。
     */
    private String status;

    /**
     * 申请时创建id
     */
    private String createUid;
    /**
     * 申请时间
     */
    private String createTime;
    /**
     * 债权债务处理情况
     */
    private String debtHanding;

    /**
     * 任期年限
     */
    private Integer officeTerm;


    /**
     *工会组织机构代码证书号码
     *
     */
    private  String certificateNumber;

    /**
     * 工会法人资格证书号码 例（工法证字第xxx号）
     */
    private  String personCertificateNumber;
    /**
     * 申请变更原因
     */
    private String changeReason;

    /**
     * 审批单位
     */
    private String approveCompany;

    /**
     * 逻辑删除字段。0：未删除。1：已删除。
     */
    private String delFlg;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
