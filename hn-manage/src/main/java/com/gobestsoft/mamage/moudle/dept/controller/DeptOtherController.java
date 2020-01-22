package com.gobestsoft.mamage.moudle.dept.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.core.node.ZTreeNode;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.moudle.dept.service.DeptOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * create by gutongwei
 * on 2018/9/4 上午9:48
 */
@Controller
@RequestMapping("/dept")
public class DeptOtherController extends BaseController {


    @Autowired
    private DeptOtherService deptOtherService;

    @Autowired
    private DeptDao deptDao;

    @RequestMapping("/importLogs")
    @ResponseBody
    public Object importLogs(int type) {
        Page page = defaultPage();
        List<Map<String, String>> result = deptOtherService.importLog(getLoginUser().getId(), type, page);
        page.setRecords(result);
        return packForBT(page);
    }

    /**
     * 获取省总下级部门的tree列表
     */
    @RequestMapping(value = "/treebylevelTwo")
    @ResponseBody
    public List<ZTreeNode> treebylevelTwo() {
        List<ZTreeNode> tree = this.deptDao.treebylevelTwo();
        return tree;
    }

}
