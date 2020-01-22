package com.gobestsoft.mamage.moudle.srv.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.SrvStraitenedFirstMapper;
import com.gobestsoft.common.modular.dao.mapper.SrvStraitenedLogMapper;
import com.gobestsoft.common.modular.dao.mapper.SrvStraitenedMapper;
import com.gobestsoft.common.modular.dao.mapper.SrvTjMapper;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedFirstPojo;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedLogPojo;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedPojo;
import com.gobestsoft.common.modular.dao.model.StaffSkillPojo;
import com.gobestsoft.common.modular.model.LogModel;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.app.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;


/**
 * create by xiashasha
 * on 2018/9/15 上午11:00
 */
@Service
public class SrvStraitenedService extends ManageBasic{

    @Autowired
    private SrvStraitenedFirstMapper straitenedFirstMapper;
    
    @Autowired
    private SrvStraitenedMapper straitenedMapper;
    
    @Autowired
    private SrvStraitenedLogMapper straitenedLogMapper;
    
    @Autowired
    DeptDao deptDao;
    @Autowired
    private MessageService messageService;

    @Autowired
	private SrvTjMapper srvTjMapper;

    /**
     * 添加职工技能需求
     */
    public void addstaffSkill(StaffSkillPojo staffSkillPojo){
        srvTjMapper.insert(staffSkillPojo);
    }
	/**
	 * 获取职工技能列表
	 * @param page
	 * @param name
	 * @param mobile
	 * @return
	 */
	public List<Map<String, Object>> getlist(Page<Map<String, Object>> page, String name, String mobile) {
		List<Map<String,Object>> straitenedFirstList = this.srvTjMapper.getlist(page, name,mobile);
		return straitenedFirstList;
	}

	/**
	 * 获取困难帮扶统计
	 * @return
	 */
	public Map<String,Object> getSrvTj(){
		List<LinkedHashMap<String,Object>> list=srvTjMapper.getBfType();
		List<LinkedHashMap<String,Object>> list2=srvTjMapper.getRegion();
		List<LinkedHashMap<String,Object>> list3=srvTjMapper.getBfaAge();
		List<LinkedHashMap<String,Object>> list4=srvTjMapper.getBfPass();
		List<Map<String,Object>> list5=new ArrayList<>();
		for (Map map:list2) {
			map.get("name");
		}
		Map<String,Object> map=new HashMap<>();
		map.put("bfTypeTj",list);
		map.put("bfRegionTj",list2);
		map.put("bfAgeTj",list3);
		map.put("bfPassTj",list4);
    	return map;
	}
    /**
     * 根据条件查询困难帮扶初审列表
     * @param page
     * @param name
     * @param status
     * @param type
     * @param workUnit
     * @param deptId
     * @return
     */
	public List<Map<String, Object>> selectFirstByCondition(Page<Map<String, Object>> page, String name, Integer status,Integer type,
			String workUnit, Integer deptId) {
		List<Map<String,Object>> straitenedFirstList = this.straitenedFirstMapper.selectByCondition(page, name, status, type, workUnit, deptId);
		return straitenedFirstList;
	}
	
	/**
	 * 根据条件查询困难帮扶终审列表
	 * @param page
	 * @param name
	 * @param status
	 * @param type
	 * @param workUnit
	 * @param deptId
	 * @return
	 */
	public List<Map<String, Object>> selectByCondition(Page<Map<String, Object>> page, String name, Integer status,Integer type,
			String workUnit, Integer deptId) {
		List<Map<String,Object>> straitenedList = this.straitenedMapper.selectByCondition(page, name, status, type, workUnit, deptId);
		return straitenedList;
	}

	/**
	 * 根据id查询初审详情
	 * @param id
	 * @return
	 */
	public SrvStraitenedFirstPojo selectFirstById(Integer id) {
		return straitenedFirstMapper.selectById(id);
	}
	
	/**
	 * 根据id查询终审详情
	 * @param id
	 * @return
	 */
	public SrvStraitenedPojo selectById(Integer id){
		return straitenedMapper.selectById(id);
	}
	
