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
public class CheckProcessor extends FormProcessor {

    public CheckProcessor() {
        super("check");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        Element checkBox = new Element("input");
        checkBox.setAttribute("type", "checkbox");
        checkBox.setAttribute("id", getId());
        checkBox.setAttribute("name", getName());
        checkBox.setAttribute("style", "margin-top: 10px;");
        checkBox.setAttribute("value", getValue(arguments, element, "value"));
        checkBox.setAttribute("onclick",getOnclick());
        nodes.add(getGroup(checkBox));
        return nodes;
    }


    @Override
    public int getPrecedence() {
        return 304;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
