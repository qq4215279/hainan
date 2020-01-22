package com.gobestsoft.admin.modular.system.controller;


import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.system.service.BlackboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/returnhome")
public class ReturnHomeController extends BaseController {

    @Autowired
    private BlackboardService blackboardService;

    @RequestMapping("")
    public String getReturnHome(Model model){

        Integer totalcnt = blackboardService.getTotalCnt();
        model.addAttribute("totalcnt", totalcnt);
        return "returnhome";
    }


    /**
     * 返乡统计
     */
    @RequestMapping("/getRehomeStatistics")
    @ResponseBody
    public Tip getRehomeStatistics(){

        Map<String, Object> result = new HashMap<>();
        result.put("szghRehomeTop10", blackboardService.szghRehomeUnionsTop10());
        result.put("szgzUnderUnionsTop10", blackboardService.szgzUnderUnionsTop10());

        return new Tip(200,null,result);
    }

}
