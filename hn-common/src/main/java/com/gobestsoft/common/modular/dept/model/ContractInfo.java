package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

import lombok.Data;

/**
 * 集体合同详情信息
 * @author ke
 *
 */

@Data
public class ContractInfo extends Model<Contract>{
	
	private static final long serialVersionUID = 1L;

	
	private String contractId;
	/**
	 * 组织ID
	 */
	private Integer deptId;
	/**
	 * 工会编号
	 */
	private String deptNo;
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
	 * 组织Id
	 */
	private Integer id;
	/**
     * 排序
     */
	private Integer sort;
    /**
     * 父部门id
     */
	private Integer pid;
    /**
     * 父级ids
     */
	private String pids;
    /**
     * 简称
     */
	private String simpleName;
    /**
     * 全称
     */
	private String fullName;
    /**
     * 提示
     */
	private String tips;
    /**
     * 版本（乐观锁保留字段）
     */
	private Integer version;
	/**
	 * 工会级别（省总：0，市总：1，区县总：2，街道：3, 基层：4）
	 */
	private Integer level;
	/**
	 * 工会地址
	 */
	private String addr;
	/**
	 * 类别 0：组织  1：部门
	 */
	private Integer deptType;
	/**
	 * 上级工会编号
	 */
	private String pDeptNo;
	/**
	 * 上级工会名称
	 */
	private String pDeptName;
	/**
	 * 负责人
	 */
	private String picName;
	/**
	 * 主席
	 */
	private String chairman;
	/**
	 * 机构代码
	 */
	private String orgCode;
	/**
	 * 经济类型
	 */
	private String economyType;
	/**
	 * 单位所在政区
	 */
	private String districtCode;
	/**
	 * 
	 */
	private String streetCode;
	/**
	 * 单位性质类别
	 */
	private Integer unitType;
	/**
	 * 
	 */
	private Integer industryType;
	/**
	 * 单位名称
	 */
	private String unitName;
	/**
	 * 基层工会类型
	 */
	private Integer baseOrgType;
	/**
	 * 
	 */
	private String changeDate;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 职工人数
	 */
	private String employeeCnt;
	
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "ContractInfo [contractId=" + contractId + ", deptId=" + deptId + ", deptNo=" + deptNo + ", initDate="
				+ initDate + ", createTime=" + createTime + ", isGroupConstract=" + isGroupConstract + ", id=" + id
				+ ", sort=" + sort + ", pid=" + pid + ", pids=" + pids + ", simpleName=" + simpleName + ", fullName="
				+ fullName + ", tips=" + tips + ", version=" + version + ", level=" + level + ", addr=" + addr
				+ ", deptType=" + deptType + ", pDeptNo=" + pDeptNo + ", pDeptName=" + pDeptName + ", picName="
				+ picName + ", chairman=" + chairman + ", orgCode=" + orgCode + ", economyType=" + economyType
				+ ", districtCode=" + districtCode + ", streetCode=" + streetCode + ", unitType=" + unitType
				+ ", industryType=" + industryType + ", unitName=" + unitName + ", baseOrgType=" + baseOrgType
				+ ", changeDate=" + changeDate + ", telephone=" + telephone + ", employeeCnt=" + employeeCnt + "]";
	}

}
