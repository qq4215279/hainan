package com.gobestsoft.api.modular.cms.model;

import lombok.Data;

/**
 * 资讯列表banner
 */
@Data
public class ArticleBannerDto {


    private String id;

    private String article_id;

    private String title;

    private String cover;

    private String jump_url;

    private String published_time;


}
