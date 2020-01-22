package com.gobestsoft.mamage.dialect;

import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;

import java.util.List;

/**
 * 控件辅助类
 * create by gutongwei
 * on 2018/8/7 上午9:03
 */
public class ControlOption {


    /**
     * 下拉框
     *
     * @param groupCode
     * @return
     */
    public static Element getSelect(String groupCode, String selectedValue, String id, String name) {
        List<Dict> dicts = ConstantFactory.me().findInDict(groupCode);
        Element select = new Element("select");
        select.setAttribute("class", "form-control");
        select.setAttribute("id", id);
        select.setAttribute("name", name);

        Element selected = new Element("option");
        Text selectedText = new Text("请选择");
        selected.setAttribute("value", "");
        if (StringUtils.isEmpty(selectedValue)) {
            selected.setAttribute("selected", "true");
        }
        selected.addChild(selectedText);
        select.addChild(selected);

        dicts.forEach(d -> {
            Element option = new Element("option");
            Text optionText = new Text(d.getName());
            option.addChild(optionText);
            option.setAttribute("value", d.getCode());
            if (d.getCode().equals(selectedValue)) {
                option.setAttribute("selected", "true");
            }
            select.addChild(option);
        });

        return select;
    }


    /**
     * 下拉框
     * @return
     */
    public static Element getCategorySelect(List<DictModel> list, String id, String name) {
        Element select = new Element("select");
        select.setAttribute("class", "form-control");
        select.setAttribute("id", id);
        select.setAttribute("name", name);

        Element selected = new Element("option");
        Text selectedText = new Text("请选择");
        selected.setAttribute("value", "");
        selected.addChild(selectedText);
        select.addChild(selected);

        list.forEach(d -> {
            Element option = new Element("option");
            Text optionText = new Text(d.getName());
            option.addChild(optionText);
            option.setAttribute("value", d.getCode());
            select.addChild(option);
        });

        return select;
    }

}
