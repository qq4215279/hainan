
package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.DateUtil;

import lombok.Data;

/**
 *   @author  作者 : lqc
 * 
 * @date 创建时间：2017年11月26日 下午11:28:46   
 */
@Data
public class Subscription {

	/**
	 * 订阅号id
	 */
	private String id;

	/**
	 * 订阅号头像
	 */
	private String avatar;

	/**
	 * 订阅号名称
	 */
	private String name;

	/**
	 * 订阅号创建时间
	 */
	private String create_time;

	/**
	 * 订阅号是否订阅
	 */
	private int is_subscribed;
	
	private String description;

	

	

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreate_time() {
		return DateUtil.format(DateUtil.parse(create_time, "yyyyMMddHHmmss"), "yyyy-MM-dd");
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getIs_subscribed() {
		return is_subscribed;
	}

	public void setIs_subscribed(int is_subscribed) {
		this.is_subscribed = is_subscribed;
	}

	
}
