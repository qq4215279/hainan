package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
/**
 * 工会补足表（基层）
 * @author ke
 *
 */

@Data
@TableName("org_dept_ext")
public class DeptExt extends Model<DeptExt>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@TableId(value="dept_Id",type=IdType.AUTO)
	private Integer dept_Id;
	
	/**
	 * 类别 0：组织  1：部门
	 */
	private Integer deptType;
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
		return this.dept_Id;
	}

	@Override
	public String toString() {
		return "DeptExt [dept_Id=" + dept_Id + ", deptType=" + deptType + ", orgCode=" + orgCode + ", economyType="
				+ economyType + ", districtCode=" + districtCode + ", streetCode=" + streetCode + ", unitType="
				+ unitType + ", industryType=" + industryType + ", unitName=" + unitName + ", baseOrgType="
				+ baseOrgType + ", changeDate=" + changeDate + ", telephone=" + telephone + ", employeeCnt="
				+ employeeCnt + "]";
	}

	
}
