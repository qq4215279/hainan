package com.gobestsoft.admin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * create by gutongwei
 * on 2018/7/30 上午9:19
 */

@Component
@ConfigurationProperties(prefix = AdminProperties.PREFIX)
@Data
public class AdminProperties {
    public static final String PREFIX = "admin";


    /**
     * PHP站点
     */
    private String phpSite;

}
