package com.gobestsoft.common.modular.cms.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * This is `资讯目录`
 * @Category.java
 * @author wxy
 * @2017年12月5日 上午11:09:42
 */
@Data
@TableName("cms_category")
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Integer id;
    /**
     * 咨询父目录ID
     */
	private Integer pid;
    /**
     * 资讯目录名称
     */
	private String name;

	/**
	 * 图标
     */
	private String icon;
    /**
     * 
     */
	private String createTime;
	/**
	 *
     */
	private String createUser;
    /**
     * 
     */
	private String updateTime;
	/**
	 *
     */
	private String updateUser;
    /**
     * del_flg:0：未删除，1已删除
     */
	private Integer delFlg;
 
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", pid=" + pid + ", name=" + name + ", icon=" + icon + ", createUser=" + createUser + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", updateUser=" + updateUser + ", delFlg=" + delFlg + "]";
	}

	

	

	
}
