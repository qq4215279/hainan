package com.gobestsoft.admin.modular.dept.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.moudle.dept.PrepareMemberService;

/**
 * 预备会员临时管理控制器
 * @author zhangdaowei
 */
@Controller
@RequestMapping("prepare/member")
public class PrepareMemberController extends BaseController {

	private String PREFIX = "dept/member/prepare/";
	@Resource
	PrepareMemberService prepareMemberService;

	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return PREFIX + "index";
	}

	/**
	 * 多条件分页查询
	 * @param unitName
	 * @param name
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String unitName,@RequestParam(required = false) String name
			, @RequestParam(required = false) String certificateNum) {
		Page<Map<String, Object>> page = this.defaultPage();
		List<Map<String, Object>> result = prepareMemberService.selectPrePareMemberPageByCondition(page, unitName, name,certificateNum);
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 查看详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		prepareMemberService.selectPrePareMemberById(id,model);
		return PREFIX + "detail";
	}

}
