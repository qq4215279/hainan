package com.gobestsoft.admin.modular.cms.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.cms.service.ColumnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 栏目管理
 */
@Controller
@RequestMapping(value = "cms/column")
public class ColumnController extends BaseController{

    private String PREFIX = "/cms/column/";

    @Resource
    private ColumnService columnService;

    /**
     * 跳转页面 -- index/add/edit
     */
    @RequestMapping("{page}")
    public String index(@PathVariable String page,Integer id, Model model){
        if(ToolUtil.isNotEmpty(id)){
            CmsCategoryPojo pojo = columnService.getDetailById(id);
            model.addAttribute("pojo", pojo);
        }
        return PREFIX + page;
    }


    /**
     * 查询列表list
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false, value = "name") String name,
                       @RequestParam(required = false, value = "begDate") String begDate,
                       @RequestParam(required = false, value = "endDate") String endDate){
        Page<CmsCategoryPojo> page = defaultPage();
        begDate = DateUtil.parseAndFormat(begDate, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        endDate = DateUtil.parseAndFormat(endDate, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
        List<CmsCategoryPojo> result = columnService.selectByCondition(page, name, begDate, endDate);
        page.setRecords(result);
        return super.packForBT(page);
    }


    /**
     * 保存
     */
    @RequestMapping("/doSave")
    @ResponseBody
    public Tip doSave(@Valid CmsCategoryPojo pojo, BindingResult result){
        if(result.hasErrors()){
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        String uid = getLoginUser().getId();
        try {
            columnService.save(pojo, uid);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }



    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Tip del(@RequestParam("id") String id){
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        columnService.delete(id);
        return success();
    }

}
