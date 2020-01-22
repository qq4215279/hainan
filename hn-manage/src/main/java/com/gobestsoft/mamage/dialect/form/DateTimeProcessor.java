package com.gobestsoft.mamage.dialect.form;

import com.gobestsoft.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/4 上午12:01
 */
public class DateTimeProcessor extends FormProcessor {

    public DateTimeProcessor() {
        super("datetime");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        String format = element.getAttributeValue("format");
        String istime = StringUtils.defaultIfEmpty(element.getAttributeValue("istime"), "false");
        Element input = new Element("input");
        input.setAttribute("id", getId());
        input.setAttribute("class", "form-control datetime-control");
        input.setAttribute("type", "text");
        input.setAttribute("name", getName());
        input.setAttribute("data-format", format);
        input.setAttribute("data-istime", istime);
        String value = "false".equals(istime) ? DateUtil.parseAndFormat(getValue(arguments, element, "value"), "yyyyMMdd", format) : DateUtil.parseAndFormat(getValue(arguments, element, "value"), "yyyyMMddHHmmss", format);
        input.setAttribute("value", value);
        input.setAttribute("onclick", getOnclick());
        if (StringUtils.isEmpty(getDisabled()) || "false".equals(getDisabled())) {
            setReadOnly("true");
        }
        nodes.add(getGroup(input));
        return nodes;
    }


    @Override
    public int getPrecedence() {
        return 303;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
