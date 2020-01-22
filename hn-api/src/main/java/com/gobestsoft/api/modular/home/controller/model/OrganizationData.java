package com.gobestsoft.api.modular.home.controller.model;

import lombok.Data;

/**
 * 组织建设统计数据
 */
@Data
public class OrganizationData {
    /**
     * 组织个数
     */
    private Integer org_count;
    /**
     * 会员人数
     */
    private Integer member_count;
    /**
     * 新增人数
     */
    private Integer addition_member_count;
    /**
     * 转会人数
     */
    private Integer transfer_member_count;
    /**
     * 新增企业建会人数
     */
    private Integer new_member_company_established;


}
