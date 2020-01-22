package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.WebSiteUtil;

public class SubscribedNumber {

	public SubscribedNumber() {
	}

	public SubscribedNumber(String id, String name, String logo, String jump_url, String qr_code) {
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.jump_url=jump_url;
		this.qr_code=qr_code;
	}

	private String id;

	private String name;

	private String logo;

	private String jump_url;

	private String qr_code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return WebSiteUtil.getBrowseUrl(logo);
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getJump_url() {
		return jump_url;
	}

	public void setJump_url(String jump_url) {
		this.jump_url = jump_url;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}
}
