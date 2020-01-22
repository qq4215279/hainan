package com.gobestsoft.common.modular.cms.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.cms.model.CmsApproveEntity;
import com.gobestsoft.common.modular.cms.model.CmsOperateEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CmsApproveDao {
    /**
     * 新增审核记录
     */
    public int insertApproveLog(CmsApproveEntity entity);

    /**
     * 更新资讯状态
     */
    public int updateArticleStatus(@Param("articleId") String articleId, @Param("status") String status);

    /**
     *
     * 获取资讯编辑项目操作状态
     */
    public CmsOperateEntity getCmsOperation(@Param("role") String role, @Param("operateType") int operateType);

    /**
     * 获取该条资讯的最新审核状态
     */
    public CmsApproveEntity selectLastApprove(@Param("articleId") String articleId);

    /**
     * 获取该条资讯的开始审核状态
     */
    public CmsApproveEntity selectFirstApprove(@Param("articleId") String articleId);

    /**
     * 获取资讯log
     */
    public List<Map<String,Object>> selectArticlelogById(@Param("id") String id);
}
