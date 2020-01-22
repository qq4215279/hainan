package com.gobestsoft.mamage.moudle.srv.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.dao.mapper.SrvStraitenedMoneyMapper;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedMoneyPojo;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.constant.factory.IConstantFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 困难资金
 */
@Service
public class SrvStraitenedMoneyService extends ManageBasic{

	@Resource
	private SrvStraitenedMoneyMapper moneyMapper;

	@Resource
	IConstantFactory constant;

	public List<Map<String, Object>> selectByCondition(Page<Map<String, Object>> page, String name, Integer status) {
		List<Map<String, Object>> list = moneyMapper.selectByCondition(page, name, status);
		list.forEach(pojo -> {
			setPojoName(pojo);
		});
		return list;
	}

	public SrvStraitenedMoneyPojo getDetailById(Integer id) {
		SrvStraitenedMoneyPojo pojo = moneyMapper.selectById(id);
		setPojoName(pojo);
		return pojo;
	}

	private void setPojoName(Object obj) {
		if(ToolUtil.isNotEmpty(obj)){
			if(obj instanceof Map){
				Map<String,Object> pojo = (Map<String, Object>) obj;
				if(pojo.containsKey("sex")) {
					Dict dict = constant.findInDictName(DictCodeConstants.LIB_SEX, pojo.get("sex").toString());
					pojo.put("sexName", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
				}
				if(pojo.containsKey("work_status")) {
					Dict dict = constant.findInDictName(DictCodeConstants.LIB_WORK_STATUS, pojo.get("work_status").toString());
					pojo.put("workStatusName", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
				}
				if(pojo.containsKey("status")) {
					String statusName = getStatusName((Integer) pojo.get("status"));
					pojo.put("statusName", statusName);
				}
			}else if(obj instanceof SrvStraitenedMoneyPojo){
				SrvStraitenedMoneyPojo pojo = (SrvStraitenedMoneyPojo) obj;
				if(ToolUtil.isNotEmpty(pojo.getSex())){
					Dict dict = constant.findInDictName(DictCodeConstants.LIB_SEX, String.valueOf(pojo.getSex()));
					pojo.setSexName(dict.getName());
				}
				if(ToolUtil.isNotEmpty(pojo.getWorkStatus())){
					Dict dict = constant.findInDictName(DictCodeConstants.LIB_WORK_STATUS, String.valueOf(pojo.getWorkStatus()));
					pojo.setWorkStatusName(dict.getName());
				}
				if(ToolUtil.isNotEmpty(pojo.getStatus())){
					String statusName = getStatusName(pojo.getStatus());
					pojo.setStatusName(statusName);
				}
			}
		}
	}

	private String getStatusName(Integer status) {
		String statusName;
		switch (status){
			case 0:
				statusName = "待处理";
				break;
			case 1:
				statusName = "通过";
				break;
			case 2:
				statusName = "拒绝";
				break;
			default:
				statusName = "-";
		}
		return statusName;
	}

	public void doSave(SrvStraitenedMoneyPojo pojo, String name) {
		if(ToolUtil.isEmpty(pojo.getId())){
			pojo.setCreateUser(name);
			pojo.setCreateTime(DateUtil.getAllTime());
			moneyMapper.insert(pojo);
		}else{
			SrvStraitenedMoneyPojo _pojo = moneyMapper.selectById(pojo.getId());
			pojo.setCreateTime(_pojo.getCreateTime());
			pojo.setCreateUser(_pojo.getCreateUser());
			moneyMapper.updateById(pojo);
		}
	}

	public void delete(Integer id) {
		SrvStraitenedMoneyPojo pojo = new SrvStraitenedMoneyPojo();
		pojo.setId(id);
		pojo.setDelFlg(1);
		moneyMapper.updateById(pojo);
	}
}