	/**
	 * 提交初级审核情况
	 * @param straitenedFirst
	 * @param comment
	 * @param straitenedLogId
	 * @throws IOException
	 */
	public void submitFirst(SrvStraitenedFirstPojo straitenedFirst, String comment , Integer straitenedLogId)throws IOException{
		Integer deptId = getLoginUser().getDeptId();// 当前组织
		String uid = getLoginUser().getId();// 当前登录人uid
		Integer level = getLoginUser().getDept().getLevel();// 当前等级
		
		SrvStraitenedLogPojo straitenedLog = straitenedLogMapper.selectById(straitenedLogId);
		straitenedLog.setStatus(straitenedFirst.getStatus());
		straitenedLog.setComment( comment);
		straitenedLog.setCheckUid(uid);
		straitenedLog.setCheckDate(DateUtil.getAllTime());
		straitenedLogMapper.updateById(straitenedLog);
		
		SrvStraitenedLogPojo logPojo = new SrvStraitenedLogPojo();
		if(straitenedFirst.getStatus() == 2 && level !=1) { // 审核通过且非省级下增添日志表
			straitenedFirst.setStatus(1);
			logPojo.setStatus(1);// 如果非市级，新增的日志状态处于待审核（市级登录时进行审核）
			logPojo.setType(0);// 初级审核
			logPojo.setCheckDeptId(deptDao.selectByOrgId(deptId));
			logPojo.setStraitenedId(straitenedFirst.getId());
			logPojo.setCreateUserType(1);
			logPojo.setCreateUid(uid);
			logPojo.setCreateTime(DateUtil.getAllTime());
			straitenedLogMapper.insert(logPojo);// 插入新的日志信息
		}
		straitenedFirstMapper.updateById(straitenedFirst);// 修改主表信息

	}
	
	/**
	 * 提交终极审核情况
	 * @param straitened
	 * @param comment
	 * @param straitenedLogId
	 * @throws IOException
	 */
    public void submit(SrvStraitenedPojo straitened, String comment , Integer straitenedLogId)throws IOException{
    	Integer deptId = getLoginUser().getDeptId();// 当前组织
		String uid = getLoginUser().getId();// 当前登录人uid
		Integer level = getLoginUser().getDept().getLevel();// 当前等级
		
		SrvStraitenedLogPojo straitenedLog = straitenedLogMapper.selectById(straitenedLogId);
		straitenedLog.setStatus(straitened.getStatus());
		straitenedLog.setComment( comment);
		straitenedLog.setCheckUid(uid);
		straitenedLog.setCheckDate(DateUtil.getAllTime());
		straitenedLogMapper.updateById(straitenedLog);
		
		SrvStraitenedLogPojo logPojo = new SrvStraitenedLogPojo();
		if(straitened.getStatus() == 2 && level != 1) { // 审核通过且非省级下增添日志表
			straitened.setStatus(1);
			logPojo.setStatus(1);// 如果非市级，新增的日志状态处于待审核（市级登录时进行审核）
			logPojo.setType(1);// 终级审核
			logPojo.setCheckDeptId(deptDao.selectByOrgId(deptId));
			logPojo.setStraitenedId(straitened.getId());
			logPojo.setCreateUserType(1);
			logPojo.setCreateUid(uid);
			logPojo.setCreateTime(DateUtil.getAllTime());
			straitenedLogMapper.insert(logPojo);// 插入新的日志信息
		}
		straitenedMapper.updateById(straitened);// 修改主表信息
	}
	
	/**
	 * 查看审核流程
	 * @param id
	 * @return
	 */
	public List<LogModel> log(Integer id,int type){
		List<LogModel> logs = straitenedLogMapper.selectListMapById(id,type);
		logs.forEach(log -> {
			if(log.getAccount()==null){
				log.setContent("等待" + log.getFullname() + "审核");
			}else{
				log.setContent("用户：" + log.getAccount() + "已审核");
				log.setCheckTime(DateUtil.parseAndFormat(log.getCheckTime(), "yyyyMMddHHmmss",
					"yyyy-MM-dd HH:mm:ss"));
			}
		});
		LogModel result = new LogModel();
		result.setStatus(0);
		result.setFullname("APP用户");
		String comment = "";
		String content = "";
		if(type == 1){// 1代表终审
			comment = "操作描述：" + logs.get(0).getName() + "已经提交了困难帮扶终审申请";
			content = "用户：" + logs.get(0).getName() + "提交困难帮扶终审申请";
		}else if (type == 0){ // 代表初审
			comment = "操作描述：" + logs.get(0).getName() + "已经提交了困难帮扶初审申请";
			content = "用户：" + logs.get(0).getName() + "提交困难帮扶初审申请";
		}		
		result.setComment(comment);
		result.setContent(content);
		result.setCheckTime( DateUtil.parseAndFormat(logs.get(0).getCreateTime(), "yyyyMMddHHmmss",
				"yyyy-MM-dd HH:mm:ss"));
		logs.add(0, result);
		return logs;
	}


