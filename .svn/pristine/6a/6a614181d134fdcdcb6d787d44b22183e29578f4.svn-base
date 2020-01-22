package com.gobestsoft.mamage.moudle.app.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.cms.mapper.ArticleMapper;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.*;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app用户服务
 *
 * Created by li on 2018/8/30
 */
@Service
public class AppUserService {

	@Resource
    private AppUserDao appUserDao;
	
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ConstantFactory constantFactory;

    @Resource
    private ManageProperties manageProperties;

    /**
     * appuser条件查询
     */
    public List<AppUserEntity> appuUserList(Page<AppUserEntity> page,
//                                              ,Integer status,
                                              String account,
                                              String begDate,
                                              String endDate,
//                                              String userId,
                                              String nickname,
                                              Integer isMember
                                              ){
        List<AppUserEntity> resultList = appUserDao.appuUserList(page, account, nickname, begDate, endDate,isMember);
        return resultList;
    }
    
    /**
     * 根据auid 查询appuser
     */
    public HashMap<String, Object> appUserByAuid(
    		String auid
    		){
    	HashMap<String, Object> appUser = appUserDao.appUserByAuid(auid);
    	if (appUser.containsKey("create_time")) {
    		appUser.put("create_time", DateUtil.parseAndFormat(appUser.get("create_time").toString() , "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:SS"));
        }
    	return appUser;
    }

    public List<Map<String,Object>> appuUserLeaveMessageList(Page<Map<String,Object>> page,Map map){
        List<Map<String,Object>> resultList = appUserDao.appuUserLeaveMessageList(page, map);
        return resultList;
    }

    /**
     * 根据id 查询留言信息
     */
    public HashMap<String, Object> appUserLeaveMessageById(
            String id
    ){
        HashMap<String, Object> appUser = appUserDao.appUserLeaveMessageById(id);
        if (appUser.containsKey("create_time")) {
            appUser.put("create_time", DateUtil.parseAndFormat(appUser.get("create_time").toString() , "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:SS"));
        }
        return appUser;
    }


}
