package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.mapper.AppBannerMapper;
import com.gobestsoft.common.modular.dao.mapper.AppHomeDialogMapper;
import com.gobestsoft.common.modular.dao.mapper.CmsArticleMapper;
import com.gobestsoft.common.modular.dao.model.AppBannerPojo;
import com.gobestsoft.common.modular.homepage.model.AppHomeDialog;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.system.service.AppBannerService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【APP轮播图】控制器
 *
 * @author caoy
 * @Date 2018年02月07日 上午10:13:41
 */
@Controller
@RequestMapping("/banner")
public class AppBannerController extends BaseController {

    private String PREFIX = "banner/";
    @Autowired
    AppBannerMapper appBannerMapper;
    @Autowired
    CmsArticleMapper cmsArticleMapper;
    @Autowired
    AppBannerService appBannerService;
    @Autowired
    private ConstantFactory constantFactory;
    @Autowired
    private AppHomeDialogMapper appHomeDialogMapper;
    
    /**
     * AppBanner列表页
     *
     * @return
     */
    @RequestMapping("")
    public String banner(@RequestParam("category") String category, Model model) {
    	model.addAttribute("category", category);
        return PREFIX + "banner";
    }

    /**
     * 【APP轮播图】列表查询
     *
     * @param category banner类型
     * @param keyword  关键字
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String category
            , @RequestParam(required = false) String keyword , @RequestParam(required = false) String status) {
        // 获取分页数据
        Page page = defaultPage();
        // 画面参数设定
        Wrapper<AppBannerPojo> wrapper = new EntityWrapper().eq("category", category).orderBy("sort").orderBy("create_time", false);
        if (StringUtils.isNotEmpty(keyword)) {
            wrapper.like("title", keyword);
        }
        if (StringUtils.isNotEmpty(status)) {
        	wrapper.eq("status", status);
        }

        List<AppBannerPojo> pojos = appBannerMapper.selectPage(page, wrapper);
        List<Map<String, String>> result = new ArrayList<>();
        pojos.forEach(p -> {
            Map<String, String> m = new HashMap<>();
            m.put("id", p.getId().toString());
            m.put("title", p.getTitle());
            m.put("jumpUrl", p.getJumpUrl());
            m.put("status", "0".equals(p.getStatus().toString()) ? "启用" : "禁用");
            result.add(m);
        });

        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 相关【资讯】列表查询
     *
     * @param relationId  关联ID
     * @param keyword     查询关键字
     * @param begDate     开始时间
     * @param endDate     开始时间
     * @param articleType 类型
     */
    @RequestMapping("/articleList")
    @ResponseBody
    public Object articleList(@RequestParam(required = false) String relationId
            , @RequestParam(required = false) String keyword
            , @RequestParam(required = false) String begDate
            , @RequestParam(required = false) String endDate
            , @RequestParam(required = false) String articleType) {
        // 获取分页数据
        Page page = defaultPage();
//        List<Map<String, Object>> resultList = appBannerService.getArticleForBanner(page, keyword, begDate, endDate);
//        page.setRecords(resultList);
        return super.packForBT(page);
    }


    /**
     * 跳转到新增Banner画面
     *
     * @param category
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String category, Model model) {
    	List<Dict> bannerType = constantFactory.findInDict(DictCodeConstants.LIB_BANNER_TYPE);

    	StringBuffer sb = new StringBuffer();
        for(Dict dict: bannerType){
        	sb.append(dict.getCode() + "=" + dict.getName()).append(",");
        }
        String subCode = sb.toString().substring(0, sb.toString().length()-1);
        model.addAttribute("bannerTypeCode", subCode);
    	model.addAttribute("category", category);

//        List<Map> seminarList = appBannerMapper.selectSeminarList();
//        model.addAttribute("seminarList", seminarList);
        return PREFIX + "banner_add";
    }

    /**
     * 新增保存Banner
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Tip save(@Valid AppBannerPojo banner) {
        try {
            banner.setCover(WebSiteUtil.base64TransferImage(banner.getCover(),manageProperties.getFileUploadPath(),UploadConstants.BANNER));
        } catch (IOException e) {
            e.printStackTrace();
        }
        banner.setCreateUid(getLoginUser().getId());
    	banner.setCreateTime(DateUtil.getAllTime());
        appBannerMapper.insert(banner);
        return success();
    }
    
    /**
     * 跳转至更新banner页面
     * 
     * @param model
     * @param category
     * @param bannerId
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, String category, String bannerId) {
    	List<Dict> bannerType = constantFactory.findInDict(DictCodeConstants.LIB_BANNER_TYPE);
    	StringBuffer sb = new StringBuffer();
        for(Dict dict: bannerType){
        	sb.append(dict.getCode() + "=" + dict.getName()).append(",");
        }
        String subCode = sb.toString().substring(0, sb.toString().length()-1);
        model.addAttribute("bannerTypeCode", subCode);
    	// 判断当前ID是否存在
        AppBannerPojo bannerPojo = appBannerMapper.selectById(Integer.valueOf(bannerId));
        if (ToolUtil.isEmpty(bannerPojo)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 封面地址全路径返回
        bannerPojo.setCover(WebSiteUtil.getBrowseUrl(bannerPojo.getCover()));
        model.addAttribute("bannerInfo", bannerPojo);
        model.addAttribute("category", category);
//        List<Map> seminarList = appBannerMapper.selectSeminarList();
//        model.addAttribute("seminarList", seminarList);
    	return PREFIX + "banner_edit";
    }
    
    /**
     * 更新保存banner
     * @param banner
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Tip update(@Valid AppBannerPojo banner) {
    	// 查询源数据
        AppBannerPojo bannerOld = appBannerMapper.selectById(banner.getId());
        if (ToolUtil.isEmpty(bannerOld)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        try {
            banner.setCover(WebSiteUtil.base64TransferImage(banner.getCover(),manageProperties.getFileUploadPath(),UploadConstants.BANNER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	appBannerMapper.updateById(banner);
    	return success();
    }

    /**
     * 启用Banner
     */
    @RequestMapping(value = "/online", method = RequestMethod.POST)
    @ResponseBody
    public Tip online(String bannerIds) {
    	if (ToolUtil.isEmpty(bannerIds)) {
            return fail("bannerId不能为空");
        }
    	// 批量处理启用操作
        appBannerService.doOnline(bannerIds.split(","));
        return success();
    }

