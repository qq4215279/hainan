package com.gobestsoft.common.modular.cms.model;

import lombok.Data;

@Data
public class CmsRelationEntity{

    private int id;

    private boolean exist;

    private String articleId;

    private int categoryId;

    private String categoryName;

    private int pubStatus;

    private String publishedTime;


}
