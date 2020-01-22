package com.gobestsoft.common.modular.cms.model;

import java.util.List;

import lombok.Data;

/** 
*@author  作者 : liqicheng
*@date 创建时间：2017年11月27日 下午2:16:23 
 */
@Data
public class ThumbsUp {
	
	 /**
     * 点赞数
     */
	private Integer thumbupNum;
	

	/**
    * 点赞用户
    */
	private List<Users> users;
	
	
}
