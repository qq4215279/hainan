package com.gobestsoft.api.modular.cms.model;


import com.gobestsoft.common.modular.cms.model.*;
import com.gobestsoft.core.util.DateUtil;
import lombok.Data;

import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.core.util.WebSiteUtil;

/**
 * 资讯列表
 *
 * @author gutongwei
 * <p>
 * 2017年12月8日
 */
@Data
public class ArticlesDto {

    public ArticlesDto() {
    }

    public ArticlesDto(CmsArticleEntity entity, ApiProperties apiProperties) {
        if (entity != null) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.covers = entity.getCoverPath().split(",");
            for (int i = 0; i < this.covers.length; i++) {
                this.covers[i] = WebSiteUtil.getBrowseUrl(this.covers[i]);
            }
            this.jump_url = apiProperties.getReviewBaseUrl(id, true);
            this.create_by = entity.getCreateUser();
            this.info_type = entity.getInfoType();
            this.template_type = entity.getTemplateType();
            this.published_time = DateUtil.parseAndFormat(entity.getPublishedTime(), "yyyyMMddHHmmss", "yyyy-MM-dd");
            this.thumbs_up_num = entity.getThumbsUpNum();
            this.browse_num = entity.getBrowseNum();
        }
    }

    private String id;

    private String title;

    private String[] covers;

    private String jump_url;

    private String create_by;

    private Integer info_type;

    private Integer template_type;

    private String published_time;

    private Integer thumbs_up_num;

    private Integer browse_num;
}
