package com.gobestsoft.company.modular.straitened.controller;
import com.gobestsoft.common.modular.dao.mapper.SrvStraitenedMapper;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedPojo;
import com.gobestsoft.mamage.basic.BaseController;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 法人资格注销登记控制器
 * @author xiezimeng
 *
 */
@Controller
@RequestMapping("/straitened")
public class StraitenedController extends BaseController {
	@Resource
	private  SrvStraitenedMapper srvStraitenedMapper;
	
    private String PREFIX = "/straitened";
    @RequestMapping("/detail")
    public String detail (Model model){
    	
    SrvStraitenedPojo pojo=	srvStraitenedMapper.selectById(59);
    	model.addAttribute("pojo",pojo);
    	return PREFIX+"/straitened_detail";
    }

}
