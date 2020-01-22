package com.gobestsoft.common.modular.legal.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("dept_legal_history")
public class DeptLegalHistory extends Model<DeptLegalHistory> {
    private Integer id;

    private Integer apply_id;

    /**
     * 工会id
     */
    private Integer deptId;

    /**
     * 法人姓名
     */
    private  String  name;

    /**
     * 法人性别
     */
    private String sex;

    /**
     * 名族
     */
    private String nation;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 学历
     */
    private String education;

    /**
     * 政治面貌
     */
    private String politicalOutlook;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 现任工会职务
     */
    private String workPosition;

    /**
     * 任职开始时间
     */
    private String tnureStartDate;

    /**
     * 任职结束时间
     */
    private String tnureEndData;

    /**
     * 是否是兼职0：否。1：是
     */
    private String partTimeJob;

    /**
     * 身份证号
     */
    private String certificateNo;

    /**
     * 其他职务
     */
    private String otherPosition;

    /**
     * 入会时间
     */
    private String joinTime;

    /**
     * 选举日期
     */
    private String electionDate;

    /**
     * 逻辑删除字段。0：未删除。1：已删除。
     */
//    private String delFlg;


    private Double  incomeAccumulativeLastYear;

    private Double  incomeAnnualDues;

    private Double  incomeTradeUnionFunds;

    private Double  incomeOther;

    private Double  capitalTotal;

    private Double  capitalFixedFunds;

    private Double  capitalWorking;

    private Double  capitalOther;

    private Double  placeTotal;

    private Double  placeOfficeArea;

    private Double  placeActivity;

    private Double  placeOther;

    private String  abilityToBear;

    private String  deptName;

    private String  unitTel;

    private String  unitAddress;

    private String  unitZipCode;

    private String  createunionTime;

    private String  approveNo;

    private Integer  unitNumber;

    private Integer  membership;

    private Integer  cadresNumber;

    private String  chairmanName;

    private String  chairmanSession;

    private String  chairmanMobile;

    private String  cerTime;

    private String  cerNo;

    private String  pDeptFullname;

    private String  assetsHanding;

    private String  debtHanding;

    private String  legalMobile;

    private Integer  officeTerm;

    private String  certificateNumber;

    private String  personCertificateNumber;

    private String  approveCompany;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
