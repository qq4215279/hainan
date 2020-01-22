package com.gobestsoft.admin.modular.app.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.AppInviteDept;
import com.gobestsoft.common.modular.dept.model.PersonInfo;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.dept.service.AppInviteDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 入会邀请
 * @author xiat
 * @time 2018-11-14 16:19:22
 */
@Controller
@RequestMapping("/app/invite")
@Slf4j
public class AppInviteDeptController extends BaseController {

    private String PREFIX = "/dept/invite/";

    @Autowired
    private AppInviteDeptService appInviteDeptService;

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){

        return PREFIX +"index";
    }

    /**
     * 多条件查询列表
     * @param unitName
     * @param begDate
     * @param endDate
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false,value = "unitName") String unitName,
                       @RequestParam(required =false,value="begDate") String begDate,
                       @RequestParam(required = false,value = "endDate") String endDate
                       ){
        LoginUser loginUser=getLoginUser();
        Integer deptId = loginUser.getDeptId();
        String userId=loginUser.getId();
        Page<Map<String, Object>> page=defaultPage();
        String formatBegDate = DateUtil.parseAndFormat(begDate, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        String formatEndDate = DateUtil.parseAndFormat(endDate,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss");
        List<Map<String, Object>> result=appInviteDeptService.getSelectAppInviteDateList(page,unitName,formatBegDate,formatEndDate,deptId,userId);
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 查看本单位下的所有会员信息
     * @param unit_name
     * @param model
     * @return
     */
    @RequestMapping("/detail/{unit_name}")
    public String detail(@PathVariable("unit_name") String unit_name,Model model){
        model.addAttribute("unitName",unit_name);

        return PREFIX+"detail";

    }

    @RequestMapping("/detailList/{unitName}")
    @ResponseBody
    public Object detailList(@PathVariable("unitName") String unitName,
                             HttpServletRequest request,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String memberCard,
                             @RequestParam(required = false) Integer deptId,
                             @RequestParam(required = false) String memberRange){
        Page<Map<String, Object>> page=defaultPage();
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        if(ToolUtil.isEmpty(deptId)){
            deptId=getLoginUser().getDeptId();
        }
        ObjectMap map= ObjectMap.getInstance();
        map.putRequest("query_sex");
        map.putRequest("query_ethnicGroup");
        map.putRequest("query_workSituation");
        map.putRequest("query_certificateType");
        map.putRequest("query_certificateNum");
        map.putRequest("query_education");
        map.putRequest("query_technologyLevel");
        map.putRequest("query_mobile");
        map.putRequest("query_household");
        map.putRequest("query_domicile");
        map.putRequest("query_memberChange");
        if(ToolUtil.isNotEmpty(request.getParameter("query_memberChangeDate_start"))){
            map.put("query_memberChangeDate_start", DateUtil.parseAndFormat(request.getParameter("query_memberChangeDate_start"), "yyyy-MM-dd", "yyyyMMdd"));
        } else {
            map.putRequest("query_memberChangeDate_start");
        }

        if(ToolUtil.isNotEmpty(request.getParameter("query_memberChangeDate_end"))){
            map.put("query_memberChangeDate_end", DateUtil.parseAndFormat(request.getParameter("query_memberChangeDate_end"), "yyyy-MM-dd", "yyyyMMdd"));
        } else {
            map.putRequest("query_memberChangeDate_end");
        }

        map.putRequest("query_politicalStatus");
        map.putRequest("query_workUnit");
        map.putRequest("query_nativePlace");
        map.putRequest("query_farmer_flag");
        map.putRequest("query_certified_member");

        List<Map<String, Object>> result=appInviteDeptService.getAppInviteDeptDataToModel(page,unitName, name, memberCard, deptId, memberRange, map);
        for (Map<String, Object> m : result) {

            Object changeDate = m.get("member_change_date");
            if (changeDate != null && !StringUtils.isEmpty(changeDate.toString())) {
                m.put("memberChangeDate", DateUtil.parseAndFormat(m.get("member_change_date").toString(), "yyyyMMdd", "yyyy/MM/dd"));
            }
        }
        page.setRecords(result);
        return super.packForBT(page);
    }
}