	@Transactional
	public Tip updateTransfer(Integer id, Integer deptId,Integer type, String transferReason){

		int update = 0;

		if(type==0){
			update=straitenedFirstMapper.updateAuditDeptId(id, deptId, transferReason,type);
			SrvStraitenedFirstPojo old = straitenedFirstMapper.selectById(id);
			if(update==0)return new Tip(500,"状态被修改，请刷新列表后重试",null);
			SrvStraitenedLogPojo pojo = new SrvStraitenedLogPojo();
			pojo.setStatus(1);
			pojo.setCreateTime(DateUtil.getAllTime());
			pojo.setCheckDeptId(deptId);
			pojo.setCreateUid(old.getAuid());
			pojo.setType(type);
			pojo.setStraitenedId(id);
			straitenedLogMapper.insert(pojo);
		}
		if(type==1){
			update=straitenedMapper.updateAuditDeptId(id, deptId, transferReason,type);
			SrvStraitenedPojo old = straitenedMapper.selectById(id);
			if(update==0)return new Tip(500,"状态被修改，请刷新列表后重试",null);
			SrvStraitenedLogPojo pojo = new SrvStraitenedLogPojo();
			pojo.setStatus(1);
			pojo.setCreateTime(DateUtil.getAllTime());
			pojo.setCheckDeptId(deptId);
			pojo.setCreateUid(old.getAuid());
			pojo.setType(type);
			pojo.setStraitenedId(id);
			straitenedLogMapper.insert(pojo);
		}

		return new Tip(200,null,null);
	}




	/**
	 * 获取终审详情
	 *
	 * @param id
	 * @return
	 */
	public Map<String, Object> getFinalDetail(String id) {
		Map<String, Object> map = straitenedMapper.selectInfoById(id);
        String newPattern = "yyyy年MM月dd日";
//

		if (map != null) {
			ToolUtil.formartMapDate(map, "birthday", newPattern);
			ToolUtil.formartMapDate(map, "work_time", newPattern);
			ToolUtil.formartMapDate(map, "laborContractStart", newPattern);
			ToolUtil.formartMapDate(map, "createTime", "yyyyMMddHHmmss",newPattern);

			List<Map<String, Object>> contacts = straitenedMapper.selectContacts(id,0);
			ToolUtil.transferKey(contacts);
			map.put("contacts", contacts);

			//        处理特殊值

			List<Map<String, Object>> medicalList = straitenedMapper.selectMedicalList(id);
			ToolUtil.transferKey(medicalList);
			List<Map<String, Object>> studyList = straitenedMapper.selectStudyList(id);
			ToolUtil.transferKey(studyList);
            for (Map<String, Object> m : studyList) {
                ToolUtil.formartMapDate(m, "enroll_time", newPattern);
                ToolUtil.formartMapDate(m, "graduation_time", newPattern);
            }
			map.put("medicals",medicalList);
			map.put("studies",studyList);
			map.put("memberInfo",straitenedMapper.selectMemberByAuId(map.get("auid")+""));

            List<LogModel> logModels = straitenedLogMapper.selectListMapRealById(Integer.valueOf(id));
            if(logModels!=null && logModels.size()!=0){
                logModels.forEach(log->{
                    if(StringUtils.isNotEmpty(log.getCheckTime())){
                        log.setCheckTime(DateUtil.parseAndFormat(log.getCheckTime(),"yyyyMMddHHmmss","yyyy年MM月dd日"));
                    }
                });
            }
            map.put("logModels",logModels);
            ToolUtil.transferKey(map);
		}
		return map;
	}






