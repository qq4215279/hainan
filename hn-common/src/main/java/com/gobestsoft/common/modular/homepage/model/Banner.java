package com.gobestsoft.common.modular.homepage.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * This is `首页Banner`
 * @Category.java
 * @author wxy
 * @2017年12月5日 下午12:38:21
 */
@Data
@TableName("app_banner")
public class Banner extends Model<Banner> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Integer id;
    /**
     * 分类Id
     */
	private Integer categoryId;
    /**
     * ----
     */
	private Long resourceId;
    /**
     * ----
     */
	private Integer resourceType;
    /**
     * 标题
     */
	private String title;
    /**
     * 描述
     */
	private String description;
    /**
     * 图片路径（相对路径）
     */
	private String imgPath;
    /**
     * 图片路径（绝对路径）
     */
	private String imgUrl;
	/**
	 * ----
	 */
	private String createTime;
    /**
     * ----
     */
	private String createUser;
    /**
     *  上线状态 0：下线，1：上线
     */
	private Integer isEnable;
    /**
     * del_flg:0：未删除，1已删除
     */
	private Integer delFlg;
    /**
     * 排序
     */
	private Integer sort;
	
	private String cityCode;
  
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Banner [id=" + id + ", categoryId=" + categoryId + ", resourceId=" + resourceId + ", resourceType="
				+ resourceType + ", title=" + title + ", description=" + description + ", imgPath=" + imgPath
				+ ", imgUrl=" + imgUrl + ", createTime=" + createTime + ", createUser=" + createUser + ", isEnable="
				+ isEnable + ", delFlg=" + delFlg + ", sort=" + sort + ", cityCode=" + cityCode + "]";
	}


	

}
