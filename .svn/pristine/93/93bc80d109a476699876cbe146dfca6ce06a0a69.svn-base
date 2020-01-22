package com.gobestsoft.mamage.moudle.dept.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyLogMapper;
import com.gobestsoft.common.modular.dept.model.DeptMemberApply;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.dept.AdmissionApplyService;
import com.gobestsoft.mamage.moudle.law.service.LawSupportService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 企业端：入会审核控制器
 * @author zhangdaowei
 */
@Controller
@RequestMapping("admission/apply")
public class AdmissionApplyController extends BaseController {

	private String PREFIX = "dept/company/member/admission/";

	@Autowired
	private LawSupportService supportService;

	@Resource
	AdmissionApplyService admissionApplyService;

	@Autowired
	DeptMemberApplyLogMapper deptMemberApplyLogMapper;

	/**
	 * 入会审核页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return PREFIX + "index";
	}

	/**
	 * 入会审核页多条件分页查询
	 * @param status
	 * @param name
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String status,@RequestParam(required = false) String name) {
		Page<Map<String, Object>> page = this.defaultPage();
		List<Map<String, Object>> result = admissionApplyService.selectCompanyAdmissionApplyPageByCondition(page, status, name,getLoginUser().getDeptId());
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 跳转到查看详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		admissionApplyService.selectCompanyAdmissionApplyById(id,model);
		Map<String, Object> stringObjectMap = model.asMap();
		
		Map map = deptMemberApplyLogMapper.selectLastLogByDept(id, getLoginUser().getDeptId());
		DeptMemberApply  apply = (DeptMemberApply) stringObjectMap.get("apply");
		if(map!=null&& apply!=null){
			Object apply_status = map.get("apply_status");
			if(apply_status!=null &&Integer.valueOf(apply_status+"")==2){
				apply.setApplyStatus("0");
				model.addAttribute("apply",apply);
			}
		}
		return PREFIX + "detail";
	}

	/**
	 * 跳转到查看流程页
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/log/{id}")
	public String lookLog(@PathVariable("id") Integer id, Model model) {
		admissionApplyService.selectCompanyAdmissionApplyLogById(id,model);
		return PREFIX + "look_log";
	}

	/**
	 * 入会审核通过与不通过
	 * @param id
	 * @param checkDesc
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/approval")
	@ResponseBody
	public Object approval(@RequestParam() Integer id, @RequestParam() String checkDesc,
			@RequestParam() String status) {
		admissionApplyService.updateCompanyAdmissionApplyStatusById(id, checkDesc, status, getLoginUser());
		return this.success();
	}

	/**
	 * 入会转办页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/transfer_audit")
	public String transfer_audit(Model model,@RequestParam(value = "id", required = true) Integer id) {

		LoginUser user = getLoginUser();

		Integer deptId = user.getDeptId();

		Integer pid =null;

		String pname = null;

		if(user.getDept().getParentDept()!=null){
			pid = user.getDept().getPId();
			pname = user.getDept().getParentDept().getDeptName();
		}

		model.addAttribute("deptId", deptId);
		model.addAttribute("pid", pid);
		model.addAttribute("pname",pname );

		List<Map> sons = supportService.selectSonDept(deptId);
		model.addAttribute("sons",sons);
		model.addAttribute("id", id);
		String type = getPara("type");
		model.addAttribute("type",type);
		if(pid==null&&(sons==null||sons.size()==0)){
			model.addAttribute("msg", "没有可转办组织，不能进行转办！");
			model.addAttribute("notTransfer", "1");
		}

		return PREFIX + "transfer_audit";
	}

	@ResponseBody
	@RequestMapping("/transferSubmit")
	public Tip transferSubmit(
			@RequestParam(required = true) Integer id
			,@RequestParam(required = false) String transferReason
			,@RequestParam(required = true) Integer deptId
	){

		if(deptId==null){
			throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
		}

		return admissionApplyService.updateTransfer(id,deptId,transferReason);

	}

}
