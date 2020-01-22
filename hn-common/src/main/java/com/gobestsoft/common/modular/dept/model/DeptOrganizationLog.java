package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 申请建会操作日志表
 * @author zhangdaowei
 */
@Data
@TableName("dept_organization_log")
public class DeptOrganizationLog extends Model<DeptOrganizationLog>{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;

	/**
	 * 关联组织id
	 */
	private Integer refId;
	
	/**
	 * 审核状态(1:待审核；2:不同意；3:线下批复；4:发展会员；5:建立工会小组；6:筹备召开会员大会；7:已建会；8:工会信息已完善；)
	 */
	private Integer status;
	
	/**
	 * 操作描述
	 */
	private String comment;
	
	/**
	 * 处理时间
	 */
	private String operationTime;
	
	/**
	 * 流程步骤
	 */
	private String recordFlowInfo;
	
	/**
	 * 操作人账号
	 */
	private String operationUser;
	
	/**
	 * 操作人所属组织上级id
	 */
	private Integer operationDeptId;
	
	/**
	 * 下发退回组织id
	 */
	private Integer backDeptId;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
