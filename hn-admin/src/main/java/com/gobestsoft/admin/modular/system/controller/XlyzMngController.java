package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.mapper.XlyzAmMapper;
import com.gobestsoft.common.modular.dao.mapper.XlyzMapper;
import com.gobestsoft.common.modular.dao.model.XlyzAmPojo;
import com.gobestsoft.common.modular.dao.model.XlyzMasterPojo;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.core.util.ffmpeg.FfmpegUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 心灵驿站导师
 */
@Controller
@RequestMapping("/xlyzMng")
@Slf4j
public class XlyzMngController extends BaseController {

    @Autowired
    protected ManageProperties manageProperties;

    static final String PREFIX = "xlyzMng/";
    @Autowired
    XlyzMapper xlyzMapper;

    @Autowired
    XlyzAmMapper xlyzAmMapper;

    /**
     * AppBanner列表页
     *
     * @return
     */
    @RequestMapping("")
    public String banner() {
        return PREFIX + "xlyz_list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list (@RequestParam(required = false) String query_name){

        Page page = defaultPage();


        Wrapper<XlyzMasterPojo> wrapper = new EntityWrapper().eq("del_flag",0).orderBy("sort").orderBy("sort", true);

        if(!StringUtils.isEmpty(query_name)){
            wrapper.like("name",query_name);
        }

        List<XlyzMasterPojo> list = xlyzMapper.selectPage(page, wrapper);
        page.setRecords(list);

        return super.packForBT(page);


    }


