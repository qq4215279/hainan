package com.gobestsoft.api.modular.cms.model;

import lombok.Data;

/**
 * App列表banner
 */
@Data
public class AppBannerDto {

    private String targetId;

    private String jumpUrl;

    private String category;

    /**
     * 标题
     */
    private String title;
    /**
     * 封面图
     */
    private String cover;

}
