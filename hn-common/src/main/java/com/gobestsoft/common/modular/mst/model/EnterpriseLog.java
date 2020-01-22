package com.gobestsoft.common.modular.mst.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 申请建会企业信息日志表
 * @author ke
 *
 */
@Data
@TableName("mst_enterprise_log")
public class EnterpriseLog extends Model<EnterpriseLog>{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@TableId(value="logId",type=IdType.AUTO)
	private Integer logId;

	/**
	 * 企业建会申请关联编号
	 */
	private String code;
	
	/**
	 * 企业信息对应状态
	 */
	private Integer status;
	
	/**
	 * 处理备注
	 */
	private String comment;
	
	/**
	 * 处理时间
	 */
	private String checkDate;
	
	/**
	 * 流程步骤
	 */
	private String recordFlowInfo;
	
	/**
	 * 操作人
	 */
	private String checkUser;
	
	/**
	 * 处理组织id
	 */
	private Integer checkDeptId;
	
	/**
	 * 下发退回组织id
	 */
	private Integer backDeptId;
	
	@Override
	protected Serializable pkVal() {
		return this.logId;
	}

	@Override
	public String toString() {
		return "EnterpriseLog [logId=" + logId + ", code=" + code + ", status=" + status + ", comment=" + comment
				+ ", checkDate=" + checkDate + ", recordFlowInfo=" + recordFlowInfo + ", checkUser=" + checkUser
				+ ", checkDeptId=" + checkDeptId + ", backDeptId=" + backDeptId + "]";
	}
	
}
