package com.gobestsoft.common.modular.cms.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 资讯图文表
 * </p>
 *
 * @author gobestsoft
 * @since 2017-11-21
 */
@Data
@TableName("cms_article_image")
public class ArticleImage extends Model<ArticleImage> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 资讯ID(YYYYMMDDXXXX)
     */
	private Integer articleId;
    /**
     * 类型:1：缩略图，2：详情banner，3：详情内容
     */
	private Integer imageType;
    /**
     * 图片地址
     */
	private String imgUrl;
    /**
     * 排序
     */
	private Integer sort;
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
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ArticleImage{" + 
				"id=" + id + 
				", articleId=" + articleId + 
				", imageType=" + imageType + 
				", imgUrl=" + imgUrl + 
				", sort=" + sort + 
				", createUser=" + createUser + 
				", createTime=" + createTime + 
				", updateUser=" + updateUser + 
				", updateTime=" + updateTime + 
				"}";
	}
}
