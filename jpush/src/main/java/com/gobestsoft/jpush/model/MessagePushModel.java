package com.gobestsoft.jpush.model;

import lombok.Data;

/**
 * Create by gutongwei
 * 2018/4/27
 */
@Data
public class MessagePushModel extends PushModel {

    public MessagePushModel() {
    }

    public MessagePushModel(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * 标题
     */
    private String title;

    /**
     * msg_content
     */
    private String msgContent;


}
