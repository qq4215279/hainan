package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.core.basic.ObjectMap;
import com.gobestsoft.mamage.basic.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duanmu on 2019/1/29.
 */
@Controller
@RequestMapping(value = "/searchres")
public class SearchResController extends BaseController{

    private String PREFIX = "/dept/member/";

    @Autowired
    private DeptMemberMapper memberMapper;

    @RequestMapping(value = "/household")
    public String getHouseHold(){
        return PREFIX + "search_res";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(){
        Page page = defaultPage();
        ObjectMap map = ObjectMap.getInstance();

        List<Map> list = new ArrayList<>();
        map.putRequest("query_name");
        map.putRequest("query_num");

        list = memberMapper.selectByHouseHold(page, map);
        page.setRecords(list);
        return super.packForBT(page);
    }


    @RequestMapping(value = "/rehomeActivity")
    public String getRehomeActivity(){
        return PREFIX + "search_rehome";
    }


    @RequestMapping("/relist")
    @ResponseBody
    public Object relist(){
        Page page = defaultPage();
        ObjectMap map = ObjectMap.getInstance();

        List<Map> list = new ArrayList<>();
        map.putRequest("query_name");
        map.putRequest("query_num");
        map.putRequest("query_deptname");

        list = memberMapper.selectByRehome(page, map);
        page.setRecords(list);
        return super.packForBT(page);
    }

}
