package com.gobestsoft.api.modular.srv.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.api.config.cache.RedisCacheFactory;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.srv.model.LawConsultParam;
import com.gobestsoft.api.modular.system.service.MsgService;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.dao.mapper.*;
import com.gobestsoft.common.modular.dao.model.SrvLawConsultationLogPojo;
import com.gobestsoft.common.modular.dao.model.SrvLawConsultationPojo;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.core.reids.RedisCacheModel;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by li
 * on 2018/9/5 下午3:38
 */
@Service
public class LawService {

	@Autowired
	private SrvLawConsultationMapper lawConsultationMapper;
	
	@Autowired
	private SrvLawConsultationLogMapper lawConsultationLogMapper;
	
	@Autowired
    private SrvLawSupportMapper lawSupportMapper;
	
	@Autowired
    private DictMapper dictMapper;
	
	@Autowired
	private MsgService msgService;

	@Autowired
	protected RedisCacheFactory cacheFactory;

	@Autowired
	private SrvStraitenedMapper srvStraitenedMapper;

	/**
	 * 提交法律咨询问题
	 * @param param
	 * @param auid
	 * @param deptId
	 */
	@Transactional
    public void submit(LawConsultParam param,String auid,Integer deptId) {
    	SrvLawConsultationPojo pojo = new SrvLawConsultationPojo();
    	pojo.setCreateTime(DateUtil.getAllTime());
    	pojo.setStatus(0);
    	pojo.setCategory(param.getCategory());
    	pojo.setContent(param.getContent());
    	pojo.setAuid(auid);
    	pojo.setType(param.getType());
        lawConsultationMapper.insert(pojo);
        SrvLawConsultationLogPojo entity = new SrvLawConsultationLogPojo();
        entity.setConsultationId(pojo.getId());
        entity.setStatus(0);
        entity.setAnswerDeptId(deptId);
		lawConsultationLogMapper.insert(entity );
		msgService.insertMsg(deptId, "1", "您有新的法律咨询，请尽快回复！", "2");
    }

