package com.gobestsoft.mamage.dialect.form;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IProcessorMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanmu on 2018/8/24 .
 */
public class ImgProcessor extends FormProcessor{

   public ImgProcessor() {
       super("img");
   }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        Element label1 = new Element("label");
        label1.setAttribute("class", "col-sm-4 control-label");
        
        Element label2 = new Element("label");
        label2.setAttribute("style", "color: red; position: inherit;top: 2px");
        label1.addChild(label2);
        label1.addChild(new Text(getLabel()));

        Element div1 = new Element("div");
        div1.setAttribute("class", "col-sm-5");
        
        Element imgele = new Element("img");
        if(StringUtils.isNotEmpty(getValue(arguments, element, "defImg"))){
            imgele.setAttribute("src", getValue(arguments, element, "defImg"));
            imgele.setAttribute("style", "width:100%; height:auto");
            imgele.setAttribute("data-width", getWidth());
            imgele.setAttribute("data-height", getHeight());
        }else {
            imgele.setAttribute("style", "display:none");
            imgele.setAttribute("style", "width:100%; height:auto");
            imgele.setAttribute("data-width", getWidth());
            imgele.setAttribute("data-height", getHeight());
        }

        div1.addChild(imgele);

        Element div2 = new Element("div");
        div2.setAttribute("class", "col-sm-3");
        Element labelele = new Element("label");
        labelele.setAttribute("class", "btn btn-primary choose-label");
        Element i = new Element("i");
        i.setAttribute("class", "fa fa-address-book");
        labelele.addChild(i);
        labelele.addChild(new Text("选择图片"));

        Element input1 = new Element("input");
        input1.setAttribute("type", "file");
        input1.setAttribute("class", "choose-btn");
        input1.setAttribute("style", "display: none");

        Element input2 = new Element("input");
        input2.setAttribute("type", "hidden");
        input2.setAttribute("class", "saveInput");
        input2.setAttribute("id", getId());
        input2.setAttribute("value", getValue(arguments, element, "defImg"));

        div2.addChild(labelele);
        div2.addChild(input1);
        div2.addChild(input2);
        nodes.add(getImgGroup(label1, div1, div2));
        return nodes;
    }

    @Override
    public IProcessorMatcher<? extends Element> getMatcher() {
        return super.getMatcher();
    }

    @Override
    public int getPrecedence() {
        return 600;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}