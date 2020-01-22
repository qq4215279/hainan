package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 人员信息表
 * @author xss
 * @date 2018-08-21
 */
@Data
@TableName("person_info")
public class PersonInfo extends Model<PersonInfo>{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	private String name;
	private String sex;
	private String birthday;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 就业情况work_situation
	 */
	private String workSituation;
	/**
	 * 证件类型certificate_type
	 */
	private String certificateType;
	/**
	 * 证件号certificate_num
	 */
	private String certificateNum;
	/**
	 * 学历
	 */
	private String education;
	/**
	 * 技能等级technology_level
	 */
	private String technologyLevel;
	private String mobile;
	/**
	 * 户籍类型
	 */
	private String household;
	/**
	 * 户籍所在地
	 */
	private String domicile;
	/**
	 * 会籍变化类型
	 */
	private String memberChange;
	/**
	 * 会籍变化时间member_change_date
	 */
	private String memberChangeDate;
	/**
	 * 会籍变化原因
	 */
	private String memberChangeReason;
	/**
	 * 政治面貌political_status
	 */
	private String politicalStatus;
	/**
	 * 工作单位work_unit
	 */
	private String workUnit;
	/**
	 * 籍贯native_place
	 */
	private String nativePlace;
	/**
	 * 出生地homeplace
	 */
	private String homeplace;
	/**
	 * 是否农民工 is_farmer
	 */
	private String isFarmer;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	

}

