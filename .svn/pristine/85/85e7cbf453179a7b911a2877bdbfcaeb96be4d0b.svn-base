package com.gobestsoft.mamage.moudle.app.service;

import cn.jpush.api.push.PushResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.AppMessageMapper;
import com.gobestsoft.common.modular.dao.mapper.AppMessageRelationMapper;
import com.gobestsoft.common.modular.dao.mapper.CmsArticleMapper;
import com.gobestsoft.common.modular.dao.model.AppMessagePojo;
import com.gobestsoft.common.modular.dao.model.AppMessageRelationPojo;
import com.gobestsoft.common.modular.dao.model.CmsArticlePojo;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.jpush.PushUtil;
import com.gobestsoft.jpush.model.MessagePushModel;
import com.gobestsoft.jpush.model.PushModel;
import com.gobestsoft.jpush.model.PushPlatform;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.moudle.app.dto.MessageDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * create by gutongwei
 * on 2018/7/3 上午11:02
 */

@Service("aMsg")
public class MessageService {

    @Autowired
    private PushUtil pushUtil;

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Autowired
    private AppMessageRelationMapper appMessageRelationMapper;

    @Autowired
    private CmsArticleMapper cmsArticleMapper;

    @Autowired
    private ManageProperties manageProperties;

    public void sendMessageByAuid(MessageDto dto, String sysUser) {
        sendMessageByAuid(dto.getCategoryId(), dto.getTitle(), dto.getContent(), dto.getOperationMode(), dto.getArticleId(), dto.getJump_url(), dto.getIsPush() == 1, dto.getAuids(), sysUser);
    }

    /**
     * 发送消息
     * create by gutongwei
     * 2018/6/12
     *
     * @param category 消息类型
     * @param title    标题
     * @param content  消息内容
     * @param mode     用户操作：0：无操作。1：重新登录。2：打开资讯。3：跳转网页。
     * @param targetId 目标ID
     * @param jumpUrl  跳转地址
     * @param isPush   是否推送
     * @param auids    用户ids
     */
    @Transactional
    public void sendMessageByAuid(int category, String title, String content,
                                  int mode, String targetId, String jumpUrl, boolean isPush, String[] auids) {
        sendMessageByAuid(category, title, content, mode, targetId, jumpUrl, isPush, auids, "sys");
    }



    /**
     * 发送消息
     * create by gutongwei
     * 2018/6/12
     *
     * @param category 消息类型
     * @param title    标题
     * @param content  消息内容
     * @param mode     用户操作：0：无操作。1：重新登录。2：打开资讯。3：跳转网页。
     * @param targetId 目标ID
     * @param jumpUrl  跳转地址
     * @param jumpUrl  跳转地址
     * @param needPush 是否推送
     * @param auids    用户ids
     */
    @Transactional
    public void sendMessageByAuid(int category, String title, String content,
                                  int mode, String targetId, String jumpUrl, boolean needPush, String[] auids, String userId) {

        sendMessageByAuidOperation(category,title,content,mode,targetId,jumpUrl,needPush,auids,userId,null);
    }

