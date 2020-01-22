package com.gobestsoft.mamage.moudle.dept.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.dept.CompanyOrganizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 网上建会
 *
 * @author zhandgaowei
 * @date 2018年08月15日 16点44
 */

@Controller
@RequestMapping("/company/organization")
public class OrganizationController extends BaseController {

    private String PREFIX = "/dept/company/organization/authen/";
    @Resource
    private CompanyOrganizationService companyOrganizationService;

    /**
     * 申请建会/我的工会 首页
     *
     * @param model
     * @param type  1：申请建会；2：我的工会
     * @return
     */
    @RequestMapping("/index")
    public String companyBuild(Model model, Integer type) {
        Integer num = companyOrganizationService.selectCountByUid(getLoginUser().getId());
        model.addAttribute("num", num);
        if (type == 1) {
            return PREFIX + "organization_index";
        }
        return PREFIX + "my_organization_index";
    }

    /**
     * 申请建会多条件分页查询
     *
     * @param unitName
     * @param unitOrgCode
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object companyList(@RequestParam(required = false) String unitName, @RequestParam(required = false) String unitOrgCode) {
        Page<DeptOrganization> page = this.defaultPage();
        List<DeptOrganization> result = new ArrayList<>();
        if (getLoginUser().getDept() != null) {
            result = companyOrganizationService.getDeptOrganizationByDeptId(getLoginUser().getDeptId());
        } else {
            result = companyOrganizationService.selectBuildPageByUnitNameAndCode(unitName, unitOrgCode, getLoginUser().getId());
        }
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 申请建会
     *
     * @param model
     * @param id    值存在，则表示当前是通过点击TableToolbar上面的申请建会按钮进入的
     * @return
     */
    @RequestMapping("/buildEdit")
    public String buildEdit(Model model, @RequestParam() Integer id) {
        DeptOrganization deptOrganization = companyOrganizationService.selectById(id);
        model.addAttribute("deptOrganization", deptOrganization);
        return PREFIX + "organization_edit";
    }

    /**
     * 申请建会提交
     *
     * @param deptOrganization
     * @param result
     * @return
     */
    @RequestMapping("/saveBuild")
    @ResponseBody
    public Tip saveBuild(@Valid DeptOrganization deptOrganization, BindingResult result) {
        if (result.hasErrors()) {
            return this.tip(500, "请求参数错误", null);
        }
        if (!companyOrganizationService.isTheBasicDept(deptOrganization.getPId())) {
            return tip(500, "上级工会是基层工会不能做为上级工会", null);
        }
        if (companyOrganizationService.existsOthersOrgCode(deptOrganization.getOthersOrgCode(), deptOrganization.getId())) {
            return tip(500, "法人和其他组织统一社会信用代码已存在", null);
        }
        companyOrganizationService.saveBuild(deptOrganization, getLoginUser());
        return success();
    }

    /**
     * 查看审核流程
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/log/{id}")
    public String log(@PathVariable("id") Integer id, Model model) {
        List<Map<String, Object>> log = companyOrganizationService.log(id);
        model.addAttribute("logList", log);
        return PREFIX + "organization_log";
    }

    /**
     * 查看详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/detail")
    public String detailOrganization(@RequestParam(value = "id", required = false) Integer id, Model model) {
        DeptOrganization deptOrganization = null;
        if (id != null) {
            deptOrganization = companyOrganizationService.selectById(id);
            model.addAttribute("showCancel", true);

        } else {
            deptOrganization = companyOrganizationService.selectByDeptId(getLoginUser().getDeptId());
            model.addAttribute("showCancel", false);

        }
        model.addAttribute("deptOrganization", deptOrganization);
        //绑定字典项的名称
        companyOrganizationService.bindDictNameToDeptOrganization(deptOrganization);
        return PREFIX + "organization_detail";
    }

    /**
     * 完善信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/reEdit")
    public String reEdit(@RequestParam(value = "id", required = false) Integer id, Model model) {
        DeptOrganization deptOrganization = null;
        if (id != null) {
            deptOrganization = companyOrganizationService.selectById(id);
            model.addAttribute("showCancel", true);
        } else {
            deptOrganization = companyOrganizationService.selectByDeptId(getLoginUser().getDeptId());
            model.addAttribute("showCancel", false);
        }
        model.addAttribute("deptOrganization", deptOrganization);
        return PREFIX + "organization_reEdit";
    }

    /**
     * 完善信息提交
     *
     * @param deptOrganization
     * @param result
     * @return
     */
    @RequestMapping("/reEditSaveBuild")
    @ResponseBody
    public Tip reEditSaveBuild(@Valid DeptOrganization deptOrganization, BindingResult result) {
        if (result.hasErrors()) {
            return this.tip(500, "请求参数错误", null);
        }
        companyOrganizationService.reEditSaveBuild(deptOrganization, getLoginUser());
        return success();
    }

}
