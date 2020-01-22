package com.gobestsoft.api.modular.cms.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.cms.model.ArticlesDto;
import com.gobestsoft.common.modular.dao.mapper.CkyHonorMapper;
import com.gobestsoft.common.modular.dao.mapper.CkyStudioMapper;
import com.gobestsoft.common.modular.dao.mapper.CkyWorkerMapper;
import com.gobestsoft.common.modular.dao.model.CkyHonorPojo;
import com.gobestsoft.common.modular.dao.model.CkyWorkerPojo;
import com.gobestsoft.core.util.EmojiUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.TransBeanUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/6/6 下午1:42
 */
@Service
public class CkyService {

    @Autowired
    private CkyStudioMapper ckyStudioMapper;

    @Autowired
    private CkyHonorMapper ckyHonorMapper;

    @Autowired
    private InformationService informationService;


    @Autowired
    private CkyWorkerMapper workerMapper;


    /**
     * 工作室详细
     * create by gutongwei
     * on 2018/6/13
     *
     * @param studioId
     * @return
     */
    public Map<String, Object> studioDetail(int studioId) {
        Map<String, Object> detail = ckyStudioMapper.studioDetail(studioId);
        if (detail != null) {
            if (detail.containsKey("banner")) {
                detail.put("banner", WebSiteUtil.getBrowseUrl(String.valueOf(detail.get("banner")).split(",")));
            }
            if (detail.containsKey("cover")) {
                detail.put("cover", WebSiteUtil.getBrowseUrl(String.valueOf(detail.get("cover"))));
            }
            if (detail.containsKey("summary")) {
                detail.put("summary", HtmlUtils.htmlUnescape(EmojiUtil.parseToUnicode((String) detail.get("summary")).replaceAll("& ", "&")));
            }
            if (detail.containsKey("content")) {
                detail.put("content", EmojiUtil.parseToUnicode(String.valueOf(detail.get("content"))));
            }
            List<CkyHonorPojo> honorPojos = ckyHonorMapper.selectList(new EntityWrapper().eq("del_flg", 0).eq("studio_id", studioId).orderBy("get_time", false));
            List<Object> honors = new ArrayList<>();
            honorPojos.forEach(p -> {
                Map<String, Object> m = new HashMap<>();
                m.put("honor_id", p.getId());
                m.put("title", p.getTitle());
                m.put("images", WebSiteUtil.getBrowseUrl(p.getImages().split(",")));
                m.put("content", EmojiUtil.parseToUnicode(p.getContent()));
                honors.add(m);
            });
            detail.put("honors", honors);
        }
        return detail;
    }

    /**
     * 工作室列表
     * create by gutongwei
     * on 2018/6/14
     *
     * @param basicRowBounds
     * @return
     */
    public List<Map<String, Object>> studios(BasicRowBounds basicRowBounds) {
        List<Map<String, Object>> result = ckyStudioMapper.studios(basicRowBounds);
        result.forEach(m -> {
            if (m.containsKey("cover")) {
                m.put("cover", WebSiteUtil.getBrowseUrl(m.get("cover") != null ? m.get("cover").toString() : ""));
            }
        });
        return result;
    }


    /**
     * 添加工作室
     *
     * @param listMap
     */
    private void addStudiosPage(List<Map<String, Object>> listMap) {
        listMap.add(getTitleMap(0, "工作室", "cky_gongzuoshi.png", "5001"));
        List<Map<String, Object>> work = studios(new BasicRowBounds(1, 2));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("item_type", 1);
        map.put("studios", work);
        listMap.add(map);
    }

    /**
     * 添加成果展
     * 资讯数据
     *
     * @param listMap
     */
    private void addArticlesPage(List<Map<String, Object>> listMap, String auid) {
        listMap.add(getTitleMap(0, "成果展", "cky_chengguozhan.png", "501"));
        List<ArticlesDto> articlesDtos = informationService.getArticles(new BasicRowBounds(1, 2),
                501, null, null, 0, null);
        if (ToolUtil.isNotEmpty(articlesDtos)) {
            articlesDtos.forEach(m -> {
                Map<String, Object> map = TransBeanUtil.throwExceptionByTransBean2Map(m);
                map.put("item_type", 2);
                listMap.add(map);
            });
        }
    }

    /**
     * 根据传入的数据类型；获取创客园首页所需标题的map
     *
     * @param itemType 数据类型
     * @param name     类型名称
     * @param icon     小图标路径
     * @param id       标题id
     * @return
     */
    public Map<String, Object> getTitleMap(Integer itemType, String name, String icon, String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("item_type", itemType);
        map.put("id", id);
        map.put("title", name);
        map.put("more", "更多");
        if (ToolUtil.isNotEmpty(icon)) {
            icon = WebSiteUtil.getBrowseUrl("categoryIcon/" + icon);//小图标
        }
        map.put("icon", icon);
        return map;
    }


    /**
     * 劳模/工匠列表
     *
     * @param type 0:劳模。1：工匠
     * @param page
     * @return
     */
    public List<Map<String, String>> getWorkers(String type, BasicRowBounds page) {
        List<Map<String, String>> result = new ArrayList<>();
        List<CkyWorkerPojo> workers = workerMapper.selectPage(page, new EntityWrapper().eq("type", type));
        workers.forEach(w -> {
            Map<String, String> m = new HashMap<>();
            m.put("id", w.getId().toString());
            m.put("name", w.getName());
            m.put("avatar", WebSiteUtil.getBrowseUrl(w.getAvatar()));
            m.put("summary", w.getSummary());
            result.add(m);
        });
        return result;
    }


    /**
     * 获取劳模/工匠详情
     *
     * @param id
     * @return
     */
    public Map<String, String> getWorkerDetail(Integer id) {
        Map<String, String> result = new HashMap<>();


        return result;
    }

}
