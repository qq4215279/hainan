package com.gobestsoft.jpush;


import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;
import cn.jpush.api.schedule.ScheduleResult;
import com.gobestsoft.jpush.model.*;
import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 推送工具类
 * gutongwei
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PushUtil.prefix)
public class PushUtil {

    protected static final String prefix = "jpush";

    private String appKey;

    private String masterSecret;


    private JPushClient appClient;


    @PostConstruct
    public void init() {
        appClient = new JPushClient(
                masterSecret, appKey,
                null, ClientConfig.getInstance());
//        appClient = new JPushClient(
//                "fc1e8619c99f9792bad217aa", "8ad0fadbeebbb82babd3ee8e",
//                null, ClientConfig.getInstance());
    }


    /**
     * 发送即时消息
     *
     * @param model
     * @return
     */
    public PushResult push(PushModel model) {
        PushResult result = null;
        try {
            result = appClient.sendPush(makePushPayload(model));
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送即时消息
     *
     * @return
     */
    public PushResult pushMessage(MessagePushModel model) {
        PushResult result = null;
        try {
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.registrationId(model.getRegistrationId()))
                    .setMessage(Message.newBuilder()
                            .setMsgContent(model.getMsgContent())
                            .addExtras(model.getExtras())
                            .build())
                    .build();
            result = appClient.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送延时消息
     *
     * @param model
     * @return
     */
    public ScheduleResult sendSingleSchedule(ScheduleModel model) {
        ScheduleResult result = null;
        try {
            result = appClient.createSingleSchedule(model.getScheduleName(), model.getSendTime(), makePushPayload(model));
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除延时消息
     *
     * @param scheduleId
     */
    public void delSchedule(String scheduleId) {
        try {
            appClient.deleteSchedule(scheduleId);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

    }

    /**
     * 每天创造每天的日程安排
     *
     * @param model
     * @return
     */
    public ScheduleResult sendDailySchedule(DailyScheduleModel model) {
        ScheduleResult result = null;
        try {
            result = appClient.createDailySchedule(model.getScheduleName(), model.getStart(), model.getEnd(), model.getSendTime(), makePushPayload(model));
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return result;
    }


    private PushPayload makePushPayload(PushModel model) {
        PushPayload.Builder builder = PushPayload.newBuilder();
        builder.setOptions(Options.newBuilder().build());
        Notification.Builder notification = Notification.newBuilder();
        if (model.getPlatform() == PushPlatform.All) {
            builder.setPlatform(Platform.all());
            notification.addPlatformNotification(getAndroidNotification(model)).
                    addPlatformNotification(getIosNotification(model)).
                    addPlatformNotification(getWinPhoneNotification(model));
        } else if (model.getPlatform() == PushPlatform.Android) {
            builder.setPlatform(Platform.android());
            notification.addPlatformNotification(getAndroidNotification(model));
        } else if (model.getPlatform() == PushPlatform.Android_Ios) {
            builder.setPlatform(Platform.android_ios());
            notification.addPlatformNotification(getAndroidNotification(model)).
                    addPlatformNotification(getIosNotification(model));
        } else if (model.getPlatform() == PushPlatform.Android_WinPhone) {
            builder.setPlatform(Platform.android_winphone());
            notification.addPlatformNotification(getAndroidNotification(model)).
                    addPlatformNotification(getWinPhoneNotification(model));
        } else if (model.getPlatform() == PushPlatform.Ios) {
            builder.setPlatform(Platform.ios());
            notification.addPlatformNotification(getIosNotification(model));
        } else if (model.getPlatform() == PushPlatform.Ios_WinPhone) {
            builder.setPlatform(Platform.ios_winphone());
            notification.addPlatformNotification(getIosNotification(model)).
                    addPlatformNotification(getWinPhoneNotification(model));
        } else if (model.getPlatform() == PushPlatform.WinPhone) {
            builder.setPlatform(Platform.winphone());
            notification.addPlatformNotification(getWinPhoneNotification(model));
        }
        builder.setNotification(notification.build());
        //是否所有用户
        if (model.isAllRegistration()) {
            builder.setAudience(Audience.all());
        } else {
            builder.setAudience(Audience.registrationId(model.getRegistrationId()));
        }
        return builder.build();
    }


    /**
     * 安卓通知
     *
     * @param model
     * @return
     */
    private AndroidNotification getAndroidNotification(PushModel model) {
        AndroidNotification.Builder android = AndroidNotification.newBuilder();
        if (StringUtils.isNotEmpty(model.getAlert())) {
            android.setAlert(model.getAlert());
        }
        android.addExtras(model.getExtras());
        if (model.getJsonObjectMap() != null) {
            for (Map.Entry<String, JsonObject> entry :
                    model.getJsonObjectMap().entrySet()) {
                android.addExtra(entry.getKey(), entry.getValue());
            }
        }
        return android.build();
    }

    /**
     * 苹果通知
     *
     * @param model
     * @return
     */
    private IosNotification getIosNotification(PushModel model) {
        IosNotification.Builder ios = IosNotification.newBuilder();
        if (StringUtils.isNotEmpty(model.getAlert())) {
            ios.setAlert(model.getAlert());
        }
        ios.setBadge(1);
        ios.setSound("happy");
        ios.addExtras(model.getExtras());
        if (model.getJsonObjectMap() != null) {
            for (Map.Entry<String, JsonObject> entry :
                    model.getJsonObjectMap().entrySet()) {
                ios.addExtra(entry.getKey(), entry.getValue());
            }
        }
        return ios.build();
    }

    /**
     * Winphone通知
     *
     * @param model
     * @return
     */
    private WinphoneNotification getWinPhoneNotification(PushModel model) {
        WinphoneNotification.Builder winPhone = WinphoneNotification.newBuilder();
        if (StringUtils.isNotEmpty(model.getAlert())) {
            winPhone.setAlert(model.getAlert());
        }
        winPhone.addExtras(model.getExtras());
        if (model.getJsonObjectMap() != null) {
            for (Map.Entry<String, JsonObject> entry :
                    model.getJsonObjectMap().entrySet()) {
                winPhone.addExtra(entry.getKey(), entry.getValue());
            }
        }
        return winPhone.build();
    }


    public static void main(String[] args) {
        PushUtil pushUtil = new PushUtil();
        pushUtil.init();
        MessagePushModel pushModel = new MessagePushModel("您的帐号已在其他设备登录,已被强制登出,是否重新登录");
        pushModel.setAlert("测试推送");
        pushModel.setPlatform(PushPlatform.All);
        pushModel.setRegistrationId(new String[]{"141fe1da9e8683c462f"});
        Map<String, String> extras = new HashMap<>();
        extras.put("mode", "3");
        extras.put("content", "您的帐号已在其他设备登录,已被强制登出,是否重新登录");
        pushModel.setExtras(extras);
//        pushUtil.pushMessage(pushModel);
        pushUtil.push(pushModel);
    }
}




