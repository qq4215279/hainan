package com.gobestsoft.mamage.moudle.dept.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.mapper.PersonInfoMapper;
import com.gobestsoft.common.modular.dept.model.PersonInfo;
import com.gobestsoft.mamage.moudle.system.service.DictService;

/**
 * 会员信息业务层
 * @author xss
 * @date 2018-08-16
 */
@Service
public class PersonInfoService {

	@Resource
	PersonInfoMapper personInfoMapper;
	@Resource
	DictService dictService;


	/**
	 * 查询方法
	 * @param id 主键
	 * @return
	 */
	public PersonInfo selectById(Integer id) {
		return personInfoMapper.selectById(id);
	}
	
	/**
	 * 企业端会员管理多条件分页查询
	 * @param page
	 * @param name 会员名称
	 * @param deptId 当前登录用户的orgId
	 * @param memberCard 会员卡号
	 * @return
	 */
	public List<Map<String, Object>> selectCompanyPageByCondition(Page<Map<String, Object>> page, String name
			, Integer deptId, String memberCard) {
		return personInfoMapper.selectCompanyPageByCondition(page,name,deptId,memberCard);
	}



}
