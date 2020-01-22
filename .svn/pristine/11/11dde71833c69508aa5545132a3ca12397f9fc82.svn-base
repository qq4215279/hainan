package com.gobestsoft.admin.modular.statistics.controller;

import com.gobestsoft.admin.modular.statistics.service.EducationService;
import com.gobestsoft.common.modular.statistics.model.SysEducationCount;
import com.gobestsoft.common.modular.statistics.model.SysEducationKey;
import com.gobestsoft.mamage.basic.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 教育统计
 */
@Controller
@RequestMapping("/statistics")
public class EducationController extends BaseController {

    private final String PREFIX = "/statistics/";

    @Resource
    private EducationService educationService;

    /**
     * 跳转页面 -- index/detail
     */
    @RequestMapping("education")
    public String page(Model model){
        Map<String,Object> map1 = new LinkedHashMap<>();

        List<SysEducationKey> list = educationService.getKeyName(0);
        for(int i=0; i<list.size(); i++){

            String name1 = list.get(i).getName();
            Integer p1 = list.get(i).getId();

            Map<String,Object> map2 = new LinkedHashMap<>();
            List<SysEducationKey> list2 = educationService.getKeyName(p1);
            for(int j=0; j<list2.size(); j++){

                Integer p2 = list2.get(j).getId();
                String name2 = list2.get(j).getName();

                Map<String,Integer> map3 = new LinkedHashMap<>();
                List<SysEducationKey> list3 = educationService.getKeyName(p2);
                for(int k=0; k<list3.size(); k++){
                    Integer keyId = list3.get(k).getId();
                    String name3 = list3.get(k).getName();
                    SysEducationCount count = educationService.getKeyCount(keyId);
                    map3.put(name3, count.getCountNum());

                }
                map2.put(name2, map3);

            }
            map1.put(name1, map2);

        }

        model.addAttribute("map", map1);
        return PREFIX + "education/index";
    }

}
