package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptOrganizationMapper;
import com.gobestsoft.core.basic.ObjectMap;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.system.service.RepeatDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by duanmu on 2018/12/25.
 */

@Controller
@RequestMapping("/repeat")
public class RepeatDataController extends BaseController{

    private String PREFIX = "/repeatData/";

    @Autowired
    RepeatDataService repeatDataService;

    @Autowired
    DeptMemberMapper deptMemberMapper;

    @Autowired
    DeptOrganizationMapper deptOrganizationMapper;

    @RequestMapping("/memberList/{repeatType}")
    public String memberList(@PathVariable String repeatType, Model model){

        String path = "";
        if(ToolUtil.equals("1", repeatType)){
            path = "repeatData_member";
        } else if(ToolUtil.equals("2", repeatType)){
            path = "repeatData_register";
        } else if(ToolUtil.equals("3", repeatType)){
            path = "repeatData_organize";
        }
        model.addAttribute("pagetype", repeatType);
        return PREFIX + path;
    }


    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam("type") String type){
        Page page = defaultPage();
        ObjectMap map = ObjectMap.getInstance();

        List<Map> list = new ArrayList<>();
        if(ToolUtil.equals("1", type)){
            map.putRequest("query_mobile");
            map.putRequest("query_certificate_num");
            map.put("deptId",getLoginUser().getDeptId());
            list = deptMemberMapper.selectByCondition4Remove(page, map);
        } else if(ToolUtil.equals("2", type)){
            map.putRequest("query_deptname");
            list = deptOrganizationMapper.selectByRegRemove(page, map);
        } else if(ToolUtil.equals("3", type)){
            map.putRequest("query_deptname");
            map.putRequest("query_orgcode");
            list = deptOrganizationMapper.selectByOrgRemove(page, map);
        }

        page.setRecords(list);
        return super.packForBT(page);

    }

    @ResponseBody
    @RequestMapping("/delMember")
    public Object delMember(@RequestParam(required = true) Integer person_id){
        String uid = getLoginUser().getId();


        repeatDataService.delMember(person_id,uid);
        return success();
    }

    @RequestMapping("/delOrganize")
    @ResponseBody
    public Object delOrganize(@RequestParam("deptName") String deptName){

        String uid= getLoginUser().getId();
        repeatDataService.delOrganize(deptName, uid);
        return success();
    }

    @RequestMapping("/delOrganizeCode")
    @ResponseBody
    public Object delOrganizeCode(@RequestParam("deptId") String deptId){

        String uid = getLoginUser().getId();
        int info = repeatDataService.delOrganizeCode(deptId, uid);

        if(info == 2){
            return new Tip(201, "工会已经注册，暂时无法删除", null);
        }

        return success();
    }

}
