package com.gobestsoft.api.modular.cms.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.api.modular.appuser.model.AppUserDto;
import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.cms.model.AppBannerDto;
import com.gobestsoft.api.modular.cms.model.Attachment;
import com.gobestsoft.common.modular.cms.model.ShowCmsCourseEntity;
import com.gobestsoft.common.modular.dao.mapper.*;
import com.gobestsoft.common.modular.dao.model.AppBannerPojo;
import com.gobestsoft.common.modular.dao.model.AppVersionPojo;
import com.gobestsoft.common.modular.dao.model.CmsCourseOperationPojo;
import com.gobestsoft.core.reids.RedisConstants;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.core.util.ZXingCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/show-page")
@Slf4j
public class BrowseController extends BaseController {

    @Autowired
    private CmsArticleMapper cmsArticleMapper;

    @Autowired
    private CmsCourseMapper cmsCourseMapper;

    @Autowired
    private CmsCourseOperationMapper cmsCourseOperationMapper;

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Autowired
    private AppBannerMapper appBannerMapper;

    @RequestMapping("/layout")
    public String layout() {
        return "/layout/_Layout";
    }


    /**
     * 预览资讯详情
     *
     * @param articleId 资讯ID
     * @param model
     * @return
     */
    @RequestMapping("/show-article/{articleId}")
    public String showArticle(@PathVariable("articleId") String articleId, Model model) {
        String token = getWebToken();
        AppUserDto appUserDto = appUserService.getUserInfoByToken(token);
        try {
            if (StringUtils.isEmpty(articleId)) return "404";
            String nowTime = DateUtil.getAllTime();
            Map<String, Object> article = cmsArticleMapper.selectShowOne(articleId, nowTime);
            if (article == null) return "404";
            article.put("content", HtmlUtils.htmlUnescape(article.get("content").toString().replaceAll("& ", "&")));
            if (ToolUtil.isEmpty(article.get("author"))) {
                article.put("author", "");
            }
            String publishTime = DateUtil.getAllTime();
            try {
                if (StringUtils.isNotEmpty(article.get("published_time").toString())) {
                    publishTime = article.get("published_time").toString();
                }
                publishTime = DateUtil.parseAndFormat(publishTime, "yyyyMMddHHmmss", "yyyy-MM-dd");
                model.addAttribute("publishTime", publishTime);
            } catch (Exception ex) {
                model.addAttribute("publishTime", "");
            }
            model.addAttribute("browsenum", article.get("browseNum"));
            model.addAttribute("commentnum", article.get("commentNum"));
            model.addAttribute("article", article);

            List<Attachment> attachment = new ArrayList<>();
            if ("1".equals(article.get("info_type").toString())) {
                String jumpUrl = article.get("jump_url").toString();
                if (StringUtils.isNotEmpty(jumpUrl)) {
                    Arrays.asList(jumpUrl.split(",")).forEach(s -> {
                        if (StringUtils.isNotEmpty(s)) {
                            attachment.add(new Attachment(WebSiteUtil.getBrowseUrl(s), getFileName(s), StringUtils.isEmpty(article.get("author").toString()) ? "海南省总工会" : article.get("author").toString(), WebSiteUtil.getBrowseUrl(article.get("cover_path").toString())));
                        }
                    });
                }
            } else if ("2".equals(article.get("info_type").toString())) {
                String jumpUrl = article.get("jump_url").toString();
                if (StringUtils.isNotEmpty(jumpUrl)) {
                    Arrays.asList(jumpUrl.split(",")).forEach(s -> {
                        if (StringUtils.isNotEmpty(s)) {
                            attachment.add(new Attachment(WebSiteUtil.getBrowseUrl(s), null, null, WebSiteUtil.getBrowseUrl(article.get("cover_path").toString())));
                        }
                    });
                }
            }
            model.addAttribute("attachment", attachment);
            model.addAttribute("apiPath", apiProperties.getApiWebSite());
        } catch (Exception ex) {
            log.error("{}", ex);
            return "404";
        }

        cmsArticleMapper.addArticleOperation(articleId, appUserDto == null ? null : appUserDto.getAuid(), DateUtil.getAllTime(), 1);
        return "article";
    }

    /**
     * 根据路径返回文件名
     *
     * @return
     */
    private String getFileName(String filePathName) {
        return filePathName.substring(filePathName.lastIndexOf('/') + 1, filePathName.length());
    }

