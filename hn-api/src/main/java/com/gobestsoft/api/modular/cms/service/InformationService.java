package com.gobestsoft.api.modular.cms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.basic.RestBasic;
import com.gobestsoft.api.modular.cms.model.AppBannerDto;
import com.gobestsoft.api.modular.cms.model.ArticlesCatalogDto;
import com.gobestsoft.api.modular.cms.model.ArticlesDto;
import com.gobestsoft.api.modular.cms.model.ShareInfo;
import com.gobestsoft.common.modular.cms.dao.CategoryDao;
import com.gobestsoft.common.modular.cms.dao.InformationDao;
import com.gobestsoft.common.modular.cms.model.Channel;
import com.gobestsoft.common.modular.cms.model.CmsArticleEntity;
import com.gobestsoft.common.modular.cms.model.CmsCourseEntity;
import com.gobestsoft.common.modular.dao.mapper.AppBannerMapper;
import com.gobestsoft.common.modular.dao.mapper.CmsArticleMapper;
import com.gobestsoft.common.modular.dao.mapper.CmsCategoryMapper;
import com.gobestsoft.common.modular.dao.mapper.CmsCourseMapper;
import com.gobestsoft.common.modular.dao.model.AppBannerPojo;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 资讯
 *
 * @author
 * @date 2017-04-27 17:00
 */
@Service
@Slf4j
public class InformationService extends RestBasic {

    @Autowired
    private CmsArticleMapper cmsArticleMapper;

    @Autowired
    private AppBannerMapper appBannerMapper;

    @Autowired
    private CmsCategoryMapper cmsCategoryMapper;

    @Autowired
    private CmsCourseMapper cmsCourseMapper;

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private InformationDao informationDao;

    @Autowired
    private CategoryDao categoryDao;

    /**
     * 获取资讯详情
     * create by gutongwei
     * 2018/6/4
     *
     * @param id
     * @param userId
     * @return
     */
    public Map<String, Object> articleDetail(String id, String userId) {
        Map<String, Object> result = cmsArticleMapper.articleDetail(id, userId, DateUtil.getAllTime());
        if (result != null) {
            result.remove("content");
            ShareInfo shareInfo = new ShareInfo();
            shareInfo.setShare_title((String) result.get("title"));
            String roundup = (String) result.get("roundup");
            if (StringUtils.isEmpty(roundup)) {
                roundup = shareInfo.getShare_title();
            }
            shareInfo.setShare_content(roundup);
            shareInfo.setShare_link(apiProperties.getReviewBaseUrl((String) result.get("id"), false));
            shareInfo.setShare_cover("");
            result.put("share_info", shareInfo);
        }
        return result;
    }

    /**
     * 获取资讯
     * create by gutongwei
     *
     * @param rowBounds
     * @param categoryId
     * @param keyword
     * @return
     */
    public List<ArticlesDto> getArticles(BasicRowBounds rowBounds, Integer categoryId, String keyword, String cityCode, int isCollected, String auid) {
        List<ArticlesDto> result = new ArrayList<>();
        List<CmsArticleEntity> pojos = cmsArticleMapper.articleList(rowBounds, categoryId, keyword, DateUtil.getAllTime(), cityCode, isCollected, auid);

        List<String> list = new ArrayList<>();
        pojos.forEach(e -> {
            list.add(e.getId());
        });
        //根据资讯流水号查询对应的点赞和浏览
        if(list.size()>0){
            List<Map<String, Object>> mapList = cmsArticleMapper.articleOperationCount(list);
            for(CmsArticleEntity c:pojos){
                for(Map<String, Object> m:mapList){
                    if(c.getId().equals(m.get("article_id")+"")&& "0".equals(m.get("type")+"")){
                        c.setThumbsUpNum(Integer.parseInt(m.get("num")+""));
                    }
                    if(c.getId().equals(m.get("article_id")+"")&& "1".equals(m.get("type")+"")){
                        c.setBrowseNum(Integer.parseInt(m.get("num")+""));
                    }
                }
            }

        }
        pojos.forEach(e -> {
            result.add(new ArticlesDto(e, apiProperties));
        });

        return result;
    }


    /**
     * 获取经过会列表
     *
     * @return
     */
    private List<Object> jgh(BasicRowBounds rowBounds, String auid) {
        List<CmsCategoryPojo> pojos = cmsCategoryMapper.selectList(new EntityWrapper().eq("pid", 200).orderBy("id"));
        List<Object> result = new ArrayList<>();
        if (pojos != null) {
            pojos.forEach(p -> {
                Map<String, Object> category = new HashMap<>();
                category.put("id", p.getId());
                category.put("title", p.getName());
                category.put("icon", WebSiteUtil.getBrowseUrl(p.getIcon()));
                category.put("item_type", 0);
                result.add(category);
                if (p.getId() == 201 || p.getId() == 203) {
                    List<ArticlesDto> articlesDtos = getArticles(rowBounds, p.getId(), null, null, 0, null);
                    articlesDtos.forEach(dto -> {
                        result.add(new ArticlesCatalogDto(dto));
                    });
                }
                if (p.getId() == 202) {
                    List<CmsCourseEntity> entities = cmsCourseMapper.courseList(2, auid, false, rowBounds);
                    entities.forEach(entity -> {
                        result.add(new ArticlesCatalogDto(entity, apiProperties));
                    });
                }

            });
        }
        return result;
    }

