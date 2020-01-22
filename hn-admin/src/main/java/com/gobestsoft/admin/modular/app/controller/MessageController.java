package com.gobestsoft.admin.modular.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.model.CmsArticleEntity;
import com.gobestsoft.common.modular.dao.mapper.CmsArticleMapper;
import com.gobestsoft.common.modular.dao.mapper.CmsCategoryMapper;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.app.dto.MessageDto;
import com.gobestsoft.mamage.moudle.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by li
 * on 2018/7/5 下午2:17
 */
@RequestMapping("/msg")
@Controller
public class MessageController extends BaseController {

    private String PREFIX = "/msg/";

    @Resource(name="aMsg")
    private MessageService messageService;

    @Autowired
    private CmsCategoryMapper cmsCategoryMapper;

    @Autowired
    private CmsArticleMapper cmsArticleMapper;

    @RequestMapping("/index")
    public String appUser(Model model) {
        model.addAttribute("sourceFromList", messageService.getMessageCategory());
        return PREFIX + "msg";
    }

    /**
     * 用户选择
     *
     * @return
     */
    @RequestMapping("/select")
    public String select() {
        return PREFIX + "appuser_select";
    }
    /**
     * 获取消息列表
     *
     * @param categoryId
     * @param appoint    消息范围 0：所有用户。1：指定用户。
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                       @RequestParam(value = "appoint", required = false) Integer appoint) {

        Page page = defaultPage();
        List<Map<String, String>> list = messageService.getAllMessage(page, categoryId, appoint);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 消息发送界面
     *
     * @return
     */
    @RequestMapping("/send")
    public String send(@RequestParam(value = "id", required = false)String id, Model model) {
        if(ToolUtil.isNotEmpty(id)){
            model.addAttribute("msgType", "1");
        } else {
            model.addAttribute("msgType", "0");
        }

        model.addAttribute("sourceFromList", messageService.getMessageCategory());
        return PREFIX + "add";
    }

    /**
     * 获取消息列表
     *
     * @param id
     * @return
     */
    @RequestMapping("/getMessage")
    @ResponseBody
    public Tip getMessage(@RequestParam("id") int id) {
        return tip(200,null,messageService.getMessage(id));
    }


    /**
     * 发送消息
     *
     * @return
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public Tip sendMessage(@RequestBody MessageDto dto) {
        messageService.sendMessageByAuid(dto, getLoginUser().getId());
        return success();
    }


    /**
     * 资讯选择页
     *
     * @return
     */
    @RequestMapping("/selectArticle")
    public String selectArticle(Model model) {
        List<CmsCategoryPojo> pojos = cmsCategoryMapper.selectList(new EntityWrapper().ne("pid", 0));
        List<Dict> categoryDictList = new ArrayList<>();
        Dict all = new Dict();
        all.setName("全部");
        all.setCode("");
        categoryDictList.add(all);
        pojos.forEach(p -> {
            Dict d = new Dict();
            d.setName(p.getName());
            d.setCode(String.valueOf(p.getId()));
            categoryDictList.add(d);
        });
        model.addAttribute("categoryDictList", categoryDictList);
        return PREFIX + "select_article.html";
    }

    /**
     * 获取消息列表
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/articleList")
    @ResponseBody
    public Object list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "isCurrentOrg", required = false) Boolean isCurrentOrg) {
        Page page =defaultPage();
        Integer orgId = null;
        if (isCurrentOrg != null && isCurrentOrg) {
            orgId = getLoginUser().getDeptId();
        }
        List<CmsArticleEntity> pojos = cmsArticleMapper.articleSelectList(page, categoryId, keyword, DateUtil.getAllTime(), orgId);
        page.setRecords(pojos);
        return super.packForBT(page);
    }

}
