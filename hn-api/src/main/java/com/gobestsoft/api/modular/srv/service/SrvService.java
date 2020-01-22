package com.gobestsoft.api.modular.srv.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.api.modular.appuser.model.AppUserDto;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.basic.RestBasic;
import com.gobestsoft.api.modular.srv.model.*;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.dao.mapper.*;
import com.gobestsoft.common.modular.dao.model.*;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.common.modular.srv.StraitenedEntity;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.core.reids.RedisCacheModel;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

/**
 * create by gutongwei
 * on 2018/6/20 下午2:38
 */
@Service
public class SrvService extends RestBasic {

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    private SrvStraitenedMapper srvStraitenedMapper;

    @Autowired
    private SrvStraitenedFirstMapper srvStraitenedFirstMapper;

    @Autowired
    private SrvStraitenedLogMapper srvStraitenedLogMapper;

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private SrvLawSupportMapper lawSupportMapper;

    @Autowired
    private SrvContactsMapper contactsMapper;

    @Autowired
    private SrvLawSupportLogMapper lawSupportLogMapper;

    @Autowired
    private SrvStraitenedExeMedicalMapper exeMedicalMapper;

    @Autowired
    private SrvStraitenedExeStudyMapper exeStudyMapper;

    @Autowired
    private DeptMapper deptMapper;


    /**
     * 申请帮扶初审
     * create by xiashasha
     * on 2018/09/17 上午10:12
     *
     * @param param
     * @param userDto
     * @return
     */
    public String straitenedFirstApply(StraitenedFirstApplyParam param, AppUserDto userDto) {
        //判断是否是重复数据
        Wrapper<SrvStraitenedFirstPojo> wrapper = new EntityWrapper<>();
        wrapper.eq("certificate_num", param.getCertificateNum());
        wrapper.eq("status", 1);//TODO 未来添加查询条件
        List<SrvStraitenedFirstPojo> lists = srvStraitenedFirstMapper.selectList(wrapper);
        if (lists.size() > 0) {
            return "已发送帮扶申请等待审批，请勿重复发起！";
        }
//        如果id不为空，直接删除旧的申请
        if(StringUtils.isNotEmpty(param.getId())){
            SrvStraitenedFirstPojo firstPojo = srvStraitenedFirstMapper.selectById(param.getId());

            firstPojo.setStatus(4);//已经重新提交，不能再点击

            srvStraitenedFirstMapper.updateById(firstPojo);
        }

        SrvStraitenedFirstPojo pojo = new SrvStraitenedFirstPojo();
        pojo.setAuid(userDto.getAuid());
        pojo.setStatus(1);
        pojo.setName(param.getName());
        pojo.setCertificateNum(param.getCertificateNum());
        pojo.setWorkUnit(param.getWorkUnit());
        pojo.setIdentityFace(param.getIdentityFace());
        pojo.setCardNationalEmblem(param.getCardNationalEmblem());
        pojo.setCommitLetter(param.getCommitLetter());
        pojo.setUnitProve(param.getUnitProve());
        pojo.setAttachments(param.getAttachments());
        pojo.setCreateTime(DateUtil.getAllTime());
        srvStraitenedFirstMapper.insert(pojo);

        SrvStraitenedLogPojo logPojo = new SrvStraitenedLogPojo();
        logPojo.setStraitenedId(pojo.getId());
        logPojo.setType(0);// 0:初审；1:终审
        logPojo.setCreateTime(DateUtil.getAllTime());
        logPojo.setStatus(1);
        logPojo.setCreateUid(userDto.getAuid());
        logPojo.setCreateUserType(0);// 系统
        logPojo.setCheckDeptId(userDto.getMember_info().getDept_id());
        srvStraitenedLogMapper.insert(logPojo);

        return SUCCESS;
    }

