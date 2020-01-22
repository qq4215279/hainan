package com.gobestsoft.admin.modular.cky.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.admin.modular.cky.service.StudioService;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * create by gutongwei
 * on 2018/8/31 上午8:47
 */
@Controller
@RequestMapping("/studio")
public class StudioController extends BaseController {

    private final String view_path = "studio/";//信息展示页面放置的相对文件夹

    @Autowired
    private StudioService studioService;

    /**
     * 工作室列表页面
     * create by gutongwei
     * on 2018/6/25
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return view_path + "index";
    }

    /**
     * 工作室列表数据
     * create by gutongwei
     * on 2018/6/25
     *
     * @param name 工作室名称
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "name", required = false) String name) {
        Page page = defaultPage();
        page.setRecords(studioService.list(name, page));
        return packForBT(page);
    }


    /**
     * 工作室页面
     * create by gutongwei
     * on 2018/6/25
     *
     * @return
     */
    @RequestMapping("/studio")
    public String studio() {
        return view_path + "studio";
    }

    /**
     * 工作室详细数据
     * create by gutongwei
     * on 2018/6/25
     *
     * @param id 工作室ID
     * @return
     */
    @RequestMapping("/studioDetail")
    @ResponseBody
    public Tip studioDetail(@RequestParam("id") int id) {
        return tip(200, null, studioService.getStudioDetail(id));
    }


    /**
     * 新增工作室或更新工作室
     * create by gutongwei
     * on 2018/6/25
     *
     * @param id
     * @param name    工作室名称
     * @param cover   工作室封面
     * @param banner  工作室banner
     * @param summary 工作室描述
     * @return
     */
    @RequestMapping(value = "/renew", method = RequestMethod.POST)
    @ResponseBody
    public Tip renew(@RequestParam(value = "id", required = false) Integer id,
                     @RequestParam("name") String name, @RequestParam("cover") String cover,
                     @RequestParam("banner") String banner, @RequestParam("summary") String summary) throws IOException {
        studioService.renew(id, name, base64TransferImage(cover, manageProperties.getFileUploadPath(), UploadConstants.CKY_STUDIO), base64TransferImage(banner.split("_"), manageProperties.getFileUploadPath(), UploadConstants.CKY_STUDIO_BANNER), summary, getLoginUser().getId());
        return success();
    }


    /**
     * 删除工作室
     * create by gutongwei
     * on 2018/6/25
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteStudio", method = RequestMethod.POST)
    @ResponseBody
    public Tip deleteStudio(@Param("ids") String ids) {
        studioService.deleteStudio(ids);
        return success();
    }


    /**
     * 工作室荣誉列表页面
     * create by gutongwei
     * on 2018/6/25
     *
     * @return
     */
    @RequestMapping("/honors")
    public String honors() {
        return view_path + "honors";
    }


    /**
     * 工作室荣誉页面
     * create by gutongwei
     * on 2018/6/25
     *
     * @return
     */
    @RequestMapping("/honor")
    public String honor() {
        return view_path + "honor";
    }


    /**
     * 工作室荣誉列表
     * create by gutongwei
     * on 2018/6/25
     *
     * @param title 工作室荣誉名称
     * @return
     */
    @RequestMapping("/honor/list")
    @ResponseBody
    public Object honorList(@RequestParam("studioId") int studioId, @RequestParam(value = "title", required = false) String title) {
        Page page = defaultPage();
        page.setRecords(studioService.honorList(title, studioId, page));
        return packForBT(page);
    }

    /**
     * 工作室荣誉详情
     * create by gutongwei
     * on 2018/6/26
     *
     * @param id
     * @return
     */
    @RequestMapping("/honor/detail")
    @ResponseBody
    public Tip honorDetail(@RequestParam("id") Integer id) {
        return tip(200, null, studioService.honorDetail(id));
    }

    /**
     * 添加或更新荣誉
     * create by gutongwei
     * on 2018/6/25
     *
     * @param id
     * @param studioId 工作室id
     * @param title    荣誉标题
     * @param content  荣誉内容
     * @param images   荣誉图片
     * @param isShow   是否展示到列表
     * @return
     */
    @RequestMapping(value = "/honor/renew", method = RequestMethod.POST)
    @ResponseBody
    public Tip honorRenew(@RequestParam("id") Integer id, @RequestParam("studioId") int studioId,
                          @RequestParam("title") String title, @RequestParam("content") String content,
                          @RequestParam("images") String images, @RequestParam("getTime") String getTime,
                          @RequestParam("isShow") int isShow) throws IOException {
        getTime = DateUtil.parseAndFormat(getTime, "yyyy-MM-dd", "yyyyMMdd");
        studioService.honorRenew(id, studioId, title, content, base64TransferImage(images.split("_"), manageProperties.getFileUploadPath(), UploadConstants.CKY_STUDIO_HONOR), isShow, getTime, getLoginUser().getId());
        return success();
    }

    /**
     * 删除工作室荣誉
     * create by gutongwei
     * on 2018/6/25
     *
     * @param ids 荣誉id，以','分割
     * @return
     */
    @RequestMapping(value = "/honor/deleteHonor", method = RequestMethod.POST)
    @ResponseBody
    public Tip deleteHonor(@RequestParam("ids") String ids) {
        studioService.deleteHonor(ids);
        return success();
    }
}
