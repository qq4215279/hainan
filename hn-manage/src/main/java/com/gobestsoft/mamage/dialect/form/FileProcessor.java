package com.gobestsoft.mamage.dialect.form;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.IProcessorMatcher;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanmu on 2018/8/28.
 */
public class FileProcessor extends FormProcessor{

    public FileProcessor() {
        super("file");
    }

    @Override
    protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element) {
        List<Node> nodes = new ArrayList<>();
        init(element);
        Element div1 = new Element("div");
        div1.setAttribute("class", "col-sm-8");

        Element div2 = new Element("div");
        div2.setAttribute("style", "display: -moz-box");

        Element div3 = new Element("div");
        div3.setAttribute("style", "display: table-cell; width: 35%");
        Element input1 = new Element("input");
        input1.setAttribute("class", "form-control");
        input1.setAttribute("type", "text");
        input1.setAttribute("id", getName());
        input1.setAttribute("name", getName());
        input1.setAttribute("style", "width:" + getWidth());
        div3.addChild(input1);

        Element div4 = new Element("div");
        div4.setAttribute("style", "display: table-cell; vertical-align: middle");

        Element div5 = new Element("div");
        div5.setAttribute("class", "jquery-fileupload");

        Element label = new Element("label");
        label.setAttribute("for", "fileupload");
        label.setAttribute("class", "btn btn-info");
        label.setAttribute("style", "display:inline-block;width:100px");
        Element i = new Element("i");
        i.setAttribute("class", "fa fa-upload");
        i.addChild(new Text("选择上传"));
        label.addChild(i);

        Element div6 = new Element("div");
        div6.setAttribute("class", "uploadBtn");
        div6.setAttribute("style", "display:none");
        Element input2 = new Element("input");
        input2.setAttribute("id", "fileupload");
        input2.setAttribute("type", "file");
        input2.setAttribute("name", "attch");
        input2.setAttribute("multiple", "");
        Element span1 = new Element("span");
        span1.addChild(new Text("+选择文件"));
        div6.addChild(input2);
        div6.addChild(span1);

        Element span2 = new Element("span");
        span2.setAttribute("class", "tips");

        Element div7 = new Element("div");
        div7.setAttribute("style", "clear: both");

        Element table = new Element("table");
        Element tbody = new Element("tbody");
        tbody.setAttribute("class", "filesName");
        table.addChild(tbody);

        div5.addChild(label);
        div5.addChild(div6);
        div5.addChild(span2);
        div5.addChild(div7);
        div5.addChild(table);

        div2.addChild(div3);
        div2.addChild(div5);

        Element div8 = new Element("div");
        div8.setAttribute("id", "progress");
        div8.setAttribute("class", "progress");
        Element i1 = new Element("i");
        i1.setAttribute("id", "progressText");
        Element div9 = new Element("div");
        div9.setAttribute("class", "progress-bar progress-bar-success");
        div8.addChild(i1);
        div8.addChild(div9);

        div1.addChild(div2);
        div1.addChild(div8);

        nodes.add(getFileGroup(div1));
        return nodes;
    }

    @Override
    public IProcessorMatcher<? extends Element> getMatcher() {
        return super.getMatcher();
    }

    @Override
    public int getPrecedence() {
        return 700;
    }


    @Override
    public int hashCode() {
        return getPrecedence();
    }
}
