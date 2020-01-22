package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedExeStudyPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 困难帮扶附加-助学
 */
@Repository
public interface SrvStraitenedExeStudyMapper extends BaseMapper<SrvStraitenedExeStudyPojo> {

    /**
     * 添加帮扶-医疗救助
     * @param pojos
     */
    void addAll(@Param("pojos") List<SrvStraitenedExeStudyPojo> pojos);

}