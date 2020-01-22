package com.gobestsoft.mamage.moudle.system.controller;

import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.model.Dept;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/allunionrehome")
public class AllUnionRehomeController extends BaseController {

    @Autowired
    private BlackboardService blackboardService;

    @Autowired
    private DeptMapper deptMapper;


    @RequestMapping("")
    public String getReturnHome(Model model){

        String roleflg = "0";
        LoginUser user = getLoginUser();
        List<Integer> listInfo = user.getRoleIds();
        for(Integer index: listInfo){
            if(index == 1){
                roleflg = "1";
                break;
            }
        }

        Dept dept = deptMapper.selectById(user.getDeptId());
        String deptname = dept.getSimplename();

        model.addAttribute("deptname", deptname);
        model.addAttribute("deptId", user.getDeptId());
        model.addAttribute("roleflg", roleflg);
        return "allUnionRehome";
    }

    /**
     * 省总下级具体工会返乡返乡统计
     */
    @RequestMapping("/getAllRehomeStat")
    @ResponseBody
    public Tip getAllRehomeStat(@RequestParam(value = "deptId", required = false) String deptId){

        Map<String, Object> result = new HashMap<>();
        Map<String,Object> resultinfo = new HashMap<>();



        if(ToolUtil.isEmpty(deptId) || ToolUtil.equals("1", deptId)){
            resultinfo = blackboardService.szgzUnderUnionsTop10();
            Integer totalcnt = blackboardService.getTotalCnt();
            result.put("totalcnt", totalcnt);
            result.put("heightFlg", "1");
        } else {
            resultinfo = blackboardService.szghAllRehomeStat(deptId);
            Integer allTotalCnt = blackboardService.getAllTotalCnt(deptId);
            if(allTotalCnt == null){
                allTotalCnt = 0;
            }
            Integer unionbyCnt = blackboardService.getUnionbyCnt(deptId);

            if(0 <= unionbyCnt && unionbyCnt <= 30 ){
                result.put("heightFlg", "1");
            } else if(30 < unionbyCnt && unionbyCnt<= 60){
                result.put("heightFlg", "2");
            } else if(60 < unionbyCnt && unionbyCnt<= 100){
                result.put("heightFlg", "3");
            }

            result.put("totalcnt", allTotalCnt);
        }
        result.put("allRehomeStat", resultinfo);

        return new Tip(200,null,result);
    }

}
