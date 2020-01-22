package com.gobestsoft.jpush.model;

import cn.jiguang.common.utils.StringUtils;

import java.util.Date;

/**
 * 定时任务
 * Create by gutongwei
 * 2018/4/12
 */
public class ScheduleModel extends PushModel {

    public ScheduleModel(String scheduleName, Date sendTime) {
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
        return formatDate(this.sendTime, DATE_TIME_PATTERN);
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
