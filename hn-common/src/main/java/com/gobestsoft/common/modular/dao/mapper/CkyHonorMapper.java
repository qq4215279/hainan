package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.CkyHonorPojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 创客园-工作室表
 */
@Repository
public interface CkyHonorMapper extends BaseMapper<CkyHonorPojo> {

    /**
     * 后台管理用
     *
     * @param title
     * @param rowBounds
     * @return
     */
    List<Map<String, String>> managementHonor(@Param("title") String title, @Param("studioId") int studioId, @Param("page") RowBounds rowBounds);


    /**
     * 将工作室下所有所有is_show 改为0
     * @param studioId
     */
    void updateIsShow(@Param("studioId") int studioId);

    /**
     * 删除工作室荣誉
     * @param ids
     */
    void deleteHonor(@Param("ids") String ids);
}