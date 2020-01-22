package com.gobestsoft.mamage.dialect.form;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * create by gutongwei
 * on 2018/8/3 下午11:54
 */
@Data
public abstract class FormProcessor extends AbstractMarkupSubstitutionElementProcessor {

    /**
     * 控件ID
     */
    private String id;
    /**
     * 控件描述
     */
    private String label;
    /**
     * name
     */
    private String name;
    /**
     * 是否必填
     */
    private String isMust;
    /**
     * 是否需要线
     */
    private String hasLine;

    /**
     * 点击事件
     */
    private String onclick;

    /**
     * 禁止输入
     */
    private String disabled;

    /**
     * 图片宽度
     */
    private String width;

    /**
     * 图片高度
     */
    private String height;

    /**
     * 图片值
     */
    private String defImg;

    /**
     * 音频/视频区别
     */
    private String type;

    /**
     * 页面类型
     */
    private String viewtype;


    /**
     * 只读
     */
    private String readOnly;

    protected FormProcessor(String elementName) {
        super(elementName);
    }


    /**
     * 初始化
     *
     * @param element
     */
    protected void init(Element element) {
        id = element.getAttributeValue("id");
        label = element.getAttributeValue("label");
        name = element.getAttributeValue("name");
        hasLine = element.getAttributeValue("line");
        isMust = element.getAttributeValue("must");
        onclick = element.getAttributeValue("onclick");
        disabled = element.getAttributeValue("disabled");
        width = element.getAttributeValue("width");
        height = element.getAttributeValue("height");
        defImg = element.getAttributeValue("defImg");
        type = element.getAttributeValue("type");
        viewtype = element.getAttributeValue("viewtype");
        readOnly = element.getAttributeValue("readonly");
    }


    /**
     * 获取控件
     *
     * @param input
     * @return
     */
    protected Element getGroup(Element input) {
        Element group = new Element("div");
        group.setAttribute("class", "form-group");
        Element labelElement = new Element("label");
        if ("true".equals(isMust)) {//将必填项*放置于label之前
            Element must = new Element("label");
            must.addChild(new Text("*"));
            must.setAttribute("style", "color: red; position: inherit;top: 2px;");
            labelElement.addChild(must);
        }
        labelElement.setAttribute("for", id);
        labelElement.setAttribute("class", "col-sm-4 control-label");
        labelElement.addChild(new Text(label));
        group.addChild(labelElement);
        Element inputDiv = new Element("div");
        inputDiv.setAttribute("class", "col-sm-8");
        if ("true".equals(disabled)) {
            input.setAttribute("disabled", "true");
        }
        if ("true".equals(readOnly)) {
            input.setAttribute("readonly", "true");
        }
        inputDiv.addChild(input);
        group.addChild(inputDiv);

        if ("true".equals(hasLine)) {
            group.setAttribute("style", "border-bottom: 1px dotted #dedede;padding-bottom: 20px;");
        }


        return group;
    }

    /**
     * 获取控件
     *
     * @param ele
     * @return
     */
    protected Element getLoopGroup(Element ele, int type) {
        Element group = new Element("div");
        group.setAttribute("class", "form-group");
        Element lab = new Element("label");
        if ("true".equals(isMust)) {//将必填项*放置于label之前
            Element must = new Element("label");
            must.addChild(new Text("*"));
            must.setAttribute("style", "color: red; position: inherit;top: 2px;");
            lab.addChild(must);
        }
        lab.setAttribute("class", "col-sm-4 control-label");
        if (type == 1) {
            lab.addChild(new Text("可操作信息设置"));
        } else if (type == 2) {
            lab.addChild(new Text("列表展示类型"));
        }
        group.addChild(lab);
        group.addChild(ele);
        if ("true".equals(hasLine)) {
            group.setAttribute("style", "border-bottom: 1px dotted #dedede;padding-bottom: 20px;");
        }

        return group;
    }

    /**
     * 获取图片控件
     *
     * @param
     * @return
     */
    protected Element getImgGroup(Element label, Element div1, Element div2) {
        Element group = new Element("div");
        group.addChild(label);
        group.addChild(div1);
        group.addChild(div2);
        group.setAttribute("class", "form-group clip-group");
        if ("true".equals(hasLine)) {
            group.setAttribute("style", "border-bottom: 1px dotted #dedede;padding-bottom: 20px;");
        }

        return group;
    }

    /**
     * 获取文件控件
     *
     * @param ele
     * @return
     */
    protected Element getFileGroup(Element ele) {
        Element group = new Element("div");
        group.setAttribute("class", "form-group");
        Element labelElement = new Element("label");
        labelElement.setAttribute("class", "col-sm-4 control-label");
        labelElement.addChild(new Text("上传文件"));
        group.addChild(labelElement);
        group.addChild(ele);

        if ("true".equals(hasLine)) {
            group.setAttribute("style", "border-bottom: 1px dotted #dedede;padding-bottom: 20px;");
        }

        return group;
    }

    /**
     * 获取value
     *
     * @param arguments
     * @param element
     * @return
     */
    protected String getValue(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        try {
            if (StringUtils.isNotEmpty(attributeValue)) {
                Configuration configuration = arguments.getConfiguration();
                IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
                IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, attributeValue);
                Object result = expression.execute(configuration, arguments);
                return result == null ? "" : result.toString();
            }
        } catch (Exception ex) {
            return "";
        }
        return "";

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

}
