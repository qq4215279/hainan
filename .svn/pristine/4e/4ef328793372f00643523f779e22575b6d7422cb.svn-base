package com.gobestsoft.mamage.dialect.search;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/3 下午5:12
 */
@Data
public class SearchInputProcessor extends SearchProcessorBasic {

    public SearchInputProcessor() {
        super("input");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        String placeholder = element.getAttributeValue("placeholder");
        Element group = getGroup();
        Element groupBtn = getGroupBtn(label);
        Element input = getInput(placeholder, getValue(arguments, element, "value"));
        group.addChild(groupBtn);
        group.addChild(input);
        nodes.add(group);
        return nodes;
    }

    private Element getInput(String placeholder, String value) {
        Element input = new Element("input");
        input.setAttribute("name", name);
        input.setAttribute("id", name);
        input.setAttribute("placeholder", placeholder);
        input.setAttribute("value", value);
        input.setAttribute("type", "text");
        input.setAttribute("class", "form-control");
        if (StringUtils.isNotEmpty(disable)) {
            input.setAttribute("disabled", "");
        }
        return input;
    }

    @Override
    public int getPrecedence() {
        return 102;
    }

    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
