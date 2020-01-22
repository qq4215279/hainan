package com.gobestsoft.company.modular.dept;

import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.dept.MemberCardApplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 企业端：会员卡申请审核控制器
 * @author zhangdaowei
 */
@Controller
@RequestMapping("membercard/apply")
public class MemberCardApplyController extends BaseController {

	private String PREFIX = "dept/company/member/membercard/";

	@Resource
	MemberCardApplyService memberCardApplyService;

	/**
	 * 会员卡申请审核页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return PREFIX + "index";
	}

	/**
	 * 会员卡申请审核页多条件分页查询
	 * @param status
	 * @param name
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String status,@RequestParam(required = false) String name) {
//		Page<Map<String, Object>> page = this.defaultPage();
//		List<Map<String, Object>> result = memberCardApplyService.selectCompanyMemberCardApplyPageByCondition(page, status, name,getLoginUser().getDeptId());
//		page.setRecords(result);
//		return super.packForBT(page);
		return null;
	}

	/**
	 * 跳转到查看详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		LoginUser loginUser = getLoginUser();
		memberCardApplyService.selectCompanyMemberCardApplyById(id,model);
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
		memberCardApplyService.selectCompanyMemberCardApplyLogById(id,model);
		return PREFIX + "look_log";
	}

}
