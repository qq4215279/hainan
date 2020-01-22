package com.gobestsoft.api.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * Meshsite3配置
 * Create by gutongwei
 * 2018/5/22
 */
public class Meshsite3Filter extends ConfigurableSiteMeshFilter {


    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/show-page/**", "/show-page/layout")
                .addExcludedPath("/static/**").
                addExcludedPath("/**");//首页
    }
}
