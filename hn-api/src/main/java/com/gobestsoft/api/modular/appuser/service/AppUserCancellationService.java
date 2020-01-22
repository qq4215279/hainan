package com.gobestsoft.api.modular.appuser.service;

import com.gobestsoft.api.modular.basic.RestBasic;
import com.gobestsoft.common.modular.appuser.dao.AppUserCancellationMapper;
import com.gobestsoft.common.modular.appuser.model.AppUserCancellation;
import com.gobestsoft.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注销用户
 * @author xiat
 * @time 2018-12-06 19:23
 */
@Service
public class AppUserCancellationService extends RestBasic {

    @Autowired
    private AppUserCancellationMapper appUserCancellationMapper;

    /**
     * 保存注销记录
     * @param auid
     * @param account
     * @param nickName
     * @param type
     * @param reason
     */
    public void saveCancellation(String auid,String account,String nickName,Integer type,String reason){
        //当前时间获取
        String nowtime = DateUtil.getAllTime();
        AppUserCancellation appUserCancellation=new AppUserCancellation();
        appUserCancellation.setAuid(auid);
        appUserCancellation.setAccount(account);
        appUserCancellation.setNickName(nickName);
        appUserCancellation.setType(type);
        appUserCancellation.setReason(reason);
        appUserCancellation.setCreateTime(nowtime);
        appUserCancellationMapper.insert(appUserCancellation);

    }

    /**
     * 获取lib_delete_account 字典name
     * @param type
     * @return
     */
    public String getDictionarieName(Integer type){

        return appUserCancellationMapper.getDictionarieName(type);
    }


}
