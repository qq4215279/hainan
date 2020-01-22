package com.gobestsoft.common.modular.appuser.model;

import com.gobestsoft.core.util.WebSiteUtil;
import lombok.Data;

/**
 * Created by gutongwei on 2018/1/8.
 */
@Data
public class AppIntegralTaskEntity {

    private int task_id;

    private String task_name;


    private int integral;

    private int is_complete;

    private String avatar;

    private String complete_tip;

    private String uncomplete_tip;


    public String getAvatar() {
        return WebSiteUtil.getBrowseUrl(avatar);
    }
}
