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
public class SearchDateTimeProcessor extends SearchProcessorBasic {

    public SearchDateTimeProcessor() {
        super("datetime");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);

        String format = element.getAttributeValue("format");
        String istime = StringUtils.defaultIfEmpty(element.getAttributeValue("istime"), "false");

        String placeholder = element.getAttributeValue("placeholder");
        String value = element.getAttributeValue("value");
        Element group = getGroup();
        Element groupBtn = getGroupBtn(label);
        Element input = getInput(placeholder, value);
        input.setAttribute("data-format", format);
        input.setAttribute("data-istime", istime);
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
        input.setAttribute("class", "form-control datetime-control");

        return input;
    }

    @Override
    public int getPrecedence() {
        return 101;
    }

    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
