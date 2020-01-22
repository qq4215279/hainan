package com.gobestsoft.mamage.dialect.form;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/4 上午12:01
 */
public class NumberProcessor extends FormProcessor {

    public NumberProcessor() {
        super("figure");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        String min = element.getAttributeValue("min");
        String max = element.getAttributeValue("max");
        Element input = new Element("input");
        input.setAttribute("id", getId());
        input.setAttribute("class", "form-control");
        input.setAttribute("type", "number");
        input.setAttribute("name", getName());
        input.setAttribute("max", max);
        input.setAttribute("min", min);
        input.setAttribute("value", getValue(arguments, element, "value"));
        input.setAttribute("onclick",getOnclick());
        nodes.add(getGroup(input));
        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 302;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
