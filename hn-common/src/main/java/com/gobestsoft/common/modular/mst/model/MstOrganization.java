package com.gobestsoft.common.modular.mst.model;
import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
/**
 * 组织信息表
 * @author zhangdw
 * 2018-05-11 下午1点 
 */
@Data
@TableName("mst_organization")
public class MstOrganization extends Model<MstOrganization> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	@TableId(value="organizationId",type=IdType.AUTO)
	private Integer organizationId;
	/**
	 * 单位名称
	 */
	private String unitName;
	/**
	 * 统一社会信用代码（组织机构代码）
	 */
	private String unitOrgCode;
	/**
	 * 所属业务领域
	 */
	private String unitPlate;
	/**
	 * 单位地址
	 */
	private String unitAddress;
	/**
	 * 职工人数
	 */
	private Integer unitNumber;
	/**
	 * 工会名称==工会全称
	 */
	private String unionName;
	/**
	 * 工会简称
	 */
	private String unionSimpleName;
	/**
	 * 工会组织机构代码
	 */
	private String unionOrgCode;
	/**
	 * 工会编号 （生成规则：补零+主键；共六位数）
	 */
	private String unionNumber;
	/**
	 * 工会类型
	 */
	private String unionType;
	/**
	 * 建会时间
	 */
	private String createUnionTime;
	/**
	 * 换届日期
	 */
	private String changeTime;
	/**
	 * 上级工会id（sys_dept表id）
	 */
	private Integer pId;
	/**
	 * 上级工会名称（sys_dept表fullname全称）
	 */
	private String pName;
	/**
	 * 部门id（sys_dept表id）
	 */
	private Integer deptId;
	/**
	 * 部门类型（下拉框：工会0、部门1）
	 */
	private Integer deptType;
	/**
	 * 地方协管工会名称
	 */
	private String localAssistManageUnionName;
	/**
	 * 会员人数
	 */
	private Integer membership;
	/**
	 * 工会负责人（主席、工委主任）
	 */
	private String unionLeader;
	/**
	 * 任职时间
	 */
	private String tenureTime;
	/**
	 * 专职（兼职）1:专职 2:兼职
	 */
	private String fullTime;
	/**
	 * 工会负责人电话
	 */
	private String unionLeaderPhone;
	/**
	 * 序号
	 */
	private Integer sort;
	private String createUser;
	private String createTime;
	private String updateUser;
	private String updateTime;
	@Override
	protected Serializable pkVal() {
		return this.organizationId;
	}

}
