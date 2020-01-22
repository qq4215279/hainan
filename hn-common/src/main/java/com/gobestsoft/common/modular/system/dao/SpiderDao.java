package com.gobestsoft.common.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SpiderArticlePojo;

/**
 * 爬网管理dao
 */
public interface SpiderDao extends BaseMapper<SpiderArticlePojo> {

    /**
     * 获取id
     * @param pojo
     * @return
     */
    Integer getIdByPojo(SpiderArticlePojo pojo);
}
