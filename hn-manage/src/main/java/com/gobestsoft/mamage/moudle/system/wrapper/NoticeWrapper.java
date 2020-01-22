package com.gobestsoft.mamage.moudle.system.wrapper;

import com.gobestsoft.mamage.basic.BaseControllerWrapper;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author gobestsoft
 * @date 2017年4月25日 18:10:31
 */
public class NoticeWrapper extends BaseControllerWrapper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
    	String creater = (String) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
