package com.gobestsoft.common.modular.cms.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author wxy
 * @since 2017-11-21
 */
@Data
@TableName("cms_ad")
public class Ad extends Model<Ad> {

    private static final long serialVersionUID = 1L;

    /**
     * 广告idID
     */
	private Long id;
    /**
     * 标题
     */
	private String title;
    /**
     * 封面 相对地址
     */
	private String coverPath;
    /**
     * 封面 绝对路径
     */
	private String coverUrl;
    /**
     * 栏目 id
     */
	private Integer columnId;
    /**
     * 链接地址
     */
	private String linkUrl;
    /**
     * 投放开始时间
     */
	private String startTime;
    /**
     * 投放结束时间
     */
	private String endTime;
    /**
     * 顺序
     */
	private Integer sort;
    /**
     * 
     */
	private String createUser;
    /**
     * 
     */
	private String createTime;
    /**
     * 
     */
	private String updateUser;
    /**
     * 
     */
	private String updateTime;
    /**
     * 状态 0：草稿，1：发布，-1：下架
     */
	private Integer status;
    /**
     * 点击数
     */
	private Integer hitNum;
	/**
	 * 标签
	 */
	private String tags;
    /**
     * 广告商商标
     */
	private String logo;
    /**
     * 广告商公司名字
     */
	private String companyName;
    /**
     * 广告商类型0：为普通广告。1:t推广广告(资讯列表第一页必出)
     */
	private Integer type;
	/**
	 * 是否删除
	 */
	private Integer delFlg;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Ad [adId=" + id + ", title=" + title + ", coverPath=" + coverPath + ", coverUrl=" + coverUrl
				+ ", columnId=" + columnId + ", linkUrl=" + linkUrl + ", startTime=" + startTime + ", endTime="
				+ endTime + ", sort=" + sort + ", createUser=" + createUser + ", createTime=" + createTime
				+ ", updateUser=" + updateUser + ", updateTime=" + updateTime + ", status=" + status + ", hitNum="
				+ hitNum + ", tags=" + tags + ", logo=" + logo + ", companyName=" + companyName + ", type=" + type
				+ ", delFlg=" + delFlg + "]";
	}



	


	
}
