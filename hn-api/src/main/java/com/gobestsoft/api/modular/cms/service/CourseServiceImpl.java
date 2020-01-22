package com.gobestsoft.api.modular.cms.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.basic.RestBasic;
import com.gobestsoft.api.modular.cms.model.CmsCourseDto;
import com.gobestsoft.api.modular.cms.model.ShareInfo;
import com.gobestsoft.common.modular.cms.model.CmsCourseEntity;
import com.gobestsoft.common.modular.dao.mapper.CmsCourseMapper;
import com.gobestsoft.common.modular.dao.mapper.CmsCourseOperationMapper;
import com.gobestsoft.common.modular.dao.model.CmsCourseOperationPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/6/15 上午11:12
 */
@Service
public class CourseServiceImpl extends RestBasic implements CourseService {

    @Autowired
    private CmsCourseMapper cmsCourseMapper;

    @Autowired
    private CmsCourseOperationMapper cmsCourseOperationMapper;

    @Autowired
    private ApiProperties apiProperties;
    @Autowired
    private InformationService informationService;

    /**
     * 课程列表
     *
     * @param type      【0:普通课程】【1：系列课程】【2：女子学堂】
     * @param auid
     * @param isCollect 是否是收藏
     * @param rowBounds
     * @return
     */
    @Override
    public List<Map<String, Object>> courseList(Integer type, String auid, boolean isCollect, BasicRowBounds rowBounds) {
        List<CmsCourseEntity> entities = cmsCourseMapper.courseList(type, auid, isCollect, rowBounds);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        entities.forEach(e -> {
            CmsCourseDto cmsCourseDto = new CmsCourseDto(e, apiProperties);
            Map<String, Object> map = new HashMap<>();
            map.put("id", cmsCourseDto.getId());

            if (cmsCourseDto.getInfo_type() == 0) {
                map.put("template_type", 0);
                map.put("info_type", 0);
            } else {
                map.put("template_type", 2);
                map.put("info_type", 2);
            }
            map.put("cover", cmsCourseDto.getCover());
            map.put("title", cmsCourseDto.getTitle());
            map.put("published_time", cmsCourseDto.getPublished_time());
            map.put("browse_num", cmsCourseDto.getBrowse_num());
            map.put("thumbs_up_num", cmsCourseDto.getThumbs_up_num());
            map.put("is_thumbs_up", cmsCourseDto.getIs_thumbs_up());
            map.put("is_collect", cmsCourseDto.getIs_collect());
            map.put("jump_url", cmsCourseDto.getJump_url());
            result.add(map);
        });
        return result;
    }

    /**
     * 资讯操作
     *
     * @param courseId
     * @param auid
     * @param type     操作类型:[0:点赞][1:收藏][2:分享][3:浏览]
     * @return
     */
    @Override
    public int articleOperation(int courseId, String auid, int type) {
        CmsCourseOperationPojo pojo = new CmsCourseOperationPojo();
        pojo.setAuid(auid);
        pojo.setCourse_id(courseId);
        pojo.setType(type);
        pojo.setCreateTime(DateUtil.getAllTime());
        if (type == 2 || type == 3) {
            cmsCourseOperationMapper.insert(pojo);
        }

        if (type == 0 || type == 1) {
            boolean exist = cmsCourseOperationMapper.selectCount(
                    new EntityWrapper().eq("course_id", courseId).
                            eq("auid", auid).eq("type", type)) > 0;
            if (exist) {
                cmsCourseOperationMapper.delete(new EntityWrapper().eq("course_id", courseId).
                        eq("auid", auid).eq("type", type));
                return 201;
            } else {
                cmsCourseOperationMapper.insert(pojo);
            }
        }

        return 200;
    }

    /**
     * e学堂首页
     */
    @Override
    public Map<String, Object> getHomePage(String auid, String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取首页banner
//        resultMap.put("banner", informationService.getArticleBanner(2));
//        List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
//        //添加课程超市
//        addCmsCourseDtoPage(pageList, auid);
//        //闯关练兵
////        addTraining(pageList,token);
//        //网上比武
////        addTournament(pageList,token);
//        resultMap.put("page", pageList);
        return resultMap;
    }


