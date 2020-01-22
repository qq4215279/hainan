package com.gobestsoft.mamage.dialect.form;

import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.dialect.ControlOption;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/4 上午12:01
 */
public class SelectProcessor extends FormProcessor {

    public SelectProcessor() {
        super("select");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        Element select = null;
        init(element);
        String groupCode = element.getAttributeValue("code");
        if(ToolUtil.isEmpty(groupCode)){
            select = ControlOption.getCategorySelect((List<DictModel>) getObject(arguments, element, "categorycode"), getId(), getName());
        } else {
            select = ControlOption.getSelect(groupCode, getValue(arguments, element, "value"), getId(), getName());
        }
        nodes.add(getGroup(select));
        return nodes;
    }


    @Override
    public int getPrecedence() {
        return 301;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
