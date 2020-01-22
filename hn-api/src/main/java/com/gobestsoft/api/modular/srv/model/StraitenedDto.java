package com.gobestsoft.api.modular.srv.model;

import lombok.Data;

/**
 * create by gutongwei
 * on 2018/10/18 上午11:14
 */
@Data
public class StraitenedDto {

    /**
     * 初审或终审ID
     */
    private int id;


    /**
     * 0：初审
     * 1：终审
     */
    private int type;

    /**
     * 状态
     */
    private int status;


    /**
     * 拒绝原因
     */
    private String reason;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建日期
     */
    private String createTime;

    /**
     * 困难帮扶类型
     */
    private String straitenedType;

    /**
     * 初审Id
     */
    private Integer firstStraitenedId;

    /**
     * 帮扶类型
     */
    private String StraitenedName;

    /**
     * 是否重新提交
     */
    private boolean commitFlg;

}
