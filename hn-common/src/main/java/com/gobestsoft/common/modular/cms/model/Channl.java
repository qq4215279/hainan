/**
 * 
 */package com.gobestsoft.common.modular.cms.model;

import lombok.Data;

/**
 * @author lqc
 *
 */
 @Data
public class Channl {
	 
	 /**
	  * 频道id
	  */
	private int id;
	
	 /**
	  * 频道名称
	  */
	private String name;
	@Override
	public String toString() {
		return "Channl [id=" + id + ", name=" + name + "]";
	}
	
	

}
