package com.gobestsoft.admin.modular.cms.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.cms.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by duanmu on 2018/9/11.
 */
@Controller
@RequestMapping(value = "/topic")
public class TopicController extends BaseController{

    private String PREFIX = "/cms/topic/";

    @Autowired
    private TopicService topicService;

    /**
     * 跳转到专题首页
     */
    @RequestMapping("")
    public String index(Model model){
        return PREFIX + "topiclist";
    }


    /**
     * 查询专题列表list
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false, value = "name") String name,
                       @RequestParam(required = false, value = "begDate") String begDate,
                       @RequestParam(required = false, value = "endDate") String endDate){
        Page<CmsCategoryPojo> page = defaultPage();
        begDate = DateUtil.parseAndFormat(begDate, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        endDate = DateUtil.parseAndFormat(endDate, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        List<CmsCategoryPojo> result = topicService.getTopicList(page, name, begDate, endDate);
        page.setRecords(result);
        return super.packForBT(page);

    }

    /**
     * 专题增加
     */
    @RequestMapping("/topic_add")
    public String topicAdd(Model model){
        return PREFIX + "topic_add";
    }

    /**
     * 专题数据添加
     */
    @RequestMapping("/add")
    @ResponseBody
    public Tip add(@Valid CmsCategoryPojo pojo, BindingResult result,
                    @RequestParam(value = "photoBase64Data", required = false) String topicBase64Data){

        if(result.hasErrors()){
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        String uid = getLoginUser().getId();
        try {
            this.topicService.addTopic(pojo,topicBase64Data, uid);
        } catch (IOException e) {
            return fail();
        }
        return success();
    }

    /**
     * 专题编辑
     */
    @RequestMapping("/topic_edit")
    public String editTopic(@RequestParam(value = "id") String id, Model model){
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        CmsCategoryPojo pojo = topicService.getTopicInfoById(id);
        model.addAttribute("pojo", pojo);

        return PREFIX + "topic_edit";

    }

    /**
     * 专题数据编辑
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Tip edit(@Valid CmsCategoryPojo pojo, BindingResult result,
                    @RequestParam(value = "photoBase64Data", required = false) String topicBase64Data){
        if (result.hasErrors()) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        pojo.setUpdateUser(getLoginUser().getId());
        try {

            this.topicService.updateTopicById(pojo, topicBase64Data);
        } catch (IOException e) {
            return fail();
        }

        return success();
    }

    /**
     * 删除专题资讯
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Tip del(@RequestParam("id") String id){

        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.topicService.delById(id);
        return success();
    }

}
