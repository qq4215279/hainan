package com.gobestsoft.mamage.moudle.system.wrapper;



import com.gobestsoft.mamage.basic.BaseControllerWrapper;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 角色列表的包装类
 *
 * @author gobestsoft
 * @date 2017年2月19日10:59:02
 */
public class RoleWrapper extends BaseControllerWrapper {

    public RoleWrapper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void wrapTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
    }

}
