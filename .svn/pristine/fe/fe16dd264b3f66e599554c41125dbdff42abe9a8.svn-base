package com.gobestsoft.common.modular.legal.model;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("dept_legal_approve_log")
public class DeptLegalApproveLog extends Model<DeptLegalApproveLog> {


    /**
     *执行ID
     */
    private Integer id;

    /**
     *申请ID
     */
    private Integer applyId;

    /**
     * 审核状态：0:待处理。1：审核通过。2：审核拒绝。
     */
    private String status;

    /**
     * 审核用户，sys_uid
     */
    private String checkUid;


    /**
     * 审核时间
     */
    private String checkTime;

    /**
     * 审核意见
     */
    private String checkOpinion;

    /**
     * 下一级审核组织ID
     */
    private Integer nextDeptId;

    /**
     *
     */
    private String flowInfo;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	public DeptLegalApproveLog() {
		super();
	}
	
	public DeptLegalApproveLog(Integer applyId, String status, String checkUid, String checkTime,
			String checkOpinion, Integer nextDeptId, String flowInfo) {
		super();
		this.applyId = applyId;
		this.status = status;
		this.checkUid = checkUid;
		this.checkTime = checkTime;
		this.checkOpinion = checkOpinion;
		this.nextDeptId = nextDeptId;
		this.flowInfo = flowInfo;
	}

}





