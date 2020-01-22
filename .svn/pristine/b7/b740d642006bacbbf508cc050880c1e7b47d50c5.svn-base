package com.gobestsoft.common.modular.dept.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptMemberDetailMapper {
    List<Map<String,Object>> getDeptMember(@Param("page") RowBounds rowBounds, String deptId);
}
