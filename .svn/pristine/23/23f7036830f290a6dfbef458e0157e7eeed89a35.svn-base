package com.gobestsoft.common.modular.srv;

/**
 * create by gutongwei
 * on 2018/10/18 上午11:09
 */
public class StraitenedEntity {

    /**
     * 初审或终审ID
     */
    private int id;


    /**
     * 初审ID
     */
    private Integer firstStraitenedId;

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
     * 创建日期
     */
    private String createTime;

    /**
     * 帮扶类型
     */
    private String straitenedType;

    private Boolean commitFlag;

    public Boolean getCommitFlag() {
        return commitFlag;
    }

    public void setCommitFlag(Boolean commitFlag) {
        this.commitFlag = commitFlag;
    }

    /**
     * 终审
     */
    private StraitenedEntity endStraitened;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getFirstStraitenedId() {
        return firstStraitenedId;
    }

    public void setFirstStraitenedId(Integer firstStraitenedId) {
        this.firstStraitenedId = firstStraitenedId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public StraitenedEntity getEndStraitened() {
        return endStraitened;
    }

    public void setEndStraitened(StraitenedEntity endStraitened) {
        if (endStraitened != null) {
            this.id = endStraitened.getId();
            this.type = 1;
            this.reason = endStraitened.getReason();
            this.status = endStraitened.getStatus();
        }
        this.endStraitened = endStraitened;
    }

    public String getStraitenedType() {
        return straitenedType;
    }

    public void setStraitenedType(String straitenedType) {
        this.straitenedType = straitenedType;
    }
}