    /**
     * 申请帮扶
     * create by gutongwei
     * on 2018/6/20
     *
     * @param param
     * @param appUserDto
     */
    @Transactional
    public String straitenedApply(StraitenedApplyParam param, AppUserDto appUserDto) {
        //判断是否是重复数据
        Wrapper<SrvStraitenedPojo> wrapper = new EntityWrapper<>();
        Integer straitenedFirstId = param.getStraitenedFirstId();
        wrapper.eq("certificate_num", param.getCertificateNum());
        wrapper.eq("status", 1);//TODO 未来添加查询条件
        wrapper.eq("straitened_first_id",straitenedFirstId);
        List<SrvStraitenedPojo> lists = srvStraitenedMapper.selectList(wrapper);
        if (lists.size() > 0) {
            return "已发送帮扶申请等待审批，请勿重复发起！";
        }
        if(StringUtils.isNotEmpty(param.getId())){
//            srvStraitenedMapper.deleteById(Integer.valueOf(param.getId()));

            SrvStraitenedPojo finalPojo = srvStraitenedMapper.selectById(param.getId());

            finalPojo.setStatus(4);//之前重新申请的不再允许点击

            srvStraitenedMapper.updateById(finalPojo);

        }

        SrvStraitenedPojo pojo = new SrvStraitenedPojo();
        pojo.setStraitenedFirstId(param.getStraitenedFirstId());
        pojo.setAuid(appUserDto.getAuid());
        pojo.setStatus(1);
        pojo.setName(param.getName());
        pojo.setNation(param.getNation());
        pojo.setSex(param.getSex());
        pojo.setAge(param.getAge());
        pojo.setPoliticalStatus(param.getPoliticalStatus());
        pojo.setCertificateNum(param.getCertificateNum());
        pojo.setBirthday(param.getBirthday());
        pojo.setMobile(param.getMobile());
        pojo.setOtherContactTel(param.getOtherContactTel());
        pojo.setZipCode(param.getZipCode());
        pojo.setHealth(param.getHealth());
        pojo.setDisability(param.getDisability());
        pojo.setModelWorker(param.getModelWorker());
        pojo.setHouseholdType(param.getHouseholdType());
        pojo.setHouseArea(param.getHouseArea());
        pojo.setHouseholdType(param.getHouseholdType());
        pojo.setHouseholeAddress(param.getHouseholeAddress());
        pojo.setHouseType(param.getHouseType());
        pojo.setWorkStatus(param.getWorkStatus());
        pojo.setWorkTime(param.getWorkTime());
        pojo.setIndustryType(param.getIndustryType());
        pojo.setMaritalStatus(param.getMaritalStatus());
        pojo.setLiveAddress(param.getLiveAddress());
        pojo.setAddress(param.getAddress());
        pojo.setWorkUnit(param.getWorkUnit());
        pojo.setUnitType(param.getUnitType());
        pojo.setEnterpriseSituation(param.getEnterpriseSituation());
        pojo.setIsSingleParent(param.getIsSingleParent());
        pojo.setMonthlyIncome(param.getMonthlyIncome());
        pojo.setFamilyYearIncomeOther(param.getFamilyYearIncomeOther());
        pojo.setFamilyYearIncome(param.getFamilyYearIncome());
        pojo.setFamilyPopulation(param.getFamilyPopulation());
        pojo.setFamilyMonthIncomeAvg(param.getFamilyMonthIncomeAvg());
        pojo.setMedicalInsurance(param.getMedicalInsurance());
        pojo.setHasSaveOneself(param.getHasSaveOneself());
        pojo.setLaborContractStart(param.getLaborContractStart());
        pojo.setLaborContractEnd(param.getLaborContractEnd());
        pojo.setFamilyNoWorker(param.getFamilyNoWorker());
        pojo.setMemberCardNo(param.getMemberCardNo());
        pojo.setReason(param.getReason());
        pojo.setCreateTime(DateUtil.getAllTime());
        pojo.setDisease(param.getDisease());
        srvStraitenedMapper.insert(pojo);

        addContacts(param.getContacts(), 0, pojo.getId());
        if ("1".equals(param.getStraitenedType())) {
            addMedicals(param.getMedicals(), pojo.getId());
        }
        if ("2".equals(param.getStraitenedType())) {
            addStudies(param.getStudies(), pojo.getId());
        }
        if ("3".equals(param.getStraitenedType())) {
            addMedicals(param.getMedicals(), pojo.getId());
            addStudies(param.getStudies(), pojo.getId());
        }

        SrvStraitenedLogPojo logPojo = new SrvStraitenedLogPojo();
        logPojo.setStraitenedId(pojo.getId());
        logPojo.setType(1);
        logPojo.setCreateTime(DateUtil.getAllTime());
        logPojo.setStatus(1);
        logPojo.setCreateUid(appUserDto.getAuid());
        logPojo.setCreateUserType(0);// 系统
        logPojo.setCheckDeptId(appUserDto.getMember_info().getDept_id());
        srvStraitenedLogMapper.insert(logPojo);

        return SUCCESS;
    }

