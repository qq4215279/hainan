package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.DeptMemApplyMapper;
import com.gobestsoft.common.modular.dao.model.DeptMemberApplyPojo;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.system.service.DeptMemApplySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 会员申请
 */
@Controller
@RequestMapping("/deptMemApply")
public class DeptMemApplyController extends BaseController {

    private static final String PREX= "dept/member/deptMemApply/";

    @Autowired
    DeptMemApplyMapper deptMemApplyMapper;

    @Autowired
    DeptMemApplySerivce deptMemApplySerivce;

    @RequestMapping("")
    public String apply(){

        return PREX+"deptMemApply_list";

    }

    //查询会员申请中没有创建公会的
    @RequestMapping("/list")
    @ResponseBody
    public Object list(){

        Page page = defaultPage();

        ObjectMap queryParam = ObjectMap.getInstance();

        HttpServletRequest request = getHttpServletRequest();
        queryParam.putRequest( "query_name");
        List<Map<String, Object>> list = deptMemApplyMapper.selectList(page, queryParam);

        page.setRecords(list);

        return super.packForBT(page);

    }


    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam(required = true) String id){

        DeptMemberApplyPojo bean = deptMemApplyMapper.selectById(id);

        model.addAttribute("bean",bean);

        return PREX+"deptMemApply_edit";

    }

    @RequestMapping("/updateMemberDept")
    @ResponseBody
    public Tip updateMemberDept(@RequestParam(required = true) String deptId,@RequestParam(required = true) String id){

        try {
            deptMemApplySerivce.updateMemberDept(deptId,id);
        }catch (Exception e){
            e.printStackTrace();
            return fail("更新失败");
        }

        return success();
    }




}
