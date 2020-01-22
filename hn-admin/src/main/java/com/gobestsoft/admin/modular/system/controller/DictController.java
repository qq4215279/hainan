package com.gobestsoft.admin.modular.system.controller;

import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.system.parameter.DictParameter;
import com.gobestsoft.mamage.moudle.system.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 字典控制器
 *
 * @author gobestsoft
 * @Date 2017年4月26日 12:55:31
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private String PREFIX = "/system/dict/";


    @Resource
    DictService dictService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dict_add";
    }

    /**
     * 跳转到修改字典
     */
    @RequestMapping("/dict_edit/{groupCode}")
    public String deptUpdate(@PathVariable(value = "groupCode") String groupCode, Model model) {
        DictModel dictModel = dictService.getDictionary(groupCode);
        model.addAttribute("dictionary", dictModel);
        model.addAttribute("groupCode", groupCode);
        return PREFIX + "dict_edit";
    }

    /**
     * 新增字典
     */
    @RequestMapping(value = "/renew", method = RequestMethod.POST)
    @ResponseBody
    public Tip add(@RequestBody DictParameter parameter) {
        this.dictService.addDict(parameter);
        redisUtils.remove(CacheConstant.getAllDict()+parameter.getGroupCode());
        return success();
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "condition", required = false) String condition) {
        return dictService.getAllDict(condition);
    }

    /**
     * 删除字典记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String groupCode) {
        this.dictService.deleteDict(groupCode);
        return success();
    }

}
