package com.gobestsoft.mamage.dialect.search;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * create by gutongwei
 * on 2018/8/3 下午5:10
 */
public abstract class SearchProcessorBasic extends AbstractMarkupSubstitutionElementProcessor {

    protected String name;

    protected String label;

    protected String disable;


    protected SearchProcessorBasic(String elementName) {
        super(elementName);
    }

    protected SearchProcessorBasic(IElementNameProcessorMatcher matcher) {
        super(matcher);
    }

    protected void init(Element element) {
        name = element.getAttributeValue("name");
        label = element.getAttributeValue("label");
        disable = element.getAttributeValue("disable");
    }


    protected Element getGroup() {
        Element group = new Element("div");
        group.setAttribute("class", "input-group");
        return group;
    }


    protected Element getButton() {
        Element button = new Element("button");
        button.setAttribute("data-toggle", "dropdown");
        button.setAttribute("class", "btn btn-white dropdown-toggle");
        button.setAttribute("type", "button");
        return button;
    }


    protected Element getGroupBtn(String label) {
        Element groupBtn = new Element("div");
        groupBtn.setAttribute("class", "input-group-btn");
        Element button = new Element("button");
        button.setAttribute("data-toggle", "dropdown");
        button.setAttribute("class", "btn btn-white dropdown-toggle");
        button.setAttribute("type", "button");
        Text labelText = new Text(label);
        button.addChild(labelText);
        groupBtn.addChild(button);
        return groupBtn;
    }

    /**
     * 获取value
     *
     * @param arguments
     * @param element
     * @return
     */
    protected String getValue(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        if (StringUtils.isNotEmpty(attributeValue)) {
            Configuration configuration = arguments.getConfiguration();
            IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
            IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, attributeValue);
            Object result = expression.execute(configuration, arguments);
            return result == null ? "" : result.toString();
        }
        return "";

    }

}
