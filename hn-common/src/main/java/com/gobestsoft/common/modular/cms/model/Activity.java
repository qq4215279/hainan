package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.DateUtil;

import com.gobestsoft.core.util.WebSiteUtil;

/**
* @author 作者 : liqicheng
* @version 创建时间：2017年12月1日 下午2:03:45
* 
*/

public class Activity {

	private long id;
	
	private String title;
	
	private String start_time;
	
	private String end_time;
	
	private String cover;
	
	private String jump_url;
	
	private boolean is_expired;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart_time() {
		return DateUtil.format(DateUtil.parse(this.start_time, "yyyyMMddHHmmss"), "yyyy-MM-dd");
	}

	public void setStart_time(String start_time) {
		
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return DateUtil.format(DateUtil.parse(this.end_time, "yyyyMMddHHmmss"), "yyyy-MM-dd");
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getCover() {
		return WebSiteUtil.getBrowseUrl(cover);
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getJump_url() {
		return jump_url;
	}

	public void setJump_url(String jump_url) {
		this.jump_url = jump_url;
	}

	public boolean getIs_expired() {
		 long nowTime=Long.parseLong(DateUtil.getAllTime());
//		yyyyMMddHHmmss
		long  endTime = Long.parseLong(this.end_time);
	
		return nowTime>endTime;
	}

	public void setIs_expired(Boolean is_expired) {
		this.is_expired = is_expired;
	}


	public void setIs_expired(boolean is_expired) {
		this.is_expired = is_expired;
	}
	
	

}
