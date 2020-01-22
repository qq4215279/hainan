package com.gobestsoft.common.modular.mst.model;
import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
/**
 * 会员信息表
 * @author zhangdw
 * 2018-05-11 下午1点 
 */
@Data
@TableName("mst_member")
public class MstMember extends Model<MstMember> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	@TableId(value="memberId",type=IdType.AUTO)
	private Integer memberId;
	/**
	 * 单位名称
	 */
	private String workUnit;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 最高学历
	 */
	private String education;
	/**
	 * 政治面貌
	 */
	private String politicalStatus;
	/**
	 * 身份证号
	 */
	private String idcardNumber;
	/**
	 * 参加工作日期
	 */
	private String workTime;
	/**
	 * 所在部门id（当前值为null，未导入数据）
	 */
	private Integer deptId;
	/**
	 * 所在部门名称
	 */
	private String deptName;
	/**
	 * 岗位职务
	 */
	private String duty;
	/**
	 * 专业技术职务
	 */
	private String technicalDuty;
	/**
	 * 工人技术等级名称
	 */
	private String technicalTitle;
	/**
	 * 所属工会id（sys_dept表id）
	 */
	private Integer unionId;
	/**
	 * 所属工会（sys_dept表中的fullname全称）
	 */
	private String unionName;
	/**
	 * 手机号码
	 */
	private String linkphone;
	/**
	 * 婚姻状况（1:未婚；2:已婚；3:丧偶；4:离婚）
	 */
	private String maritalStatus;
	/**
	 * 是否劳务派遣工(1：是；0否）
	 */
	private String workerMember;
	/**
	 * 家庭住址
	 */
	private String homeAddress;
	/**
	 * 工作类别
	 */
	private String jobType;
	/**
	 * 境外工作所在地
	 */
	private String overseasLocation;
	/**
	 * 是否是三级公司职工代表(1：是；0否）
	 */
	private String threeLevelEmployee;
	/**
	 * 是否是集团公司职工代表(1：是；0否）
	 */
	private String groupEmployee;
	/**
	 * 是否是股份公司职工代表(1：是；0否）
	 */
	private String cropEmployee;
	/**
	 * 家庭主要成员称谓
	 */
	private String familyMemberAppellation;
	/**
	 * 家庭主要成员姓名
	 */
	private String familyMemberName;
	/**
	 * 家庭主要成员联系方式
	 */
	private String familyMemberTelephone;
	/**
	 * 是否是干部 （1：是 ； 0：否）
	 */
	private String ifCadre;
	
	/**
	 * 序号
	 */
	private Integer sort;
	private String createUser;
	private String createTime;
	private String updateUser;
	private String updateTime;
	
	/**
	 * 仅用于会员数据保存时绑定干部信息；以下字段皆为干部信息表字段
	 */
	private String unionPosition;
	private String samelevelUnionCommitteeman;
	private String trialCommitteeman;
	private String womanWorkerCommitteeman;
	private String fullTimeUnion;
	private String divisionPosition;
	private String bureauPosition;
	private String corpPosition;
	private Integer organizationId;
	
	@Override
	protected Serializable pkVal() {
		return this.memberId;
	}
}
