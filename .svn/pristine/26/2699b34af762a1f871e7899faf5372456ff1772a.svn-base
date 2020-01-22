package com.gobestsoft.api.modular.cms.service;

import java.util.List;
import java.util.Map;

import com.gobestsoft.api.modular.basic.BasicRowBounds;

/**
 * 课程
 * create by gutongwei
 * on 2018/6/15 上午11:10
 */
public interface CourseService {

    /**
     * 课程列表
     *
     * @param type      【0:普通课程】【1：系列课程】【2：女子学堂】
     * @param auid
     * @param isCollect 是否是收藏
     * @param rowBounds
     * @return
     */
    List<Map<String, Object>> courseList(Integer type, String auid, boolean isCollect, BasicRowBounds rowBounds);

    /**
     * 资讯操作
     *
     * @param courseId
     * @param auid
     * @param type     操作类型:[0:点赞][1:收藏][2:分享][3:浏览]
     * @return
     */
    int articleOperation(int courseId, String auid, int type);

    /**
     * e学堂首页
     *
     * @param auid
     * @param token
     * @return
     */
    Map<String, Object> getHomePage(String auid,String token);
    
    /**
     * 课程详情
     * @param courseId 课程id
     * @param auid
     * @return
     */
    Map<String, Object> detail(Integer courseId,String auid);
}
