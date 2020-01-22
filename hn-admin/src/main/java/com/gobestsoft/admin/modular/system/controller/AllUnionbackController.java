package com.gobestsoft.admin.modular.system.controller;

import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.system.service.BlackboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/allunionback")
public class AllUnionbackController extends BaseController {

    @Autowired
    private BlackboardService blackboardService;


    @RequestMapping("")
    public String getReturnHome(Model model){

        LoginUser user = getLoginUser();
        Integer deptId = user.getDeptId();
        return "allUnionBack";
    }

    /**
     * 省总下级具体工会返乡返乡统计
     */
    @RequestMapping("/getAllRehomeStatback")
    @ResponseBody
    public Tip getAllRehomeStat(@RequestParam(value = "deptId", required = false) String deptId){

        Map<String, Object> result = new HashMap<>();
        Map<String,Object> resultinfo = new HashMap<>();

        if(ToolUtil.isEmpty(deptId)){
            resultinfo = blackboardService.szgzUnderUnionsTop10();
        } else {
            resultinfo = blackboardService.szghAllRehomeStat(deptId);
        }
        result.put("allRehomeStat", resultinfo);

        return new Tip(200,null,result);
    }

}
