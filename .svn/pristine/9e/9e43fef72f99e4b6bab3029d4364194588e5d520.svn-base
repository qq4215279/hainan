package com.gobestsoft.common.modular.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gobestsoft.common.modular.cms.model.SubmissionMine;

/** 
*@author  作者 : liqicheng
*@date 创建时间：2017年11月29日 下午3:23:13 
 */
@Repository
public interface SubmissionDao {

	/**
	 * 我要投稿
	 * @param map
	 */
	void addSubmission(Map<String, Object> map);

	//void personalSubmission(@Param("articleId")Long articleId,@Param("content")String content);
	
	/**
	 * 我的投稿
	 * 
	 */
	List<SubmissionMine> mineSubmission(@Param("startIndex") int startIndex,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("auId") String auId);

}
