package com.gobestsoft.common.modular.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.model.Ad;

/**
 * 资讯的dao
 *
 * @author wxy
 * @date 2017-11-21
 */
public interface AdDao {

    List<Ad> selectByCondition(@Param("page") Page<Ad> page,
                               @Param("title") String title,
                               @Param("columnId") Integer columnId,
                               @Param("status") Integer status,
                               @Param("companyName") String companyName,
                               @Param("type") Integer type,
                               @Param("begDate") String begDate,
                               @Param("endDate") String endDate
    );

    Ad selectById(@Param("id") Long id);

    int insert(Ad ad);

    int update(Ad ad);

    int deleteById(Long id);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

}
