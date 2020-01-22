package com.gobestsoft.mamage.moudle.system.wrapper;

import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseControllerWrapper;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 字典列表的包装
 *
 * @author gobestsoft
 * @date 2017年4月25日 18:10:31
 */
public class DictWrapper extends BaseControllerWrapper {

    public DictWrapper(Object list) {
        super(list);
    }

    /** 2017-11-30 caoy  modify ---- start */
    @Override
    public void wrapTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        String groupCode = ToolUtil.toStr(map.get("group_code"));
        List<Dict> dicts = ConstantFactory.me().findInDict(groupCode);
        if(dicts != null){
            for (Dict dict : dicts) {
            	if (ToolUtil.isEmpty(dict.getCode())) {
    				continue;
    			}
                detail.append("["+dict.getCode() + ":" +dict.getName() + "],");
            }
            map.put("detail", ToolUtil.removeSuffix(detail.toString(),","));
        }
    }
    /** 2017-11-30 caoy  modify ---- end */


}
