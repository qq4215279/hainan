package com.gobestsoft.company.meshsite;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * create by gutongwei
 * on 2018/7/17 下午4:19
 */
public class JsTagRuleBundle implements TagRuleBundle {
    @Override
    public void install(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        state.addRule("js", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("js"), false));
        state.addRule("css", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("css"), false));
    }

    @Override
    public void cleanUp(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }
}
