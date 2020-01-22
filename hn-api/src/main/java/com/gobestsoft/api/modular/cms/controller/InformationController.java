package com.gobestsoft.api.modular.cms.controller;

import com.gobestsoft.api.config.cache.DictDto;
import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.cms.service.CommentService;
import com.gobestsoft.api.modular.cms.service.GiveThumbsService;
import com.gobestsoft.api.modular.cms.service.InformationService;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.cms.dao.CommentDao;
import com.gobestsoft.common.modular.cms.model.Comment;
import com.gobestsoft.common.modular.cms.model.ReplyContent;
import com.gobestsoft.core.util.WebSiteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * create by gtw
 * on 2018/5/31
 */
@RestController
@RequestMapping("/information")
public class InformationController extends BaseController {

    /**
     * 默认城市CODE
     */
    private static final String DEF_CITY_CODE = "460100";

    @Autowired
    private InformationService informationService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GiveThumbsService giveThumbsService;

    @Autowired
    private CommentDao commentDao;

    /**
     * 查询资讯列表
     * create by gutongwei
     *
     * @param categoryId
     * @param keyword
     * @param cityCode
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResult list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "cityCode", required = false) String cityCode,
                           @RequestParam(value = "isCollected", required = false,defaultValue = "0") int isCollected) {

        if(isCollected==1 && !isLogin()){
            return fail(BaseResult.ResultCode.ERROR100);
        }

        List<DictDto> dtos = cacheFactory.getDictsByGroupCodes(DictCodeConstants.LIB_ADMINISTRATIVE_AREA);

        List<String> cityList = new ArrayList<String>();
        List<DictDto> list = dtos.get(0).getDict();
        for (DictDto dict : list) {
            cityList.add(dict.getCode());
        }
        if (!cityList.contains(cityCode)) {
            cityCode = DEF_CITY_CODE;
        }

        return success(informationService.getArticles(getPageBounds(), categoryId, keyword, cityCode, isCollected,getUserId()));
    }




    /**
     * 订阅频道
     */
    @RequestMapping(value = "/addChannel", method = RequestMethod.POST)
    public BaseResult addChannel(@RequestParam("ids") Integer[] ids) {

        String userId = getUserId();
        try {
            if (ids == null) {
                return fail("输入参数不合格");
            }
            informationService.addChannel(ids, userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success();

    }

    /**
     * 获取栏目频道 根据用户id 查询出用户所有的已订阅的频道，
     * 然后查询出这些订阅频道的id，和所有频道的id，
     * 推荐频道就是没有订阅的频道id
     */
    @RequestMapping(value = "/getChannel", method = RequestMethod.GET)
    public BaseResult getChannel() {
        Map<String, Object> map = informationService.getChannels(isLogin(), getUserId());
        return success(map);

    }

    /**
     * 用户点赞
     *
     * @param id   点赞内容ID
     * @param type 点赞类型。0：资讯。1：评论。2:评论回复
     * @return
     */
    @RequestMapping(value = "/giveThumbsUp", method = RequestMethod.POST)
    public BaseResult giveThumbsUp(@RequestParam(name = "id") long id,
                                   @RequestParam(name = "type") int type) {

        Integer count = informationService.getExist(type, id);
        if (count <= 0) {
            return new BaseResult(500, "内容不存在", null);
        }

        Map<String, Integer> result = new HashMap<>();
        if (giveThumbsService.isGotThumbsUp(type, id, getUserId())) {
            result.put("thumbs_up_count", giveThumbsService.deleteThumbsUp(id, getUserId(), type));
            return new BaseResult(201, "取消成功", result);
        }
        result.put("thumbs_up_count", giveThumbsService.giveThumbsUp(id, getUserId(), type));
        //点赞成功，更新积分
        appUserService.completeTaskGiveIntegral(getUserDto(),5);
        return new BaseResult(200, "点赞成功", result);
    }

    /**
     * 收藏
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCollUp", method = RequestMethod.POST)
    public BaseResult getCollUp(@RequestParam(name = "id") String id) {
        int code = informationService.articleOperation(id, getUserId(), 3);
        if (code == 200) {

            //资讯收藏成功，更新积分
            appUserService.completeTaskGiveIntegral(getUserDto(),6);

            return baseResult(200, "收藏成功", null);
        }
        return baseResult(201, "取消收藏成功", null);
    }

    /**
     * 获取H5页面详情
     * create by gutongwei
     * 2018/6/4
     *
     * @param id
     * @return
     */
    @RequestMapping("/h5Detail")
    public BaseResult h5Detail(@RequestParam("id") String id) {
        return success(informationService.articleDetail(id, getUserId()));
    }

    /**
     * 针对资讯写评论
     *
     * @param articleId 资讯ID
     * @param content   评论内容
     * @return
     */
    @RequestMapping(value = "/writeComment", method = RequestMethod.POST)
    public BaseResult writeComment(@RequestParam("articleId") long articleId, @RequestParam("content") String content) {
        commentService.writeComment(articleId, content, getUserId());
        return success();
    }

    /**
     * 针对评论写回复
     *
     * @param commentId 评论ID
     * @param content   评论内容
     * @return
     */
    @RequestMapping(value = "/writeCommentReply", method = RequestMethod.POST)
    public BaseResult writeCommentReply(@RequestParam("commentId") long commentId,
                                        @RequestParam("content") String content) {
        String replyId = commentService.writeCommentReply(commentId, content, getUserId());
        if(content!=null&&content.trim().length()>20){
            content= content.substring(0,19)+"...";
        }
        Map<String, Object> comment = commentDao.selectOneById(commentId + "",null);
        if(comment!=null){
            if (replyId != null) {
                appUserService.sendMessageByAuid(comment.get("auid")+"", 2, "收到 "+getUserDto().getNickname()+" 的回复", content, 7, replyId, "", false);
            }
        }
        return success();
    }

    /**
     * 获取用户评论的回复
     *
     * @param commentId 评论ID
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/commentReply", method = RequestMethod.GET)
    public BaseResult commentReply(@RequestParam("commentId") long commentId,
                                   @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize) {
        Map<String, Object> result = commentService.commentReply(getUserId(), commentId, pageIndex, pageSize);
        return new BaseResult(200, null, result);
    }

    /**
     * 获取资讯评论以及评论回复
     *
     * @param articleId 资讯ID
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/comment")
    public BaseResult comment(@RequestParam("articleId") long articleId, @RequestParam("pageIndex") int pageIndex,
                              @RequestParam("pageSize") int pageSize) {
        List<Comment> list = commentService.comment(getUserId(), articleId, pageIndex, pageSize);
        for (Comment comment : list) {
            comment.setAvatar(WebSiteUtil.getBrowseUrl(comment.getAvatar()));
            List<ReplyContent> replyContents = comment.getReply_contents();
            for (ReplyContent replyContent : replyContents) {
                replyContent.setAvatar(WebSiteUtil.getBrowseUrl(replyContent.getAvatar()));
            }
        }

        return success(list);
    }

    /**
     * 专题列表
     */
    @RequestMapping("/topic")
    public BaseResult topic() {
        return success(informationService.getTopicList(getPageBounds()));
    }

    /**
     * 热门搜索
     */
    @RequestMapping("/hotSearch")
    public BaseResult getHotArticleKey() {
        List<Map<String, Object>> result = informationService.getHotArticlekey();
        for (Map<String, Object> map : result) {
            map.put("jump_url", apiProperties.getReviewBaseUrl(map.get("id").toString(), true));
        }
        return success(result);
    }

}
