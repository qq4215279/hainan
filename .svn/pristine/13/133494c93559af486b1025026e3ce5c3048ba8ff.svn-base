package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.WebSiteUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 新闻音频
 * 
 * @author gutongwei
 *
 *         2017年11月30日
 */
public class NewsTape {

	private long id;

	private String title;

	private String cover_url;

	private String create_by;

	private String create_time;

	private String tape_url;

	private String tape_time;

	public String getCreate_time() {

		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


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

	public String getCover_url() {
		return WebSiteUtil.getBrowseUrl(cover_url);
	}

	public void setCover_url(String cover_url) {
		this.cover_url = cover_url;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public String getTape_url() {
		return WebSiteUtil.getBrowseUrl(tape_url);
	}

	public void setTape_url(String tape_url) {
		this.tape_url = tape_url;
	}

	public String getTape_time() {
		if(StringUtils.isEmpty(this.tape_time)){
			return  "未知";
		}
		return tape_time;
	}

	public void setTape_time(String tape_time) {
		this.tape_time = tape_time;
	}

}
