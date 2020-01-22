package com.gobestsoft.admin.config.meshsite;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * create by gutongwei
 * on 2018/7/27 下午5:38
 */
public class Meshsite3Filter extends ConfigurableSiteMeshFilter {


    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/**", "/layout")
                .addExcludedPath("/login").addExcludedPath("/company/login").addExcludedPath("/welcome")
                .addExcludedPath("/static/**").addExcludedPath("*/exportList");
        builder.addTagRuleBundle(new JsTagRuleBundle());
    }
}