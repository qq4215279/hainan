package com.gobestsoft.common.modular.homepage.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.homepage.model.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BannerDao {

    public List<Banner> selectBannerByCondition(@Param("page") Page<Banner> page,
                                                @Param("categoryId") Integer categoryId, @Param("title") String title, @Param("isEnable") Integer isEnable);

    public int updateOneColumnWithArgsId(@Param("isEnable") Integer isEnable, @Param("delFlg") Integer delFlg, @Param("array") int[] array);

    /**
     * 通过Id主键修改
     *
     * @param banner
     * @return
     */
    public int updateBannerById(Banner banner);

    /**
     * 插入一条数据
     *
     * @param banner 对象
     * @return
     */
    public int addBanner(Banner banner);

    /**
     * 主键查询 Banner
     *
     * @param id 主键
     */
    public Banner selectBannerById(Integer id);

    /**
     * 条件查询，无分页（状态，标题，开始日期，结束日期）
     *
     * @param article
     * @return
     * @date 2017-11-21
     */
    public List<Map<String, Object>> selectByCondition(@Param("cateId") Integer cateId,
                                                       @Param("page") Page<Map<String, Object>> page, @Param("status") Integer status,
                                                       @Param("title") String title, @Param("begDate") String begDate, @Param("endDate") String endDate,
                                                       @Param("articleType") String articleType, @Param("infoType") Integer infoType);

    /**
     * 获取新媒体轮播图可选资讯
     *
     * @param page
     * @param columnId
     * @param keyword
     * @param begDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> getArticleForNewMediaBanner(@Param("page") Page<Map<String, Object>> page
            , @Param("columnId") Integer columnId
            , @Param("keyword") String keyword
            , @Param("begDate") String begDate
            , @Param("endDate") String endDate);

    /**
     * 获取工会资讯轮播图可选资讯
     *
     * @param page
     * @param categoryId
     * @param keyword
     * @param begDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> getArticleForUnionBanner(@Param("page") Page<Map<String, Object>> page
            , @Param("categoryId") Integer categoryId
            , @Param("keyword") String keyword
            , @Param("begDate") String begDate
            , @Param("endDate") String endDate);

    /**
     * 获取轮播资讯
     *
     * @param page
     * @param keyword
     * @param begDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> getArticleForBanner(@Param("page") Page<Map<String, Object>> page
            , @Param("keyword") String keyword
            , @Param("begDate") String begDate
            , @Param("endDate") String endDate);

}
