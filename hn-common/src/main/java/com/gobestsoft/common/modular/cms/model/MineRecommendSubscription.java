package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.WebSiteUtil;

/**
 * 我推荐的订阅号
 * @author root
 *
 */
public class MineRecommendSubscription {
	
	private String id;
	
	private String name; 
	
	private String avatar;
	
	private String description;

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

	public String getAvatar() {
		return WebSiteUtil.getBrowseUrl(avatar);
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
