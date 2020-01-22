package com.gobestsoft.mamage.moudle.dept;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.PersonInfoMapper;
import com.gobestsoft.common.modular.dept.model.DeptMember;
import com.gobestsoft.common.modular.dept.model.DeptMemberApply;
import com.gobestsoft.common.modular.dept.model.PersonInfo;
import com.gobestsoft.core.util.DateUtil;

/**
 * 预备会员
 * @author Administrator
 */
@Service
public class PrepareMemberService {

	@Resource
	DeptMemberApplyMapper deptMemberApplyMapper;
	@Resource
	AppMemberApplyService appMemberApplyService;
	@Resource
	PersonInfoMapper personInfoMapper;
	@Resource
	DeptMemberMapper deptMemberMapper;
	
	/**
	 * 多条件分页查询
	 * @param page
	 * @param unitName
	 * @param name
	 * @return
	 */
	public List<Map<String, Object>> selectPrePareMemberPageByCondition(Page<Map<String, Object>> page,
			String unitName, String name,String certificateNum) {
		return deptMemberApplyMapper.selectPrePareMemberPageByCondition(page, unitName, name,DeptMember.A_TYPE[1],certificateNum);
	}

	/**
	 * 查看详情页
	 * @param id
	 */
	public void selectPrePareMemberById(Integer id,Model model) {
		appMemberApplyService.bindMemberApplyDataToModel(id, model);
	}
	
	/**
	 * APP用户申请入会，若输入单位名称和身份证号都无法找到工会，则插入一条申请入会信息dept_member_apply，不插入日志(dept_member_apply_log)
	 * 会员信息插入person_info表
	 * @param unitName
	 * @param name
	 */
	public void insertPrePareMemberData(String unitName,String name,String auid){
		
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName(name);
		personInfoMapper.insert(personInfo);
		
		DeptMember deptMember = new DeptMember();
		deptMember.setPersonId(personInfo.getId());
		deptMember.setType(DeptMember.A_TYPE[1]);
		deptMember.setCreateTime(DateUtil.getAllTime());
		deptMember.setCreateUser(auid);
		deptMember.setUpdateTime(DateUtil.getAllTime());
		deptMember.setUpdateUser(auid);
		deptMemberMapper.insert(deptMember);
		
		DeptMemberApply deptMemberApply = new DeptMemberApply();
		deptMemberApply.setAuid(auid);
		deptMemberApply.setApplyStatus(DictCodeConstants.LIB_APPROVE_STATUS_WAIT+"");
		deptMemberApply.setCreateTime(DateUtil.getAllTime());
		deptMemberApply.setUnitName(unitName);
		deptMemberApplyMapper.insert(deptMemberApply);
	}

}
