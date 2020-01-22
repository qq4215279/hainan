package com.gobestsoft.admin.modular.cms.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.cms.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 资讯控制器
 * <p>
 * Created by duanmu on 2018/8/22.
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

    private String PREFIX = "/cms/article/";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ConstantFactory constantFactory;

    /**
     * 跳转到资讯首页
     */
    @RequestMapping("")
    public String index(@RequestParam("cateid") String cateid, Model model) {
        if (ToolUtil.isEmpty(cateid)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        Map<String, Object> result = this.articleService.toIndex(cateid);
        for (String key : result.keySet()) {
            model.addAttribute(key, result.get(key));
        }
        return PREFIX + result.get("html");
    }

    /**
     * 查询资讯列表list
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false, value = "title") String title,
                       @RequestParam(required = false, value = "begDate") String begDate,
                       @RequestParam(required = false, value = "endDate") String endDate,
                       @RequestParam(required = false, value = "status") Integer status,
                       @RequestParam(required = false, value = "infoType") String infoType) {
        LoginUser loginUser = getLoginUser();
        Integer deptId = loginUser.getDeptId();
        String userId = loginUser.getId();
        Page<Article> page = defaultPage();
        String formatBegDate = DateUtil.parseAndFormat(begDate, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        String formatendDate = DateUtil.parseAndFormat(endDate, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        List<Article> result = articleService.getSelectArtilceList(page, status, title, formatBegDate, formatendDate, deptId, userId, infoType);
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 跳转到添加资讯
     */
    @RequestMapping("/article_add")
    public String articleAdd(@RequestParam("cateId") String cateid, Model model) {
        List<Dict> templateType = constantFactory.findInDict(DictCodeConstants.LIB_ARTICLE_TEMPLATE_TYPE);
        StringBuffer sb = new StringBuffer();
        for (Dict dict : templateType) {
            sb.append(dict.getCode() + "=" + dict.getName()).append(",");
        }
        String subCode = sb.toString().substring(0, sb.toString().length() - 1);
        model.addAttribute("templateCode", subCode);
        return PREFIX + "article" + cateid + "_add";
    }

    /**
     * 新增资讯
     */
    @RequestMapping("/add")
    @ResponseBody
    public Tip add(@Valid Article article, BindingResult result,
                   @RequestParam(value = "photoBase64Data", required = false) String cateidphotoBase64Data,
                   @RequestParam(value = "photoBase64Data2", required = false) String cateidphotoBase64Data2,
                   @RequestParam(value = "photoBase64Data3", required = false) String cateidphotoBase64Data3) {
        if (result.hasErrors()) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        article.setOrgId(getLoginUser().getDeptId());
        article.setCreateUser(getLoginUser().getId());
        article.setUpdateUser(getLoginUser().getId());
        try {
            this.articleService.addArticle(article, cateidphotoBase64Data, cateidphotoBase64Data2,
                    cateidphotoBase64Data3);
        } catch (IOException e) {
            return fail();
        }
        return success();
    }

    /**
     * 跳转到编辑资讯
     */
    @RequestMapping("/article_edit")
    public String articleEdit(@RequestParam(value = "id") String id, @RequestParam("cateId") String cateid, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        Map<String, Object> models = this.articleService.toEdit(id);
        for (String key : models.keySet()) {
            model.addAttribute(key, models.get(key));
        }

        return PREFIX + "article" + cateid + "_edit";
    }

    /**
     * 修改资讯
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Tip edit(@Valid Article article, BindingResult result,
                    @RequestParam(value = "photoBase64Data", required = false) String photoBase64Data,
                    @RequestParam(value = "photoBase64Data2", required = false) String photoBase64Data2,
                    @RequestParam(value = "photoBase64Data3", required = false) String photoBase64Data3) {
        if (result.hasErrors()) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        article.setUpdateUser(getLoginUser().getId());
        try {

            this.articleService.updateArticleById(article, photoBase64Data, photoBase64Data2, photoBase64Data3);
        } catch (IOException e) {
            return fail();
        }
        return success();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "attch", required = false) MultipartFile file, @RequestParam(value = "type", required = false) String type) {
        String packName = "";
        if ("yp".equals(type)) {
            packName = UploadConstants.AUDIO;
        } else if ("sp".equals(type)) {
            packName = UploadConstants.VIDEO;
        }
        String strDate = DateUtil.getDays();
        String fileExtensionName = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        String fileSavePath = "";
        fileSavePath = manageProperties.getFileUploadPath() + packName + strDate + "/";
        try {
            File p = new File(fileSavePath);
            if (!p.exists()) {
                p.mkdirs();
            }
            file.transferTo(new File(fileSavePath + fileName));
        } catch (Exception e) {
            throw new BusinessException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return packName + strDate + "/" + fileName;

    }

    /**
     * 跳转到预览
     */
    @RequestMapping(value = "/article_preview")
    public String toPreview() {
        return PREFIX + "preview";
    }

    /**
     * 跳转到预览de demo
     */
    @RequestMapping(value = "/demo")
    public String toDemo() {
        return PREFIX + "demo";
    }


    @RequestMapping("/choose")
    public String choose() {
        return PREFIX + "article_choose";
    }
}
