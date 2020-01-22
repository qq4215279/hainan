package com.gobestsoft.common.modular.mst.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 申请建会信息表
 * @author ke
 *
 */

@Data
@TableName("mst_enterprise")
public class Enterprise extends Model<Enterprise> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@TableId(value="enterpriseId",type=IdType.AUTO)
	private Integer enterpriseId;

	/**
	 * 上级工会id
	 */
	private Integer pId;
	
	/**
	 * 上级工会名称
	 */
	private String pName;
	
	/**
	 * 单位名称
	 */
	private String enterpriseName;
	
	/**
	 * 组织机构代码
	 */
	private String orgCode;
	
	/**
	 * 单位类型
	 */
	private String unitType;
	
	/**
	 * 经济类型
	 */
	private String economicType;
	
	/**
	 * 所属行业
	 */
	private String industryType;
	
	/**
	 * 详细地址
	 */
	private String address;
	
	/**
	 * 联系人
	 */
	private String person;
	
	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 职工数量
	 */
	private Integer enterpriseNumber;
	
	/**
	 * 建会目的
	 */
	private String comment;
	
	/**
	 * 企业建会申请关联编号
	 */
	private String code;
	
	private Integer status;
	
	private String createUser;
	
	private String createTime;
	
	private String updateUser;
	
	private String updateTime;
	
	
	@Override
	protected Serializable pkVal() {
		return this.enterpriseId;
	}


	@Override
	public String toString() {
		return "Enterprise [enterpriseId=" + enterpriseId + ", pId=" + pId + ", pName=" + pName + ", enterpriseName="
				+ enterpriseName + ", orgCode=" + orgCode + ", unitType=" + unitType + ", economicType=" + economicType
				+ ", industryType=" + industryType + ", address=" + address + ", person=" + person + ", phone=" + phone
				+ ", enterpriseNumber=" + enterpriseNumber + ", comment=" + comment + ", code=" + code + ", status="
				+ status + ", createUser=" + createUser + ", createTime=" + createTime + ", updateUser=" + updateUser
				+ ", updateTime=" + updateTime + "]";
	}
	
}
