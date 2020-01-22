package com.gobestsoft.common.modular.cms.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 资讯表
 * </p> 
 *
 * @author gobestsoft
 * @since 2017-11-21
 */
@Data
@TableName("cms_article")
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    /**
     * 资讯ID
     * 资讯ID(CCCC+YYMMDDHHmmss+xxx)
     * (局code+子公司code+部门code+日期时间+随机三位数)
     */
	private String id;
    /**
     * 标题
     */
	private String title;
    /**
     * 子标题:专题
     */
	private String subTitle;
    /**
     * 标签: 逗号分割
     */
	private String tags;
    /**
     * 摘要
     */
	private String roundup;
    /**
     * 资讯来源
     */
	private String sourceFrom;
    /**
     * 作者
     */
	private String author;
    /**
     * 封面图路径
     */
	private String coverPath;
    /**
     * 内容
     */
	private String content;
    /**
     * 资讯类型:0：图文，1：音频，2：视频，3：跳转 4：文件（保留）
     */
	private Integer infoType;
	/**
	 * 城市编码
     */
	private String districtCode;
	/**
	 * 音频视频文件或跳转等的url
	 */
	private String jumpUrl;
    /**
     * 模板类型 0：左图右文，1：左文右图，2：单幅长图，3：三张多图
     */
	private Integer templateType;
    /**
     * 排序
     */
	private Integer sort;
	/**
	 * 所属组织id
	 */
	private Integer orgId;
	/**
	 * 管理平台定时发布时间
	 */
	private String publishedTime;
	/**
	 * 管理平台发布者uid
	 */
	private String publishedUid;
	/**
	 * 管理平台，下架时间
	 */
	private String underTime;
	/**
	 * 发布状态0：下架，1：上架，2：定时发布
	 */
	private Integer publishedStatus;
    /**
     * 状态  0：无效，1：有效
     */
	private Integer status;
	/**
	 * 是否置顶:0：否，1：是
	 */
	private Integer isOnTop;
	/**
	 * 置顶过期时间
	 */
	private String isOnTopOverdueTime;
    /**
     * 审批意见
     */
	private String checkDesc;
    /**
     * 审核时间
     */
	private String checkDate;
    /**
     * 审核者
     */
	private String checkUid;
    /**
     * 可分享:0：不可，1：可
     */
	private Integer isShare;
    /**
     * 可评论:0：不可，1：可
     */
	private Integer isTopic;
	/**
	 * 评论需要审核:0：不要，1：要
	 */
	private Integer isTopicCheck; 
    /**
     * 可点赞:0：不可，1：可
     */
	private Integer isLike;
    /**
     * 可收藏:0：不可，1：可
     */
	private Integer isCollect;
    /**
     * create_user
     */
	private String createUser;
    /**
     * create_time
     */
	private String createTime;
    /**
     * update_user
     */
	private String updateUser;
    /**
     * update_time
     */
	private String updateTime;
    /**
     * del_flg:0：未删除，1已删除
     */
	private Integer delFlg;

	/**
	 * 操作状态
     */
	private String divide;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", subTitle=" + subTitle + ", tags=" + tags + ", roundup="
				+ roundup + ", sourceFrom=" + sourceFrom + ", author=" + author + ", coverPath=" + coverPath
				+ ", content=" + content + ", infoType=" + infoType + ", districtCode=" + districtCode + ", jumpUrl=" + jumpUrl + ", templateType=" + templateType + ", sort=" + sort + ", orgId=" + orgId + ", publishedTime=" + publishedTime
				+ ", publishedUid=" + publishedUid + ", underTime=" + underTime + ", publishedStatus=" + publishedStatus
				+ ", status=" + status + ", isOnTop=" + isOnTop + ", isOnTopOverdueTime="
				+ isOnTopOverdueTime + ", checkDesc=" + checkDesc + ", checkDate=" + checkDate + ", checkUserId="
				+ checkUid + ", isShare=" + isShare + ", isTopic=" + isTopic + ", isTopicCheck=" + isTopicCheck
				+ ", isLike=" + isLike + ", isCollect=" + isCollect + ", createUser=" + createUser + ", createTime=" + createTime + ", updateUser=" + updateUser + ", updateTime="
				+ updateTime + ", delFlg=" + delFlg + "]";
	}

}
