package com.gobestsoft.api.modular.cms.service;

import com.gobestsoft.api.modular.appuser.service.AppUserService;
import com.gobestsoft.common.modular.cms.dao.CommentDao;
import com.gobestsoft.common.modular.cms.dao.InformationDao;
import com.gobestsoft.common.modular.cms.model.Comment;
import com.gobestsoft.common.modular.cms.model.ReplyContent;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户评论
 *
 * @author gutongwei
 * <p>
 * 2017年11月24日
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private InformationDao informationDao;

    @Autowired
    private AppUserService appUserService;


    /**
     * 针对资讯写评论
     *
     * @param articleId
     * @param content
     * @param uId
     */
    public void writeComment(long articleId, String content, String uId) {
        String createTime = DateUtil.getAllTime();
        commentDao.writeComment(articleId, EmojiParser.parseToAliases(content), uId, createTime);
        // TODO: 2018/9/7 待定 评论资讯添加积分
        // appUserService.completeTaskGiveIntegral(uId, 4);
    }

    /**
     * 获取资讯评论以及评论回复
     *
     * @param uId
     * @param articleId
     * @param startIndex
     * @param pageSize
     * @return
     */
    public List<Comment> comment(String uId, long articleId, int startIndex, int pageSize) {
        return commentDao.comment(uId, articleId, startIndex - 1, pageSize);
    }

    /**
     * 针对评论写回复
     *
     * @param commentId
     * @param content
     */
    public String writeCommentReply(long commentId, String content, String uId) {
        String createTime = DateUtil.getAllTime();
        Map<String,Object> map = new HashMap<>();
        map.put("commentId",commentId);
        map.put("content",content);
        map.put("uId",uId);
        map.put("createTime",createTime);
        commentDao.writeCommentReply(map);
        if(map.get("id")!=null){
            return map.get("id")+"";
        }
        return null;
        //评论审核通过添加积分
        //appUserService.userHoustonIntegral(uId, 8);
    }

    /**
     * 用户评论的回复
     *
     * @param commentId
     * @param startIndex
     * @param pageSize
     * @return
     */
    public Map<String, Object> commentReply(String uId, long commentId, int startIndex, int pageSize) {

        Map<String, Object> result = new HashMap<>();
        if (startIndex == 1) {
            result.put("thumbs_up_users", informationDao.getCommentThumbsUpUsers(commentId));
            result.put("hot", commentDao.hotCommentReply(uId, commentId));
            List<ReplyContent> replyContents = commentDao.commentReply(uId, commentId, (startIndex - 1) * pageSize, pageSize);
            for(ReplyContent replyContent : replyContents){
                replyContent.setAvatar(WebSiteUtil.getBrowseUrl(replyContent.getAvatar()));
            }
            result.put("all", replyContents);

        } else {
            List<ReplyContent> replyContents = commentDao.commentReply(uId, commentId, (startIndex - 1) * pageSize, pageSize);
            for(ReplyContent replyContent : replyContents){
                replyContent.setAvatar(WebSiteUtil.getBrowseUrl(replyContent.getAvatar()));
            }
            result.put("all", replyContents);
        }

        Map<String, Object> comment = commentDao.selectOneById(commentId + "", uId);
        if(comment!=null&& comment.containsKey("avatar")){
            comment.put("avatar",WebSiteUtil.getBrowseUrl(comment.get("avatar")+""));
            comment.put("create_time", DateUtil.parseAndFormat(comment.get("create_time")+"","yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
        }
        result.put("comment",comment);
        return result;
    }

}