	/**
	 * 给客户发送超时提醒
	 */
	@Transactional
	public void sendExpireFirstNotice(int day){

		boolean hasnext = true;

		while (hasnext){
			List<Map<String, Object>> list = straitenedFirstMapper.selectExpireAudit(day);
			if(list!=null && list.size()>0){

				List<String> idlist =  new ArrayList<>();
				List<String> auidlist =  new ArrayList<>();

				list.forEach(map->{

					idlist.add(map.get("id")+"");
					auidlist.add(map.get("auid")+"");

				});
				straitenedFirstMapper.updateExpireAudit(idlist);
				straitenedFirstMapper.updateExpireLog(idlist);

				list.forEach(map->{
					int category=4;
					int mode = 6;//法律援助
					int targetId = Integer.valueOf(map.get("id")+"");
					boolean isPush = true;
					String title = "困难帮扶初审审核超时";
					String comment = "本级工会超时未审核，请重新提交申请";
					String[] auids= {map.get("auid")+""};
					messageService.sendMessageByAuid(category,title,comment,mode,targetId+"",null,isPush,auids);

				});
			}else{
				hasnext=false;
			}
		}



	}



	/**
	 * 给客户发送超时提醒
	 */
	@Transactional
	public void sendExpireFinalNotice(int day){

		boolean hasnext = true;

		while (hasnext){
			List<Map<String, Object>> list = straitenedMapper.selectExpireAudit(day);
			if(list!=null && list.size()>0){

				List<String> idlist =  new ArrayList<>();
				List<String> auidlist =  new ArrayList<>();

				list.forEach(map->{

					idlist.add(map.get("id")+"");
					auidlist.add(map.get("auid")+"");

				});
				straitenedMapper.updateExpireAudit(idlist);
				straitenedMapper.updateExpireLog(idlist);

				list.forEach(map->{
					int category=4;
					int mode = 6;//法律援助
					int targetId = Integer.valueOf(map.get("id")+"");
					boolean isPush = true;
					String title = "困难帮扶终审审核超时";
					String comment = "本级工会超时未审核，请重新提交申请";
					String[] auids= {map.get("auid")+""};
					messageService.sendMessageByAuid(category,title,comment,mode,targetId+"",null,isPush,auids);

				});
			}else{
				hasnext=false;
			}
		}



	}

    /**
     * 初审和终审的列表
     * @param rowBounds
     * @param query
     * @return
     */
	public List<Map<String,Object>>getStraitenedAll(RowBounds rowBounds, ObjectMap query){

        List<Map<String,Object>> list = straitenedMapper.getStraitenedAll(rowBounds,query);
		List<String> idlist = new ArrayList<>();
        if(list!=null && list.size()==0){
            return list;
        }
        Calendar ca = Calendar.getInstance();
        list.forEach(o->{
            String certificateNum = (String) o.get("certificateNum");
            if(certificateNum!=null){

                if(o.get("sex")==null||o.get("birthday")==null){
                    String birthday = certificateNum.substring(6, 14);
                    int year = DateUtil.getDiffYear(birthday, DateUtil.format(ca.getTime(),"yyyy-MM-dd"));
                    o.put("age",year);
                    o.put("birthday",birthday);
                    try {
                        String sex = Integer.valueOf(certificateNum.substring(16, 17))%2+"";
                        o.put("sex",sex);
                    }catch (Exception e){
                    }
                }
            }
		    idlist.add(o.get("id")+"");
        });

        List<Map<String, Object>> logIdList = straitenedMapper.selectLogIdList(idlist);
        logIdList.forEach(log->{
            int straitened_id = (int) log.get("straitened_id");
            list.forEach(o->{
                int id = (int) o.get("id");
                if(id==straitened_id){
                    o.put("straitenedLogId",log.get("straitenedLogId"));
                }
            });
        });

        return list;
	}

	public Integer getStraitenedIdByStudyId(int id){
		return straitenedMapper.selectStraitenedIdByStudyId(id);
	};

	public Integer getStraitenedIdByMedicalId(int id){
		return straitenedMapper.selectStraitenedIdByMedicalId(id);
	};
}
