package com.gobestsoft.common.modular.mst.model;
import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
/**
 * 会员干部信息表
 * @author zhangdw
 * 2018-05-11 下午5点49分 
 */
@Data
@TableName("mst_member_cadre")
public class MstMemberCadre extends Model<MstMemberCadre> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	@TableId(value="cadre_id",type=IdType.AUTO)
	private Integer cadreId;
	/**
	 * 公司id（mst_organization表id）
	 */
	private Integer organizationId;
	/**
	 * 工会id（sys_dept表id）
	 */
	private Integer unionId;
	/**
	 * 会员id
	 */
	private Integer memberId;
	/**
	 * 工会职务
	 */
	private String unionPosition;
	/**
	 * 专/兼职工会职务==专职（兼职）工会干部(1:专职；2:兼职)
	 */
	private String fullTimeUnion;
	/**
	 * 是否同级工会委员
	 */
	private String samelevelUnionCommitteeman;
	/**
	 * 是否同级工会经审委委员
	 */
	private String trialCommitteeman;
	/**
	 * 是否同级工会女工委委员
	 */
	private String womanWorkerCommitteeman;
	/**
	 * 兼任处级工会职务
	 */
	private String divisionPosition;
	/**
	 * 兼任局级工会职务
	 */
	private String bureauPosition;
	/**
	 * 兼任股份公司工会职务
	 */
	private String corpPosition;
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
		return this.cadreId;
	}
}
