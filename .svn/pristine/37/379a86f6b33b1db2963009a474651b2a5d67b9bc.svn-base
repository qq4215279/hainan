package com.gobestsoft.api.modular.cms.model;


import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.common.modular.cms.model.CmsCourseEntity;
import com.gobestsoft.core.util.WebSiteUtil;
import lombok.Data;

/**
 * 资讯列表
 *
 * @author gutongwei
 * <p>
 * 2017年12月8日
 */
@Data
public class ArticlesCatalogDto extends ArticlesDto {

    public ArticlesCatalogDto(ArticlesDto dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.covers = dto.getCovers();
        this.jump_url = dto.getJump_url();
        this.create_by = dto.getCreate_by();
        this.info_type = dto.getInfo_type();
        this.template_type = dto.getTemplate_type();
        this.published_time = dto.getPublished_time();
        this.thumbs_up_num = dto.getThumbs_up_num();
        this.browse_num = dto.getBrowse_num();
        this.item_type = 1;
    }

    public ArticlesCatalogDto(CmsCourseEntity entity, ApiProperties apiProperties) {
        this.id = String.valueOf(entity.getCourse_id());
        this.title = entity.getTitle();
        this.covers = new String[]{WebSiteUtil.getBrowseUrl(entity.getCover())};
        this.jump_url = apiProperties.getCourseUrl(String.valueOf(entity.getCourse_id()), true);
        this.create_by = entity.getCreate_by();
        this.info_type = entity.getInfo_type();
        this.template_type = 2;
        this.published_time = entity.getCreate_time();
        this.thumbs_up_num = entity.getThumbs_up_num();
        this.browse_num = entity.getPreview_num();
        this.item_type = 1;
        this.which = 1;
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

    private int item_type;

    private int which;
}
