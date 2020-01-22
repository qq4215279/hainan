package com.gobestsoft.admin.modular.cms.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.mapper.ArticleMapper;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.cms.model.CmsRelationEntity;
import com.gobestsoft.common.modular.dao.mapper.CmsCategoryMapper;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.cms.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 资讯定向发布控制器
 *
 * Created by duanmu on 2018/8/31.
 */
@Controller
@RequestMapping("/articlevalid")
public class ArticleValidController extends BaseController{

    private String PREFIX = "/cms/article/";

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CmsCategoryMapper cmsCategoryMapper;

    /**
     * 跳转到资讯首页
     */
    @RequestMapping(value = "")
    public String index(Model model){

        List<DictModel> dictModels = new ArrayList<>();
        List<CmsCategoryPojo> cmsCategoryPojos = cmsCategoryMapper.getCategoryList(0);
        for(CmsCategoryPojo pojo : cmsCategoryPojos){
            DictModel dictModel = new DictModel();
            dictModel.setCode(pojo.getId().toString());
            dictModel.setName(pojo.getName());
            dictModels.add(dictModel);
        }

        List<DictModel> dictModels1 = new ArrayList<>();
        List<CmsCategoryPojo> cmsCategoryPojos1 = cmsCategoryMapper.getCategoryList(1);
        for(CmsCategoryPojo pojo : cmsCategoryPojos1){
            DictModel dictModel = new DictModel();
            dictModel.setCode(pojo.getId().toString());
            dictModel.setName(pojo.getName());
            dictModels1.add(dictModel);
        }

        model.addAttribute("categorycode", dictModels);
        model.addAttribute("seminar", dictModels1);
        return PREFIX + "article_valid";
    }

    /**
     * 查询有效资讯列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false, value = "begDate") String begDate,
                    @RequestParam(required = false, value = "endDate") String endDate,
                    @RequestParam(required = false, value = "title") String title,
                    @RequestParam(required = false, value = "category") String category,
                    @RequestParam(required = false, value = "seminar") String seminar){
        Page<Article> page = defaultPage();
        Integer deptId = getLoginUser().getDeptId();
        begDate = DateUtil.parseAndFormat(begDate, "yyyy-MM-dd HH:mm:SS", "yyyyMMddHHmmss");
        endDate = DateUtil.parseAndFormat(endDate, "yyyy-MM-dd HH:mm:SS", "yyyyMMddHHmmss");
        List<Article> result = articleService.getSelectValidList(page, title, begDate, endDate, deptId, category, seminar);
        page.setRecords(result);
        return super.packForBT(page);

    }

    /**
     * 资讯发布
     */
    @RequestMapping(value = "/article_pub")
    public String articlePub(@RequestParam("id") String id, Model model){
        Article article = this.articleMapper.selectById(id);
        model.addAttribute("pubId", id);
        return PREFIX + "article_pub";
    }

    /**
     * 跳转到添加栏目
     */
    @RequestMapping(value = "/article_getCates")
    public String getCates(Model model, @RequestParam("articleId") String articleId, @RequestParam("type") String type) {
        List<CmsRelationEntity> list = articleService.getPublishList(articleId, type);
        model.addAttribute("publishList", list);
        model.addAttribute("articleId", articleId);
        model.addAttribute("pubtype", type);
        return PREFIX + "article_publish";
    }

    //定向发布
    @RequestMapping(value = "/publish")
    @ResponseBody
    public Tip publishToCategory(@RequestParam("articleId") String articleId,
                                 @RequestParam("categoryId") String categoryId,
                                 @RequestParam("divide") boolean divide,
                                 @RequestParam("pubStatus") String pubStatus) {
        String uid = getLoginUser().getId();
        String ret = articleService.publishToCategory(divide, articleId, categoryId, pubStatus, uid);
        return new Tip(200, ret, null);
    }

    /**
     * 删除资讯
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public Tip del(@RequestParam("id") String id) {
        this.articleService.del(id);
        return success();
    }

    /**
     * 资讯下线
     */
    @RequestMapping(value = "/offLine")
    @ResponseBody
    public Tip offLine(@RequestParam("id") String id) {
        String uid = getLoginUser().getId();
        this.articleService.offLine(id, uid);
        return success();
    }

    /**
     * 资讯上线
     */
    @RequestMapping(value = "/onLine")
    @ResponseBody
    public Tip onLine(@RequestParam("id") String id) {
        String uid = getLoginUser().getId();
        this.articleService.onLine(id, uid);
        return success();
    }
}
