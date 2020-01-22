package com.gobestsoft.mamage.moudle.srv.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedMoneyPojo;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.moudle.srv.service.SrvStraitenedMoneyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 困难资金
 */
@Controller
@RequestMapping("/srv/straitened/money")
public class SrvStraitenedMoneyController extends BaseController {

    private final String PREFIX = "/srv/straitened/money/";

    @Resource
	private SrvStraitenedMoneyService moneyService;

	/**
     * 跳转页面 -- index/add/edit/detail
     */
	@RequestMapping("{page}")
	public String page(@PathVariable String page, @RequestParam(required = false)Integer id, Model model){
		if(ToolUtil.isNotEmpty(id)){
			model.addAttribute("pojo",moneyService.getDetailById(id));
		}
		return PREFIX + page;
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer status) {
		Page<Map<String, Object>> page = defaultPage();
		List<Map<String, Object>> result = moneyService.selectByCondition(page, name, status);
		page.setRecords(result);
		return super.packForBT(page);
	}


	/**
	 * 保存
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public Object doSave(SrvStraitenedMoneyPojo pojo) {
		try {
			moneyService.doSave(pojo,getLoginUser().getName());
		}catch (Exception e){
			return fail();
		}
		return success();
	}

	/**
	 * 保存
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer id) {
		try {
			moneyService.delete(id);
		}catch (Exception e){
			return fail();
		}
		return success();
	}
}
