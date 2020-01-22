package com.gobestsoft.common.modular.dao.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("app_user")
@Data
public class AppUserPojo extends Model<AppUserPojo> {

    private static final long serialVersionUID = 1L;

	@Override
	protected Serializable pkVal() {
		return this.auid;
	}
    

    private String auid;


    private String avatar;


    private String account;

    private String password;

    private String nickName;

    private Short sex;

    private Short status;

    private String createTime;

    private String lastLoginTime;

    private String lastLoginIp;

    private String realName;

    private String auroraAccount;

    private Integer memberId;

    private String deviceInfo;


}