    /**
     * 获取头部banner(根据时间排序获取前五条有效数据)
     *
     * @return
     */
    public List<AppBannerDto> getBanner(String category) {
        int count = 1;// 展示数据数量
        if ("0".equals(category)) {// category 为头部banner时展示5条数据
            count = 5;
        }
        List<AppBannerDto> banner = new ArrayList<>();
        List<AppBannerPojo> entities = appBannerMapper.bannerList(category, count);
        entities.forEach(p -> {
            AppBannerDto dto = new AppBannerDto();
            dto.setTargetId(p.getTargetId());
            dto.setTitle(p.getTitle());
            if (p.getType() == 1) {
                dto.setJumpUrl(apiProperties.getReviewBaseUrl(p.getCategory(), true));
            } else {
                dto.setJumpUrl(p.getJumpUrl());
            }
            dto.setCover(WebSiteUtil.getBrowseUrl(p.getCover()));
            banner.add(dto);
        });
        return banner;
    }

    /**
     * 资讯操作
     * create by gutongwei
     *
     * @param articleId
     * @param auid
     * @param type      操作类型:[0:点赞][1:浏览][2:分享][3:收藏]
     * @return
     */
    public Integer articleOperation(String articleId, String auid, int type) {
        if (!isGiveOperation(articleId, auid, type)) {
            addArticleOperation(articleId, auid, type);
            return 200;
        }
        delArticleOperation(articleId, auid, type);
        return 201;
    }

    /**
     * T判断是否点过赞
     * create by gutongwei
     *
     * @param articleId
     * @param auid
     * @param type      操作类型:[0:点赞][1:浏览][2:分享][3:收藏]
     * @return
     */
    public boolean isGiveOperation(String articleId, String auid, int type) {
        return cmsArticleMapper.getArticleOperationCount(articleId, type, auid) > 0;
    }

    /**
     * 删除资讯操作
     * create by gutongwei
     *
     * @param articleId
     * @param auid
     * @param type
     */
    public void delArticleOperation(String articleId, String auid, int type) {
        cmsArticleMapper.delArticleOperation(articleId, auid, type);
    }

    /**
     * 添加资讯操作
     * create by gutongwei
     *
     * @param articleId
     * @param auid
     * @param type
     */
    public void addArticleOperation(String articleId, String auid, int type) {
        cmsArticleMapper.addArticleOperation(articleId, auid, DateUtil.getAllTime(), type);
    }

    /**
     * 获取首页中部滚动文字
     * create by gutongwei
     *
     * @return
     */
    public List<Map<String, String>> getIndexRollingTip() {
        List<Map<String, String>> result = new ArrayList<>();
        return result;
    }


    /**
     * 全局查询
     * 根据全局搜索输入的关键字,查询并绑定矩阵、资讯、活动信息至list数组中
     *
     * @throws IllegalAccessException
     */
    public List<Map<String, Object>> bindMatrixAndArticlesAndActivityDataByKeyword(
            String keyword, String auid, String token) {
        List<Map<String, Object>> obj = new ArrayList<Map<String, Object>>();
        //添加资讯数据
        addArticleToListMap(obj, keyword, auid);
        //添加活动数据
        addActivityToListMap(obj, keyword, token);
        return obj;
    }


    /**
     * 添加矩阵的map至list
     *
     * @param obj
     * @param matrixList
     */
    private void addMatrixMapToList(List<Map<String, Object>> obj, List<Map<String, Object>> matrixList) {
        matrixList.forEach(map -> {
            map.put("item_type", 1);
            obj.add(map);
        });
    }

    /**
     * 添加资讯数据至listMap中
     *
     * @param obj
     * @param keyword
     */
    private void addArticleToListMap(List<Map<String, Object>> obj, String keyword, String auid) {
        List<ArticlesDto> articlesDtos = getArticles(new BasicRowBounds(1, 4), null, keyword, null, 0, null);
        if (ToolUtil.isNotEmpty(articlesDtos)) {
            obj.add(getTitleMap("资讯"));//标题
            if (articlesDtos.size() <= 3) {
                addArticleMapToList(obj, articlesDtos);
                return;
            }
            articlesDtos.remove(3);
            addArticleMapToList(obj, articlesDtos);
            obj.add(getSearchMap(2));//查看更多
        }
    }

    /**
     * 添加资讯map至List
     *
     * @param obj
     * @param articlesDtos
     */
    private void addArticleMapToList(List<Map<String, Object>> obj, List<ArticlesDto> articlesDtos) {
        articlesDtos.forEach(article -> {
            Map<String, Object> map = TransBeanUtil.throwExceptionByTransBean2Map(article);
            map.put("item_type", 2);
            obj.add(map);
        });
    }

