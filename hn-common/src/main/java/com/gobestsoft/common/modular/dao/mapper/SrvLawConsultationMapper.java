package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.SrvLawConsultationPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SrvLawConsultationMapper extends BaseMapper<SrvLawConsultationPojo> {


    /**
     * 获取咨询列表
     *
     * @param category 咨询类型
     * @param page
     * @return
     */
List<Map<String, Object>> consultation(@Param("type") Integer type,@Param("groupCode") String groupCode,@Param("deptId") Integer deptId,@Param("category") String category,@Param("query_status") String query_status, @Param("page") Page page);
List<Map<String, Object>> consultationNull(@Param("type") Integer type,@Param("groupCode") String groupCode,@Param("category") String category,@Param("query_status") String query_status, @Param("page") Page page);

}