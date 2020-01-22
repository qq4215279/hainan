package com.gobestsoft.api.modular.cms.model;

import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.common.modular.cms.model.CmsCourseEntity;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.WebSiteUtil;

import lombok.Data;

/**
 * create by gutongwei
 * on 2018/6/15 上午10:44
 */
@Data
public class CmsCourseDto {

    public CmsCourseDto(CmsCourseEntity entity, ApiProperties apiProperties) {
        this.id = entity.getCourse_id();
        this.info_type = entity.getInfo_type();
        this.cover = WebSiteUtil.getBrowseUrl(entity.getCover());
        this.title = entity.getTitle();
        this.published_time = DateUtil.parseAndFormat(entity.getCreate_time(), "yyyyMMddHHmmss", "yyyy-MM-dd");
        this.browse_num = entity.getPreview_num();
        this.thumbs_up_num = entity.getThumbs_up_num();
        this.is_collect = entity.getIs_collect();
        this.is_thumbs_up = entity.getIs_thumbs_up();
        this.jump_url = apiProperties.getCourseUrl(String.valueOf(this.id), true);
        this.type = entity.getType();
    }


    public CmsCourseDto() {
    }

    /**
     * 课程ID
     */
    private int id;

    /**
     * 课程类型类型:0：图文，1：音频，2：视频
     */
    private int info_type;

    /**
     * 课程封面图
     */
    private String cover;
    /**
     * 标题
     */
    private String title;
    /**
     * 创建时间
     */
    private String published_time;
    /**
     * 浏览数
     */
    private int browse_num;
    /**
     * 点赞数
     */
    private int thumbs_up_num;
    /**
     * 用户是否点过赞【0：没有】【1：已点】
     */
    private int is_thumbs_up;
    /**
     * 用户是否收藏【0：没有】【1：已收藏】
     */
    private int is_collect;
    /**
     * 跳转地址
     */
    private String jump_url;
    /**
     * 课程类型【0:普通课程】【1：系列课程】
     */
    private int type;
}
