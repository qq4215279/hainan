package com.gobestsoft.mamage.dialect.authority;

import com.gobestsoft.mamage.basic.ManageBasic;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/8/3 下午11:33
 */
public class ButtonProcessor extends AbstractMarkupSubstitutionElementProcessor {

    private ManageBasic manageBasic;

    public ButtonProcessor(ManageBasic manageBasic) {
        super("btn");
        this.manageBasic = manageBasic;
    }


    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        String btnClass = StringUtils.defaultString(element.getAttributeValue("class"), "btn btn-primary");
        String id = element.getAttributeValue("id");
        String label = element.getAttributeValue("label");
        String fa = element.getAttributeValue("fa");
        String onclick = element.getAttributeValue("onclick");
        String permission = element.getAttributeValue("permission");
        String disabled = element.getAttributeValue("disabled");
        if (manageBasic.getLoginUser() == null || (!manageBasic.isAdmin() && StringUtils.isNotEmpty(permission) && !manageBasic.hasPermission(permission))) {
            return new ArrayList<>();
        }

        Element button = new Element("button");
        button.setAttribute("class", btnClass);
        button.setAttribute("id", id);
        button.setAttribute("type", "button");
        button.setAttribute("onclick", onclick);
        if(StringUtils.isNotEmpty(disabled) && "true".equals(disabled)) {
        	button.setAttribute("disabled", disabled);
        }
        if (StringUtils.isNotEmpty(fa)) {
            Element i = new Element("i");
            i.setAttribute("class", fa);
            button.addChild(i);
        }
        Text labelText = new Text(label);
        button.addChild(labelText);
        nodes.add(button);

        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 103;
    }
}
