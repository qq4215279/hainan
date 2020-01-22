package com.gobestsoft.admin.modular.opinion.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.admin.modular.opinion.service.OpinionService;
import com.gobestsoft.common.modular.opinion.model.OpinionLog;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 建言献策
 */
@Controller
@RequestMapping("/opinion")
public class OpinionController extends BaseController {

    private final String PREFIX = "/opinion/";

    @Resource
    private OpinionService opinionService;

    /**
     * 跳转页面 -- index/detail
     */
    @RequestMapping("{page}")
    public String page(@PathVariable String page, @RequestParam(required = false)Integer id, Model model){
        if(ToolUtil.isNotEmpty(id)){
            model.addAttribute("pojo",opinionService.getDetailById(id));
            model.addAttribute("replyContent", opinionService.getReplyContent(id, getLoginUser().getDeptId()));
        }
        return PREFIX + page;
    }

    /**
     * 跳转页面 -- log
     */
    @RequestMapping("log/{id}")
    public String page(@PathVariable Integer id, Model model){
        model.addAttribute("list",opinionService.getLogByLogId(id));
        return PREFIX + "log";
    }

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "begDate", required = false) String startTime,
                       @RequestParam(value = "endDate", required = false) String endTime,
                       @RequestParam(value = "type", required = false) Integer type,
                       @RequestParam(value = "status", required = false) Integer status

                       ) {
        if (StringUtils.isNotEmpty(startTime)) {
            startTime = DateUtil.parseAndFormat(startTime, "yyyy-MM-dd hh:mm:ss", "yyyyMMdd")+"000000";
        }
        if (StringUtils.isNotEmpty(endTime)) {
            endTime = DateUtil.parseAndFormat(endTime, "yyyy-MM-dd hh:mm:ss", "yyyyMMdd")+"235959";
        }
        Page<Map<String, String>> page = defaultPage();
        page.setRecords(opinionService.selectByCondition(page, startTime, endTime,type,status,getLoginUser().getDeptId()));
        return packForBT(page);
    }

    /**
     * 审核
     */
    @RequestMapping("reply")
    @ResponseBody
    public Tip reply(OpinionLog pojo){
        try {
            opinionService.reply(pojo,getLoginUser());
        }catch (Exception e){
            return fail();
        }
        return success();
    }

}
