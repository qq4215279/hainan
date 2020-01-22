package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.AppVersionPojo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AppVersionMapper extends BaseMapper<AppVersionPojo> {


    /**
     * 获取app版本
     *
     * @param type 0：安卓，1：苹果
     * @return
     */
    AppVersionPojo getLastAppVersion(@Param("type") int type);


    /**
     * 获取app版本List
     *
     * @param page
     * @param begDate
     * @param endDate
     * @param keyword
     * @return
     */
    List<AppVersionPojo> getVersionList(@Param("page") Page<AppVersionPojo> page
            , @Param("begDate") String begDate
            , @Param("endDate") String endDate
            , @Param("keyword") String keyword);

}