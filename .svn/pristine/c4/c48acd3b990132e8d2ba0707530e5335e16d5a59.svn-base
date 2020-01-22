package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 集体合同表
 * @author ke
 *
 */

@Data
@TableName("org_contract")
public class Contract extends Model<Contract>{
	
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * 主键
	 */
	@TableId(value="contractId",type=IdType.AUTO)
	private String contractId;
	/**
	 * 建立时间
	 */
	private String initDate;
	
	private String createTime;
	/**
	 * 是否签订集体合同
	 */
	private Integer isGroupConstract;
	/**
	 * 组织ID
	 */
	private Integer deptId;
	
	@Override
	protected Serializable pkVal() {
		return this.contractId;
	}

	@Override
	public String toString() {
		return "Contract [contractId=" + contractId + ", initDate=" + initDate + ", createTime=" + createTime
				+ ", isGroupConstract=" + isGroupConstract + ", deptId=" + deptId + "]";
	}
	

}
