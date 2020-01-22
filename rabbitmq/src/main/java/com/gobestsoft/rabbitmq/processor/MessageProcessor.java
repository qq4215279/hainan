package com.gobestsoft.rabbitmq.processor;

import lombok.Data;

import java.util.Map;

/**
 * 消息
 * create by gutongwei
 * on 2018/7/3 上午11:33
 */
@Data
public class MessageProcessor extends AbstractProcessor {


    private static final long serialVersionUID = -1003677765097216169L;
    /**
     * 消息类型
     */
    private int category;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息模式
     */
    private int mode;

    /**
     * 跳转目标Id
     */
    private String targetId;

    /**
     * 跳转地址
     */
    private String jumpUrl;

    /**
     * 是否推送
     */
    private boolean isPush;


    /**
     * 推送用户
     */
    private String[] auids;

    /**
     * 操作信息
     */
    private Map<String,String> operation;


}
