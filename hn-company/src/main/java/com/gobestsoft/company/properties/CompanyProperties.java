package com.gobestsoft.company.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * create by gutongwei
 * on 2018/7/30 上午9:19
 */

@Component
@ConfigurationProperties(prefix = CompanyProperties.PREFIX)
@Data
public class CompanyProperties {
    public static final String PREFIX = "company";


}
