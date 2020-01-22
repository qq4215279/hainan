package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * app用户入会、转会、办卡审批日志表
 * @author zhangdaowei 2018年08月21日 17点33
 */
@TableName("dept_member_apply_log")
@Data
public class DeptMemberApplyLog extends Model<DeptMemberApplyLog> {

	private static final long serialVersionUID = 1L;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    /**
     * 执行ID
     */
    private Integer id;

    /**
     * 申请ID，dept_member_apply的ID
     */
    private Integer applyId;

    /**
     * appid
     */
    private String auid;

    /**
     * 审核状态（0:拒绝；1:同意）
     */
    private String applyStatus;

    /**
     * 结果查看状态(0：未查看   1：已查看)
     */
    private Integer checkFlag;
    
    /**
     * 流程步骤
     */
    private String recordFlowInfo;

    /**
     * 审核理由
     */
    private String checkReason;

    /**
     * 审核时间
     */
    private String checkTime;

    /**
     * 审核人uid
     */
    private String checkUid;
    /**
     * 审核工会流水号
     */
    private Integer checkDeptId;

    /**
     * 插入时间
     */
    private String createTime;



    @Override
    public String toString() {
        return "DeptMemberApplyLog{" +
                "id=" + id +
                ", applyId=" + applyId +
                ", auid=" + auid +
                ", applyStatus='" + applyStatus + '\'' +
                ", checkFlag='" + checkFlag + '\'' +
                ", recordFlowInfo='" + recordFlowInfo + '\'' +
                ", checkReason='" + checkReason + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", checkUid='" + checkUid + '\'' +
                '}';
    }
}