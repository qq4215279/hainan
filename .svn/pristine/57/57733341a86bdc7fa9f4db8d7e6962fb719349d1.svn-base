package com.gobestsoft.common.modular.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gobestsoft.common.modular.cms.model.MineRecommend;

/**
 * 推荐
 * 
 * @author gutongwei
 *
 *         2017年12月1日
 */
@Repository
public interface RecommendDao {

	/**
	 * 获取我的推荐列表
	 * 
	 * @param auid
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<MineRecommend> getMineRecommend(@Param("auId") String auId, @Param("startIndex") int startIndex,
                                         @Param("pageSize") int pageSize);

	/**
	 * 添加推荐
	 * 
	 * @param auId
	 * @param recommendation
	 * @param type
	 * @param target
	 * @param createTime
	 */
	void addRecommend(@Param("auId") String auId, @Param("title") String title,
                      @Param("recommendation") String recommendation, @Param("type") int type, @Param("target") String target,
                      @Param("createTime") String createTime);

}
