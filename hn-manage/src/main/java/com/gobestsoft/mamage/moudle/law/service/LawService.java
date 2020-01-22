package com.gobestsoft.mamage.moudle.law.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.SrvLawConsultationLogMapper;
import com.gobestsoft.common.modular.dao.mapper.SrvLawConsultationMapper;
import com.gobestsoft.common.modular.dao.model.SrvLawConsultationLogPojo;
import com.gobestsoft.common.modular.dao.model.SrvLawConsultationPojo;
import com.gobestsoft.common.modular.model.LogModel;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.EmojiUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * create by li
 * on 2018/9/4 下午7:35
 */
@Service
public class LawService {

    @Autowired
    private SrvLawConsultationMapper lawConsultationMapper;
    
    @Autowired
    private SrvLawConsultationLogMapper lawConsultationLogMapper;


    /**
     * 获取法律资讯列表
     *
     * @param page
     * @return
     */
    public List<Map<String, Object>> consultation(Integer type,Integer deptId, String category,String query_status, Page page) {
    	String groupCode="";
    	if(type == 1) {
    		groupCode="lib_legal_advice";
    	}else if(type == 2) {
    		groupCode="lib_law_question";
    	}
        List<Map<String, Object>> result = lawConsultationMapper.consultation(type, groupCode, deptId, category,query_status, page);
        result.forEach(r -> {
            if (r.containsKey("create_time")) {
                r.put("create_time", DateUtil.parseAndFormat(r.get("create_time").toString(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
            }
            if (r.containsKey("reply_time") && r.get("reply_time") != null) {
                r.put("reply_time", DateUtil.parseAndFormat(r.get("reply_time").toString(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
            }
            r.put("nick_name", EmojiUtil.parseToUnicode(r.get("nick_name").toString()));
            String status = r.get("status").toString();
            if ("0".equals(status)) {
                r.put("statusDesc", "未回复");
            } else {
                r.put("statusDesc", "已回复");
            }
        });
        return result;
    }
    public List<Map<String, Object>> consultationNull(Integer type,Integer deptId, String category,String query_status, Page page) {
    	String groupCode="";
    	if(type == 1) {
    		groupCode="lib_legal_advice";
    	}else if(type == 2) {
    		groupCode="lib_law_question";
    	}
        List<Map<String, Object>> result = lawConsultationMapper.consultationNull(type, groupCode, category,query_status, page);
        result.forEach(r -> {
            if (r.containsKey("create_time")) {
                r.put("create_time", DateUtil.parseAndFormat(r.get("create_time").toString(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
            }
            if (r.containsKey("reply_time") && r.get("reply_time") != null) {
                r.put("reply_time", DateUtil.parseAndFormat(r.get("reply_time").toString(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
            }
            r.put("nick_name", EmojiUtil.parseToUnicode(r.get("nick_name").toString()));
            String status = r.get("status").toString();
            if ("0".equals(status)) {
                r.put("statusDesc", "未回复");
            } else {
                r.put("statusDesc", "已回复");
            }
        });
        return result;
    }

    /**
     * 审批法律咨询
     *
     * @return
     */
    @Transactional
    public void approveLaw(Integer id,String replyUid,String replyContent,Integer deptId) {
    	SrvLawConsultationPojo entity = new SrvLawConsultationPojo();
    	entity.setReplyTime(DateUtil.getAllTime());
    	entity.setStatus(1);
    	entity.setReplyUid(replyUid);
    	entity.setReplyContent(replyContent);
    	entity.setId(id);
    	lawConsultationMapper.updateById(entity);
    	SrvLawConsultationLogPojo log = new SrvLawConsultationLogPojo();
    	log.setStatus(1);
    	log.setConsultationId(id);
    	log.setAnswerDeptId(deptId);
    	lawConsultationLogMapper.insert(log);
    }
    
    /**
     * 法律咨询转办
     *
     * @return
     */
    @Transactional
    public void transferLaw(Integer id,String transferReason,Integer answerDeptId,Integer transferDeptId) {
    	SrvLawConsultationLogPojo log = new SrvLawConsultationLogPojo();
    	log.setStatus(0);
    	log.setConsultationId(id);
    	log.setAnswerDeptId(answerDeptId);
    	lawConsultationLogMapper.insert(log);
    	SrvLawConsultationLogPojo entity = new SrvLawConsultationLogPojo();
    	entity.setStatus(2);
//    	entity.setAnswerDeptId(answerDeptId);
    	entity.setTransferDeptId(answerDeptId);
    	entity.setTransferReason(transferReason);
    	entity.setTransferTime(DateUtil.getAllTime());
		Wrapper wrapper = new EntityWrapper().eq("consultation_id", id).eq("answer_dept_id", transferDeptId);
		lawConsultationLogMapper.update(entity, wrapper);
    }
    
    /**
     * 查询回退时组织ID
     *
     * @param consultationId
     * @return
     */
    public int consultationId(Integer consultationId) {
    	return lawConsultationLogMapper.selBackId(consultationId);
    }
    
    /**
	 * 查看审核流程
	 * @param id
	 * @return
	 */
	public List<LogModel> log(Integer id) {
		List<LogModel> logs = lawConsultationLogMapper.logList(id);
		logs.forEach(log -> {
			if(log.getStatus() == 1) {
				log.setContent("已回复！");
				log.setStatus(2);
			}else if(log.getStatus() == 2) {
				log.setFullname("由"+log.getFullname()+"转办");
				log.setContent("转办到" + log.getAnswerDept()+ ",等待处理！");
				log.setComment("操作描述：" + log.getComment());
				log.setStatus(4);
			}else if(log.getStatus() == 0) {
//				log.setFullname("由"+log.getFullname()+"转办");
				log.setContent("等待" + log.getAnswerDept()+ "处理！");
				log.setStatus(1);
			}
			if(StringUtils.isNoneBlank(log.getCheckTime())) {
				log.setCheckTime(DateUtil.parseAndFormat(log.getCheckTime(), "yyyyMMddHHmmss",
						"yyyy-MM-dd HH:mm:ss"));
			}
//			if(StringUtils.isNoneBlank(log.getCreateTime())) {
//				log.setCreateTime(DateUtil.parseAndFormat(log.getCreateTime(), "yyyyMMddHHmmss",
//						"yyyy-MM-dd HH:mm:ss"));
//			}
//			if(log.getAccount()==null){
//				log.setContent("等待" + log.getFullname() + "审核");
//			}else{
//				log.setContent("用户：" + log.getAccount() + "已审核");
//				log.setCheckTime(DateUtil.parseAndFormat(log.getCheckTime(), "yyyyMMddHHmmss",
//					"yyyy-MM-dd HH:mm:ss"));
//			}
		});
		LogModel result = new LogModel();
//		LogModel result = logs.get(0);
		result.setStatus(0);
		result.setFullname("APP用户");
		String name  =  logs.get(0).getName() ;
		result.setComment("操作描述：" +(StringUtils.isNotEmpty(name)?name:logs.get(0).getAccount() )+ "已经提交了法律咨询申请");
		result.setContent("用户：" + logs.get(0).getName() + "提交法律咨询申请");
		result.setCheckTime( DateUtil.parseAndFormat(logs.get(0).getCreateTime(), "yyyyMMddHHmmss",
				"yyyy-MM-dd HH:mm:ss"));
		logs.add(0, result);
		
		return logs;
	}
    
}
