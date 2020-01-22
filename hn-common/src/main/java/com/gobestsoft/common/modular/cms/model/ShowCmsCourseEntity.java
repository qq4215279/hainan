package com.gobestsoft.common.modular.cms.model;

import lombok.Data;

/**
 * create by gutongwei
 * on 2018/6/19 下午5:21
 */
@Data
public class ShowCmsCourseEntity {

    /**  */
    private int id;
    /**  */
    private int pid;
    /**
     * 【0:普通课程】【1：系列课程】
     */
    private int type;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图路径
     */
    private String cover;
    /**
     * 内容
     */
    private String content;
    /**
     * 附件，以“,”分割
     */
    private String attachment;
    /**
     * 课程类型类型:0：图文，1：音频，2：视频，3：跳转 4：文件（保留）
     */
    private int infoType;
    /**
     * 状态  【0：草稿】 ，【1：上线】，【2:下架】
     */
    private int status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 组织id
     */
    private Integer orgId;
    /**  */
    private String createTime;
    /**  */
    private String updateUser;
    /**  */
    private String updateTime;

    private String createUser;


    private String createUserName;

    private Integer delFlg;
}
