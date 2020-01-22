package com.gobestsoft.common.modular.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gobestsoft.common.modular.cms.model.Newspaper;
import com.gobestsoft.common.modular.cms.model.NewspaperPage;


/**
 * 报纸
 * 
 * @author root
 *
 */
@Repository
public interface NewspaperDao {

	/**
	 * 获取报纸目录
	 * 
	 * @param newspapperId
	 * @param date
	 * @return
	 */
	List<NewspaperPage> getNewspaperCatalogs(@Param("newspapperId") int newspapperId, @Param("date") String date);

	/**
	 * 获取新闻内容
	 * 
	 * @param newspapperId
	 * @param catalogID
	 * @return
	 */

	String getNewspapperContent(@Param("newspapperId") int newspapperId, @Param("catalogID") int catalogID);
	



	/**
	 * 获取报纸列表
	 * 
	 * @return
	 */
	List<Newspaper> getNewspaperList(@Param("cityCode") String cityCode);
}
