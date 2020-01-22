package com.gobestsoft.mamage.moudle.dept;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyLogMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberTransferMapper;
import com.gobestsoft.common.modular.dept.model.DeptMemberApply;
import com.gobestsoft.common.modular.dept.model.DeptMemberApplyLog;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 会员入会审核
 * @author Administrator
 */
@Service
public class AdmissionApplyService {

	@Resource
	DeptMemberApplyMapper deptMemberApplyMapper;
	@Resource
	AppMemberApplyService appMemberApplyService;
	@Resource
	DeptMemberTransferMapper deptMemberTransferMapper;

	@Autowired
	private DeptMemberApplyLogMapper deptMemberApplyLogMapper;

	@Autowired
	private DeptService deptService;
	/**
	 * 入会审核页多条件分页查询
	 * @param page
	 * @param status
	 * @param name
	 * @return
	 */
	public List<Map<String, Object>> selectCompanyAdmissionApplyPageByCondition(Page<Map<String, Object>> page,
			String status, String name, Integer deptId) {
		return deptMemberApplyMapper.selectByCondition(page, status, deptId, name);
	}

	/**
	 * 入会查看详情页
	 * @param id
	 */
	public void selectCompanyAdmissionApplyById(Integer id,Model model) {
		appMemberApplyService.bindMemberApplyDataToModel(id,model);
	}

	/**
	 * 入会查看流程页
	 * @param id
	 */
	public void selectCompanyAdmissionApplyLogById(Integer id,Model model) {
		appMemberApplyService.bindMemberApplyLogDataToModel(id, model);
	}

	/**
	 * 入会审核通过与不通过
	 * @param id
	 * @param checkDesc
	 * @param status
	 * @param loginUser
	 */
	public void updateCompanyAdmissionApplyStatusById(Integer id, String checkDesc, String status,
			LoginUser loginUser) {
		appMemberApplyService.updateCompanyAdmissionApplyStatusById(id,checkDesc,status,loginUser);
	}

	/**
	 * 上级工会报备多条件查询
	 * @param page
	 * @param unionName
	 * @param name
	 * @param orgId
	 * @return
	 */
	public List<Map<String, Object>> selectPreparationMemberPageByCondition(Page<Map<String, Object>> page,
			String unionName, String name,Integer orgId,String certificateNum) {
		return deptMemberApplyMapper.selectPreparationMemberPageByCondition(page,unionName,name
				,Integer.valueOf(DictCodeConstants.MEMBER_APPLY_TYPE_JOIN)
				,DictCodeConstants.LIB_APPROVE_STATUS_PASS.toString(),orgId,certificateNum);
	}

	/**
	 * 转出工会多条件查询
     */
	public List<Map<String, Object>> selectCheckByCondition(Page<Map<String, Object>> page,
																 String status,
																 Integer deptId,
																 String name){
		return deptMemberTransferMapper.selectCheckByCondition(page, status, deptId, name);
	}

	/**
	 * 转入工会多条件查询
	 */
	public List<Map<String, Object>> selectSecondCheckByCondition(Page<Map<String, Object>> page,
															String status,
															Integer deptId,
															String name){
		return deptMemberTransferMapper.selectSecondCheckByCondition(page, status, deptId, name);
	}

	/**
	 * 转出工会审核通过与不通过
	 * @param id
	 * @param checkDesc
	 * @param status
	 * @param loginUser
	 */
	public void updateTransferLogStatusById(Integer id, String checkDesc, String status,
													  LoginUser loginUser) {
		appMemberApplyService.updateTransferLogStatusById(id,checkDesc,status,loginUser);
	}

	/**
	 * 转入工会审核通过与不通过
	 * @param id
	 * @param checkDesc
	 * @param status
	 * @param loginUser
	 */
	public void updateInStatusById(Integer id, String checkDesc, String status,
											LoginUser loginUser) {
		appMemberApplyService.updateInStatusById(id,checkDesc,status,loginUser);
	}

	/**
	 * 入会申请转办给其他工会进行审核
	 * @param id
	 * @param deptId
	 * @param transferReason
	 * @return
	 */
	@Transactional
	public Tip updateTransfer(Integer id, Integer deptId, String transferReason){

		int update = 0;

		DeptMemberApply old = deptMemberApplyMapper.selectById(id);
		update=deptMemberApplyMapper.updateAuditDeptId(id,old.getDeptId(), transferReason);
		if(update==0)return new Tip(500,"状态被修改，请刷新列表后重试",null);
		deptMemberApplyMapper.updateDeptId(id,deptId);
		DeptMemberApplyLog pojo = new DeptMemberApplyLog();
		pojo.setApplyStatus("-1");
		pojo.setCreateTime(DateUtil.getAllTime());
		pojo.setCheckDeptId(deptId);
		pojo.setAuid(old.getAuid());
		pojo.setApplyId(old.getId());
		pojo.setCheckFlag(0);
		deptMemberApplyLogMapper.insert(pojo);
		return new Tip(200,null,null);
	}

}
