package com.gobestsoft.common.modular.cms.dao;

import java.util.List;
import java.util.Map;

import com.gobestsoft.common.modular.cms.model.*;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Repository;

/**
 * 资讯的dao
 *
 * @author wxy
 * @date 2017-11-21
 */
@Repository
public interface ArticleDao {

    /**
     * 条件查询，无分页（状态，标题，开始日期，结束日期）
     *
     * @date 2017-11-21
     */
    List<Article> selectByCondition(@Param("page") Page<Article> page,
                                    @Param("status") Integer status,
                                    @Param("title") String title,
                                    @Param("begDate") String begDate,
                                    @Param("endDate") String endDate);

    /**
     * 增加资讯
     *
     * @param article
     * @return
     * @date 2017-11-23
     */
    public Integer addArticle(Article article);

    /**
     * 修改资讯
     *
     * @param article
     * @return
     * @date 2017-11-23
     */
    public Integer updateArticle(Article article);

    /**
     * 修改资讯
     *
     * @param id
     * @return
     */
    public Integer delArticle(Long id);

    /**
     * 修改资讯状态
     *
     * @param article
     * @return
     * @date 2017-11-23
     */
    int updateArticleStatusById(@Param("id") Long id, @Param("status") Integer status, @Param("updateTime") String updateTime, @Param("updateUser") String updateUser, @Param("pubtime") String pubtime);

    /**
     * 修改
     *
     * @param article
     * @return
     * @date 2017-11-23
     */
    // public Integer UpdateArticle(Article article);

    /**
     * 主键查询
     *
     * @param id
     * @return Article
     * @date 2017-11-23
     */
    public Article selectById(@Param("id") String id);

    /**
     * 获取资讯详细
     *
     * @param articleId
     * @return
     */
    ArticleInfo getArticleInfo(@Param("articleId") long articleId, @Param("auId") String auId);


    /**
     * 获取用户投稿资讯
     *
     * @param columnId
     * @param cityCode
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<SubmissionArticle> getSubmissionArticle(@Param("columnId") Integer columnId, @Param("cityCode") String cityCode,
                                                 @Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("showedIds") String showedIds);

    /**
     * 获取订阅号以及是否订阅
     *
     * @param id
     * @param auId
     * @return
     */
    Subscription getSubscriptionByArticleId(@Param("id") long id, @Param("auId") String auId);


    /**
     * 获取自诩列表banner
     *
     * @return
     */
    List<CmsBanner> getRecommendBannerArticles(@Param("cityCode") String cityCode);

    /**
     * 根据媒体id获取资讯列表
     *
     * @return
     */
    public List<MediaArticles> getBannerArticle(@Param("mediaId") int mediaId, @Param("startIndex") int startIndex);

    /**
     * 条件查询，无分页（状态，标题，开始日期，结束日期）
     *
     * @date 2017-11-21
     */
    List<Article> selectArtilceList(@Param("page") Page<Article> page,
                                    @Param("status") Integer status,
                                    @Param("title") String title,
                                    @Param("begDate") String begDate,
                                    @Param("endDate") String endDate,
                                    @Param("deptId") Integer deptId,
                                    @Param("userId") String userId);

    /**
     * 条件查询，无分页（状态，标题，开始日期，结束日期）
     *
     * @date 2018-06-14
     */
    List<Article> selectValidList(@Param("page") Page<Article> page,
                                  @Param("title") String title,
                                  @Param("begDate") String begDate,
                                  @Param("endDate") String endDate,
                                  @Param("deptId") Integer deptId);

    /**
     * 发布节目检索节目检索
     *
     */
    List<CmsRelationEntity> selectPublishList(@Param("articleId") String articleId, @Param("pubtype") Integer pubtype);

    /**
     * 获取指定栏目资讯状态
     *
     */
    CmsRelationEntity selectCmsRelation(@Param("articleId") String articleId,
                                        @Param("categoryId") String categoryId);


    /**
     * 指定资讯发布到栏目
     *
     */
    int insertCmsRelation(@Param("articleId") String articleId,
                          @Param("categoryId") String categoryId,
                          @Param("pubStatus") String pubStatus,
                          @Param("publishedTime") String publishedTime);
    /**
     * 删除所在栏目指定资讯
     *
     */
    int deleteCmsRelation(@Param("id") int id);

    /**
     * 更新所在栏目指定资讯状态
     *
     */
    int updateCmsRelation(@Param("id") int id,
                          @Param("pubStatus") String pubStatus,
                          @Param("publishedTime") String publishedTime);



}