    /**
     * 课程页面
     *
     * @param courseId
     * @return
     */
    @RequestMapping("/course/{courseId}")
    public String course(@PathVariable("courseId") int courseId, Model model) {
        String token = getWebToken();
        ShowCmsCourseEntity pojo = cmsCourseMapper.showCmsCourse(courseId);
        if (pojo == null) {
            return "404";
        }
        addCourseOperation(courseId, token, 3);
        pojo.setCover(WebSiteUtil.getBrowseUrl(pojo.getCover()));
        pojo.setContent(HtmlUtils.htmlUnescape(pojo.getContent().replaceAll("& ", "&")));
        pojo.setCreateTime(DateUtil.parseAndFormat(pojo.getCreateTime(),
                "yyyyMMddHHmmss", "yyyy-MM-dd"));
        model.addAttribute("course", pojo);

        List<Attachment> attachment = new ArrayList<>();
        if (pojo.getInfoType() == 1) {
            if (StringUtils.isNotEmpty(pojo.getAttachment())) {
                Arrays.asList(pojo.getAttachment().split(",")).forEach(s -> {
                    if (StringUtils.isNotEmpty(s)) {
                        attachment.add(new Attachment(WebSiteUtil.getBrowseUrl(s), getFileName(s), StringUtils.isEmpty(pojo.getCreateUserName()) ? "海南省总工会" : pojo.getCreateUserName(), WebSiteUtil.getBrowseUrl(pojo.getCover())));
                    }
                });
            }
        } else if (pojo.getInfoType() == 2) {
            if (StringUtils.isNotEmpty(pojo.getAttachment())) {
                Arrays.asList(pojo.getAttachment().split(",")).forEach(s -> {
                    if (StringUtils.isNotEmpty(s)) {
                        attachment.add(new Attachment(WebSiteUtil.getBrowseUrl(s), null, null, WebSiteUtil.getBrowseUrl(pojo.getCover())));
                    }
                });
            }
        }
        model.addAttribute("attachment", attachment);
        if (pojo.getType() == 0 || pojo.getPid() != 0) {
            return "course";
        }
        List<Map<String, Object>> catalogSource = cmsCourseMapper.selectMaps(new EntityWrapper().
                eq("pid", pojo.getId()).eq("del_flg", 0).eq("status", 1));
        catalogSource.forEach(s -> {
            s.put("jumpUrl", apiProperties.getCourseUrl(String.valueOf(s.get("id")), false));
            s.put("cover", WebSiteUtil.getBrowseUrl(String.valueOf(s.get("cover"))));
            s.put("createTime", DateUtil.parseAndFormat(String.valueOf(s.get("createTime")),
                    "yyyyMMddHHmmss", "yyyy-MM-dd"));
        });
        model.addAttribute("catalog", catalogSource);
        return "series_course";
    }


    /**
     * 添加课程浏览数
     *
     * @param courseId
     * @param token
     */
    private void addCourseOperation(int courseId, String token, int type) {
        AppUserDto appUserDto = appUserService.getUserInfoByToken(token);
        CmsCourseOperationPojo cmsCourseOperationPojo = new CmsCourseOperationPojo();
        cmsCourseOperationPojo.setAuid(appUserDto == null ? null : appUserDto.getAuid());
        cmsCourseOperationPojo.setCourse_id(courseId);
        cmsCourseOperationPojo.setCreateTime(DateUtil.getAllTime());
        cmsCourseOperationPojo.setType(type);
        cmsCourseOperationMapper.insert(cmsCourseOperationPojo);
//        if (appUserDto != null) {
//            //'10', '课程浏览' 添加积分
//            appUserService.completeTaskGiveIntegral(appUserDto, 10);
//        }
    }

    /**
     * 二维码分享地址
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/getQrCodeByPlatform")
    @ResponseBody
    public void getQrCodeByPlatform(HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("image/jpeg");
        FileInputStream fis = null;
        try {
            File qrFile = ZXingCode.getLogoQRCode(getClass().getResource("/hn-logo.png").openStream(),
                    apiProperties.getDownloadUrl(), null);
            fis = new FileInputStream(qrFile);
            int size = fis.available(); //得到文件大小
            byte data[] = new byte[size];
            fis.read(data);  //读数据
            fis.close();
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
            qrFile.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载地址
     *
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/download")
    public String download(HttpServletResponse response, HttpServletRequest request) {
        redisUtils.incr(RedisConstants.DOWNLOAD_COUNT);
        String userAgent = request.getHeader("user-agent").toLowerCase();

        if (userAgent.indexOf("iphone") > -1 || userAgent.indexOf("ipod") > -1 || userAgent.indexOf("mac") > -1) {
            AppVersionPojo appVersion = appVersionMapper.getLastAppVersion(1);
            if (appVersion != null && StringUtils.isNotEmpty(appVersion.getDownloadAddress())) {
                return REDIRECT + WebSiteUtil.getBrowseUrl(appVersion.getDownloadAddress());
            }
            return REDIRECT + apiProperties.getAppStoreLink();
        }
        if (userAgent.indexOf("micromessenger") > -1) {
            return "share_it";
        }
        AppVersionPojo appVersion = appVersionMapper.getLastAppVersion(0);
        if (appVersion != null && StringUtils.isNotEmpty(appVersion.getDownloadAddress())) {
            return REDIRECT + WebSiteUtil.getBrowseUrl(appVersion.getDownloadAddress());
        }
        return REDIRECT + apiProperties.getApkDownload();
    }


    /**
     * 分享回调
     * create by gutongwei
     * 2018/6/25
     *
     * @param targetId
     * @return
     */
    @RequestMapping("/shareBack")
    @ResponseBody
    public BaseResult shareBack(@RequestParam("targetId") String targetId) {
        if (isLogin()) {
            cmsArticleMapper.addArticleOperation(targetId, getUserId(), DateUtil.getAllTime(), 3);
            appUserService.completeTaskGiveIntegral(getUserDto(), 7);

        }
        return success();
    }

    /**
     * 互动社区协议
     *
     * @return
     */
    @RequestMapping("/hdsqxy")
    public String hdsqxy() {
        return "hdsqxy";
    }

    /**
     * 用户服务协议
     *
     * @return
     */
    @RequestMapping("/yhwfxy")
    public String yhwfxy() {
        return "yhwfxy";
    }


    /**
     * 敬请期待
     *
     * @return
     */
    @RequestMapping("/come-soon")
    public String comeSoon() {
        return "comeSoon";
    }


    /**
     * 普法活动
     */
    @RequestMapping("/pfhd")
    public String getPfhd(Model model){
        List<AppBannerPojo> banners = appBannerMapper
                .selectList(new EntityWrapper<AppBannerPojo>().eq("title", "职工法律知识有奖竞答").eq("status", 0).orderBy("create_time", false));
        AppBannerPojo pojo = banners.get(0);
        model.addAttribute("imageUrl", WebSiteUtil.getBrowseUrl(pojo.getCover()));
        model.addAttribute("title", pojo.getTitle());
        return "pfhd";
    }

}
