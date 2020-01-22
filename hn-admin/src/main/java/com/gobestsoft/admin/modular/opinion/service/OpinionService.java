package com.gobestsoft.admin.modular.opinion.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.opinion.mapper.OpinionLogMapper;
import com.gobestsoft.common.modular.opinion.mapper.OpinionMapper;
import com.gobestsoft.common.modular.opinion.model.Opinion;
import com.gobestsoft.common.modular.opinion.model.OpinionLog;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.EmojiUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.constant.factory.IConstantFactory;
import com.gobestsoft.mamage.model.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 建言献策
 */
@Service
public class OpinionService {

    @Resource
    private OpinionMapper opinionMapper;

    @Resource
    private OpinionLogMapper opinionLogMapper;

    @Resource
    IConstantFactory constant;


    /**
     * 列表
     */
    public List<Map<String, String>> selectByCondition(Page<Map<String, String>> page, String startTime, String endTime,Integer type,Integer status,Integer orgId) {
        List<Map<String, String>> result = opinionMapper.selectByCondition(page, startTime, endTime,type,status,orgId);
        result.forEach(m -> {
            if (ToolUtil.isNotEmpty(m.get("nick_name"))) {
                m.put("nick_name", EmojiUtil.parseToUnicode(m.get("nick_name")));
            }
            if (ToolUtil.isNotEmpty(m.get("content"))) {
                String content = EmojiUtil.parseToUnicode(m.get("content"));
                m.put("content", content.length() > 60 ? content.substring(0, 56) + "..." : content);
                m.put("content_desc", content);
            }
            if (ToolUtil.isNotEmpty(m.get("contact"))) {
                m.put("contact", EmojiUtil.parseToUnicode(m.get("contact")));
            }
            if (ToolUtil.isNotEmpty(m.get("create_time"))) {
                m.put("create_time", DateUtil.parseAndFormat(m.get("create_time"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
            }
            if(ToolUtil.isNotEmpty(m.get("type"))){
                Dict dict = constant.findInDictName(DictCodeConstants.LIB_OPINION_TYPE, String.valueOf(m.get("type")));
                m.put("type_name", dict.getName());
            }
            if(ToolUtil.isNotEmpty(m.get("status"))){
                Dict dict = constant.findInDictName(DictCodeConstants.LIB_OPINION_STATUS, String.valueOf(m.get("status")));
                m.put("status_name", dict.getName());
            }
        });
        return result;
    }

    /**
     * 详情
     */
    public Object getDetailById(Integer id) {
        Opinion pojo = opinionMapper.selectById(id);
        if(ToolUtil.isNotEmpty(pojo.getType())){
            Dict dict = constant.findInDictName(DictCodeConstants.LIB_OPINION_TYPE, String.valueOf(pojo.getType()));
            pojo.setTypeName(dict.getName());
        }
        if(ToolUtil.isNotEmpty(pojo.getStatus())){
            Dict dict = constant.findInDictName(DictCodeConstants.LIB_OPINION_STATUS, String.valueOf(pojo.getStatus()));
            pojo.setStatusName(dict.getName());
        }
        return pojo;
    }

    /**
     * 获取回复内容
     */
    public String getReplyContent(Integer opinionId,Integer orgId) {
        return opinionLogMapper.selectReplyContent(opinionId, orgId);
    }


    /**
     * 流程
     */
    public List<Map<String, Object>> getLogByLogId(Integer id) {
        List<Map<String, Object>> list = opinionLogMapper.selectListByCondition(id);
        list.forEach(map ->{
            if(ToolUtil.isNotEmpty(map.get("createDate"))){
                Date date = DateUtil.parseDateTime(map.get("createDate").toString());
                map.put("createDate",DateUtil.getTime(date));
            }
        });
        return list;
    }

    /**
     * 审核
     */
    public void reply(OpinionLog logPojo, LoginUser user) {
        logPojo.setOrgId(user.getDeptId());
        String now = DateUtil.getAllTime();
        logPojo.setReplyTime(now);

        opinionLogMapper.update(logPojo);

        Opinion opinion = new Opinion();
        opinion.setId(logPojo.getOpinionId());
        opinion.setStatus(logPojo.getStatus());
        opinionMapper.updateById(opinion);
    }

}
