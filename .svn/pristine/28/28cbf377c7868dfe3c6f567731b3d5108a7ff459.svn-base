package com.gobestsoft.common.modular.opinion.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.opinion.model.OpinionLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 建言献策
 */
@Repository
public interface OpinionLogMapper extends BaseMapper<OpinionLog> {

    List<Map<String,Object>> selectListByCondition(Integer opinionId);

    String selectReplyContent(@Param("opinionId") Integer opinionId, @Param("orgId")Integer orgId);

    int update(OpinionLog pojo);
}
