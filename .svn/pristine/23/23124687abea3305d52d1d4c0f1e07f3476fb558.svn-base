package com.gobestsoft.common.modular.dept.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * <p>
 * 组织会员表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-11-30
 */
@Data
@TableName("org_member")
public class Member extends Model<Member> {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
	private String ouid;
	/**
     * 会员编号
     */
	private String memberCode;
	/**
     * 姓名
     */
	private String name;
	/**
     * 单位名称
     */
	private String unitName;
	/**
     * 电话
     */
	private String phone;
	/**
     * 是否劳模
     */
	private Integer isModel;
	/**
     * 工会组织ID
     */
	private Integer orgId;
	/**
     * 证件类型 1:身份证；2:护照；
     */
	private String identificationType;
	/**
     * 证件号码
     */
	private String identificationNum;
	@TableField(value="del_flg")
	private Integer delFlg;
    
    
	@Override
	protected Serializable pkVal() {
		return this.ouid;
	}

	@Override
	public String toString() {
		return "Member [ouid=" + ouid + ", memberCode=" + memberCode + ", name=" + name + ", unitName=" + unitName
				+ ", phone=" + phone + ", isModel=" + isModel + ", orgId=" + orgId + ", identificationType="
				+ identificationType + ", identificationNum=" + identificationNum + ", delFlg=" + delFlg + "]";
	}

}
