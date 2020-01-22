package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.DateUtil;

import lombok.Data;

/**
 * 广告信息
 * 
 * @author gutongwei
 *
 *         2017年12月8日
 */
public class Advertisement {

	private int id;

	/**
	 * 广告标题
	 */
	private String title;

	/**
	 * 广告跳转页
	 */
	private String link;

	/**
	 * 广告封面图
	 */
	private String cover;

	/**
	 * 点击数
	 */
	private int hit_num;

	private String create_by;

	private String create_time;
	
	/**
	 * 标签
	 */
	private String tags;

	private String logo;
	
	private String company_name;
	
	
	private int type;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public int getHit_num() {
		return hit_num;
	}

	public void setHit_num(int hit_num) {
		this.hit_num = hit_num;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public String getCreate_time() {
		return DateUtil.format(DateUtil.parse(create_time, "yyyyMMddHHmmss"), "yyyy-MM-dd");
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
