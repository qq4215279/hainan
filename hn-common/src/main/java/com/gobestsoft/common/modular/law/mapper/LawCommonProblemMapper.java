package com.gobestsoft.common.modular.law.mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.law.model.LawCommonProblem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/*
常见问题查询表
 */
@Repository
public interface LawCommonProblemMapper extends BaseMapper<LawCommonProblem> {
    List<Map<String, Object>> selectLawCommonProblem(@Param("page") Page page);
}