    @RequestMapping("/add")
    public String add() {
        return PREFIX + "xlyz_add";
    }

    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam(required = true) String id) {

        XlyzMasterPojo bean = xlyzMapper.selectById(id);

        bean.setAvatar(WebSiteUtil.getBrowseUrl(bean.getAvatar()));

        model.addAttribute("bean",bean);

        return PREFIX + "xlyz_edit";
    }



    @RequestMapping("/save")
    @ResponseBody
    public Tip save (XlyzMasterPojo xlyzMasterPojo){


        try {
            String image = WebSiteUtil.base64TransferImage(xlyzMasterPojo.getAvatar(), manageProperties.getFileUploadPath(), UploadConstants.XLZX_AVATAR);
            xlyzMasterPojo.setAvatar(image);
            xlyzMapper.insert(xlyzMasterPojo);
            return success();
        }catch (Exception e){
            log.info(e.getMessage());
            return fail("更新失败");
        }

    }


    @RequestMapping("/update")
    @ResponseBody
    public Tip update (XlyzMasterPojo xlyzMasterPojo){

        XlyzMasterPojo old = xlyzMapper.selectById(xlyzMasterPojo.getId());
        if (ToolUtil.isEmpty(old)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        try {
            String image = WebSiteUtil.base64TransferImage(xlyzMasterPojo.getAvatar(), manageProperties.getFileUploadPath(), UploadConstants.XLZX_AVATAR);
            xlyzMasterPojo.setAvatar(image);
            xlyzMapper.updateById(xlyzMasterPojo);
            return success();
        }catch (Exception e){
            log.info(e.getMessage());
            return fail("更新失败");
        }

    }


    @ResponseBody
    @RequestMapping("/delete")
    public Tip delete(@RequestParam(required = true) String id){

        try {
            xlyzMapper.deleteById(id);
            return success();
        }
        catch (Exception e){
            return fail("失败");
        }
    }

    @RequestMapping("/amList")
    public String amList(){

        return PREFIX+"xlyz_am_list";
    }

    //心理按摩音频附件管理
    @RequestMapping("/getAmList")
    @ResponseBody
    public Object getAmList(@RequestParam(required = false)String query_title){
        Page page = defaultPage();

        Wrapper<XlyzAmPojo> wrapper= new EntityWrapper();

        if(StringUtils.isNotEmpty(query_title)){
            wrapper.like("title",query_title);
        }

        List<XlyzAmPojo> list = xlyzAmMapper.selectPage(page, wrapper);
        for(XlyzAmPojo pojo:list){
            pojo.setAttachment(WebSiteUtil.getBrowseUrl(pojo.getAttachment()));
            pojo.setCreateTime(DateUtil.parseAndFormat(pojo.getCreateTime(),"yyyyMMddHHmmss","yyyy-MM-dd"));
        }
        page.setRecords(list);

        return super.packForBT(page);

    }

    @RequestMapping("/xlam_add")
    public String xlam_add(){

        return PREFIX+"xlyz_am_add";

    }
    @RequestMapping("/xlam_edit")
    public String am_edit(Model model,@RequestParam(required = true) String id){

        XlyzAmPojo pojo = xlyzAmMapper.selectById(id);
//        pojo.setAttachment(WebSiteUtil.getBrowseUrl(pojo.getAttachment()));
        pojo.setCover(WebSiteUtil.getBrowseUrl(pojo.getCover()));
        model.addAttribute("bean",pojo);
        return PREFIX+"xlyz_am_edit";

    }


    @RequestMapping("/saveAudio")
    @ResponseBody
    public Tip saveAudio (XlyzAmPojo xlyzAmPojo){
        try {
            xlyzAmPojo.setCreateUid(getLoginUser().getId());
            String cover = WebSiteUtil.base64TransferImage(xlyzAmPojo.getCover(), manageProperties.getFileUploadPath(), UploadConstants.XLYZ_AM);
            xlyzAmPojo.setCover(cover);
            xlyzAmPojo.setCreateTime(DateUtil.format(new Date(),"yyyyMMddHHmmss"));
            xlyzAmMapper.insert(xlyzAmPojo);
            return success();
        }catch (Exception e){
            log.info(e.getMessage());
            return fail("更新失败");
        }

    }


    @RequestMapping("/updateAudio")
    @ResponseBody
    public Tip updateAudio (XlyzAmPojo xlyzAmPojo){
        try {

            XlyzAmPojo pojo = xlyzAmMapper.selectById(xlyzAmPojo.getId());
            if(pojo==null){
                throw new  BusinessException(BizExceptionEnum.REQUEST_NULL);
            }

            xlyzAmPojo.setCreateUid(getLoginUser().getId());
            String cover = WebSiteUtil.base64TransferImage(xlyzAmPojo.getCover(), manageProperties.getFileUploadPath(), UploadConstants.XLYZ_AM);
            xlyzAmPojo.setCover(cover);
            xlyzAmMapper.updateById(xlyzAmPojo);
            return success();
        }catch (Exception e){
            log.info(e.getMessage());
            return fail("更新失败");
        }

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map upload(@RequestParam(value = "attch", required = false) MultipartFile file, @RequestParam(value = "type", required = false) String type) {
        String packName = "";
        String fileExtensionName = FilenameUtils.getExtension(file.getOriginalFilename());
        if(!"mp3".equals(fileExtensionName.toLowerCase())){
            throw new BusinessException("只能上传mp3");
        }
        packName = UploadConstants.AUDIO;


        String res=null;
        try {

            long duration=0;
            //获取音频文件的时长
            res = fileUpload(file, packName);
            duration =FfmpegUtil.getMediaDuration( manageProperties.getFileUploadPath()+res);
            ObjectMap map = ObjectMap.getInstance();
            map.put("fileName",res);
            map.put("duration",duration);

            return map;
    } catch (Exception e) {
            e.printStackTrace();
                throw new BusinessException(BizExceptionEnum.UPLOAD_ERROR);
        }

    }



    @ResponseBody
    @RequestMapping("/amDelete")
    public Tip amDelete(@RequestParam(required = true) String id){

        try {
            xlyzAmMapper.deleteById(id);
            return success();
        }
        catch (Exception e){
            return fail("失败");
        }
    }

}
