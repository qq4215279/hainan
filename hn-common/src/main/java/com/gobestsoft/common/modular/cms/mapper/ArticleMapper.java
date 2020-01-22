package com.gobestsoft.common.modular.cms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.cms.model.CmsRelationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duanmu on 2018/8/22 0022.
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

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
                                    @Param("userId") String userId,
                                    @Param("infoType") String infoType);

    /**
     * 增加资讯
     *
     * @param article
     * @return
     * @date 2018-08-25
     */
    public Integer addArticle(Article article);

    /**
     * 条件查询，有效资讯列表
     */
    List<Article> selectValidList(@Param("page") Page<Article> page,
                                    @Param("title") String title,
                                    @Param("begDate") String begDate,
                                    @Param("endDate") String endDate,
                                    @Param("deptId") Integer deptId,
                                    @Param("category") String category,
                                    @Param("seminar") String seminar);

    /**
     * 获取栏目列表
     */
    List<CmsRelationEntity> selectPublishList(@Param("articleId") String articleId, @Param("type") String type);

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
                          @Param("pubStatus") String pubStatus);
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
                          @Param("pubStatus") String pubStatus);

    /**
     * 修改资讯状态
     *
     * @param article
     * @return
     * @date 2017-11-23
     */
    int updateArticleStatusById(@Param("id") String id,
                                @Param("status") Integer status,
                                @Param("updateTime") String updateTime,
                                @Param("updateUser") String updateUser,
                                @Param("pubtime") String pubtime);

    /**
     * 修改资讯
     *
     * @param id
     * @return
     */
    public Integer delArticle(String id);

}
