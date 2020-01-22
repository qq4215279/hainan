package com.gobestsoft.common.modular.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gobestsoft.common.modular.cms.model.Advertisement;
import org.springframework.stereotype.Repository;

/**
 * 广告
 * 
 * @author gutongwei
 *
 *         2017年12月8日
 */
@Repository
public interface AdvertisementDao {

	/**
	 * 获取广告列表
	 * 
	 * @return
	 */
	List<Advertisement> getAdvertisement(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                         @Param("type") Integer type, @Param("showedIds") String showedIds);

	/**
	 * 获取广告列表
	 * 
	 * @return
	 */
	List<Advertisement> getAdvertisementByArticleId(@Param("articleId") long articleId, @Param("startTime") String startTime,
                                                    @Param("endTime") String endTime, @Param("type") Integer type);
	

	/**
	 * 添加广告点击数
	 * @param id
	 */
	void addAdvertisementHit(@Param("id") long id);
}
