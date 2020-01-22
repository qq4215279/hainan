package com.gobestsoft.mamage.moudle.law.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.law.model.LawCommonProblem;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.law.service.LawCommonProblemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * create
 * on 2018/11/1 下午1.11
 */

@Controller
@RequestMapping("/law/problem")
public class LawCommonProblemController extends BaseController {
    private String PREFIX = "/law/problem/";

    @Resource
    LawCommonProblemService lawCommonProblemService;

    /*
    列表页
     */
    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "problem_index";
    }
    /**
     * 多条件分页查询
     * @param title
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        Page<Map<String, Object>> page = this.defaultPage();
        List<Map<String, Object>> result = lawCommonProblemService.selectLawCommonProblem(page);
        page.setRecords(result);
        return super.packForBT(page);
    }


    /**
     * 新增页
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
        return PREFIX + "problem_add";
    }
    /**
     * 数据保存方法
     * @param model
     * @param deptLegalApply
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Tip save(Model model, LawCommonProblem lawCommonProblem){

        LoginUser loginUser = getLoginUser();
//
//        if(loginUser.getDept().getLevel()==1){
//            return fail("当前用户没有上级部门");
//        }

            if(ToolUtil.isEmpty(lawCommonProblem.getId())){//插入方法

            lawCommonProblemService.insertByLawCommonProblem(lawCommonProblem);
        }else{//更新方法
            lawCommonProblemService.updateByLawCommonProblem(lawCommonProblem);
        }
        return this.success();
    }


    /**
     * 详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Model model, @RequestParam("id") Integer id) {
        LawCommonProblem lawCommonProblem = lawCommonProblemService.selectById(id);

        model.addAttribute(lawCommonProblem);
//        model.addAttribute("pName", getLoginUser().getDept().getParentDept().getDeptName());
        return PREFIX + "problem_detail";
    }

    /**
     * 编辑页
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam("id") Integer id) {

        LawCommonProblem lawCommonProblem = lawCommonProblemService.selectById(id);
        model.addAttribute(lawCommonProblem);
        return PREFIX + "problem_edit";
    }

    /**
     * 删除方法
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/remove")
    public Tip remove(@RequestParam("id") Integer id) {
        LawCommonProblem lawCommonProblem = lawCommonProblemService.selectById(id);
        if (ToolUtil.isEmpty(lawCommonProblem)) {
            return this.tip(500, "数据不存在", id);
        }
        lawCommonProblemService.remove(id);
        return this.success();
    }
}

