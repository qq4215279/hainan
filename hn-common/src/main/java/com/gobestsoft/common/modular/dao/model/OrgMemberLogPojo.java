package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 
 */
@TableName("org_member_log")
public class OrgMemberLogPojo extends Model<OrgMemberLogPojo> {

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    private Integer id;

    /**
     * 1:新增；2:修改；3:删除
     */
    private String type;

    /**
     * 会员ID
     */
    private String ouid;

    /**
     * 工会组织ID
     */
    private Integer orgId;

    /**
     * 入会日期
     */
    private String entryDate;

    /**
     * 会员编号
     */
    private String memberCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 1:男；2:女；
     */
    private String sex;

    /**
     * 生年月日
     */
    private String birthday;

    /**
     * 电话
     */
    private String phone;

    /**
     * 民族
     */
    private String nation;

    /**
     * 学历 1:小学及以下；2:初中；3:中专；4:高中；5:大专；6:大学；7:研究生；
     */
    private String education;

    /**
     * 学位 1:学士；2硕士；3:博士；
     */
    private String degree;

    /**
     * 证件类型 1:身份证；2:护照；
     */
    private String identificationType;

    /**
     * 证件号码
     */
    private String identificationNum;

    /**
     * 政治面貌 1:中国共产党员；2:民主党派；3:共青团员；4:群众
     */
    private String politicalStatus;

    /**
     * 户口所在地区代码
     */
    private String domicileCode;

    /**
     * 户口所在地详细
     */
    private String domicileDetail;

    /**
     * 现居地区代码
     */
    private String presentAddressCode;

    /**
     * 现居地址详情
     */
    private String presentAddressDetail;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 职务
     */
    private String position;

    /**
     * 是否劳模
     */
    private String isModel;

    /**
     * 简历
     */
    private String curriculumVitae;

    /**
     * 特长
     */
    private String specialty;

    private String createTime;

    private String createUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNum() {
        return identificationNum;
    }

    public void setIdentificationNum(String identificationNum) {
        this.identificationNum = identificationNum;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getDomicileCode() {
        return domicileCode;
    }

    public void setDomicileCode(String domicileCode) {
        this.domicileCode = domicileCode;
    }

    public String getDomicileDetail() {
        return domicileDetail;
    }

    public void setDomicileDetail(String domicileDetail) {
        this.domicileDetail = domicileDetail;
    }

    public String getPresentAddressCode() {
        return presentAddressCode;
    }

    public void setPresentAddressCode(String presentAddressCode) {
        this.presentAddressCode = presentAddressCode;
    }

    public String getPresentAddressDetail() {
        return presentAddressDetail;
    }

    public void setPresentAddressDetail(String presentAddressDetail) {
        this.presentAddressDetail = presentAddressDetail;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIsModel() {
        return isModel;
    }

    public void setIsModel(String isModel) {
        this.isModel = isModel;
    }

    public String getCurriculumVitae() {
        return curriculumVitae;
    }

    public void setCurriculumVitae(String curriculumVitae) {
        this.curriculumVitae = curriculumVitae;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}