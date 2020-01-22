package com.gobestsoft.mamage.moudle.cms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.cms.mapper.ArticleMapper;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.cms.model.CmsRelationEntity;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.NumUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
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
 * 资讯服务
 * <p>
 * Created by duanmu on 2018/8/22
 */
@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ConstantFactory constantFactory;

    @Resource
    private ManageProperties manageProperties;

    /**
     * 对跳转向资讯首页提供数据支持
     *
     * @param cateId
     * @return
     */
    public Map<String, Object> toIndex(String cateId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("html", "article" + cateId);
        result.put("cateId", cateId);
        return result;
    }

    /**
     * 条件查询
     */
    public List<Article> getSelectArtilceList(Page<Article> page,
                                              Integer status,
                                              String title,
                                              String begDate,
                                              String endDate,
                                              Integer deptId,
                                              String userId,
                                              String infoType) {
        List<Article> resultList = articleMapper.selectArtilceList(page, status, title, begDate, endDate, deptId, userId, infoType);
        return resultList;
    }


    /**
     * 资讯增加
     */
    @Transactional
    public Integer addArticle(Article article, String imgStr, String imgStr2,
                              String imgStr3) throws IOException {
        if (StringUtils.isEmpty(article.getPublishedTime())) {
            article.setPublishedTime(DateUtil.getAllTime());
        }
        article.setTitle(ToolUtil.parseHtmlStr(article.getTitle()));
        article.setContent(ToolUtil.parseHtmlStr(article.getContent()));
        // 设置id，时间+随机4位数（201711110000）
        int i = NumUtil.nextInt(1000, 10000);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String str = sdf.format(date);
        str += i + "";
        article.setId(str);
        // 设置资讯状态
        article.setStatus(1);
        // 设置delFlg为没有删除
        article.setDelFlg(0);
        // 设置创建时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYYMMddHHmmss");
        String createTime = sdf2.format(date);
        article.setCreateTime(createTime);
        article.setUpdateTime(createTime);
        // 传入图片 并返回图片相对路径,插入Article对象中 并判断 无图，三国，单图
        String coverPath = "";
        String relativePath = manageProperties.getFileUploadPath();
        String folderName = UploadConstants.ARTICLE + DateUtil.getDays();
        String templateTypeStr = article.getTemplateType().toString();
        if (DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_LEFT.equals(templateTypeStr)
                || DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_RIGHT.equals(templateTypeStr)
                || DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_LONG.equals(templateTypeStr)) {// 单图
            coverPath = WebSiteUtil.base64TransferImage(imgStr, relativePath, folderName);
        } else if (DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_THREE.equals(templateTypeStr)) {// 三图
            String coverPath1 = WebSiteUtil.base64TransferImage(imgStr, relativePath, folderName);
            String coverPath2 = WebSiteUtil.base64TransferImage(imgStr2, relativePath, folderName);
            String coverPath3 = WebSiteUtil.base64TransferImage(imgStr3, relativePath, folderName);
            coverPath = coverPath1 + "," + coverPath2 + "," + coverPath3;
        }

        article.setCoverPath(coverPath);
        article.setIsTopicCheck(0);
        // 将封装好的对象传入insert方法
        int result = articleMapper.addArticle(article);

        return result;
    }

    /**
     * 对跳转向资讯首页提供数据支持
     *
     * @param id
     * @return
     */
    public Map<String, Object> toEdit(String id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Article article = articleMapper.selectById(id);
        article.setContent(HtmlUtils.htmlUnescape(article.getContent().replaceAll("& ", "&")));
        result.put("article", article);
        List<Dict> templateType = constantFactory.findInDict(DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE);
        StringBuffer sb = new StringBuffer();
        for (Dict dict : templateType) {
            sb.append(dict.getCode() + "=" + dict.getName()).append(",");
        }
        String subCode = sb.toString().substring(0, sb.toString().length() - 1);
        result.put("templateCode", subCode);
        // 判断是否是多图
        String templateTypeStr = article.getTemplateType().toString();
        String coverPath2 = "";
        String coverPath3 = "";
        if (DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_LEFT.equals(templateTypeStr)
                || DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_RIGHT.equals(templateTypeStr)
                || DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_LONG.equals(templateTypeStr)) {// 单图
            String coverPath = article.getCoverPath();
            coverPath = WebSiteUtil.getBrowseUrl(coverPath);
            article.setCoverPath(coverPath);
        } else if (DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_THREE.equals(templateTypeStr)) {// 三图
            String[] coverArg = article.getCoverPath().split(",");
            String coverPath = WebSiteUtil.getBrowseUrl(coverArg[0]);
            article.setCoverPath(coverPath);
            if (coverArg.length >= 2) {
                coverPath2 = WebSiteUtil.getBrowseUrl(coverArg[1]);
            }
            if (coverArg.length >= 3) {
                coverPath3 = WebSiteUtil.getBrowseUrl(coverArg[2]);
            }
        } else {// 无图

        }
        result.put("coverPath2", coverPath2);
        result.put("coverPath3", coverPath3);
        return result;
    }

    /**
     * 修改【资讯】对象
     *
     * @param articleParam
     * @return
     */
    @Transactional
    public Integer updateArticleById(Article articleParam, String imgStr, String imgStr2, String imgStr3) throws IOException {
        // 当前时间取得
        String nowTime = DateUtil.getAllTime();

        Article article = articleMapper.selectById(articleParam.getId());
        if (ToolUtil.isEmpty(article)) {
            throw new BusinessException(BizExceptionEnum.DB_RESOURCE_NULL);
        }

        if (StringUtils.isEmpty(article.getPublishedTime())) {
            article.setPublishedTime(DateUtil.getAllTime());
        }
        articleParam.setUpdateTime(nowTime);

        // 获取上传路径 + 目录
        String relativePath = manageProperties.getFileUploadPath();
        String folderName = UploadConstants.ARTICLE + DateUtil.getDays();

        // 获得封面图模式
        String templateTypeStr = articleParam.getTemplateType().toString();
        String coverPath = "";

        // 单图状态
        if (DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_LEFT.equals(templateTypeStr)
                || DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_RIGHT.equals(templateTypeStr)
                || DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_LONG.equals(templateTypeStr)) {
            coverPath = WebSiteUtil.base64TransferImage(imgStr, relativePath, folderName);
        }
        // 三图状态
        else if (DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE_THREE.equals(templateTypeStr)) {
            // 传入图片 并返回图片相对路径,插入Article对象中
            String coverPath1 = WebSiteUtil.base64TransferImage(imgStr, relativePath, folderName);
            String coverPath2 = WebSiteUtil.base64TransferImage(imgStr2, relativePath, folderName);
            String coverPath3 = WebSiteUtil.base64TransferImage(imgStr3, relativePath, folderName);
            coverPath = coverPath1 + "," + coverPath2 + "," + coverPath3;
        }
        articleParam.setCoverPath(coverPath);

        int result = articleMapper.updateById(articleParam);
        if (result != 1) {
            throw new BusinessException(BizExceptionEnum.SERVER_ERROR);
        }

        return result;
    }


    /**
     * 获取有效发布资讯列表
     */
    public List<Article> getSelectValidList(Page<Article> page,
                                            String title,
                                            String begDate,
                                            String endDate,
                                            Integer deptId,
                                            String category,
                                            String seminar) {
        List<Article> resultList = articleMapper.selectValidList(page, title, begDate, endDate, deptId, category, seminar);
        return resultList;
    }

    /**
     * 获取发布资讯栏目列表
     */
    public List<CmsRelationEntity> getPublishList(String articleId, String type) {
        List<CmsRelationEntity> list = articleMapper.selectPublishList(articleId, type);
        if (list != null) {
            for (CmsRelationEntity entity : list) {
                String pTime = entity.getPublishedTime();
                if (ToolUtil.isNotEmpty(pTime)) {
                    entity.setPublishedTime(DateUtil.parseAndFormat(pTime, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
                }
            }
        }
        return list;
    }

    /**
     * 发布指定栏目资讯
     */
    @Transactional
    public String publishToCategory(boolean divide, String articleId, String categoryId, String pubStatus, String uid) {

        CmsRelationEntity entity = articleMapper.selectCmsRelation(articleId, categoryId);

        String ret = "";
        if (ToolUtil.isNotEmpty(entity)) {
            if (!divide) {
                //删除指定栏目资讯
                articleMapper.deleteCmsRelation(entity.getId());
                ret = "删除成功";
            } else {
                articleMapper.updateCmsRelation(entity.getId(), pubStatus);
                ret = "更新成功";
            }
        } else {
            //关闭
            if (ToolUtil.equals(divide, false)) {
                return ret;
            } else {
                //新增指定栏目资讯
                articleMapper.insertCmsRelation(articleId, categoryId, pubStatus);
                articleMapper.updateArticleStatusById(articleId, Integer.valueOf(pubStatus), DateUtil.getAllTime(), uid, DateUtil.getAllTime());
                ret = "发布成功";
            }
        }
        return ret;
    }

    /**
     * 删除资讯
     *
     * @param sid
     * @return
     */
    @Transactional
    public Integer del(String sid) {
        return articleMapper.delArticle(sid);
    }

    /**
     * 下线 无效
     *
     * @param articleId
     * @param uid
     * @return
     */
    @Transactional
    public Integer offLine(String articleId, String uid) {
        return articleMapper.updateArticleStatusById(articleId, Integer.parseInt(DictCodeConstants.ARTICLE_PUB_STATUS_OFFLINE), DateUtil.getAllTime(), uid, DateUtil.getAllTime());
    }

    /**
     * 上线 有效
     *
     * @param articleId
     * @param uid
     * @return
     */
    @Transactional
    public Integer onLine(String articleId, String uid) {
        return articleMapper.updateArticleStatusById(articleId, Integer.parseInt(DictCodeConstants.ARTICLE_PUB_STATUS_ONLINE), DateUtil.getAllTime(), uid, DateUtil.getAllTime());
    }

}
