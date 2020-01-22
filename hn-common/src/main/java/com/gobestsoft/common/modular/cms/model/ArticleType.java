package com.gobestsoft.common.modular.cms.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * This is `资讯类型`
 * @ArticleType.java
 * @author wxy
 * @2017年12月7日 上午10:16:31
 */
@Data
@TableName("cms_article")
public class ArticleType extends Model<ArticleType> {

    private static final long serialVersionUID = 1L;

    private String articleType;
    
    private String articleTypeName;

	@Override
	protected Serializable pkVal() {
		return this.articleType;
	}

	@Override
	public String toString() {
		return "ArticleType [articleType=" + articleType + ", articleTypeName=" + articleTypeName + "]";
	}
	
	


}
