package com.gobestsoft.jpush.model;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.google.gson.JsonObject;


/**
 * Create by gutongwei
 * 2018/3/29
 */
@Data
public class PushModel {

    /**
     * 转成日期格式
     */
    protected final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 转成时间格式
     */
    protected final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 设备
     */
    private PushPlatform platform;

    /**
     * 是否所有用户
     */
    private boolean isAllRegistration;

    /**
     * 极光注册ID
     */
    private String[] registrationId;


    /**
     * 提示描述
     */
    private String alert;

    /**
     * 附加属性
     */
    private Map<String, String> extras;


    private Map<String, JsonObject> jsonObjectMap;


    /**
     * 转换jPush需要的时间给事
     *
     * @param d
     * @return
     */
    protected String formatDate(Date d, String pattern) {
        if (d == null) {
            d = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }


}
