package com.gobestsoft.mamage.moudle.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.mapper.AppVersionMapper;
import com.gobestsoft.common.modular.dao.model.AppVersionPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * app版本 控制器
 *
 * @author gobestsoft
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
@RequestMapping(AppVersionController.SUB_CONTEXT)
public class AppVersionController extends BaseController {

    static final String SUB_CONTEXT = "/appVersion";

    private String PREFIX = "/system/appVersion/";

    @Resource
    AppVersionMapper appVersionMapper;

    /**
     * 跳转到资讯的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "appVersion";
    }

    /**
     * 跳转到版本详情
     */
    @RequestMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        AppVersionPojo appVersionPojo = appVersionMapper.selectById(id);
        appVersionPojo.setDownloadAddress(WebSiteUtil.getBrowseUrl(appVersionPojo.getDownloadAddress()));
        model.addAttribute("pojo",appVersionPojo);
        return PREFIX + "detail";
    }

    /**
     * 跳转到发布新版
     */
    @RequestMapping(value = "/openAdd")
    public String openAdd(Model model) {
        AppVersionPojo appVersionPojo = new AppVersionPojo();
        model.addAttribute(appVersionPojo);
        return PREFIX + "detail";
    }

    /**
     * 删除版本
     */
    @ResponseBody
    @RequestMapping(value = "/del")
    public Tip delVer(Model model, @RequestParam("id") Integer id){

        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        appVersionMapper.deleteById(id);
        return success();
    }



    /**
     * 发布新版
     */

    @RequestMapping(value = "/add")
    @ResponseBody
    public Tip add(@Valid AppVersionPojo appVersion, @RequestParam(value = "apk", required = false) MultipartFile apk, BindingResult result) {
        if (result.hasErrors()) {
            return fail(result.getFieldError().getDefaultMessage());
        }
        String nowTime = DateUtil.getAllTime();
        if (apk != null && !apk.isEmpty()) {
            try {
                appVersion.setDownloadAddress(fileUpload(apk, UploadConstants.APK_MIR));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        appVersion.setUpdateTime(nowTime);
        appVersionMapper.insert(appVersion);
        return success();
    }


    /**
     * 查询资讯列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String begDate
            , @RequestParam(required = false) String endDate
            , @RequestParam(required = false) String keyword) {
        if(StringUtils.isNotEmpty(begDate)){
            begDate=DateUtil.parseAndFormat(begDate,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss");
        }
        if(StringUtils.isNotEmpty(endDate)){
            endDate=DateUtil.parseAndFormat(endDate,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss");
        }
        Page<AppVersionPojo> page = defaultPage();
        List<AppVersionPojo> result = appVersionMapper.getVersionList(page, begDate, endDate, keyword);
        result.forEach(m -> {
            if (StringUtils.isNotEmpty(m.getUpdateTime())) {
                m.setUpdateTime(DateUtil.parseAndFormat(m.getUpdateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
            }
        });
        page.setRecords(result);
        return super.packForBT(page);
    }

}
