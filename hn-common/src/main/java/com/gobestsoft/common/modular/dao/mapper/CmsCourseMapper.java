package com.gobestsoft.common.modular.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.cms.model.CmsCourseEntity;
import com.gobestsoft.common.modular.cms.model.ShowCmsCourseEntity;
import com.gobestsoft.common.modular.dao.model.CmsCoursePojo;

@Repository
public interface CmsCourseMapper extends BaseMapper<CmsCoursePojo> {

    /**
     * 课程列表
     *
     * @param type
     * @param auid
     * @param rowBounds
     * @return
     */
    List<CmsCourseEntity> courseList(@Param("type") Integer type, @Param("auid") String auid, @Param("isCollect") boolean isCollect, @Param("page") RowBounds rowBounds);

    /**
     * 获取展示用课程
     *
     * @param id
     * @return
     */
    ShowCmsCourseEntity showCmsCourse(@Param("id") int id);
    
    /**
     * 根据课程id,获取课程详情信息
     * @param courseId
     * @param auid
     * @return
     */
    Map<String,Object> courseDetail(@Param("courseId") Integer courseId, @Param("auid") String auid);
}