package com.gobestsoft.api.modular.srv.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.srv.service.LawService;
import com.gobestsoft.api.modular.srv.service.XlyzService;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.modular.dao.mapper.XlyzAmMapper;
import com.gobestsoft.common.modular.dao.mapper.XlyzMapper;
import com.gobestsoft.common.modular.dao.model.XlyzAmPojo;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.WebSiteUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 心灵驿站app接口
 */
@RestController
@RequestMapping("/xlyz")
public class XlyzController extends BaseController {

    @Autowired
    XlyzService xlyzService;

    @Resource
    RedisUtils redisUtils;

    @Autowired
    XlyzAmMapper xlyzAmMapper;

    @Autowired
    XlyzMapper xlyzMapper;

    @Resource
    ApiProperties apiProperties;

    /**
     * 获取首页内容
     *
     * @return
     */
    @RequestMapping("/getHome")
    public BaseResult getHome() {
        //获取banner

        Map cache = (Map) redisUtils.get(CacheConstant.APP_HEART_POST);


        if (cache != null ) {
            return success(cache);
        }

        cache = xlyzService.getHomeInfo();
        if (cache != null) {
            List<Map<String, Object>> articles = (List<Map<String, Object>>) cache.get("articles");
            String[] delList = {"is_on_top_flag", "sub_title"};
            removeValue(articles, delList);
            List<Map<String,Object>> bannerList = (List<Map<String, Object>>) cache.get("bannerList");

            for(Map b:bannerList){
                b.put("cover",WebSiteUtil.getBrowseUrl(b.get("cover")+""));
                    if(StringUtils.isNotEmpty((b.get("target_id")+""))){
                    b.put("jump_url",   apiProperties.getReviewBaseUrl() +b.get("target_id"));
                }
            }
        }

        JSONObject images = (JSONObject) getAppConfig("images");

        //获取图标地址
        JSONArray icons = images.getJSONObject("xlyz").getJSONArray("home_icon");



        for(Object o:icons){
            JSONObject obj = (JSONObject) o;
            obj.put("icon",WebSiteUtil.getBrowseUrl(obj.getString("icon")));
        }

        cache.put("icons", icons);
        redisUtils.set(CacheConstant.APP_HEART_POST, cache,120l);
        return success(cache);
    }



    /**
     * 获取心理知识列表
     *
     * @return
     */
    @RequestMapping("/getArticles")
    public BaseResult getArticles() {

        try {
            Page page = getDefaultPage();
            String[] delList = {"is_on_top_flag", "sub_title"};
            return success(removeValue(xlyzService.selectArticles(page), delList));

        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }


    @RequestMapping("/getMasters")
    public BaseResult getMasters() {
        try {
            Page page = getDefaultPage();
            return success(xlyzService.selectMasters(page));

        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }


    @RequestMapping("/getAudioList")
    public BaseResult getAudioList() {


        BasicRowBounds pageBounds = getPageBounds();

        Wrapper<XlyzAmPojo> wrapper = new EntityWrapper<>();

        wrapper.orderBy("create_time", false);

        List<XlyzAmPojo> list = xlyzAmMapper.selectPage(pageBounds, wrapper);

        for (XlyzAmPojo pojo : list) {
            pojo.setAttachment(WebSiteUtil.getBrowseUrl(pojo.getAttachment()));

            pojo.setCover(WebSiteUtil.getBrowseUrl(pojo.getCover()));


        }

        return success(list);

    }
    @Autowired
    LawService lawService;

    @RequestMapping("")
    public Object xlyz(){
        return lawService.lawSupportDetail(110);
    }


}
