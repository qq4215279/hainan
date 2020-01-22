package com.gobestsoft.admin.modular.cky.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.CkyHonorMapper;
import com.gobestsoft.common.modular.dao.mapper.CkyStudioMapper;
import com.gobestsoft.common.modular.dao.model.CkyHonorPojo;
import com.gobestsoft.common.modular.dao.model.CkyStudioPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.EmojiUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/6/25 下午1:23
 */
@Service
public class StudioService {


    @Autowired
    private CkyStudioMapper ckyStudioMapper;

    @Autowired
    private CkyHonorMapper ckyHonorMapper;

    /**
     * 工作室列表
     * create by gutongwei
     * on 2018/6/25
     *
     * @param name 工作室名称
     * @param page
     * @return
     */
    public List<Map<String, String>> list(String name, Page<Map<String, String>> page) {
        List<Map<String, String>> result = ckyStudioMapper.managementStudios(name, page);
        result.forEach(r -> {
            if (r.containsKey("create_time") && StringUtils.isNotEmpty(r.get("create_time"))) {
                r.put("create_time",
                        DateUtil.parseAndFormat(r.get("create_time"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
            }
        });
        return result;
    }


    /**
     * 新增或者更新工作室
     * create by gutongwei
     * on 2018/6/25
     *
     * @param id
     * @param name       工作室名称
     * @param cover      封面图
     * @param banner     工作室banner
     * @param summary    工作室详细
     * @param createUser 作者
     */
    public void renew(Integer id, String name, String cover, String banner, String summary, String createUser) {
        name = EmojiUtil.parseToAliases(name);
        summary = EmojiUtil.parseToAliases(summary);
        CkyStudioPojo ckyStudioPojo;
        if (id == null) {
            newInsert(id, name, cover, banner, summary, createUser);
        } else {
            ckyStudioPojo = ckyStudioMapper.selectById(id);
            if (ckyStudioPojo == null) {
                newInsert(id, name, cover, banner, summary, createUser);
            } else {
                ckyStudioPojo.setBanner(banner);
                ckyStudioPojo.setCover(cover);
                ckyStudioPojo.setName(name);
                ckyStudioPojo.setCreateUser(createUser);
                ckyStudioPojo.setDelFlg(0);
                ckyStudioPojo.setSummary(summary);
                ckyStudioMapper.updateById(ckyStudioPojo);
            }
        }
    }


    private void newInsert(Integer id, String name, String cover, String banner, String summary, String createUser) {
        CkyStudioPojo ckyStudioPojo;
        ckyStudioPojo = new CkyStudioPojo();
        ckyStudioPojo.setBanner(banner);
        ckyStudioPojo.setCover(cover);
        ckyStudioPojo.setName(name);
        ckyStudioPojo.setCreateUser(createUser);
        ckyStudioPojo.setCreateTime(DateUtil.getAllTime());
        ckyStudioPojo.setDelFlg(0);
        ckyStudioPojo.setSummary(summary);
        ckyStudioMapper.insert(ckyStudioPojo);
    }

    /**
     * 获取工作室
     * create by gutongwei
     * on 2018/6/25
     *
     * @param id
     * @return
     */
    public Map<String, Object> getStudioDetail(int id) {
        CkyStudioPojo pojo = ckyStudioMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (pojo != null) {
            result.put("cover", WebSiteUtil.getBrowseUrl(pojo.getCover()));
            String[] banner = StringUtils.isEmpty(pojo.getBanner()) ? new String[]{} : pojo.getBanner().split(",");
            result.put("banner", WebSiteUtil.getBrowseUrl(banner));
            result.put("name", EmojiUtil.parseToUnicode(pojo.getName()));
            result.put("summary", HtmlUtils.htmlUnescape(EmojiUtil.parseToUnicode(pojo.getSummary()).replaceAll("& ", "&")));
        }
        return result;
    }

    /**
     * 删除工作室
     *
     * @param ids
     */
    public void deleteStudio(String ids) {
        ckyStudioMapper.deleteStudio(ids);
    }

    /**
     * 工作室荣誉列表
     * create by gutongwei
     * on 2018/6/25
     *
     * @param title 工作是荣誉标题
     * @param title
     * @return
     */
    public List<Map<String, String>> honorList(String title, int studioId, Page<Map<String, String>> page) {
        List<Map<String, String>> result = ckyHonorMapper.managementHonor(title, studioId, page);
        result.forEach(r -> {
            if (r.containsKey("create_time") && StringUtils.isNotEmpty(r.get("create_time"))) {
                r.put("create_time",
                        DateUtil.parseAndFormat(r.get("create_time"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
            }
        });
        return result;
    }

    /**
     * 工作室荣誉详情
     * create by gutongwei
     * on 2018/6/25
     *
     * @param id 荣誉ID
     * @return
     */
    public Map<String, Object> honorDetail(int id) {
        CkyHonorPojo pojo = ckyHonorMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (pojo != null) {
            result.put("getTime", DateUtil.parseAndFormat(pojo.getGetTime(), "yyyyMMdd", "yyyy-MM-dd"));
            result.put("title", EmojiUtil.parseToUnicode(pojo.getTitle()));
            result.put("content", EmojiUtil.parseToUnicode(pojo.getContent()));
            result.put("isShow", pojo.getIsShow());
            String[] images = StringUtils.isEmpty(pojo.getImages()) ? new String[]{} : pojo.getImages().split(",");
            result.put("images", WebSiteUtil.getBrowseUrl(images));
        }
        return result;
    }

    /**
     * 荣誉证书添加
     * create by gutongwei
     * on 2018/6/25
     *
     * @param id
     * @param studioId   工作室id
     * @param title      荣誉标题
     * @param content    内容
     * @param images     荣誉证书
     * @param isShow     是否显示列表
     * @param getTime    获取荣誉时间
     * @param createUser 作者
     */
    @Transactional
    public void honorRenew(Integer id, int studioId,
                           String title, String content,
                           String images, int isShow, String getTime, String createUser) {
        content = EmojiUtil.parseToAliases(content);
        title = EmojiUtil.parseToAliases(title);
        CkyHonorPojo ckyHonorPojo;
        if (id == null) {
            honorNewInsert(studioId, title, content, images, isShow, getTime, createUser);
        } else {
            ckyHonorPojo = ckyHonorMapper.selectById(id);
            if (ckyHonorPojo == null) {
                honorNewInsert(studioId, title, content, images, isShow, getTime, createUser);
            } else {
                ckyHonorPojo.setTitle(title);
                ckyHonorPojo.setContent(content);
                ckyHonorPojo.setImages(images);
                ckyHonorPojo.setGetTime(getTime);
                ckyHonorPojo.setIsShow(isShow);
                if (isShow == 1) {
                    ckyHonorMapper.updateIsShow(studioId);
                }
                ckyHonorMapper.updateById(ckyHonorPojo);
            }
        }
    }

    private void honorNewInsert(int studioId,
                                String title, String content,
                                String images, int isShow, String getTime, String createUser) {
        CkyHonorPojo ckyHonorPojo = new CkyHonorPojo();
        ckyHonorPojo.setStudioId(studioId);
        ckyHonorPojo.setTitle(title);
        ckyHonorPojo.setContent(content);
        ckyHonorPojo.setImages(images);
        ckyHonorPojo.setGetTime(getTime);
        ckyHonorPojo.setIsShow(isShow);
        ckyHonorPojo.setCreateUser(createUser);
        ckyHonorPojo.setCreateTime(DateUtil.getAllTime());
        ckyHonorPojo.setDel_flg(0);
        if (isShow == 1) {
            ckyHonorMapper.updateIsShow(studioId);
        }
        ckyHonorMapper.insert(ckyHonorPojo);
    }

    /**
     * 删除工作室荣誉
     * create by gutongwei
     * on 2018/6/25
     *
     * @param ids 荣誉IDS
     */
    public void deleteHonor(String ids) {
        ckyHonorMapper.deleteHonor(ids);
    }
}
