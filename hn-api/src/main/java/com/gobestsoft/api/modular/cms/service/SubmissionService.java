package com.gobestsoft.api.modular.cms.service;

import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.basic.RestBasic;
import com.gobestsoft.common.modular.dao.mapper.MyContributeMapper;
import com.gobestsoft.common.modular.dao.model.MyContributePojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 我要投稿
 *
 * Created by duanmu on 2018/9/20.
 */
@Service
@Slf4j
public class SubmissionService extends RestBasic {

    @Autowired
    private MyContributeMapper myContributeMapper;

    /**
     * 提交投稿
     */
    public void addSubmission(MyContributePojo pojo){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        String createTime = sdf.format(date);
        pojo.setCreateTime(createTime);
        myContributeMapper.addSubmission(pojo);
    }

    /**
     * 我的投稿
     */
    public List<MyContributePojo> mySubmission(BasicRowBounds bounds, String auid){
        List<MyContributePojo> list = myContributeMapper.mySubmission(bounds, auid);
        return list;
    }

    /**
     * 删除投稿
     */
    public void delSubmission(Integer id){
        myContributeMapper.delSubmission(id);
    }

}
