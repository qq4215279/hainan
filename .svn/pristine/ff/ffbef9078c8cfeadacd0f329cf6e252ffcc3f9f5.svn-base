package com.gobestsoft.common.modular.cms.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import com.gobestsoft.common.modular.cms.model.Category;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao {
	
	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	public Category selectCategoryById(Integer id);
	
	/**
	 * 添加article和category关系
	 * @param articleid
	 * @param categoryid
	 * @return
	 */
	public int insertRArticleCategory(@Param("articleid") String articleid, @Param("categoryid") Integer categoryid, @Param("pubStatus") Integer pubStatus, @Param("published_time") String published_time);
	
	public int updateRArticleCategory(@Param("articleid") String articleid, @Param("categoryid") Integer categoryid, @Param("pubStatus") Integer pubStatus, @Param("published_time") String published_time);
	
	public int checkDown(@Param("articleid") String articleid, @Param("categoryid") Integer categoryid);
	/**
	 * 条件查询   包含层级关系
	 * @return
	 */
	public List<Category> getCategoryByConditionWithTree(@Param("catType") Integer catType, @Param("tree") String tree);
	
	public List<Category> getAll();

	List<Map<String, Object>> getTopicList(RowBounds page);

}
 