    /**
     * 相互帮扶列表
     * create by gutongwei
     * on 2018/6/20 下午2:21
     *
     * @param bounds
     * @param auid   用户id
     * @return
     */
    public List<StraitenedEntity> straitenedList(BasicRowBounds bounds, String auid) {
        List<StraitenedEntity> entityFirstList = srvStraitenedFirstMapper.getStraitenedAll(bounds, auid);
        return entityFirstList;
    }

    /**
     * 相互帮扶详情
     * create by gutongwei
     * on 2018/6/20 下午2:21
     *
     * @param id 帮扶id
     * @return
     */
    public Map<String, Object> straitenedDetail(int id) {
        Map<String, Object> result = srvStraitenedMapper.straitenedDetail(id);
        return result;
    }


    private void refresh(Set<StraitenedLogDto> process, StraitenedLogDto one) {
        process.remove(one);
        process.add(one);
    }

    private String[] getDictDescArray(String groupCode, String codes) {
        String[] result = codes.split(",");
        for (int i = 0; i < result.length; i++) {
            result[i] = getDictName(groupCode, result[i]);
        }
        return result;
    }


    private String isNon(String n) {
        if (StringUtils.isNotEmpty(n)) {
            if ("1".equals(n)) {
                return "是";
            }
            return "否";
        }
        return "否";
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
        result.put("straitened_education", getDicts("lib_straitened_student_edu"));//学历
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
        result.put("identityType", getDicts("lib_identity"));//身份
        result.put("enrollBatchType", getDicts("lib_enroll_batch"));//毕业批次
        result.put("studyYear", getDicts("lib_study_year"));//毕业批次
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


    private String getDictName(String groupCode, String code) {
        List<Dict> dictModels = dictMapper.selectList(new EntityWrapper().eq("group_code", groupCode).eq("code", code));
        if (dictModels != null && dictModels.size() > 0) {
            return dictModels.get(0).getName();
        }
        return "";
    }

    public int removeStraitened(int id) {
        SrvStraitenedPojo pojo = srvStraitenedMapper.selectById(id);
        if (pojo == null) return 2;
        if (pojo.getStatus() == 0) {
            return srvStraitenedMapper.deleteById(id);
        }

        return 2;
    }


    /**
     * 发起法律申请
     *
     * @param param
     */
    @Transactional
    public String lawSupportApply(LawSupportParam param, String userId, Integer deptId) {

        //判断是否是重复数据
        Wrapper<SrvLawSupportPojo> wrapper = new EntityWrapper<>();
        wrapper.eq("certificate_num", param.getCertificateNum());
        wrapper.eq("status", 1);
        List<SrvLawSupportPojo> lists = lawSupportMapper.selectList(wrapper);
        if (lists.size() > 0) {
            return "已发送法律申请等待审批，请勿重复发起！";
        }
        //如果id不为空，则之前的不再允许点击
        if(StringUtils.isNotEmpty(param.getId())){
//            lawSupportMapper.deleteById(Integer.valueOf(param.getId()));
            SrvLawSupportPojo lawSupportPojo = lawSupportMapper.selectById(param.getId());

            lawSupportPojo.setStatus(4);

            lawSupportMapper.updateById(lawSupportPojo);

        }

        SrvLawSupportPojo pojo = new SrvLawSupportPojo();
        if ("1".equals(param.getShape()) || "5".equals(param.getPersonType())) {
            pojo.setType(1);
        } else {
            pojo.setType(0);
        }
        pojo.setCause(param.getCause());
        pojo.setShape(param.getShape());
        pojo.setName(param.getName());
        pojo.setSex(param.getSex());
        pojo.setNation(param.getNation());
        pojo.setBirthday(param.getBirthday());
        pojo.setCertificateNum(param.getCertificateNum());
        pojo.setMobile(param.getMobile());
        pojo.setOccupation(param.getOccupation());
        pojo.setDomicilePlace(param.getDomicilePlace());
        pojo.setPersonType(param.getPersonType());
        pojo.setWorkUnit(param.getWorkUnit());
        pojo.setCaseReason(param.getCaseReason());
        pojo.setUnitAddress(param.getUnitAddress());
        pojo.setAddress(param.getAddress());
        pojo.setHomeAddress(param.getHomeAddress());
        pojo.setZipCode(param.getZipCode());
        pojo.setTel(param.getTel());
        pojo.setFamilyNumber(param.getFamilyNumber());
        // 代理人暂不用
//        pojo.setAgentName(param.getAgentName());
//        pojo.setAgentCertificateNum(param.getAgentCertificateNum());
        pojo.setMonthlyIncome(param.getMonthlyIncome());
        pojo.setFamilyMonthlyIncome(param.getFamilyMonthlyIncome());
        pojo.setFamilyMonthlyAvgIncome(param.getFamilyMonthlyAvgIncome());
        pojo.setFamilyIncomeCondition(param.getFamilyIncomeCondition());
        pojo.setCaseType(param.getCaseType());
        pojo.setCaseMoney(param.getCaseMoney());
        pojo.setCaseSituation(param.getCaseSituation());
        pojo.setEvidence(param.getEvidence());
        pojo.setIncome(param.getIncome());
        pojo.setResidenceBooklet(param.getResidenceBooklet());
        pojo.setCreateUid(userId);
        pojo.setCreateTime(DateUtil.getAllTime());
        pojo.setStatus(1);
        // 获取十天之后的日期（格式为：yyyyMMddHHmmss）
        String expireTime = DateUtil.parseAndFormat(DateUtil.getAfterDayDate("10"), "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        pojo.setExpireTime(expireTime);// 过期时间默认为申请提交后的十天
        /*if(ToolUtil.isEmpty(param.getDeptLevel())){
            pojo.setDeptLevel(DictCodeConstants.DEPT_LEVEL_CITY);//默认 市县：2
        }else{
            pojo.setDeptLevel(param.getDeptLevel());
        }*/
        lawSupportMapper.insert(pojo);

        if (param.getType() == 0 && param.getContacts() != null && param.getContacts().size() > 0) {
            addContacts(param.getContacts(), 1, pojo.getId());
        }
        SrvLawSupportLogPojo logPojo = new SrvLawSupportLogPojo();

        logPojo.setSupportId(pojo.getId());
        logPojo.setCreateUid(userId);
        logPojo.setCreateUserType(0);// 0:APP用户;1系统用户
        logPojo.setStatus(1);
        //获取当前部门的市级工会，如果没有，则设置为省总工会审核
        /*Dept dept = lawSupportMapper.selectCityDept(deptId);

        if(dept==null){
            deptId = 1;
        }else{
            deptId = dept.getId();
        }*/

        logPojo.setCreateTime(DateUtil.getAllTime());
        logPojo.setCheckDeptId(deptId);
        //插入市级审核工会组织id
        if (ToolUtil.isNotEmpty(deptId) && deptId.equals(1)){//是否是省级deptId
            logPojo.setCheckleadDeptId(deptId);
        } else {
            List<Integer> secondDeptIds = this.deptMapper.selectSecondDeptIds();
            secondDeptIds.forEach(secondDId ->{
                if (ToolUtil.isNotEmpty(deptId) && secondDId.equals(deptId)){//市级deptId
                    logPojo.setCheckleadDeptId(deptId);
                }else {
                    int secondDeptIdByDeptId = this.deptMapper.selectSecondDeptIdByDeptId(deptId);//非市级
                    logPojo.setCheckleadDeptId(secondDeptIdByDeptId);
                }
            });
        }
        lawSupportLogMapper.insert(logPojo);

        return SUCCESS;

    }


    /**
     * 添加家庭联系人
     *
     * @param contacts
     * @param type     0:困难帮扶家庭联系人。1：法律援助家庭联系人。
     * @param targetId 困难帮扶ID,法律援助ID。
     */
    private void addContacts(List<ApplyContacts> contacts, Integer type, Integer targetId) {
        if (contacts != null && contacts.size() > 0) {
            List<SrvContactsPojo> pojos = new ArrayList<>();
            contacts.forEach(c -> {
                SrvContactsPojo p = new SrvContactsPojo();
                p.setTargetId(targetId);
                p.setType(type);
                p.setName(c.getName());
                p.setCertificateNum(c.getCertificateNum());
                p.setRelation(c.getRelation());
                p.setPoliticalStatus(c.getPoliticalStatus());
                p.setEducation(c.getEducation());
                p.setMedicalInsurance(c.getMedicalInsurance());
                p.setHealth(c.getHealth());
                p.setSex(c.getSex());
                p.setAge(c.getAge());
                p.setMonthlyIncome(c.getMonthlyIncome());
                p.setIdentity(c.getIdentity());
                p.setUnit(c.getUnit());
                p.setTel(c.getTel());
                p.setSchoolType(c.getSchoolType());
                pojos.add(p);
            });
            contactsMapper.insertAll(pojos);
        }
    }


    /**
     * 添加医疗救助人
     *
     * @param medicals
     * @param srvStraitenedId 终审ID
     */
    private void addMedicals(List<ApplyMedical> medicals, Integer srvStraitenedId) {
        if (medicals != null && medicals.size() > 0) {
            List<SrvStraitenedExeMedicalPojo> pojos = new ArrayList<>();
            medicals.forEach(m -> {
                SrvStraitenedExeMedicalPojo pojo = new SrvStraitenedExeMedicalPojo();
                pojo.setSrvStraitenedId(srvStraitenedId);
                pojo.setName(m.getName());
                pojo.setRelation(m.getRelation());
                pojo.setCertificate_num(m.getCertificateNum());
                pojo.setAge(m.getAge());
                pojo.setSex(m.getSex());
                pojo.setWorkUnit(m.getWorkUnit());
                pojo.setMobile(m.getMobile());
                pojo.setDisease(m.getDisease());
                pojo.setHospitalizationNum(m.getHospitalizationNum());
                pojo.setUnitDonationMoney(m.getUnitDonationMoney());
                pojo.setSociologyDonationMoney(m.getSociologyDonationMoney());
                pojo.setRemark(m.getRemark());
                pojos.add(pojo);
            });
            exeMedicalMapper.addAll(pojos);
        }
    }


    /**
     * 添加医疗救助人
     *
     * @param studies
     * @param srvStraitenedId 终审ID
     */
    private void addStudies(List<ApplyStudy> studies, Integer srvStraitenedId) {
        if (studies != null && studies.size() > 0) {
            List<SrvStraitenedExeStudyPojo> pojos = new ArrayList<>();
            studies.forEach(m -> {
                SrvStraitenedExeStudyPojo pojo = new SrvStraitenedExeStudyPojo();
                pojo.setSrvStraitenedId(srvStraitenedId);
                pojo.setName(m.getName());
                pojo.setCertificateNum(m.getCertificateNum());
                pojo.setAge(m.getAge());
                pojo.setSex(m.getSex());
                pojo.setIsCurrent(m.getIsCurrent());
                pojo.setMobile(m.getMobile());
                pojo.setPoliticalStatus(m.getPoliticalStatus());
                pojo.setEducation(m.getEducation());
                pojo.setCollegeScore(m.getCollegeScore());
                pojo.setEnrollSchool(m.getEnrollSchool());
                pojo.setEnrollMajor(m.getEnrollMajor());
                pojo.setSchoolYear(m.getSchoolYear());
                pojo.setEnrollBatch(m.getEnrollBatch());
                pojo.setEnrollTime(m.getEnrollTime());
                pojo.setGraduationTime(m.getGraduationTime());
                pojo.setLearnMoney(m.getLearnMoney());
                pojo.setHasLoan(m.getHasLoan());
                pojo.setLoanMoney(m.getLoanMoney());
                pojo.setLiveAddress(m.getLiveAddress());
                pojo.setAddress(m.getAddress());
                pojo.setNation(m.getNation());
                pojos.add(pojo);
            });
            exeStudyMapper.addAll(pojos);
        }
    }

    /**
     * 获取初审详情
     *
     * @param id
     * @return
     */
    public Map<String, Object> getFirsDetail(String id) {

        Map<String, Object> pojo = srvStraitenedFirstMapper.selectOneById(id);

        String cardNationalEmblem = WebSiteUtil.getBrowseUrl(pojo.get("cardNationalEmblem") + "");
        String commitLetter = WebSiteUtil.getBrowseUrl(pojo.get("commitLetter") + "");
        String identityFace = WebSiteUtil.getBrowseUrl(pojo.get("identityFace") + "");
        String unitProve = WebSiteUtil.getBrowseUrl(pojo.get("unitProve") + "");

        pojo.put("cardNationalEmblem", cardNationalEmblem);
        pojo.put("commitLetter", commitLetter);
        pojo.put("identityFace", identityFace);
        pojo.put("unitProve", unitProve);
        ToolUtil.transferKey(pojo);

        if (pojo != null) {

            String attachments = (String)pojo.get("attachments");
            if (StringUtils.isNotEmpty(attachments)) {
                try {
                    JSONArray arr = JSONArray.parseObject(attachments,JSONArray.class);
                    for(Object o: arr){
                        JSONObject obj = (JSONObject) o;
                        obj.put("path",WebSiteUtil.getBrowseUrl(obj.getString("path")));
                    }
                    pojo.put("attachments", arr);
                }catch (Exception e){
                    throw new RuntimeException("附件数据json解析错误");
                }


            }


        }


        return pojo;
    }


    /**
     * 获取终审详情
     *
     * @param id
     * @return
     */
    public Map<String, Object> getFinalDetail(String id) {
        Map<String, Object> map = srvStraitenedMapper.selectInfoById(id);
//        String newPattern = "yyyy-MM-dd";
//
//        ToolUtil.formartMapDate(map, "birthday", newPattern);
//        ToolUtil.formartMapDate(map, "workTime", newPattern);

        if (map != null) {

            List<Map<String, Object>> contacts = srvStraitenedMapper.selectContacts(id,0);
            ToolUtil.transferKey(contacts);
            map.put("contacts", contacts);

            //        处理特殊值

            List<Map<String, Object>> medicalList = srvStraitenedMapper.selectMedicalList(id);
            ToolUtil.transferKey(medicalList);
            List<Map<String, Object>> studyList = srvStraitenedMapper.selectStudyList(id);
            ToolUtil.transferKey(studyList);
//            for (Map<String, Object> m : studyList) {
//                ToolUtil.formartMapDate(m, "enroll_time", newPattern);
//                ToolUtil.formartMapDate(m, "graduation_time", newPattern);
//            }
            map.put("medicals",medicalList);
            map.put("studies",studyList);


        }
        ToolUtil.transferKey(map);
        return map;
    }




    /**
     * 将有字典值的转成 sex:{code:xx,value:xx}，存回对象
     * @param obj  Map类型或者自定义的pojo
     * @param key
     * @param lib_key
     */
    private void transferViaLib(Object obj,String key, String lib_key) {

        if (obj == null) {
            return;
        }
        Object parameters = parameters();

        Map<String, Object> map = (Map<String, Object>) parameters;

        List<DictModel> list = (List<DictModel>) map.get(lib_key);

        if (list == null || list.size() == 0) {
            throw new RuntimeException("没有根据lib_key(" + lib_key + ")找到对应的字典值列表");
        }

        if (obj instanceof Map) {
            Map m = (Map) obj;
            if (!m.containsKey(key) ) {
                return;
            }
            if(m.get(key) == null){
                return;
            }

            ObjectMap temp = null;
            if (list != null) {
                for (DictModel model : list) {
                    if (m.get(key).toString().equals(model.getCode())) {
                        temp = ObjectMap.getInstance();
                        temp.put("code", m.get(key).toString());
                        temp.put("value", model.getName());
                        m.put(key, temp);
                        break;
                    }
                }
            }

        } else {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.getName().equals(key)) {
                    for (DictModel model : list) {
                        try {
                            if (f.get(obj).toString().equals(model.getCode())) {
                                if (f.getType() != String.class) {
                                    return;
                                }
                                JSONObject json = new JSONObject();
                                json.put("code", f.get(obj).toString());
                                json.put("value", model.getName());
                                f.setAccessible(true);
                                try {
                                    f.set(obj, json.toString());
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}