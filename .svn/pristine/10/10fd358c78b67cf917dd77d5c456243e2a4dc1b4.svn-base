package com.gobestsoft.common.modular.cms.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gobestsoft.common.modular.cms.model.Comment;
import com.gobestsoft.common.modular.cms.model.ReplyContent;

@Repository
public interface CommentDao  {

	/**
	 * 针对资讯写评论
	 * 
	 * @param articleId
	 * @param content
	 * @param uId
	 * @param createTime
	 */
	void writeComment(@Param("articleId") long articleId, @Param("content") String content, @Param("uId") String uId,
                      @Param("createTime") String createTime);

	/**
	 * 增加资讯评论数量
	 * @param articleId
	 */
	void amendArticleCommentTotal(@Param("articleId") long articleId);

	/**
	 * 获取资讯评论以及评论回复
	 * 
	 * @param article
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<Comment> comment(@Param("uId") String uId, @Param("articleId") long articleId,
                          @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 针对评论写回复
	 * 
	 * @param commentId
	 * @param content
	 */
	void writeCommentReply(Map<String,Object> map);

	/**
	 * 用户评论的回复
	 * 
	 * @param commentId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<ReplyContent> commentReply(@Param("uId") String uId, @Param("id") long commentId,
                                    @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	/**
	 * 用户评论的回复
	 * 
	 * @param commentId
	 * @return
	 */
	List<ReplyContent> hotCommentReply(@Param("uId") String uId, @Param("id") long commentId);


	Map<String,Object> selectOneById(@Param(("id")) String id,@Param("uId") String uId);

	Map<String,Object> selectApplyById(@Param(("id")) String id);


}
