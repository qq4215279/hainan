package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.system.dao.LogDao;
import com.gobestsoft.mamage.basic.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志管理
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Resource
    LogDao logDao;


    /**
     * 列表页面 -- login/error/operation
     */
    @RequestMapping("{page}")
    public String page(@PathVariable String page) {
        return PREFIX + page;
    }


    /**
     * 获取异常日志列表
     */
    @RequestMapping(value = "list/{table}")
    @ResponseBody
    public Object list(@PathVariable String table,@RequestParam(required = false) String name, @RequestParam(required = false) String status) {
        Page<Map<String, Object>> page = defaultPage();
        List<Map<String,Object>> list = logDao.selectByCondition(page, name, status, "sys_log_"+table);
        page.setRecords(list);
        return super.packForBT(page);
    }

}
