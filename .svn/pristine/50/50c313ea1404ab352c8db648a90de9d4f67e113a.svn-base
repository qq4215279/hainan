package com.gobestsoft.mamage.moudle.law.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.SrvLawConsultationLogMapper;
import com.gobestsoft.common.modular.model.LogModel;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.law.service.LawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by gutongwei
 * on 2018/9/4 下午7:34
 */
@Controller
@RequestMapping("/law")
public class LawController extends BaseController {

    private final String PREFIX = "law/";


    @Autowired
    private LawService lawService;
    
    @Autowired
    private SrvLawConsultationLogMapper srvLawConsultationLogMapper;
    
    @Autowired
    private DeptDao deptDao;


    @RequestMapping("/consultation/{type}")
    public String consultation(Model model,@PathVariable int type) {
    	model.addAttribute("type", type);
    	model.addAttribute("deptId", getLoginUser().getDeptId());
    	model.addAttribute("pid", deptDao.selectByOrgId(getLoginUser().getDeptId()));
//    	Wrapper wrapper = new EntityWrapper<>();
//    	wrapper.addFilter("consultation_id", id);
//		int num = srvLawConsultationLogMapper.selectCount(wrapper);
//    	model.addAttribute("sid", deptDao.selectByOrgId(getLoginUser().deptId));
        return PREFIX + "consultation";
    }


	@RequestMapping("/consultationNull/{type}")
	public String consultationNull(Model model,@PathVariable int type) {
		model.addAttribute("type", type);
		model.addAttribute("pid", deptDao.selectByOrgId(getLoginUser().getDeptId()));
		return PREFIX + "consultationNull";
	}
    
    @RequestMapping(value="/replyWeb")
    public String reply(Model model
    				,   @RequestParam(value = "id", required = true) Integer id
    		) {
    	model.addAttribute("id", id);
    	return PREFIX + "consult_reply";
    }
    
    @RequestMapping(value="/transferWeb")
    public String transferWeb(Model model
    		 			     ,@RequestParam(value = "id", required = true) Integer id
    		) {
    	Integer pid = deptDao.selectByOrgId(getLoginUser().getDeptId());
    	model.addAttribute("deptId", getLoginUser().getDeptId());
    	model.addAttribute("pid", pid);
    	model.addAttribute("pname", deptDao.selectFullNameById(pid));
//    	model.addAttribute("pname", deptDao.selectFullNameById(pid)==null?"已是省总工会，不能上办！":deptDao.selectFullNameById(pid));
    	Wrapper wrapper = new EntityWrapper<>().eq("consultation_id", id);
//    	wrapper.addFilter("consultation_id", id);
		int num = srvLawConsultationLogMapper.selectCount(wrapper);
		if(num > 1) {
			int sid = srvLawConsultationLogMapper.selBackId(id);
	    	model.addAttribute("sid", sid);
	    	model.addAttribute("sname", deptDao.selectFullNameById(sid));
//	    	model.addAttribute("sname", deptDao.selectFullNameById(sid)==null?"没有下级组织可以回退!":deptDao.selectFullNameById(sid));
		}else if(num <= 1 && deptDao.selectFullNameById(pid)==null) {
			//如果没有转送记录 并且 没有上级组织 则不能进行转送
			model.addAttribute("sname",null);
//			model.addAttribute("sname","没有下级组织可以回退!");
			model.addAttribute("notTransfer", true);
			model.addAttribute("msg", "没有可转办组织，不能进行转办！");
		}
//		model.addAttribute("sname","没有下级组织可以回退!");
		
    	model.addAttribute("id", id);
    	return PREFIX + "transfer_law";
    }

    @RequestMapping("/consultationList")
    @ResponseBody
    public Object consultationList(@RequestParam(value = "type") Integer type
    		   					  ,@RequestParam(value = "category", required = false) String category,
									   @RequestParam(value = "status",required = false)String status) {
        Page page = defaultPage();
        page.setRecords(lawService.consultation(type,getLoginUser().getDeptId(), category,status, page));
        return packForBT(page);
    }

    @RequestMapping("/consultationListNull")
    @ResponseBody
    public Object consultationListNull(@RequestParam(value = "type") Integer type
    		   					  ,@RequestParam(value = "category", required = false) String category
									,@RequestParam(value = "status",required = false)String status) {
        Page page = defaultPage();
        page.setRecords(lawService.consultationNull(type, null,category,status, page));
        return packForBT(page);
    }

    /**
     * 更新方法
     *
     * @return
     */
    @RequestMapping(value="/reply",method=RequestMethod.POST)
    @ResponseBody
    public Tip reply(
    		@RequestParam(value = "id", required = true) Integer id
    	   ,@RequestParam(value = "content", required = true) String content
    		) {
        if (ToolUtil.isEmpty(content) || ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        lawService.approveLaw(id, getLoginUser().getId(), content,getLoginUser().getDeptId());
        return success();
    }

    /**
       * 转办方法
     *
     * @return
     */
    @RequestMapping(value="/transferLaw",method=RequestMethod.POST)
    @ResponseBody
    public Tip transferLaw(
    		@RequestParam(value = "id", required = true) Integer id
    		,@RequestParam(value = "transferReason", required = true) String transferReason
    		,@RequestParam(value = "answerDeptId", required = true) Integer answerDeptId
    		) {
    	if (ToolUtil.isEmpty(answerDeptId) || ToolUtil.isEmpty(id) || ToolUtil.isEmpty(answerDeptId)) {
    		throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
    	}
    	lawService.transferLaw(id, transferReason, answerDeptId, getLoginUser().getDeptId());
    	return success();
    }
    
    /**
  	 * 查看审核流程
  	 * @param id
  	 * @param model
  	 * @return
  	 */
  	@RequestMapping("/log")
  	public String log(@RequestParam("id") Integer id, Model model) {
  		List<LogModel> log = lawService.log(id);
  		model.addAttribute("logList", log);
  		return   "/applyLog/look_log";
  	}
    
}
