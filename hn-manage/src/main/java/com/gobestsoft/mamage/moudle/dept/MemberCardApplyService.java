package com.gobestsoft.mamage.moudle.dept;

import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyMapper;
import com.gobestsoft.mamage.model.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;

/**
 * 会员卡申请审核
 * @author Administrator
 */
@Service
public class MemberCardApplyService {

	@Resource
	DeptMemberApplyMapper deptMemberApplyMapper;
	@Resource
	AppMemberApplyService appMemberApplyService;

	/**
	 * 会员卡申请查看详情页
	 * @param id
	 */
	public void selectCompanyMemberCardApplyById(Integer id,Model model) {
		appMemberApplyService.bindMemberApplyDataToModel(id, model);
	}

	/**
	 * 会员卡申请查看流程页
	 * @param id
	 */
	public void selectCompanyMemberCardApplyLogById(Integer id,Model model) {
		appMemberApplyService.bindMemberApplyLogDataToModel(id, model);
	}

	/**
	 * 会员卡申请审核通过与不通过
	 * @param id
	 * @param checkDesc
	 * @param status
	 * @param loginUser
	 */
	public void updateCompanyMemberCardApplyStatusById(Integer id, String checkDesc, String status,
			LoginUser loginUser) {
//		appMemberApplyService.updateCompanyMemberCardApplyStatusById(id,checkDesc,status,loginUser);
	}

	
}
