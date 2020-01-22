package com.gobestsoft.common.modular.cms.model;


import lombok.Data;

@Data
public class CmsApproveEntity {

    /**
     * 资讯ID
     */
    private String articleID;

    /**
     *
     */
    private int sort;

    /***
     * 审核提出人
     */
    private String fromPerson;

    /***
     * 审核提出组织
     */
    private int fromDept;

    /***
     * 审核状态 0：待审核 -1：审核不通过 1：审核通过
     */

    private int status;

    /***
     * 审核级别 1：项目部 2：分公司 3：局级
     */
    private int checkLevel;

    /***
     * 审核接收人
     */
    private String toPerson;

    /***
     * 审核接收组织
     */
    private int toDept;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改意见
     */
    private String comment;



}