    /**
     * 添加活动数据至listMap中
     *
     * @param obj
     * @param keyword
     * @param token
     */
    private void addActivityToListMap(List<Map<String, Object>> obj, String keyword, String token) {
        int page_num = 1;
        int page_size = 4;
        String url = apiProperties.getPhpSite() + "index.php?s=/w1/Train/Api/train_list"
                + "&token=" + token + "&page_num=" + page_num + "&page_size=" + page_size + "&key_word="
                + keyword + "&api_type=2";
        String activity = HttpUtil.sendGet(url);
        JSONObject json = JSON.parseObject(activity);
        Integer code = Integer.parseInt(json.get("code").toString());
        if (code == 200 && json.getJSONArray("data").size() > 0) {
            obj.add(getTitleMap("活动"));//标题
            if (json.getJSONArray("data").size() <= 3) {
                addActivityMapToList(obj, json);
                return;
            }
            json.getJSONArray("data").remove(3);
            addActivityMapToList(obj, json);
            obj.add(getSearchMap(3));//查看更多
        }
    }

    /**
     * 添加活动map至list
     *
     * @param obj
     * @param json
     */
    private void addActivityMapToList(List<Map<String, Object>> obj, JSONObject json) {
        Iterator<Object> jsonArray = json.getJSONArray("data").iterator();
        while (jsonArray.hasNext()) {
            Map<String, Object> map = (Map<String, Object>) jsonArray.next();
            map.put("item_type", 3);
            obj.add(map);
        }
    }

    /**
     * 获取标题对象
     *
     * @param name
     * @return
     */
    private Map<String, Object> getTitleMap(String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("item_type", 0);
        map.put("name", name);
        return map;
    }

    /**
     * 获取查看更多对象
     *
     * @param searchType
     * @return
     */
    private Map<String, Object> getSearchMap(Integer searchType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("item_type", 4);
        map.put("name", "查看更多");
        map.put("search_type", searchType);
        return map;
    }

    /**
     * 增加栏目频道
     */
    public void addChannel(Integer[] ids, String userId) {
        String creatTime = DateUtil.getAllTime();
        informationDao.deleteAllChannel(userId);
        informationDao.addChannel(ids, userId, creatTime);
    }

    /**
     * 获取频道栏目
     */
    public Map<String, Object> getChannels(Boolean isLogin, String auid) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询已订阅的频道
        List<Channel> ownChannel = getOwnChannels(isLogin, auid);
        map.put("own", ownChannel);
        map.put("recommend", recommendChannels(ownChannel));
        return map;
    }

    /**
     * 修正栏目频道
     *
     * @param ownChannel
     */
    private List<Channel> recommendChannels(List<Channel> ownChannel) {
        StringBuilder sb = new StringBuilder();
        for (Channel channel : ownChannel) {
            sb.append("'" + channel.getId() + "',");
        }
        return informationDao.getRecommendChannels(sb.toString().substring(0, sb.toString().length() - 1));
    }

    /**
     * 查询用户已订阅的频道
     *
     * @param isLogin
     * @param userId
     */
    public List<Channel> getOwnChannels(boolean isLogin, String userId) {
        List<Channel> ownChannel = new ArrayList<>();
        ownChannel.add(0, new Channel(102, "省总新闻"));
        ownChannel.add(1, new Channel(104, "全总资讯"));
        ownChannel.add(2, new Channel(101, "学习思想"));
        ownChannel.add(3, new Channel(103, "基层动态"));
        ownChannel.add(4, new Channel(131, "制度文件"));
        ownChannel.add(5, new Channel(132, "财务资产"));
        return ownChannel;
    }

    /**
     * 判断相对应的内容是否存在
     *
     * @param type
     * @param id
     * @return
     */
    public Integer getExist(int type, long id) {
        if (type == 0) {
            // 查询对应的资讯是否存在
            Integer count = informationDao.getExistArticle(id);
            return count;
        }
        if (type == 1) {
            // 查询对应的评论是否存在
            Integer count = informationDao.getExistComment(id);
            return count;
        }
        if (type == 2) {
            // 查询相对应的评论回复是否存在
            Integer count = informationDao.getExistCommentReply(id);
            return count;
        }
        return null;
    }

    /**
     * 专题列表
     */
    public List<Map<String, Object>> getTopicList(BasicRowBounds basicRowBounds) {
        List<Map<String, Object>> list = categoryDao.getTopicList(basicRowBounds);
        if (list.size() > 0 && list != null) {
            for (Map<String, Object> c : list) {
                if (ToolUtil.isNotEmpty(c.get("create_time"))) {
                    c.put("create_time", DateUtil.parseAndFormat(c.get("create_time").toString(), "yyyyMMddHHmmss", "yyyy-MM-dd"));
                } else {
                    c.put("create_time", "");
                }

                if (ToolUtil.isNotEmpty(c.get("icon"))) {
                    c.put("icon", WebSiteUtil.getBrowseUrl(c.get("icon").toString()));
                } else {
                    c.put("icon", "");
                }
            }
        }
        return list;
    }

    /**
     * 获取热门搜索
     */
    public List<Map<String, Object>> getHotArticlekey() {
        List<Map<String, Object>> result = cmsArticleMapper.getHotArticleKey();
        return result;
    }
}
