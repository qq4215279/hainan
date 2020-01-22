package com.gobestsoft.mamage.moudle.legal.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalHistoryMapper;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalMapper;
import com.gobestsoft.common.modular.legal.model.DeptLegal;
import com.gobestsoft.common.modular.legal.model.DeptLegalApply;
import com.gobestsoft.common.modular.legal.model.DeptLegalHistory;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.legal.LegalApplyService;
import com.gobestsoft.mamage.moudle.legal.LegalChangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 法人资格变更登记控制器
 * @author lufengfan
 */
@Controller
@RequestMapping("legal/change")
public class LegalChangeController extends BaseController {
    @Resource
    private LegalChangeService legalChangeService;

    @Resource
    LegalApplyService legalApplyService;

    private String PREFIX = "/legal/change/";
    @Resource
    DeptLegalHistoryMapper deptLegalHistoryMapper;

    @Resource
    DeptLegalMapper deptLegalMapper;
    /**
     * 列表页
     *
     * @param model
     * @return
     */
    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "change_index";
    }

    @ResponseBody

    /**
     * 多条件分页查询
     * @param name
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String agentName, @RequestParam(required = false) String deptName) {
        Page<Map<String, Object>> page = this.defaultPage();
        List<Map<String, Object>> result = legalChangeService.selectChangeApplyPageByCondition(page, name, deptName, agentName
                , getLoginUser().getId());
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Model model, @RequestParam("id") Integer id) {
        DeptLegalApply deptLegalApply = legalChangeService.selectById(id);
        DeptLegalApply beforedeptLegalApply = legalChangeService.selectLegalApplyByDeptId(getLoginUser().getDeptId());
        model.addAttribute("beforedeptLegalApply",beforedeptLegalApply);
        model.addAttribute(deptLegalApply);
        DeptLegalHistory history = this.deptLegalHistoryMapper.selectOneByApplyId(id);
        model.addAttribute("history",history);
//        model.addAttribute("pName", getLoginUser().getDept().getParentDept().getDeptName());
        return PREFIX + "change_detail";
    }

    /**
     * 新增页
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //legalChangeService.bindLegalDataToModel(model,getLoginUser().getOrgId());
        DeptLegalApply deptLegalApply = legalChangeService.selectLegalApplyByDeptId(getLoginUser().getDeptId());
        model.addAttribute("deptLegalApply", deptLegalApply);
        model.addAttribute("pName", getLoginUser().getDept().getParentDept().getDeptName());
        return PREFIX + "change_add";
    }

    /**
     * 编辑页
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam("id") Integer id) {

//        DeptLegalApply beforedeptLegalApply = legalChangeService.selectLegalApplyByDeptId(getLoginUser().getDeptId());
        DeptLegalApply deptLegalApply = legalChangeService.selectById(id);
//        model.addAttribute("beforedeptLegalApply",beforedeptLegalApply);
       model.addAttribute(deptLegalApply);
        DeptLegalHistory history = this.deptLegalHistoryMapper.selectOneByApplyId(id);
        model.addAttribute("history",history);
        return PREFIX + "change_edit";
    }
    /**
     * 数据保存方法
     *
     * @param model
     * @param deptLegalApply
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Tip save(Model model, DeptLegalApply deptLegalApply) {

        LoginUser loginUser = getLoginUser();

        if(loginUser.getDept().getLevel()==1){
            return fail("当前用户没有上级部门");
        }

        if (ToolUtil.isEmpty(deptLegalApply.getId())) {//插入方法
            legalChangeService.insertByDeptLegalApply(deptLegalApply, getLoginUser());
        } else {//更新方法
            legalChangeService.updateByDeptLegalApply(deptLegalApply, getLoginUser());
        }
        return this.success();
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
        DeptLegalApply deptLegalApply = legalChangeService.selectById(id);
        if (ToolUtil.isEmpty(deptLegalApply)) {
            return this.tip(500, "数据不存在", id);
        }
        legalChangeService.remove(id);
        return this.success();
    }
    /**
     * 查看流程页
     * @param model
     * @return
     */
    @RequestMapping("/log")
    public String log(Model model,@RequestParam("id") Integer id) {
        //绑定审核日志信息
        legalChangeService.setLogDataToModel(model,id);
        return PREFIX + "change_log";
    }

    /**
     * 审核页
     * @param model
     * @return
     */
    @RequestMapping("/check")
    public String check(Model model) {
        return PREFIX + "change_check_index";
    }

    /**
     * 审核页多条件分页查询
     * @param name
     * @param agentName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkList")
    public Object checkList(@RequestParam(required = false) String name,@RequestParam(required = false) String agentName,@RequestParam(required = false) String deptName) {
        Page<Map<String, Object>> page = this.defaultPage();
        List<Map<String, Object>> result = legalChangeService.selectApplyCheckPageByCondition(page,name,null,agentName
                ,getLoginUser().getDeptId());
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 审核详情页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/checkDetail")
    public String checkDetail(Model model,@RequestParam("id") Integer id) {
        DeptLegalApply deptLegalApply = legalChangeService.selectById(id);
        DeptLegalApply beforedeptLegalApply = legalChangeService.selectLegalApplyByDeptId(getLoginUser().getDeptId());
        model.addAttribute("beforedeptLegalApply",beforedeptLegalApply);
        model.addAttribute(deptLegalApply);
//        model.addAttribute("pName",getLoginUser().getDept().getParentDept()==null?"":getLoginUser().getDept().getParentDept().getDeptName());
        DeptLegalHistory history = this.deptLegalHistoryMapper.selectOneByApplyId(id);
        model.addAttribute("history",history);
        return PREFIX + "change_check_detail";
    }
    /**
     * 审核方法
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkSubmit")
    public Tip checkSubmit(@RequestParam() Integer id
            ,@RequestParam() String logStatus
            ,@RequestParam() String checkOpinion){

        DeptLegalApply deptLegalApply = legalApplyService.selectById(id);
        if(deptLegalApply==null){
            return fail("记录不存在");
        }
        List<DeptLegal> deptLegals = deptLegalMapper.selectDeptLegalByDeptId(deptLegalApply.getDeptId());
        if(deptLegals==null || deptLegals.size()==0){
            return fail("法人已注销，不能通过审核");
        }

        legalChangeService.checkSubmit(id,logStatus,checkOpinion,getLoginUser());
        return this.success();
    }

    /**
     * 新增申请前的校验
     * @return
     */
    @RequestMapping("/checkAddApply")
    @ResponseBody
    public Tip checkAddApply(){
        LoginUser loginUser = getLoginUser();
        if(loginUser.getDept().getLevel()==1)return fail("没有上级审核机构，无法创建新的申请");
        Integer deptId = loginUser.getDeptId();
        List<DeptLegal> deptLegals = deptLegalMapper.selectDeptLegalByDeptId(deptId);
        if(deptLegals==null || deptLegals.size()==0)return fail("还未申请过法人，无法继续当前操作");
        Tip tip = legalApplyService.checkCreateOtherApply(deptId,1);
        return tip;
    }

}


//
//    @RequestMapping("/list")
//   @ResponseBody
//    public Object list(@RequestParam( required = false) String name){
//        Page<DeptLegal> page = this.defaultPage();
//
//        List<DeptLegal> list = legalChangeService.selectListPage(page,name);
//        page.setRecords(list);
//        return super.packForBT(page);
//
//
//
//}
