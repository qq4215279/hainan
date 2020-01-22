package com.gobestsoft.common.constant.model;

import lombok.Data;

/**
 * 组织数统计数据
 */
@Data
public class OrganizationRankData {
    /**
     * 月份
     */
    private String month;
    /**
     * pid
     */
    private Integer id;
    /**
     * 组织名称
     */
    private String name;

    @Override
    public String toString() {
        return "OrganizationRankData{" +
                "month='" + month + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
