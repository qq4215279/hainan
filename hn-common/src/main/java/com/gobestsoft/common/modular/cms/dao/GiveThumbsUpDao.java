package com.gobestsoft.common.modular.cms.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 点赞
 * 
 * @author gutongwei
 *
 *         2017年11月24日
 */
@Repository
public interface GiveThumbsUpDao {

	/**
	 * 资讯点赞
	 * 
	 * @param articleId
	 * @param uId
	 * @param createTime
	 */
	void articeGiveThumbsUp(@Param("articleId") long articleId, @Param("uId") String uId,
                            @Param("createTime") String createTime, @Param("type") int type);

	/**
	 * 添资讯点赞
	 * 
	 * @param articleId
	 */
	void addArticleThumbsUp(@Param("articleId") long articleId);

	/**
	 * 删除资讯点赞
	 * 
	 * @param articleId
	 */
	void deleteArticleThumbsUp(@Param("articleId") long articleId);

	/**
	 * 删除资讯点赞
	 *
	 * @param articleId
	 * @param uId
	 */
	void deleteArticleThumbsUpLog(@Param("articleId") long articleId, @Param("uId") String uId, @Param("type") int type);
	/**
	 * 获取资讯点赞数量
	 * 
	 * @return
	 */
	Integer getArticleThumbsUpCount(@Param("articleId") long articleId);

	/**
	 * 评论点赞
	 * 
	 * @param uId
	 * @param createTime
	 */
	void commentGiveThumbsUp(@Param("commentId") long commentId, @Param("uId") String uId,
                             @Param("createTime") String createTime);

	/**
	 * 增加评论点赞数量
	 * 
	 * @param commentId
	 */
	void addCommentThumbsUp(@Param("commentId") long commentId);

	/**
	 * 删除评论点赞
	 * 
	 * @param commentId
	 */
	void deleteCommentGiveThumbsUp(@Param("commentId") long commentId);

	void deleteCommentGiveThumbsUpLog(@Param("commentId") long commentId, @Param("uId") String uId);
	/**
	 * 获取评论点赞数量
	 * 
	 * @return
	 */
	Integer getCommentGiveThumbsUpCount(@Param("commentId") long commentId);

	/**
	 * 评论点赞回复
	 * 
	 * @param replyId
	 * @param uId
	 * @param createTime
	 */
	void replyGiveThumbsUp(@Param("replyId") long replyId, @Param("uId") String uId,
                           @Param("createTime") String createTime);

	/**
	 * 增加评论点赞
	 * 
	 * @param replyId
	 */
	void addReplyThumbsUp(@Param("replyId") long replyId);

	/**
	 * 删除评论回复点赞
	 * 
	 * @param replyId
	 */
	void deleteReplyGiveThumbsUp(@Param("replyId") long replyId);

	void deleteReplyGiveThumbsUpLog(@Param("replyId") long replyId, @Param("uId") String uId);
	/**
	 * 获取评论点赞数量
	 * 
	 * @param replyId
	 * @return
	 */
	Integer getReplyGiveThumbsUpCount(@Param("replyId") long replyId);

	/**
	 * 是否点赞过资讯
	 * 
	 * @param type
	 * @param auId
	 * @param id
	 * @return
	 */
	int isGotArtileThumbsUp(@Param("type") int type, @Param("id") long id, @Param("auId") String auId);

	/**
	 * 是否点赞过资讯
	 * 
	 * @param type
	 * @param auId
	 * @param id
	 * @return
	 */
	int isGotCommentThumbsUp(@Param("auId") String auId, @Param("id") long id);

	/**
	 * 是否点赞过资讯
	 * 
	 * @param type
	 * @param auId
	 * @param id
	 * @return
	 */
	int isGotReplyThumbsUp(@Param("auId") String auId, @Param("id") long id);

}
