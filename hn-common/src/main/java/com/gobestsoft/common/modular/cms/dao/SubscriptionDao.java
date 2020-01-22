package com.gobestsoft.common.modular.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gobestsoft.common.modular.cms.model.AllSubscribe;
import com.gobestsoft.common.modular.cms.model.SubscribedMatrix;
import com.gobestsoft.common.modular.cms.model.SubscriptionAndDynamic;
import com.gobestsoft.common.modular.cms.model.SubscriptionArticle;
import com.gobestsoft.common.modular.cms.model.SubscriptionDynamic;
import com.gobestsoft.common.modular.cms.model.SubscriptionNumber;

/**
 * 订阅号
 * 
 * @author gutongwei
 *
 *         2017年11月28日
 */
@Repository
public interface SubscriptionDao {
//	废弃

//	/**
//	 * 获取所有订阅号
//	 *
//	 * @param auId
//	 * @return
//	 */
//	List<SubscriptionNumber> getAllSubscriptionNumber(@Param("startIndex") int startIndex,
//                                                      @Param("pageSize") int pageSize, @Param("auId") String auId);
//
//	/**
//	 * 获未订阅的订阅号
//	 * @param auId
//	 * @param startIndex
//	 * @param pageSize
//	 * @return
//	 */
//	List<SubscriptionNumber> getNotSubscriptionNumber(@Param("auId") String auId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
//
//	/**
//	 * 获未订阅的订阅号
//	 * @param auId
//	 * @param startIndex
//	 * @param pageSize
//	 * @return
//	 */
//	List<SubscriptionNumber> getSubscriptionNumber(@Param("auId") String auId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
//
//	/**
//	 * 最新发现获取最新发布的咨询关联出订阅号
//	 *
//	 * @param startIndex
//	 * @param pageSize
//	 * @return
//	 */
//	List<SubscriptionArticle> getSubscriptionArticles(@Param("startIndex") int startIndex,
//                                                      @Param("pageSize") int pageSize);
//
//	/**
//	 * 获取单个的订阅号
//	 *
//	 * @param id
//	 * @return
//	 */
//	SubscriptionNumber getSingleSubscriptionNumber(@Param("id") String id, @Param("auId") String auId);
//
//	/**
//	 * 获取订阅号的最新咨询
//	 *
//	 * @param mediaId
//	 * @return
//	 */
//	List<SubscriptionDynamic> getSubscriptionDynamics(@Param("mediaId") String mediaId);
//
//	/**
//	 * 获取订阅号详细
//	 *
//	 * @param mediaId
//	 * @param auId
//	 * @return
//	 */
//	SubscriptionAndDynamic getSubscriptionAndDynamics(@Param("mediaId") String mediaId, @Param("auId") String auId);
//
//	/**
//	 * 订阅订阅号
//	 *
//	 * @param auId
//	 * @param id
//	 */
//	void addSubscribedNumber(@Param("auId") String auId, @Param("id") String id, @Param("createTime") String createTime);
//
//
//	/**
//	 * 取消订阅订阅号
//	 *
//	 * @param auId
//	 * @param id
//	 */
//	void delSubscribedNumber(@Param("auId") String auId, @Param("id") String id);
//
//	/**
//	 * 是否订阅订阅号
//	 *
//	 * @param auId
//	 * @param id
//	 * @return
//	 */
//	Integer subscribedNumber(@Param("auId") String auId, @Param("id") String id);
//	/**
//	 * 获铁新媒体订阅号矩阵
//	 * @param keyword
//	 * @return
//	 */
//	List<SubscribedMatrix> getSubscribedNumberList(@Param("keyword") String keyword);
//	/**
//	 * 获取所有的订阅号
//	 * @param startIndex
//	 * @param pageSize
//	 * @return
//	 */
//	List<AllSubscribe> getAllSubscribedNumber(@Param("startIndex") int startIndex,
//                                              @Param("pageSize") int pageSize,
//                                              @Param("auId") String auId);

}