    /**
     * 自定义operation的参数
     * @param category
     * @param title
     * @param content
     * @param mode
     * @param targetId
     * @param jumpUrl
     * @param needPush
     * @param auids
     * @param userId
     * @param operation
     */
    @Transactional
    public void sendMessageByAuidOperation(int category, String title, String content,
                                  int mode, String targetId, String jumpUrl, boolean needPush, String[] auids, String userId,Map<String,String> operation) {
        if (mode == 2) {
            jumpUrl = manageProperties.getArticleAccess() + targetId;
        }
        AppMessagePojo messagePojo = new AppMessagePojo();
        messagePojo.setTitle(title);
        messagePojo.setContent(content);
        messagePojo.setOperationMode(mode);
        messagePojo.setCategoryId(category);
        messagePojo.setIsPush(needPush ? 1 : 0);
        if(operation==null){
            operation = new HashMap<>();
        }
        operation.put("id", targetId);
        operation.put("jump_url", jumpUrl);
        messagePojo.setOperation(JSON.toJSONString(operation));
        if (auids == null || auids.length == 0) {
            messagePojo.setAppoint(0);
        } else {
            messagePojo.setAppoint(1);
        }
        messagePojo.setCreateTime(DateUtil.getAllTime());
        messagePojo.setCreateUser(userId);
        appMessageMapper.insert(messagePojo);
        if (auids != null && auids.length > 0) {
            List<AppMessageRelationPojo> pojos = new ArrayList<>();
            Arrays.asList(auids).forEach(auid -> {
                AppMessageRelationPojo pojo = new AppMessageRelationPojo();
                pojo.setIsOpen(0);
                pojo.setMessageId(messagePojo.getId());
                pojo.setOpenTime(DateUtil.getAllTime());
                pojo.setAuid(auid);
                pojo.setDelFlg(0);
                pojos.add(pojo);
            });
            appMessageRelationMapper.insertList(pojos);
        }

        if (needPush) {
            operation.put("mode", String.valueOf(mode));
            operation.put("content", content);
            operation.put("title", title);
            PushModel pushModel = new PushModel();
            if (auids != null && auids.length > 0) {
                String[] registerIds = appMessageMapper.getRegisterIds(auids);
                if (registerIds == null || registerIds.length <= 0) {
                    return;
                }
                pushModel.setRegistrationId(registerIds);
            } else {
                pushModel.setAllRegistration(true);
            }
            pushModel.setAlert(title);
            pushModel.setPlatform(PushPlatform.All);
            pushModel.setExtras(operation);
            pushUtil.push(pushModel);
        }
    }




    /**
     * 获取消息列表
     *
     * @param page
     * @param categoryId
     * @param appoint
     * @return
     */
    public List<Map<String, String>> getAllMessage(Page<Map<String, String>> page, Integer categoryId, Integer appoint) {

        List<Map<String, String>> result = appMessageMapper.getAllMessage(page, categoryId, appoint);
        for (Map<String, String> map : result) {
            if (map.containsKey("create_time")) {
                map.put("create_time", DateUtil.parseAndFormat(map.get("create_time"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
            }

            if (map.containsKey("appoint")) {
                if ("0".equals(String.valueOf(map.get("appoint")))) {
                    map.put("appoint", "所有用户");
                } else {
                    map.put("appoint", "指定用户");
                }
            }
            if (map.containsKey("is_push")) {
                if ("0".equals(map.get("is_push"))) {
                    map.put("is_push", "否");
                } else {
                    map.put("is_push", "是");
                }
            }
        }
        return result;
    }


    public List<Dict> getMessageCategory() {
        List<Dict> result = new ArrayList<>();
        List<Map<String, String>> category = appMessageMapper.getAllMessageCategory();
        for (Map<String, String> map : category) {
            Dict dict = new Dict();
            dict.setCode(String.valueOf(map.get("id")));
            dict.setName(map.get("name"));
            result.add(dict);
        }
        return result;
    }


    /**
     * 获取消息
     *
     * @param messageId
     * @return
     */
    public Object getMessage(int messageId) {
        Map<String, Object> result = new HashMap<>();
        AppMessagePojo messageEntity = appMessageMapper.selectById(messageId);
        if (messageEntity != null) {
            result.put("message", messageEntity);
            if (messageEntity.getAppoint() == 1) {
                result.put("users", appMessageMapper.getMessageRelationAppUser(messageId));
            }
            HashMap<String, String> extras = JSON.parseObject(messageEntity.getOperation(), HashMap.class);
            if (messageEntity.getOperationMode() == 2) {
                String articleId = extras.get("id");
                if (StringUtils.isNotEmpty(articleId)) {
                    CmsArticlePojo articlePojo = cmsArticleMapper.selectById(articleId);
                    if (articlePojo != null) {
                        extras.put("articleName", articlePojo.getTitle());
                    }
                }
            }
            result.put("extras", extras);

        }
        return result;
    }

    /**
     * 用户被挤下线发送推送消息
     *
     * @param registerId
     */
    public void loginExcludePush(String registerId) {
        MessagePushModel pushModel = new MessagePushModel("您的帐号已在其他设备登录,已被强制登出,是否重新登录");
        pushModel.setRegistrationId(new String[]{registerId});
        Map<String, String> extras = new HashMap<>();
        extras.put("mode", "-2");
        extras.put("content", "您的帐号已在其他设备登录,已被强制登出,是否重新登录");
        pushModel.setExtras(extras);
        PushResult PushResult = pushUtil.pushMessage(pushModel);
    }

}
