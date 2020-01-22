package com.gobestsoft.mamage.moudle.dept.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.dept.CompanyOrganizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 申请建会审核
 * @author zhandgaowei
 * @date 2018年08月30日 19点00
 */

@Controller
@RequestMapping("organization/check")
public class CompanyOrganizationController extends BaseController {

	private String PREFIX = "/dept/organization/company/check/";
	@Resource
	private CompanyOrganizationService companyOrganizationService;

	/**
	 * 查看审核流程
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
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String detailOrganization(@RequestParam() Integer id, Model model){
		DeptOrganization  deptOrganization= companyOrganizationService.selectById(id);
		model.addAttribute("deptOrganization", deptOrganization);
		//绑定字典项的名称
		companyOrganizationService.bindDictNameToDeptOrganization(deptOrganization);
		return PREFIX + "organization_detail";
	}

	/**
	 * 申请建会审核首页
	 * @return
	 */
	@RequestMapping("/audit")
	public String audit() {
		return PREFIX + "organization_audit_index";
	}

	/**
	 * 申请建会审核多条件分页查询
	 * @param status
	 * @param unitName
	 * @return
	 */
	@RequestMapping("/auditList")
	@ResponseBody
	public Object list(@RequestParam(required = false) Integer status
			, @RequestParam(required = false) String unitName) {
		Page<DeptOrganization> page = this.defaultPage();
		List<DeptOrganization> result = companyOrganizationService.selectAuditOrganizationPage(page
				,status,unitName,getLoginUser().getDeptId());
	    page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 申请建会审核详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/openAudit/{id}")
	public String openAudit(@PathVariable("id") Integer id, Model model) {
		DeptOrganization deptOrganization = companyOrganizationService.selectById(id);
		model.addAttribute("deptOrganization", deptOrganization);
		//绑定字典项的名称
		companyOrganizationService.bindDictNameToDeptOrganization(deptOrganization);
		return PREFIX + "organization_audit_add";
	}

	/**
	 * 申请建会审核通过、审核不通过方法
	 * @param id
	 * @param status
	 * @param reason
	 * @return
	 */
	@RequestMapping("/saveAuthen")
	@ResponseBody
	public Tip saveAuthen(@RequestParam() Integer id,@RequestParam() Integer status,@RequestParam() String reason) {
		companyOrganizationService.saveAuthen(id, status,reason,getLoginUser());
		return success();
	}

	/**
	 * 完善信息审核首页
	 * @return
	 */
	@RequestMapping("/perfectCheck")
	public String perfectCheck() {
		return PREFIX + "organization_perfectcheck_index";
	}

	/**
	 * 完善信息审核多条件分页查询
	 * @param perfectInformation
	 * @param unitName
	 * @return
	 */
	@RequestMapping("/auditPerFectList")
	@ResponseBody
	public Object auditPerFectList(@RequestParam(required = false) String perfectInformation
			, @RequestParam(required = false) String unitName) {
		Page<DeptOrganization> page = this.defaultPage();
		List<DeptOrganization> result = companyOrganizationService.auditPerFectListPage(page
				,unitName,getLoginUser().getDeptId());
	    page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 完善信息审核详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/openPerFectAudit/{id}")
	public String openPerFectAudit(@PathVariable("id") Integer id, Model model) {
		DeptOrganization deptOrganization = companyOrganizationService.selectById(id);
		model.addAttribute("deptOrganization", deptOrganization);
		//绑定字典项的名称
		companyOrganizationService.bindDictNameToDeptOrganization(deptOrganization);
		return PREFIX + "organization_perfectaudit_add";
	}

	/**
	 * 完善信息审核通过、审核不通过方法
	 * @param id
	 * @param reason
	 * @return
	 */
	@RequestMapping("/savePerFectAuthen")
	@ResponseBody
	public Tip savePerFectAuthen(@RequestParam() Integer id,@RequestParam() Integer status,@RequestParam() String reason) {
		companyOrganizationService.savePerFectAuthen(id, status,reason,getLoginUser());
		return success();
	}

}
