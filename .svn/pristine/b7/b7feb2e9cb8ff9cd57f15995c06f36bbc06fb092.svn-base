package com.gobestsoft.api.modular.srv.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.common.modular.dao.mapper.XlyzMapper;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XlyzService {

    @Autowired
    XlyzMapper xlyzMapper;

    @Resource
    ApiProperties apiProperties;

    /**
     * 查询心灵驿站首页banner
     * @return
     */
    public Map getHomeInfo(){

        Page page  = new Page();

        page.setSize(5);

        List<Map<String, Object>> articles = selectArticles(page);


        List<Map<String, Object>> bannerList = xlyzMapper.selectBannerList();

        page.setSize(3);

        List<Map<String, Object>> masterList = selectMasters(page);


        Map map = new HashMap();

        map.put("articles",articles);
        map.put("bannerList",bannerList);
        map.put("masterList",masterList);

        return map;
    }

    /**
     * 查询心理知识列表以及阅读量
     * @param page
     * @return
     */
    public List<Map<String,Object>> selectArticles(Page page){
        List<Map<String, Object>> articles = xlyzMapper.selectArticles(page);

        List<String> articleId_list= new ArrayList<>();

        for(Map m:articles){
            articleId_list.add(m.get("id")+"");
            String cover_path= (String) m.get("cover_path");
            m.remove(cover_path);
            m.put("covers",new String[0]);
            if(StringUtils.isNotEmpty(cover_path)){
                m.put("covers",WebSiteUtil.getBrowseUrl(cover_path.split(",")));
            }
            if(m.get("jump_url")!=null && Integer.parseInt(m.get("info_type")+"")==3){
                m.put("jump_url",   WebSiteUtil.getBrowseUrl(m.get("jump_url")+""));
            }else{
                m.put("jump_url",   apiProperties.getReviewBaseUrl() +m.get("id"));
            }
            m.put("create_time", DateUtil.parseAndFormat(m.get("create_time")+"","yyyyMMddHHmmss","yyyy-MM-dd"));
        }
        if(articleId_list.size()==0){
            return articles;
        }
        List<Map<String, Object>> articleReading = xlyzMapper.selectArticleReading(articleId_list);

        for(Map ar:articles){
            ar.put("browse_num",0);
            for (Map read:articleReading){
                if((read.get("article_id")+"").equals((ar.get("id")+""))){
                    ar.put("browse_num",read.get("counting"));
                    break;
                }
            }
        }
        return articles;
    }

    /**
     * 获取导师列表
     * @param page
     * @return
     */
    public List<Map<String,Object>> selectMasters(Page page){
        List<Map<String, Object>> masterList = xlyzMapper.selectMasterList(page);
        for(Map m: masterList){
            if(m.get("avatar")!=null){
                m.put("avatar", WebSiteUtil.getBrowseUrl(m.get("avatar")+""));
            }else{
                m.put("avatar","");
            }
        }
        return masterList;
    }
}
