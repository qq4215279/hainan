package com.gobestsoft.company.modular.dept;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.moudle.dept.AppMemberApplyService;

/**
 * 企业端：会员转会审核控制器
 * @author zhangdaowei
 * @Date 2018年08月23日
 */
@Controller
@Slf4j
@RequestMapping("/approve/transfer")
public class TransferApplyController extends BaseController {

	private String PREFIX = "dept/company/member/transfer/";

	@Resource
	AppMemberApplyService appMemberService;

	/**
	 * 跳转到转会审核页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		return PREFIX + "index";
	}

	/**
	 * 转会审核页多条件分页查询
	 * @param createTime
	 *            申请时间
	 * @param status
	 *            审批状态
	 * @param name
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String createTime, @RequestParam(required = false) String status,
			@RequestParam(required = false) String name) {
		Page<Map<String, Object>> page = this.defaultPage();
		List<Map<String, Object>> result = appMemberService.selectByCondition(page, status, createTime,
				getLoginUser().getDeptId(), name,true);
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 跳转到转会查看详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		// 绑定数据至Model
		appMemberService.bindMemberApplyDataToModel(id, model);
		return PREFIX + "detail";
	}

	/**
	 * 跳转到转会查看流程页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/log/{id}")
	public String lookLog(@PathVariable("id") Integer id, Model model) {
		// 绑定审核日志的数据至Model
		appMemberService.bindMemberApplyLogDataToModel(id, model);
		return PREFIX + "look_log";
	}

	/**
	 * 入会、转会、办卡审核通过与不通过
	 * @param id
	 * @param checkDesc
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/approval")
	@ResponseBody
	public Object approval(@RequestParam() Integer id, @RequestParam() String checkDesc,
			@RequestParam() String status) {
		//appMemberService.approveBatch(id, checkDesc, status, getLoginUser());
		return this.success();
	}
	
}
