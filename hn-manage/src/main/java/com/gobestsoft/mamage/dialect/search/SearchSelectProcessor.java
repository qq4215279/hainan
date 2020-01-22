package com.gobestsoft.mamage.dialect.search;

import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.dialect.ControlOption;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.ArrayList;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/3 下午9:35
 */
public class SearchSelectProcessor extends SearchProcessorBasic {


    public SearchSelectProcessor() {
        super("select");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        Element select = null;
        init(element);
        String groupCode = element.getAttributeValue("code");
        String selectedValue = element.getAttributeValue("value");
        Element group = getGroup();
        Element groupBtn = getGroupBtn(label);
        if(ToolUtil.isEmpty(groupCode)){
            select = ControlOption.getCategorySelect((List<DictModel>) getObject(arguments, element, "categorycode"), name, name);
        } else {
            select = ControlOption.getSelect(groupCode, selectedValue,name,name);
        }

        group.addChild(groupBtn);
        group.addChild(select);
        nodes.add(group);
        return nodes;
    }

    /**
     * 获取value
     *
     * @param arguments
     * @param element
     * @return
     */
    protected Object getObject(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        try {
            if (StringUtils.isNotEmpty(attributeValue)) {
                Configuration configuration = arguments.getConfiguration();
                IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
                IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, attributeValue);
                Object result = expression.execute(configuration, arguments);
                return result;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }


    @Override
    public int getPrecedence() {
        return 103;
    }

    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