	/**
	 * 法律援助列表
	 * @param basicRowBounds
	 * @param auid
	 * @param type
	 * @return
	 */
	public List<SrvLawConsultationPojo> lawList(BasicRowBounds basicRowBounds,String auid,Integer type) {
    	Wrapper wrapper = new EntityWrapper<>().eq("auid", auid).eq("type", type);
		List<SrvLawConsultationPojo> result = lawConsultationMapper.selectPage(basicRowBounds, wrapper);
		for (SrvLawConsultationPojo srvLawConsultationPojo : result) {
			srvLawConsultationPojo.setCategory(getDictName("lib_legal_advice", srvLawConsultationPojo.getCategory()));
			srvLawConsultationPojo.setCreateTime(DateUtil.parseAndFormat(srvLawConsultationPojo.getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd"));
			srvLawConsultationPojo.setReplyTime(DateUtil.parseAndFormat(srvLawConsultationPojo.getReplyTime(), "yyyyMMddHHmmss", "yyyy-MM-dd"));
		}
		return result;
    }
    
    private String getDictName(String groupCode, String code) {
        List<Dict> dictModels = dictMapper.selectList(new EntityWrapper().eq("group_code", groupCode).eq("code", code));
        if (dictModels != null && dictModels.size() > 0) {
            return dictModels.get(0).getName();
        }
        return "";
    }

    /**
     * 法律援助详情
     * create by xiashasha
     * on 2018/09/13 上午09:42
     * 
     * @param id 法律援助id
     * @return
     */
	public Map<String, Object> lawSupportDetail(int id) {
		Map<String, Object> result = lawSupportMapper.lawSupportDetail(id);
		if (result != null) {
			if (result.containsKey("type")) {
				String typeName = "";
				Integer type = (Integer) result.get("type");
				if (type == 1) {
					typeName = "海南省职工服务中心法律援助申请";
					result.put("contacts", null);
				} else if(type == 0) {
					typeName = "中央专项彩票公益金法律援助项目法律援助申请";
					// type为0 中彩金时需要获取到家庭人员list
//					List<SrvContactsPojo> contactsList = contactsMapper.contactsList(id,1);// 0:困难帮扶家庭联系人。1：法律援助家庭联系人；

					List<Map<String, Object>> contactsList = srvStraitenedMapper.selectContacts(id + "", 1);
 					ToolUtil.transferKey(contactsList);
					if (contactsList != null && contactsList.size() > 0) {
						result.put("contacts", contactsList);
					} else {
						result.put("contacts", null);
					}
				}else{
					typeName = null;
				}
				result.put("type_name", typeName);
			}
			if (result.containsKey("status")) {
				String statusName = "";
				Integer status = (Integer) result.get("status");
				if (status == 0 || status ==1) {
					statusName = "审核中";
				} else if (status == 2) {
					statusName = "已通过";
				} else if(status == 3) {
					statusName = "已拒绝";
				}else if(status == 4){
					statusName = "已转办";
				}
				result.put("status_name", statusName);
			}
			// 上传文件处理
			Object evidence = result.get("evidence");
			try {
				JSONArray arr = new JSONArray();
				if(ToolUtil.isNotEmpty(evidence)){
					arr = JSONArray.parseObject(evidence.toString(),JSONArray.class);
					for(Object o: arr){
						JSONObject obj = (JSONObject) o;
						obj.put("path",WebSiteUtil.getBrowseUrl(obj.getString("path")));
					}
				}
				result.put("evidence",arr);
			}catch (Exception e){
				throw new RuntimeException("证据材料的json解析错误");
			}
			// 收入证明处理
			Object income = result.get("income");
			try {
				JSONArray arr = new JSONArray();
				if(ToolUtil.isNotEmpty(income)){
					arr = JSONArray.parseObject(income.toString(),JSONArray.class);
					for(Object o: arr){
						JSONObject obj = (JSONObject) o;
						obj.put("path",WebSiteUtil.getBrowseUrl(obj.getString("path")));
					}
				}
				result.put("income",arr);
			}catch (Exception e){
				throw new RuntimeException("收入证明的json解析错误");
			}
			// 户口本处理
			Object residenceBooklet = result.get("residenceBooklet");
			try {
				JSONArray arr = new JSONArray();
				if(ToolUtil.isNotEmpty(residenceBooklet)){
					arr = JSONArray.parseObject(residenceBooklet.toString(),JSONArray.class);
					for(Object o: arr){
						JSONObject obj = (JSONObject) o;
						obj.put("path",WebSiteUtil.getBrowseUrl(obj.getString("path")));
					}
				}
				result.put("residenceBooklet",arr);
			}catch (Exception e){
				throw new RuntimeException("户口本照片的json解析错误");
			}

			ToolUtil.transferKey(result);

		}
        return result;
	}

	/**
	 * 法律咨询和反映问题列表
	 * @param type
	 * @return
     */
	public List<SrvLawConsultationPojo> getConAndQues(BasicRowBounds basicRowBounds,String auid, int type){
		Wrapper wrapper = new EntityWrapper<>().eq("auid", auid).eq("type", type);

		String group_code= "";

		if(type==1){
			group_code="lib_legal_advice";
		}else{
			group_code="lib_law_question";
		}
		List<SrvLawConsultationPojo> result = lawConsultationMapper.selectPage(basicRowBounds, wrapper);
		for(SrvLawConsultationPojo param: result){
			param.setCategory(getDictName(group_code, param.getCategory()));
			param.setCreateTime(DateUtil.parseAndFormat(param.getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
		}

		return result;

	}

	/**
	 * 获取用户的所有申请
	 * @param uid
	 * @return
	 */
	public List<Map<String,Object>> selectSupportListByUser(Page  page, String uid){
		List<Map<String, Object>> list = lawSupportMapper.selectSupportListByUser(page,uid);
		String oldPattern = "yyyyMMddHHmmss";
		String newPattern = "yyyy-MM-dd HH:mm";
		for(Map m:list){
			//处理icon图片

			ToolUtil.formartMapDate(m,"create_time",oldPattern,newPattern);
			//    	处理文案
			Integer status = Integer.valueOf(m.get("status")+"");
			String  current_status="您的法律援助";
			if(status==2){
				current_status+="已通过";
			}
			else if(status==3){
				current_status+="已拒绝";
			}else if(status==4){
				current_status+="重新提交";
			}else{
				Date date = new Date();
				if (ToolUtil.isNotEmpty(m.get("expire_time"))) {
					if(m.get("expireTime")==null){
						current_status+="审核中";
					}else{
						Date expireTime = DateUtil.parseDateTime(m.get("expireTime").toString());
						Integer flag = date.compareTo(expireTime);
						if(flag != -1){ // -1代表当前时间 < 过期时间 比如 当前时间20180912 < 过期时间20180916
							m.put("status", 3);
//						m.put("audit_comment","审核超时拒绝");
							current_status+="已拒绝";
						}else{
							current_status+="审核中";
						}
					}

				}

			}
			m.put("current_status",current_status);
			m.remove("expire_time");

		}

		return list;
	}

	/**
	 * 获取法律援助和困难帮扶所有的字典
	 *
	 * @return
	 */
	public Object parameters() {
		RedisCacheModel cacheModel = cacheFactory.getCacheModel(CacheConstant.APP_SRV_PARAMETERS);
		if (cacheModel != null) {
			return cacheModel.getData();
		}

		Map<String, Object> result = new HashMap<>();
		result.put("isNot", getDicts(DictCodeConstants.LIB_IS_NOT));//是否
		result.put("sex", getDicts(DictCodeConstants.LIB_SEX));//性别
		result.put("nation", getDicts(DictCodeConstants.LIB_ETHNIC_GROUP));//民族
		result.put("politicalOutlook", getDicts(DictCodeConstants.LIB_POLITICAL_STATUS));//政治面貌
		result.put("education", getDicts(DictCodeConstants.LIB_EDUCATION_CODE));//学历
		result.put("difficult", getDicts(DictCodeConstants.LIB_DIFFICULT));//困难类别
		result.put("filingStandard", getDicts(DictCodeConstants.LIB_FILING_STANDARD));//建档标准
		result.put("disease", getDicts(DictCodeConstants.LIB_DISEASE));//疾病类别
		result.put("disability", getDicts(DictCodeConstants.LIB_DISABILITY));//残疾类别
		result.put("workStatus", getDicts(DictCodeConstants.LIB_WORK_STATUS));//工作状态
		result.put("health", getDicts(DictCodeConstants.LIB_HEALTH));//健康状况
		result.put("house", getDicts(DictCodeConstants.LIB_HOUSE_TYPE));//住房类型
		result.put("workerModel", getDicts(DictCodeConstants.LIB_WORKER));//劳模类型
		result.put("marriage", getDicts(DictCodeConstants.LIB_MARITAL_STATUS));//婚姻状况
		result.put("unitType", getDicts(DictCodeConstants.LIB_UNIT_TYPE));//单位性质类别
		result.put("enterpriseSituation", getDicts(DictCodeConstants.LIB_ENTERPRISE_SITUATION));//企业状况
		result.put("industry", getDicts(DictCodeConstants.LIB_INDUSTRY_TYPE));//所属行业
		result.put("household", getDicts(DictCodeConstants.LIB_HOUSEHOLD));//户口类型
		result.put("difficultyReason", getDicts(DictCodeConstants.LIB_DIFFICULTY_REASON));//主要致困原因
		result.put("cause", getDicts(DictCodeConstants.LIB_CAUSE));//法律援助事由
		result.put("shape", getDicts(DictCodeConstants.LIB_SUPPORT_SHAPE));//法律援助形式
		result.put("personType", getDicts(DictCodeConstants.LIB_PERSON_TYPE));//人员所属类型
		result.put("caseType", getDicts(DictCodeConstants.LIB_CASE_TYPE));//案件类型
		result.put("medicalInsurance", getDicts("lib_medical_insurance"));//医保情况
		result.put("schoolType", getDicts("lib_school_type"));//在校类别
		result.put("familyRelation", getDicts("lib_family_relationship"));//家庭关系
		cacheFactory.cacheModel(CacheConstant.APP_SRV_PARAMETERS, result, 60 * 60);
		return result;
	}

	/**
	 * @param groupCode
	 * @return
	 */
	private List<DictModel> getDicts(String groupCode) {
		DictModel result = dictMapper.getDictionary(groupCode, 0);
		if (result != null) {
			return result.getDict();
		}
		return null;
	}

}
