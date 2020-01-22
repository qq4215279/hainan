package com.gobestsoft.common.modular.cms.dao;

import com.gobestsoft.common.modular.cms.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lqc
 *
 */
@Repository
public interface InformationDao {

	/**
	 * 资讯
	 * 
	 * @param cityId
	 * @param channelId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<Information> selectListinfo(@Param("cityId") int cityId, @Param("channelId") Integer channelId,
                                     @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * @param ids
	 *            订阅频道
	 */

	void addChannel(@Param("ids") Integer[] ids, @Param("userId") String userId, @Param("creatTime") String creatTime);

	/**
	 *            获取点赞数量
	 */
	ThumbsUp getThumbsUp(@Param("commentId") long commentId);

	/**
	 * 返回页面内容
	 * 
	 * @param id
	 * @return
	 */
	String getPageContent(int id);

	/**
	 * 获取用户已订阅的频道
	 */
	List<Channel> getOwnChannels(@Param("userId") String userId);
	/**
	 * @param userId
	 *            获取用户推荐频道
	 */
	List<Channel> getRecommendChannels(@Param("ids") String ids);

	/**
	 * 举报资讯内容
	 * 
	 * @param auId
	 * @param articleId
	 * @param description
	 * @param toContent
	 * @param createTime
	 */
	void reportArticle(@Param("auId") String auId, @Param("articleId") long articleId, @Param("tipType") int tipType,
                       @Param("description") String description, @Param("toContent") String toContent,
                       @Param("createTime") String createTime, @Param("category") String category);

	Integer hasArticle(@Param("articleId") long articleId);

	/**
	 * 删除用户所有已订阅的频道
	 */
	void deleteAllChannel(String userId);


	/**
	 * 删除用户收藏咨询
	 * 
	 * @param auId
	 * @param articleId
	 */
	void deleteCollectArticle(@Param("auId") String auId, @Param("articleId") String articleId);
	
	/**
	 * 资讯分享增加
	 * @param articleId
	 */
	void articleShare(@Param("articleId") long articleId);

	
    /**
     * 判断此条资讯是否存在
     * @param id
     * @return
     */
	Integer getExistArticle(@Param("id") long id);
	/**
	 * 判断此条评论是否存在
	 * @param id
	 * @return
	 */
	Integer getExistComment(@Param("id") long id);
	/**
	 * 判断此条评论回复是否存在
	 * @param id
	 * @return
	 */
	Integer getExistCommentReply(@Param("id") long id);

	/**
	 * 获取5个评论点赞的头像名称
	 * @param commentId
	 * @return
	 */
	List<Users> getCommentThumbsUpUsers(@Param("commentId") long commentId);
    
	void addShareComment(@Param("articleId") long articleId, @Param("auId") String auId, @Param("createTime") String createTime);
	
	/**
	 * 获取用户主页
	 * @param userId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	UserHomePage getUserHomePage(@Param("userId") String userId);
	
	
	List<Dynamics> getUserDynamics(@Param("auId") String auId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
	
	void addHitNum(@Param("articleId") long articleId);
}
