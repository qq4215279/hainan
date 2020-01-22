package com.gobestsoft.common.modular.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.XlyzMasterPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface XlyzMapper extends BaseMapper<XlyzMasterPojo> {
    /**
     * 心灵驿站banner
     * @return
     */
    public List<Map<String,Object>> selectBannerList();

    /**
     * 查询所有导师
     * @return
     */
    public List<Map<String,Object>> selectMasterList(@Param("page")Page page);

    /**
     * 查询文章列表
     * @param page
     * @return
     */
    List<Map<String,Object>> selectArticles(@Param("page") Page page);

    /**
     * 查询文章阅读量
     * @param articleList
     * @return
     */
    List<Map<String,Object>> selectArticleReading(List<String> articleList);

}
