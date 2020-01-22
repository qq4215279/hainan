package com.gobestsoft.common.modular.smp.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
/**
 * 互动圈小组表
 * @author xiashasha
 * 2018-06-06 14:47
 */
@Data
@TableName("smp_group")
public class SmpGroup  extends Model<SmpGroup>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	/**
	 * 创建人auid
	 */
	private String auid;
	/**
	 * 小组类型type(0:公开模式;1:小组模式)
	 */
	private Integer type;
	/**
	 * 小组名称group_name
	 */
	private String groupName;
	/**
	 * 小组介绍introduce
	 */
	private String introduce;
	/**
	 * 小组显示模式display_type
	 */
	private Integer displayType;
	/**
	 * 小组模式设定组织org_id
	 */
	private Integer orgId;
	/**
	 * 小组最大成员数max_member
	 */
	private Integer maxMember;
	/**
	 * 小组头像head_img
	 */
	private String headImg;
	/**
	 * 创建时间create_time
	 */
	private String createTime;
	/**
	 * 是否删除del_flg(0:未删除;1：已删除)
	 */
	private Integer delFlg;
	
	/**
	 * 审核状态 【0:待审核】、【1：通过】、【2：未通过】
	 */
	private Integer status;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SmpGroup [id=" + id + ", auid=" + auid + ", type=" + type + ", groupName=" + groupName + ", introduce="
				+ introduce + ", displayType=" + displayType + ", orgId=" + orgId + ", maxMember=" + maxMember
				+ ", headImg=" + headImg + ", createTime=" + createTime + ", delFlg=" + delFlg + ", status=" + status
				+ "]";
	}
	
}
