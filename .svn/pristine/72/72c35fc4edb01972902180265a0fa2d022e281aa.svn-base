package com.gobestsoft.admin.modular.dept.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyLogMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberTransferLogMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberTransferMapper;
import com.gobestsoft.common.modular.dept.model.DeptMemberApply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * app用户转会审核控制器
 * 
 * @author zhangdaowei
 * @Date 2018年08月22日
 */
@Controller
@RequestMapping("/approve/member")
@Slf4j
public class AppMemberApplyController extends BaseController {

	private String PREFIX = "dept/member/approve/";

	@Resource
	AppMemberApplyService appMemberService;

	@Resource
	DeptMemberTransferMapper deptMemberTransferMapper;
	@Resource
	DeptMemberTransferLogMapper deptMemberTransferLogMapper;
	@Autowired
	DeptMemberApplyLogMapper deptMemberApplyLogMapper;

	/**
	 * 跳转到转会审核页
	 * 
	 * @param model
	 * @param applyType
	 *            申请类型 1:入会；2:转会；3:办卡
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model, String applyType) {
		model.addAttribute("applyType", applyType);
		return PREFIX + "index";
	}

	/**
	 * 转会（第二级审核）审核页多条件分页查询
	 * 
	 * @param createTime
	 *            申请时间
	 * @param status
	 *            审批状态
	 * @param applyType
	 *            申请类型
	 * @param name
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String createTime, @RequestParam(required = false) String status,
			@RequestParam(required = false) String name, @RequestParam() Integer applyType) {
		Page<Map<String, Object>> page = this.defaultPage();
		List<Map<String, Object>> result = appMemberService.selectByCondition(page, status, createTime,
				getLoginUser().getDeptId(), name,false);
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 跳转到查看详情页
	 * 
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
	 * 跳转到查看流程页
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/log/{id}")
	public String lookLog(@PathVariable("id") Integer id, Model model) {
		// 绑定审核日志的数据至Model
		//appMemberService.bindMemberApplyLogDataToModel(id, model);
		appMemberService.deptMemberTransferLogDataToModel(id,model);
		return PREFIX + "look_log";
	}

}
