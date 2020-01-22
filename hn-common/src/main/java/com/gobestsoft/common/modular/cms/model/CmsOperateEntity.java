package com.gobestsoft.common.modular.cms.model;

import lombok.Data;

@Data

public class CmsOperateEntity {

    /**
     * 标题编辑可否
     */
    private int title;

    /**
     * 关键字
     */
    private int keyword;

    /**
     * 栏目
     */
    private int column;

    /**
     * 置顶
     */
    private int istop;

    /**
     * 排序
     */
    private int sort;

    /**
     * 类型
     */
    private int type;

    /**
     * 封面图
     */
    private int picture;

    /**
     * link
     */
    private int link;

    /**
     * 发布时间
     */
    private int pubTime;

    /**
     * 可操作设置
     */
    private int enable;

    /**
     * 编辑人
     */
    private int editor;

    /**
     * 资讯来源
     */
    private int sourceFrom;

    /**
     * 资讯点击数
     */
    private int readCount;

    /**
     * 定向发布
     */
    private int publish;

    /**
     * 内容
     */
    private int content;

    /**
     * 意见
     */
    private int comment;

    /**
     * 摘要
     */
    private int roundup;

    /**
     * 作者
     */
    private int author;

}
