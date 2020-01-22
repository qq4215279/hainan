package com.gobestsoft.mamage.dialect.form;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IProcessorMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanmu on 2018/8/16.
 */
public class AreaProcessor extends FormProcessor {

    public AreaProcessor() {
        super("area");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        Element input = new Element("textarea");
        input.setAttribute("id", getId());
        input.setAttribute("class", "form-control");
        input.setAttribute("name", getName());
        input.setAttribute("onclick",getOnclick());
        input.addChild(new Text(getValue(arguments, element, "value")));
        nodes.add(getGroup(input));
        return nodes;
    }

    @Override
    public IProcessorMatcher<? extends Element> getMatcher() {
        return super.getMatcher();
    }

    @Override
    public int getPrecedence() {
        return 305;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
