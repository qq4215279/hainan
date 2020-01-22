package com.gobestsoft.mamage.dialect.form;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IProcessorMatcher;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanmu on 2018/8/24 .
 */
public class ImgChgProcessor extends FormProcessor{

    public ImgChgProcessor() {
        super("imgchg");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        Element divEle = new Element("div");
        divEle.setAttribute("class", "col-sm-8");
        Element ulEle = new Element("ul");
        ulEle.setAttribute("class", "caoy_ul");
        String tem = getValue(arguments, element, "value");
        String[] strsplit = tem.split(",");
        for(String str : strsplit){
            String code = str.substring(0,1);
            String name = str.substring(str.indexOf("=") + 1, str.length());
            Element inputele = new Element("input");
            Element labelele = new Element("label");
            inputele.setAttribute("style", "display:none");
            inputele.setAttribute("class", "");
            inputele.setAttribute("id", getName() + code);
            inputele.setAttribute("name", getName());
            inputele.setAttribute("type", "radio");
            inputele.setAttribute("value", code);
            divEle.addChild(inputele);

            labelele.setAttribute("class", "");
            labelele.setAttribute("for", getName() + code);
            labelele.setAttribute("name", getName());
            labelele.setAttribute("style", "display:inline");

            Element liele = new Element("li");
            liele.setAttribute("style", "float:left");
            liele.setAttribute("class", "btn");
            liele.setAttribute("onclick", getOnclick());
            if("0".equals(code)){
                Element ele = new Element("i");
                ele.setAttribute("class", "fa fa-image");
                ele.setAttribute("class", "fa fa-bars");
                liele.addChild(ele);
                liele.addChild(new Text(name));
            } else if("1".equals(code)){
                Element ele = new Element("i");
                ele.setAttribute("class", "fa fa-bars");
                Element ele1 = new Element("i");
                ele.setAttribute("class", "fa fa-image");
                liele.addChild(ele);
                liele.addChild(ele1);
                liele.addChild(new Text(name));
            } else if("2".equals(code)){
                Element ele = new Element("i");
                ele.setAttribute("class", "fa fa-image");
                liele.addChild(ele);
                liele.addChild(new Text(name));
            } else if("3".equals(code)){
                Element ele = new Element("i");
                ele.setAttribute("class", "fa fa-image");
                Element ele1 = new Element("i");
                ele1.setAttribute("class", "fa fa-image");
                Element ele2 = new Element("i");
                ele2.setAttribute("class", "fa fa-image");
                liele.addChild(ele);
                liele.addChild(ele1);
                liele.addChild(ele2);
                liele.addChild(new Text(name));
            } else if("4".equals(code)){
                Element ele = new Element("i");
                ele.setAttribute("class", "fa fa-image");
                liele.addChild(ele);
                liele.addChild(new Text(name));
            }
            labelele.addChild(liele);
            ulEle.addChild(labelele);
        }
        divEle.addChild(ulEle);
        nodes.add(getLoopGroup(divEle, 2));
        return nodes;
    }

    @Override
    public IProcessorMatcher<? extends Element> getMatcher() {
        return super.getMatcher();
    }

    @Override
    public int getPrecedence() {
        return 500;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }

}
