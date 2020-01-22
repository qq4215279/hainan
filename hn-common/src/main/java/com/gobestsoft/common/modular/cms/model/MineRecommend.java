package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.DateUtil;

/**
 * 我的推荐
 * 
 * @author gutongwei
 *
 *         2017年12月1日
 */
public class MineRecommend {

	
	private int id;
	
	private String title;
	
	/**
	 * 推荐语
	 */
	private String recommendation;
	
	/**
	 * 推荐类型。0：订阅号。1：目标链接
	 */
	private int type;
	
	/**
	 * 推荐类型。0：订阅号。1：目标链接
	 */
	private int status;
	
	/**
	 * 推荐时间
	 */
	private String recommend_time;
	
	/**
	 * 推荐目标。如果推荐类型为0=订阅号名称。如果推荐类型为1=目标链接
	 */
	private String recommend_target;
	
	/**
	 * 拒绝理由
	 */
	private String reason;
	
	/**
	 * 审批时间
	 */
	private String check_time;
	
	
	private MineRecommendSubscription subscription;

	public String getCheck_time() {
		return check_time;
	}

	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}

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

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRecommend_time() {
		return DateUtil.formatDate(DateUtil.parse(this.recommend_time, "yyyyMMddHHmmss"), "yyyy-MM-dd HH:mm:ss");
	}

	public void setRecommend_time(String recommend_time) {
		this.recommend_time = recommend_time;
	}

	public String getRecommend_target() {
		return recommend_target;
	}

	public void setRecommend_target(String recommend_target) {
		this.recommend_target = recommend_target;
	}



	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public MineRecommendSubscription getSubscription() {
		return subscription;
	}

	public void setSubscription(MineRecommendSubscription subscription) {
		this.subscription = subscription;
	}
	
}