    /**
     * 禁用Banner
     */
    @RequestMapping(value = "/offline", method = RequestMethod.POST)
    @ResponseBody
    public Tip offline(String bannerIds) {
    	if (ToolUtil.isEmpty(bannerIds)) {
            return fail("bannerId不能为空");
        }
    	// 批量处理禁用操作
        appBannerService.doOffline(bannerIds.split(","));
        return success();
    }

    /**
     * 删除Banner
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Tip delete(String bannerIds) {
        if (ToolUtil.isEmpty(bannerIds)) {
            return fail("bannerId不能为空");
        }
        // 批量处理下架操作
        appBannerService.doDelete(bannerIds.split(","));
        return success();
    }

    /**
     * 首页弹窗
     */
    @RequestMapping("/getShowDialog")
    public String getShowDialog(Model model) {
        List<AppHomeDialog> appHomeDialog = appHomeDialogMapper.selectList(new EntityWrapper().orderBy("create_time", false));
        if(appHomeDialog.size() > 0){
            AppHomeDialog result = appHomeDialog.get(0);
            result.setShowImg(WebSiteUtil.getBrowseUrl(result.getShowImg()));
            model.addAttribute("result", result);
        }else{
            model.addAttribute("result", new AppHomeDialog());
        }
        return PREFIX + "showDialog";
    }

    /**
     * 保存首页弹窗
     */
    @RequestMapping(value = "/showDialog", method = RequestMethod.POST)
    @ResponseBody
    public Tip getShowDialog(@Valid AppHomeDialog homeDialog){
        try {
            homeDialog.setShowImg(WebSiteUtil.base64TransferImage(homeDialog.getShowImg(),manageProperties.getFileUploadPath(),UploadConstants.BANNER));
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeDialog.setCreateBy(getLoginUser().getId());
        homeDialog.setCreateTime(DateUtil.getAllTime());
        appHomeDialogMapper.insert(homeDialog);
        return success();
    }

    @ResponseBody
    @RequestMapping("uploadFile")
    public Tip saveFile(@RequestParam("uploadFile") MultipartFile uploadFile){

        String relativepath = manageProperties.getFileUploadPath() + UploadConstants.BANNER;

        String filename = "";

        String suffix = FilenameUtils.getExtension(uploadFile.getOriginalFilename());

        filename += (UUIDUtil.getUUID()+"."+suffix);
        File file = new File(relativepath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            uploadFile.transferTo(new File(relativepath+filename));
            return new Tip(200,"", WebSiteUtil.getBrowseUrl(UploadConstants.BANNER+filename));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Tip(500,"上传失败",null);



    }

    @RequestMapping("/chooseSeminar")
    public String chooseSeminar(){

        return PREFIX+"banner_choose_seminar";

    }

    /**
     * 查询专题
     * @param query_name
     * @return
     */
    @RequestMapping(value = "/seminarList", method = RequestMethod.POST)
    @ResponseBody
    public Object seminarList(@RequestParam(required = false)String query_name){
        Page page = defaultPage();

        List<Map> seminarList = appBannerMapper.selectSeminarList(page, query_name);

        page.setRecords(seminarList);

        return super.packForBT(page);

    }

}
