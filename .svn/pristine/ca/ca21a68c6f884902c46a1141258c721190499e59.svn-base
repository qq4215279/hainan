package com.gobestsoft.mamage.dialect.form;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.IProcessorMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/4 上午12:01
 */
public class TextProcessor extends FormProcessor {

    public TextProcessor() {
        super("text");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        Element input = new Element("input");
        input.setAttribute("id", getId());
        input.setAttribute("class", "form-control");
        input.setAttribute("type", "text");
        input.setAttribute("name", getName());
        if (StringUtils.isNotEmpty(getDisabled()) && "true".equals(getDisabled())) {
            input.setAttribute("disabled", getDisabled());
        }
        input.setAttribute("value", getValue(arguments, element, "value"));
        input.setAttribute("onclick", getOnclick());
        nodes.add(getGroup(input));
        return nodes;
    }

    @Override
    public IProcessorMatcher<? extends Element> getMatcher() {
        return super.getMatcher();
    }

    @Override
    public int getPrecedence() {
        return 300;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