    /**
     * e学院首页接口绑定课程超市返回数据
     * 此代码与课程列表代码部分重复；增加此方法避免修改课程列表返回数据改动影响e学院首页接口返回值
     *
     * @param type
     * @param auid
     * @param isCollect
     * @param rowBounds
     * @return
     */
    public void courseListByFirstPageUse(
            Integer type, String auid, boolean isCollect, BasicRowBounds rowBounds
            , List<Map<String, Object>> pageList) {
        List<CmsCourseEntity> entities = cmsCourseMapper.courseList(type, auid, isCollect, rowBounds);
        entities.forEach(e -> {
            CmsCourseDto cmsCourseDto = new CmsCourseDto(e, apiProperties);
            Map<String, Object> map = new HashMap<>();
            map.put("id", cmsCourseDto.getId());
            map.put("template_type", cmsCourseDto.getInfo_type() == 0 ? 0 : 1);// 【0:图片模式】【2：单幅长图(音频、视频)】
            map.put("title", cmsCourseDto.getTitle());
            map.put("published_time", cmsCourseDto.getPublished_time());
            map.put("browse_num", cmsCourseDto.getBrowse_num());
            map.put("thumbs_up_num", cmsCourseDto.getThumbs_up_num());
            map.put("is_thumbs_up", cmsCourseDto.getIs_thumbs_up());
            map.put("is_collect", cmsCourseDto.getIs_collect());
            map.put("jump_url", cmsCourseDto.getJump_url());
            //以下两个字段是课程列表返回数据未绑定的字段
            map.put("type", cmsCourseDto.getType());//	课程类型【0:普通课程】【1：系列课程】
            map.put("info_type", cmsCourseDto.getInfo_type());// 课程类型类型:0：图文，1：音频，2：视频
            map.put("item_type", 1);
            map.put("covers", ToolUtil.isEmpty(cmsCourseDto.getCover()) ? new String[]{} : cmsCourseDto.getCover().split(","));
            map.put("which", 1);// 【0：资讯】【1：课程】
            pageList.add(map);
        });
    }

    /**
     * 添加闯关练兵
     *
     * @param pageList
     * @param token
     */
    private void addTraining(List<Map<String, Object>> pageList, String token) {
//        pageList.add(ckyServiceImpl.getTitleMap(0, "闯关练兵", "exy_lianbing.png", "ext2"));
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        String url = apiProperties.getPhpSite() + "zscg/spa/index.php?token=" + token;
        listMap.add(getDatasMap("闯关练兵", url));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("item_type", 3);
        map.put("datas", listMap);
        pageList.add(map);
    }

    /**
     * 添加网上比武
     *
     * @param pageList
     */
    private void addTournament(List<Map<String, Object>> pageList, String token) {

    }

    /**
     * 闯关练兵和网上比武临时绑定数据方法
     *
     * @param title
     * @return
     */
    private Map<String, Object> getDatasMap(String title, String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("jump_url", url);
        map.put("cover", WebSiteUtil.getBrowseUrl("article/20180626/6f34bc5274054ed1b7d16ecddbfdf0c9.jpg"));
        return map;
    }

    /**
     * 课程详情
     */
    @Override
    public Map<String, Object> detail(Integer courseId, String auid) {
        Map<String, Object> map = cmsCourseMapper.courseDetail(courseId, auid);
        ShareInfo share_info = new ShareInfo();
        share_info.setShare_title(map.containsKey("title") ? map.get("title").toString() : "");
        share_info.setShare_cover(map.get("cover") == null ? "" : WebSiteUtil.getBrowseUrl(map.get("cover").toString()));
        share_info.setShare_link(apiProperties.getCourseUrl(String.valueOf(map.get("id")), false));
        share_info.setShare_content(map.containsKey("title") ? map.get("title").toString() : "");
        map.put("share_info", share_info);
        map.remove("title");
        map.remove("cover");
        return map;
    }
}
