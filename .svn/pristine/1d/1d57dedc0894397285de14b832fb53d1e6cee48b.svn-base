package com.gobestsoft.mamage.moudle.system.wrapper;

import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseControllerWrapper;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author gobestsoft
 * @date 2017年4月25日 18:10:31
 */
public class DeptWrapper extends BaseControllerWrapper {

    public DeptWrapper(Object list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
