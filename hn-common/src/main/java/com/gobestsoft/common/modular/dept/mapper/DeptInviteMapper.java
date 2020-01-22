package com.gobestsoft.common.modular.dept.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.AppInviteDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 入会邀请
 * @author xiat
 * @time 2018-11-14 16:46:12
 */
@Repository
public interface DeptInviteMapper extends BaseMapper<AppInviteDept>{

    List<Map<String,Object>> getSelectAppInviteDateList(@Param("page") Page<Map<String, Object>> page,
                                                        @Param("unionName") String unionName,
                                                        @Param("begDate") String begDate,
                                                        @Param("endDate") String endDate,
                                                        @Param("deptId") Integer depeId,
                                                        @Param("userId") String userId);


    List<Map<String,Object>> getAppInviteDeptDataToModel(@Param("page") Page<Map<String,Object>> page,
                                                         Map<String,Object> map);


    List<AppInviteDept> getUnitName(@Param("keyWord")String keyWord, @Param("page") RowBounds rowBounds);
}

