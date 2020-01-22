package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 会员表
 * @author xss
 * @date 2018-08-15 
 *
 */
@Data
@TableName("dept_member")
public class DeptMember extends Model<DeptMember>{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 1:普通会员；2：预备会员
	 */
	public final static String[] A_TYPE = {"1","2"};
	
	/**
	 * 主键id
	 */
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	/**
	 * 所属工会id（sys_dept表id）
	 */
	private Integer deptId;
	/**
	 * 人员信息id（person_info表id）
	 */
	private Integer personId;
	/**
	 * 会员卡号member_card
	 */
	private String memberCard;
	/**
	 * 内部站内卡号station_card
	 */
	private String stationCard;
	private String createUser;
	private String createTime;
	private String updateUser;
	private String updateTime;
	/**
	 * 会员类型（1：普通会员；2：预备会员）
	 */
	private String type;

	@TableField(value = "isBind")
	private Integer isBind;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	

}
