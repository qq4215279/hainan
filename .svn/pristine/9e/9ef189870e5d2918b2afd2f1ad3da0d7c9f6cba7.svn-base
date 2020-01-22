package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 
 */
@TableName("org_member_apply_exe")
public class OrgMemberApplyExePojo extends Model<OrgMemberApplyExePojo> {
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    /**
     * 执行ID
     */
    private Integer id;

    /**
     * 申请ID
     */
    private String applyId;

    /**
     * 1:发起；2:同意；3：拒绝；4:转发
     */
    private String applyType;

    /**
     * 流程审批内容：01：入会申请提出；02：转会申请提出；。。。
     */
    private String applyText;

    /**
     * 时间
     */
    private String applyTime;

    /**
     * 批注
     */
    private String applyDesc;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 姓名（备份）
     */
    private String userNameBak;

    /**
     * 组织ID
     */
    private Integer orgId;

    /**
     * 组织名称（备份）
     */
    private String orgNameBak;

    /**
     * 序列
     */
    private Integer sort;

    /**
     * 可操作标示
     */
    private String handleFlg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyText() {
        return applyText;
    }

    public void setApplyText(String applyText) {
        this.applyText = applyText;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyDesc() {
        return applyDesc;
    }

    public void setApplyDesc(String applyDesc) {
        this.applyDesc = applyDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNameBak() {
        return userNameBak;
    }

    public void setUserNameBak(String userNameBak) {
        this.userNameBak = userNameBak;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgNameBak() {
        return orgNameBak;
    }

    public void setOrgNameBak(String orgNameBak) {
        this.orgNameBak = orgNameBak;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getHandleFlg() {
        return handleFlg;
    }

    public void setHandleFlg(String handleFlg) {
        this.handleFlg = handleFlg;
    }
}