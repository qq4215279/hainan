package com.gobestsoft.api.modular.cms.service;

import com.gobestsoft.api.modular.appuser.service.AppUserService;
import com.gobestsoft.common.modular.cms.dao.GiveThumbsUpDao;
import com.gobestsoft.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author gutongwei
 *
 *         2017年11月24日
 */
@Service
public class GiveThumbsService {

	@Autowired
	private GiveThumbsUpDao giveThumbsUpDao;

	@Autowired
	private AppUserService appUserService;

	public int giveThumbsUp(long id, String uId, int type) {
		String createTime = DateUtil.getAllTime();
		if (type == 0) {
			articeGiveThumbsUp(id, uId, createTime, type);
			//TODO 暂时关闭，需要时在打开
			//appUserService.completeTaskGiveIntegral(uId,5);
			return giveThumbsUpDao.getArticleThumbsUpCount(id);
		} else if (type == 1) {
			commentGiveThumbsUp(id, uId, createTime);
			return giveThumbsUpDao.getCommentGiveThumbsUpCount(id);
		} else if (type == 2) {
			replyGiveThumbsUp(id, uId, createTime);
			return giveThumbsUpDao.getReplyGiveThumbsUpCount(id);
		}
		return 0;
	}

	@Transactional
	public int deleteThumbsUp(long id, String uId, int type) {
		if (type == 0) {
			giveThumbsUpDao.deleteArticleThumbsUp(id);
			giveThumbsUpDao.deleteArticleThumbsUpLog(id,uId,type);
			return giveThumbsUpDao.getArticleThumbsUpCount(id);
		} else if (type == 1) {
			giveThumbsUpDao.deleteCommentGiveThumbsUp(id);
			giveThumbsUpDao.deleteCommentGiveThumbsUpLog(id, uId);
			return giveThumbsUpDao.getCommentGiveThumbsUpCount(id);
		} else if (type == 2) {
			giveThumbsUpDao.deleteReplyGiveThumbsUp(id);
			giveThumbsUpDao.deleteReplyGiveThumbsUpLog(id, uId);
			return giveThumbsUpDao.getReplyGiveThumbsUpCount(id);
		}
		return 0;
	}

	@Transactional
	public void articeGiveThumbsUp(long articleId, String uId, String createTime, int type) {
		giveThumbsUpDao.articeGiveThumbsUp(articleId, uId, createTime, type);
		giveThumbsUpDao.addArticleThumbsUp(articleId);
	}

	@Transactional
	public void commentGiveThumbsUp(long commentId, String uId, String createTime) {
		giveThumbsUpDao.commentGiveThumbsUp(commentId, uId, createTime);
		giveThumbsUpDao.addCommentThumbsUp(commentId);
	}

	@Transactional
	public void replyGiveThumbsUp(long replyId, String uId, String createTime) {
		giveThumbsUpDao.replyGiveThumbsUp(replyId, uId, createTime);
		giveThumbsUpDao.addReplyThumbsUp(replyId);
	}

	/**
	 * 判断是否点过赞了
	 * 
	 * @param type
	 *            类型。0：资讯。1：评论。2:评论回复
	 * @param id
	 * @return
	 */
	public boolean isGotThumbsUp(int type, long id, String auId) {
		if (type == 0) {
			return isGotArtileThumbsUp(type,id, auId);
		} else if (type == 1) {
			return isGotCommentThumbsUp(auId, id);
		} else if (type == 2) {
			return isGotReplyThumbsUp(auId, id);
		}
		return false;
	}

	/**
	 * 是否点赞过资讯
	 * 
	 * @param auId
	 * @param id
	 * @return
	 */
	public boolean isGotArtileThumbsUp(int type,  long id, String auId) {
		return giveThumbsUpDao.isGotArtileThumbsUp(type, id, auId) > 0;

	}

	/**
	 * 是否点赞过资讯
	 * 
	 * @param auId
	 * @param id
	 * @return
	 */
	public boolean isGotCommentThumbsUp(String auId, long id) {
		return giveThumbsUpDao.isGotCommentThumbsUp(auId, id) > 0;
	}

	/**
	 * 是否点赞过资讯
	 * 
	 * @param auId
	 * @param id
	 * @return
	 */
	public boolean isGotReplyThumbsUp(String auId, long id) {
		return giveThumbsUpDao.isGotReplyThumbsUp(auId, id) > 0;
	}

}
