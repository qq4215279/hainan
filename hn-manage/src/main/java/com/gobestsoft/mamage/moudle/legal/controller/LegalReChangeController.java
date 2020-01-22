package com.gobestsoft.mamage.moudle.legal.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalMapper;
import com.gobestsoft.common.modular.legal.model.DeptLegal;
import com.gobestsoft.common.modular.legal.model.DeptLegalApply;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.legal.LegalApplyService;
import com.gobestsoft.mamage.moudle.legal.LegalReChangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 法人资格遗失补办控制器
 * @author zhangdaowei 
 * 2018年08月26日  13点03分
 */
@Controller
@RequestMapping("legal/rechange")
public class LegalReChangeController extends BaseController {
	
	@Resource
	private LegalReChangeService legalReChangeService;

	@Resource
	LegalApplyService legalApplyService;

	@Resource
	DeptLegalMapper deptLegalMapper;
    private String PREFIX = "/legal/rechange/";

    /**
     * 列表页
     * @param model
     * @return
     */
    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "rechange_index";
    }

    /**
     * 新增页
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
    	model.addAttribute("orgName",getLoginUser().getDept().getDeptName());
    	//绑定遗失补办基本信息至Model
    	legalReChangeService.bindApplyDataToModel(model,getLoginUser().getDeptId());
        return PREFIX + "rechange_add";
    }
    
    /**
     * 编辑页
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam("id") Integer id) {
    	DeptLegalApply deptLegalApply = legalReChangeService.selectById(id);
    	model.addAttribute(deptLegalApply);
        return PREFIX + "rechange_edit";
    }
    
    /**
     * 详情页
     * @param model
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Model model,@RequestParam("id") Integer id) {
    	DeptLegalApply deptLegalApply = legalReChangeService.selectById(id);
    	model.addAttribute(deptLegalApply);
        return PREFIX + "rechange_detail";
    }
    
    /**
     * 查看流程页
     * @param model
     * @return
     */
    @RequestMapping("/log")
    public String log(Model model,@RequestParam("id") Integer id) {
    	//绑定审核日志信息
    	legalReChangeService.setLogDataToModel(model,id);
        return PREFIX + "rechange_log";
    }

    /**
	 * 多条件分页查询
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Object list(@RequestParam(required = false) String name,@RequestParam(required = false) String agentName,@RequestParam(required = false) String deptName) {
		Page<Map<String, Object>> page = this.defaultPage();
		List<Map<String, Object>> result = legalReChangeService.selectApplyPageByCondition(page,name,null,agentName
				,getLoginUser().getId());
		page.setRecords(result);
		return super.packForBT(page);
	}
	
	/**
	 * 数据保存方法
	 * @param model
	 * @param deptLegalApply
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/save")
	public Tip save(Model model,DeptLegalApply deptLegalApply){
		LoginUser loginUser = getLoginUser();

		if(loginUser.getDept().getParentDept()==null){
			return fail("当前用户没有上级部门");
		}
		if(ToolUtil.isEmpty(deptLegalApply.getId())){//插入方法
			legalReChangeService.insertByDeptLegalApply(deptLegalApply,getLoginUser());
		}else{//更新方法
			legalReChangeService.updateByDeptLegalApply(deptLegalApply,getLoginUser());
		}
		return this.success();
	}
    
    /**
     * 删除方法
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/remove")
	public Tip remove(@RequestParam("id") Integer id){
    	DeptLegalApply deptLegalApply = legalReChangeService.selectById(id);
    	if(ToolUtil.isEmpty(deptLegalApply)){
    		return this.tip(500, "数据不存在", id);
    	}
    	legalReChangeService.remove(id);
		return this.success();
	}
    
    /**
     * 审核页
     * @param model
     * @return
     */
    @RequestMapping("/check")
    public String check(Model model) {
        return PREFIX + "rechange_check_index";
    }
    
    /**
     * 审核页多条件分页查询
     * @param name
     * @param agentName
     * @return
     */
	@ResponseBody
	@RequestMapping("/checkList")
	public Object checkList(@RequestParam(required = false) String name,@RequestParam(required = false) String agentName,@RequestParam(required = false) String deptName) {
		Page<Map<String, Object>> page = this.defaultPage();
		List<Map<String, Object>> result = legalReChangeService.selectApplyCheckPageByCondition(page,name,null,agentName
				,getLoginUser().getDeptId());
		page.setRecords(result);
		return super.packForBT(page);
	}
	
	/**
	 * 审核详情页
	 * @param model
	 * @param id
	 * @return
	 */
    @RequestMapping("/checkDetail")
    public String checkDetail(Model model,@RequestParam("id") Integer id) {
    	DeptLegalApply deptLegalApply = legalReChangeService.selectById(id);
    	model.addAttribute(deptLegalApply);
        return PREFIX + "rechange_check_detail";
    }
    
    /**
     * 审核方法
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkSubmit")
	public Tip checkSubmit(@RequestParam() Integer id
			,@RequestParam() String logStatus
			,@RequestParam() String checkOpinion){
		DeptLegalApply deptLegalApply = legalApplyService.selectById(id);
		if(deptLegalApply==null){
			return fail("记录不存在");
		}
		List<DeptLegal> deptLegals = deptLegalMapper.selectDeptLegalByDeptId(deptLegalApply.getDeptId());
		if(deptLegals==null || deptLegals.size()==0)return fail("法人已注销，不能通过审核");

		legalReChangeService.checkSubmit(id,logStatus,checkOpinion,getLoginUser());
		return this.success();
	}

	/**
	 * 新增申请前的校验
	 * @return
	 */
	@RequestMapping("/checkAddApply")
	@ResponseBody
	public Tip checkAddApply(){
		LoginUser loginUser = getLoginUser();
		if(loginUser.getDept().getLevel()==1)return fail("没有上级审核机构，无法创建新的申请");
		Integer deptId = loginUser.getDeptId();
		List<DeptLegal> deptLegals = deptLegalMapper.selectDeptLegalByDeptId(deptId);
		if(deptLegals==null || deptLegals.size()==0)return fail("还未申请过法人，无法继续当前操作");
		Tip tip = legalApplyService.checkCreateOtherApply(deptId,3);
		return tip;
	}
    
}
