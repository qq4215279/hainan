package com.gobestsoft.jpush.model;


import cn.jiguang.common.utils.StringUtils;

import java.util.Date;

/**
 * 定时任务
 * Create by gutongwei
 * 2018/4/12
 */
public class DailyScheduleModel extends PushModel {

    public DailyScheduleModel(String scheduleName, Date sendTime) {
        this.scheduleName = scheduleName;
        this.sendTime = sendTime;
    }

    /**
     * 日程名称
     */
    private String scheduleName;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 开始日期
     */
    private Date start;

    /**
     * 结束日期
     */
    private Date end;


    public String getScheduleName() {
        if (StringUtils.isNotEmpty(this.scheduleName)) {
            return scheduleName;
        }
        return "定时任务";
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getSendTime() {
        return formatDate(this.sendTime, TIME_PATTERN);
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


    public String getStart() {
        return formatDate(start,DATE_TIME_PATTERN);
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getEnd() {
        return formatDate(end,DATE_TIME_PATTERN);
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
