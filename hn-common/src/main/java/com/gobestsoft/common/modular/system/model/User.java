package com.gobestsoft.common.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Data
@NoArgsConstructor @AllArgsConstructor
@TableName("sys_user")
public class User extends Model<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
//	@TableId(value="uid", type= IdType.AUTO)
	@TableId(value="uid", type= IdType.INPUT)
	private String id;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 账号
     */
	private String account;
    /**
     * 密码
     */
	private String password;
    /**
     * md5密码盐
     */
	private String salt;
    /**
     * 名字
     */
	private String name;
    /**
     * 生日
     */
	//private Date birthday;
	private String birthday;
    /**
     * 性别（1：男 2：女）
     */
	private Integer sex;
    /**
     * 电子邮件
     */
	private String email;
    /**
     * 电话
     */
	private String phone;
    /**
     * 角色id
     */
	private String roleid;
    /**
     * 部门id
     */
	private Integer deptid;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
	private Integer status;
    /**
     * 创建时间
     */
	private String createtime;
	/**
	 * 是否为责任人 1：是 0：否'
	 */
	@TableField(value="is_responsible")
	private Integer isResponsible;
	/**
	 * 职务
	 */
	private String duty;
	/**
	 * 工作职责
	 */
	@TableField(value="duty_of_work")
	private String dutyOfWork;

	@TableField(exist = false)
	private String orgName;
	
	@TableField(exist = false)
	private Integer level;

	private String userType;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "User{" +
			"uid=" + id +
			", avatar=" + avatar +
			", account=" + account +
			", password=" + password +
			", salt=" + salt +
			", name=" + name +
			", birthday=" + birthday +
			", sex=" + sex +
			", email=" + email +
			", phone=" + phone +
			", roleid=" + roleid +
			", deptid=" + deptid +
			", status=" + status +
			", createtime=" + createtime +
			", isResponsible=" + isResponsible +
			", duty=" + duty +
			", dutyOfWork=" + dutyOfWork +
			"}";
	}
}
