package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.cms.model.CmsArticleEntity;
import com.gobestsoft.common.modular.dao.model.CmsArticlePojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CmsArticleMapper extends BaseMapper<CmsArticlePojo> {


    /**
     * 查询单条资讯
     * 并且资讯是已发布状态，未删除状态，在可预览的发布时间的
     *
     * @return
     */
    Map<String, Object> selectShowOne(@Param("articleId") String articleId, @Param("nowTime") String nowTime);

    /**
     * 获取资讯列表
     *
     * @param rowBounds  分页包
     * @param categoryId 资讯分类
     * @param keyword    查询关键字
     * @param nowTime    当前时间
     * @param cityCode   城市编码
     * @return
     */
    List<CmsArticleEntity> articleList(@Param("page") RowBounds rowBounds, @Param("categoryId") Integer categoryId,
                                       @Param("keyword") String keyword, @Param("nowTime") String nowTime,
                                       @Param("cityCode") String cityCode, @Param("isCollected") int isCollected,
                                       @Param("auid") String auid);


    /**
     * 访问资讯操作
     *
     * @param articleId  资讯ID
     * @param auid       操作用户
     * @param createTime 操作时间
     * @param type       操作类型:[0:点赞][1:浏览][2:分享][3:收藏]
     */
    void addArticleOperation(@Param("articleId") String articleId, @Param("auid") String auid,
                             @Param("createTime") String createTime, @Param("type") int type);

    /**
     * 统计资讯操作数量
     *
     * @param articleId
     * @param type      操作类型:[0:点赞][1:浏览][2:分享][3:收藏]
     * @param auid
     * @return
     */
    Integer getArticleOperationCount(@Param("articleId") String articleId,
                                     @Param("type") int type, @Param("auid") String auid);

    /**
     * 删除资讯操作
     *
     * @param articleId
     * @param auid
     * @param type      操作类型:[0:点赞][1:浏览][2:分享][3:收藏]
     */
    void delArticleOperation(@Param("articleId") String articleId, @Param("auid") String auid,
                             @Param("type") int type);

    /**
     * 获取资讯详情
     * create by gutongwei
     * 2018/6/4
     *
     * @param id
     * @param auid
     * @param nowTime
     * @return
     */
    Map<String, Object> articleDetail(@Param("id") String id, @Param("auid") String auid,
                                      @Param("nowTime") String nowTime);




    /**
     * 获取资讯列表
     *
     * @param rowBounds  分页包
     * @param categoryId 资讯分类
     * @param keyword    查询关键字
     * @param nowTime    当前时间
     * @param orgId      矩阵（组织）id
     * @return
     */
    List<CmsArticleEntity> articleSelectList(@Param("page") RowBounds rowBounds, @Param("categoryId") Integer categoryId,
                                             @Param("keyword") String keyword, @Param("nowTime") String nowTime, @Param("orgId") Integer orgId);

    /**
     * 获取资讯列表
     *
     * @param rowBounds 分页包
     * @param nowTime   当前时间
     * @param auid      登录用户ID
     * @return
     */
    List<CmsArticleEntity> articleVideoList(@Param("page") RowBounds rowBounds, @Param("nowTime") String nowTime,
                                            @Param("auid") String auid);

    /**
     * 获取热门搜索
     */
    List<Map<String, Object>> getHotArticleKey();

    /**
     * 获取点赞数和阅读量
     * @param list
     * type--- 0:点赞数 1:阅读量    num数量   article_id资讯流水号
     * @return
     */
    List<Map<String, Object>> articleOperationCount(List<String> list);




}