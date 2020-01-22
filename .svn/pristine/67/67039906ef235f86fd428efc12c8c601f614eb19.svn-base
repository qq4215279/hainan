package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * <p>
 * 工会信息管理表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-11-24
 */
@Data
@TableName("org_dept_ext")
public class InfoManage extends Model<InfoManage> {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
	private Integer id;
    /**
     * 上级工会id
     */
	private Integer pid;
    /**
     * 组织ID
     */
	private Integer deptId;
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
	private Integer economyType;	
    /**
     * 单位性质类别
     */
	private String unitType;
    /**
     * 单位所属行业
     */
	private String industryType;
    /**
     * 单位名称
     */
	private String unitName;
    /**
     * 基层工会类型
     */
	private String baseOrgType;
    /**
     * 换届日期
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
    /**
     * 工会名称
     */
	private String deptName;
    /**
     * 组织编号
     */
	private String deptNo;
    /**
     * 单位地址
     */
	private String addr;
	/**
     * 上级工会
     */
	private String pDeptNo;
    /**
     * 上级工会名称
     */
	private String pDeptName;
    /**
     * 工会负责人
     */
	private String picName;
    /**
     * 建会日期
     */
	private String establishDate;
    /**
     * 工会状态
     */
	private Integer isClosed;
	/**
     * 工会级别
     */
	private Integer level;
	
    
	@Override
	protected Serializable pkVal() {
		return this.deptId;
	}

	@Override
	public String toString() {
		return "InfoManage{" +
				"deptId=" + deptId +
				", deptType=" + deptType +
				", orgCode=" + orgCode +
				", economyType=" + economyType + 
				", unitType=" + unitType + 
				", industryType=" + industryType + 
				", unitName=" + unitName + 
				", baseOrgType=" + baseOrgType + 
				", changeDate=" + changeDate + 
				", telephone=" + telephone + 
				", employeeCnt=" + employeeCnt + 
				", deptName=" + deptName + 
				", deptNo=" + deptNo + 
				", addr=" + addr + 
				", pDeptName=" + pDeptName + 
				", picName=" + picName + 
				", establishDate=" + establishDate + 
				", isClosed=" + isClosed + 
				", level=" + level + 
				"}";
	}

}
