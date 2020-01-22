package com.gobestsoft.mamage.dialect.form;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IProcessorMatcher;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanmu on 2018/8/23.
 */
public class InteractProcessor extends FormProcessor{

    public InteractProcessor() {
        super("interact");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        Element divEle = new Element("div");
        divEle.setAttribute("class", "col-sm-8");
        Element group = new Element("div");
        group.setAttribute("id", getId());
        group.setAttribute("class", "btn-group");
        group.setAttribute("data-toggle", "buttons");
        String name = "";
        String[] arraystr = getLabel().split(",");
        for(String val: arraystr){
            Element labelElement = new Element("label");
            if("可分享".equals(val)){
                name = "isShare";
                labelElement.setAttribute("id", name);
            }else if("可评论".equals(val)){
                name = "isTopic";
                labelElement.setAttribute("id", name);
            } else if("可点赞".equals(val)){
                name = "isLike";
                labelElement.setAttribute("id", name);
            } else if("可收藏".equals(val)){
                name = "isCollect";
                labelElement.setAttribute("id", name);
            }
            labelElement.setAttribute("class", "btn btn-primary");
            Element input1 = new Element("input");
            if("isShare".equals(name)){
                input1.setAttribute("id", "ckbIsShare");
            } else if("isTopic".equals(name)){
                input1.setAttribute("id", "ckbIsTopic");
            } else if("isLike".equals(name)){
                input1.setAttribute("id", "ckbIsLike");
            } else if("isCollect".equals(name)){
                input1.setAttribute("id", "ckbIsCollect");
            }
            input1.setAttribute("type", "checkbox");
            input1.setAttribute("name", name);
            input1.setAttribute("value", getValue(arguments, element, "value"));
            Element i1 = new Element("i");
            if("isShare".equals(name)){
                i1.setAttribute("class", "fa fa-share-alt");
            }else if("isTopic".equals(name)){
                i1.setAttribute("class", "fa fa-commenting");
            }else if("isLike".equals(name)){
                i1.setAttribute("class", "fa fa-thumbs-o-up");
            }else if("isCollect".equals(name)){
                i1.setAttribute("class", "fa fa-star-o");
            }
            labelElement.addChild(input1);
            labelElement.addChild(i1);
            labelElement.addChild(new Text(val));
            group.addChild(labelElement);
        }
        divEle.addChild(group);
        nodes.add(getLoopGroup(divEle, 1));
        return nodes;
    }

    @Override
    public IProcessorMatcher<? extends Element> getMatcher() {
        return super.getMatcher();
    }

    @Override
    public int getPrecedence() {
        return 400;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
