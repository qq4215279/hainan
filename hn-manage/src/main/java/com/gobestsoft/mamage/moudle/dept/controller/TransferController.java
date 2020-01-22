package com.gobestsoft.mamage.moudle.dept.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.moudle.dept.AdmissionApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/10/31 下午8:46
 */
@Controller
@RequestMapping("/transfer")
@Slf4j
public class TransferController extends BaseController {

    private String PREFIX = "dept/company/member/admission/";

    @Resource
    AdmissionApplyService admissionApplyService;


    /**
     * 转出审核页
     * @param model
     * @param type 1：转出审核  2：转入审核
     * @return
     */
    @RequestMapping("/turnIndex")
    public String turnIndex(@RequestParam("type") String type, Model model) {

        String pageName = "";
        if(ToolUtil.equals("1", type)){
            model.addAttribute("type", type);
            pageName = "transfer";
        } else if(ToolUtil.equals("2", type)){
            model.addAttribute("type", type);
            pageName = "transfer_into";
        }

        return PREFIX + pageName;

    }


    /**
     * 转出审核页多条件分页查询
     * @param status
     * @param name
     * @param type 1：转出审核  2：转入审核
     * @return
     */
    @RequestMapping("/turnOutList")
    @ResponseBody
    public Object turnOutList(@RequestParam(required = false) String status,
                              @RequestParam(required = false) String name,
                              @RequestParam("type") String type) {
        Page<Map<String, Object>> page = this.defaultPage();
        List<Map<String, Object>> result = new ArrayList<>();
        if(ToolUtil.equals("1", type)){
            result = admissionApplyService.selectCheckByCondition(page, status, getLoginUser().getDeptId(), name);
        }

        if(ToolUtil.equals("2", type)){
            result = admissionApplyService.selectSecondCheckByCondition(page, status, getLoginUser().getDeptId(), name);
        }
        page.setRecords(result);
        return super.packForBT(page);
    }


    /**
     * 转出审核
     *
     * @param id
     * @param checkDesc
     * @param status
     * @return
     */
    @RequestMapping(value = "/outApproval")
    @ResponseBody
    public Object outApproval(@RequestParam() Integer id, @RequestParam() String checkDesc,
                              @RequestParam() String status) {
        admissionApplyService.updateTransferLogStatusById(id, checkDesc, status, getLoginUser());
        return success();
    }

    /**
     * 转入审核
     *
     * @param id
     * @param checkDesc
     * @param status
     * @return
     */
    @RequestMapping(value = "/inApproval")
    @ResponseBody
    public Object inApproval(@RequestParam() Integer id, @RequestParam() String checkDesc,
                              @RequestParam() String status) {
        admissionApplyService.updateInStatusById(id, checkDesc, status, getLoginUser());
        return success();
    }

}
