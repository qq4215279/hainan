package com.gobestsoft.common.modular.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.cms.model.ArticleInfo;
import com.gobestsoft.common.modular.cms.model.ArticleType;
import com.gobestsoft.common.modular.cms.model.CmsArticleEntity;

/**
 * 
 * This is `ArticleType`
 * @ArticleTypeDao.java
 * @author wxy
 * @2017年12月7日 上午10:10:51
 */
public interface ArticleTypeDao {
	
	/**
	 * 获取所有
	 * @return
	 */
	public List<ArticleType> selectAll();
	
}
