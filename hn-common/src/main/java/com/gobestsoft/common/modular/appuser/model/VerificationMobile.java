package com.gobestsoft.common.modular.appuser.model;

import java.util.Date;

/**
 * 验证码
 * @author gutongwei
 *
 * 2017年11月25日
 */
public class VerificationMobile {

	private int id;
	
	private String mobile;
	
	private String code;
	
	private String create_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	
	
	
	
}
