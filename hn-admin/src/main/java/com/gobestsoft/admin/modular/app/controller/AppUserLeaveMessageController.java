package com.gobestsoft.admin.modular.app.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.app.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * appuserleaveMessage控制器
 *
 * Created by li on 2018/8/30.
 */
@Controller
@RequestMapping("/appUserLeaveMessage")
public class AppUserLeaveMessageController extends BaseController{

    private String PREFIX = "/app/appuser/";

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ConstantFactory constantFactory;

    /**
     * 跳转到appuserleaveMessage首页
     */
    @RequestMapping("")
    public String index(Model model){
    	return PREFIX + "appUserLeaveMessage";
    }

    /**
     * 查询app用户留言list
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false, value = "content") String content,
                       @RequestParam(required = false, value = "begDate") String begDate,
                       @RequestParam(required = false, value = "endDate") String endDate
                        ){
        Page<Map<String,Object>> page = defaultPage();
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("content",content);
        queryMap.put("begDate",begDate);
        queryMap.put("endDate",endDate);
        List<Map<String,Object>> result = appUserService.appuUserLeaveMessageList(page,queryMap);
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 跳转到详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/detail")
    public String detail(@RequestParam("id") String id,Model model){
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        HashMap<String, Object> appUserLeaveMessage = appUserService.appUserLeaveMessageById(id);
        model.addAttribute("appUserLeaveMessage", appUserLeaveMessage);

        return PREFIX+"appUserLeaveMessage_detail";
    }
}
