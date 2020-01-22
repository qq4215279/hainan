package com.gobestsoft.common.modular.cms.model;


import com.gobestsoft.core.util.DateUtil;


/**
 * 发现订阅号咨询
 * 
 * @author gutongwei
 *
 *         2017年11月29日
 */
public class SubscriptionArticle {
	private long article_id;

	private String subscriber_id;

	private String subscriber_name;

	private String subscriber_avatar;

	private String create_time;

	private String title;

	private String cover;
	
	private String target_url;
	
	

	public String getTarget_url() {
		return this.target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}


	public long getArticle_id() {
		return article_id;
	}

	public void setArticle_id(long article_id) {
		this.article_id = article_id;
	}

	public String getSubscriber_id() {
		return subscriber_id;
	}

	public void setSubscriber_id(String subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	public String getSubscriber_name() {
		return subscriber_name;
	}

	public void setSubscriber_name(String subscriber_name) {
		this.subscriber_name = subscriber_name;
	}

	public String getSubscriber_avatar() {
		return subscriber_avatar;
	}

	public void setSubscriber_avatar(String subscriber_avatar) {
		this.subscriber_avatar = subscriber_avatar;
	}

	public String getCreate_time() {
		return DateUtil.format(DateUtil.parse(create_time, "yyyyMMddHHmm"), "yyyy-MM-dd HH:mm:ss");
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

}
