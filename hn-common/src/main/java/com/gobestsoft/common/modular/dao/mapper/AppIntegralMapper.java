package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.appuser.model.AppIntegralTaskEntity;
import com.gobestsoft.common.modular.appuser.model.UserIntegralEntity;
import com.gobestsoft.common.modular.dao.model.AppIntegralPojo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppIntegralMapper extends BaseMapper<AppIntegralPojo> {

    /**
     * 获取app用户的任务
     *
     * @param auId
     * @return
     */
    List<AppIntegralTaskEntity> getUserIntegralTasks(@Param("auId") String auId, @Param("type") int type,
                                                     @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据类型获取任务IDs
     *
     * @param type 任务类型：0：一次任务。1：每日任务。
     * @return
     */
    Integer[] getUserIntegralTasksIds(@Param("type") int type);

    /**
     * 任务是否完成
     *
     * @param taskId
     * @param auId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer taskIsComplete(@Param("taskId") int taskId, @Param("auId") String auId, @Param("startTime") String startTime,
                           @Param("endTime") String endTime);


    /**
     * 根据开始时间结束时间查询每日任务总积分
     *
     * @param auId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getTodayDailyTasksIntegral(@Param("auId") String auId, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 用户积分进账
     *
     * @param auId
     * @param createTime
     * @param targetId
     */
    void integralHouston(@Param("auId") String auId, @Param("createTime") String createTime, @Param("targetId")
            int targetId);

    /**
     * 完成任务
     *
     * @param auId
     * @param taskId
     * @param createTime
     */
    void taskComplete(@Param("auId") String auId, @Param("taskId") int taskId, @Param("createTime") String createTime);


    /**
     * 用户获取积分列表
     *
     * @param auId      用户id
     * @param type      积分类型【0：任务进账】【1：兑换积分商品】【2：参加活动取得】
     * @param taskId    任务ID
     * @param rowBounds
     * @return
     */
    List<UserIntegralEntity> getUserIntegralList(@Param("auId") String auId, @Param("type") Integer type, @Param("taskId") Integer taskId, @Param("page") RowBounds rowBounds);

}