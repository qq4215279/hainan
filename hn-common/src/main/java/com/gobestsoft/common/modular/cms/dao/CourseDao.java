package com.gobestsoft.common.modular.cms.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.model.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 课程dao
 *
 * @author duanmu
 * @date 2018-06-23
 */
@Repository
public interface CourseDao {

    /**
     * 条件查询，无分页（状态，标题，开始日期，结束日期）
     *
     * @date 2018-06-23
     */
    List<Course> selectNormalCourseList(@Param("page") Page<Course> page,
                                        @Param("status") Integer status,
                                        @Param("title") String title,
                                        @Param("type") String type);

    /**
     *
     * @param page
     * @param status
     * @param title
     * @param type
     * @return
     */
    List<Course> selectSeriesCourseList(@Param("page") Page<Course> page,
                                        @Param("status") Integer status,
                                        @Param("title") String title,
                                        @Param("type") String type,
                                        @Param("id") Integer id);

    /**
     * 增加普通课程
     *
     * @param Course
     * @return
     * @date 2018-6-25
     */
    public Integer addCourse(Course Course);

    /**
     * 删除课程
     *
     * @param id
     * @return
     */
    public Integer delCourse(int id);

    /**
     * 修改课程发布状态
     *
     * @param id
     * @return
     */
    int updateCourseStatusById(@Param("id") int id, @Param("status") Integer status);

    /**
     * 主键查询
     *
     * @param id
     * @return Course
     */
    public Course selectById(@Param("id") int id);

    /**
     * 编辑课程
     *
     * @param course
     * @return
     */
    public Integer updateCourse(Course course);

    /**
     * e学院横幅【课程】列表查询
     * @param page
     * @param title
     * @param courseType
     * @return
     */
	List<Map<String, Object>> selectCoursePage(@Param("page") Page<Map<String, Object>> page
            , @Param("title") String title
            , @Param("courseType") String courseType);


    /** 获取女子学堂关联课程*/
    List<Course> selectWomenList(@Param("page") Page<Course> page,
                                 @Param("status") Integer status,
                                 @Param("title") String title);

    /** 获取女子学堂关联课程*/
    List<Course> selectSelectList(@Param("page") Page<Course> page,
                                  @Param("status") Integer status,
                                  @Param("title") String title,
                                  @Param("begDate") String begDate,
                                  @Param("endDate") String endDate);

    int insertWomenCourse(@Param("id") String id);

    int deleteWomenCourse(@Param("id") String id);

}
