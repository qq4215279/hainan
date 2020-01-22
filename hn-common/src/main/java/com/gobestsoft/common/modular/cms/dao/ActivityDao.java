package com.gobestsoft.common.modular.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gobestsoft.common.modular.cms.model.Activity;


/**
* @author 作者 :liqicheng
* @version 创建时间：2017年12月1日 下午1:54:46
* 专题列表
*/
@Repository
public interface ActivityDao {

	/**
	 * 专题活动列表，暂时取出地区概念
	 * @param startIndex
	 * @param pageSize
	 * @param cityCode
	 * @return
	 */
	List<Activity> getSpecialActivities(@Param("startIndex") Integer startIndex,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("cityCode") String cityCode);
	
	void shareActivity(@Param("id") long id);

	List<Activity> getNotExpiredActivities(@Param("today") String today,
                                           @Param("cityCode") String cityCode);
}
