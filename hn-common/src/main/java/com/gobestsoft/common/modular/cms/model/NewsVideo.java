package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.DateUtil;

/**
* @author 作者 : liqicheng
* @version 创建时间：2017年12月5日 上午11:06:54
* 
*/

public class NewsVideo {
	  

	private long id;
    
	private String title;
	
	private String createBy;
	
	private String cover;
	

	private String video_url;
	
	private String create_time;
	
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getCreate_time() {
		
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

			


}
