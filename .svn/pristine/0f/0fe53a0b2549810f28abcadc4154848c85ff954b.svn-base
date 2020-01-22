package com.gobestsoft.mamage.moudle.dept.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.ExcelExportUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.dept.service.DeptOrganizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dept/organization")
public class DeptOrganizationController extends BaseController {

    private String PREFIX = "/dept/organization/";
    private final static String xls = ".xls";
    private final static String xlsx = ".xlsx";

    @Resource
    DeptOrganizationService organizationService;

    @Resource
    UserMapper userMapper;


    /**
     * 跳转到工会信息管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        /**
         * 绑定用户角色权限至Model对象中
         * 判断当前用户是否具有添加、编辑、删除工会的操作
         */
        model.addAttribute("loginOrgId", getLoginUser().getDeptId());
        model.addAttribute("addRelationByUser", "");
        if (organizationService.getRoleRelationByRoleAndUid(getLoginUser().getId(), 0)) {
            model.addAttribute("addRelationByUser", "true");
        }
        return PREFIX + "organization";
    }

    /**
     * 获取工会信息管理列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String unionName, @RequestParam(required = false) String deptNo, @RequestParam(required = false) Integer deptId) {
        Page<Map<String, Object>> page = defaultPage();
        List<Map<String, Object>> result = this.organizationService.selectByCondition(page, unionName, deptNo, deptId);
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 组织详情页
     *
     * @param id       组织信息ID
     * @param pageType 页面作用：1：申请建会。2:完善信息。
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Integer id,
                       @RequestParam(value = "pageType", required = false) Integer pageType, Model model) {
        DeptOrganization deptOrganization = new DeptOrganization();
        if (id != null) {
            deptOrganization = organizationService.selectById(id);
            if (deptOrganization == null) {
                return REDIRECT + "/wrong";
            }
        }
        model.addAttribute("deptOrganization", deptOrganization);
        model.addAttribute("pageType", pageType);
        model.addAttribute("oldUnionName", deptOrganization.getUnionName());
        return PREFIX + "organization_edit";
    }

    /**
     * 跳转到编辑页面
     *
     * @param id       工会组织信息表ID
     * @param pageType 页面类型：1：审核。2:完善审核
     * @param model
     * @return
     */
    @RequestMapping("/watch")
    public String watch(@RequestParam("id") Integer id,
                        @RequestParam(value = "pageType", required = false) Integer pageType, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        DeptOrganization deptOrganization = organizationService.selectById(id);
        model.addAttribute("deptOrganization", deptOrganization);
        model.addAttribute("pageType", pageType);
        return PREFIX + "organization_watch";
    }


    /**
     * 更新方法
     *
     * @param deptOrganization
     * @param result
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Tip update(@Valid DeptOrganization deptOrganization, BindingResult result,
                      @RequestParam(value = "oldUnionName", required = false) String oldUnionName) {

        if (result.hasErrors() || ToolUtil.isEmpty(deptOrganization.getId())) {
            return tip(500, result.getFieldError().getDefaultMessage(), null);
        }
        deptOrganization.setUpdateUser(getLoginUser().getId());
        organizationService.updateAllColumnById(deptOrganization);

        if(ToolUtil.isNotEmpty(oldUnionName) && !ToolUtil.equals(oldUnionName, deptOrganization.getUnionName())){
            User user = new User();
            user.setAccount(oldUnionName);
            User user1 = userMapper.selectOne(user);
            User userInfo = userMapper.selectById(user1.getId());
            userInfo.setAccount(deptOrganization.getUnionName());
            userInfo.setName(deptOrganization.getUnionName());
            userMapper.updateById(userInfo);
        }
        return success();
    }

    /**
     * 跳转到批量导入工会组织信息页面
     */
    @RequestMapping(value = "organization_upload")
    public String organizationUpload() {
        return PREFIX + "organization_upload";
    }

    /**
     * 根据deptId,获取工会组织信息
     *
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/getDeptOrganizationByDeptId")
    @ResponseBody
    public Object getDeptOrganizationByDeptId(@RequestParam("deptId") Integer deptId) {
        return organizationService.getDeptOrganizationByDeptId(deptId);
    }

    /**
     * 根据当前选中节点的id、部门类型，跳转至对应的工会、部门数据录入页面
     *
     * @param id       选中节点id（sys_dept表id）
     * @param deptType 部门类型 0：组织  1：部门
     * @param model
     * @return
     */
    @RequestMapping("/addOrganizationOrDept")
    public String addOrganizationAndDept(Integer id, Integer deptType, Model model) {
        String url = "organization_add";//工会信息简单添加页面
        if (deptType == 1) {
            url = "dept_add";//部门信息简单添加页面
        }
        //绑定部门信息至Model对象中
        organizationService.bindDeptDataToModel(id, deptType, model, true);
        return PREFIX + "/tree/" + url;
    }

    /**
     * 根据当前选中节点的id、部门类型，跳转至对应的工会、部门编辑页面
     *
     * @param id       选中节点id（sys_dept表id）
     * @param deptType 部门类型 0：组织  1：部门
     * @param model
     * @return
     */
    @RequestMapping("/editOrganizationOrDept")
    public String editOrganizationOrDept(Integer id, Integer deptType, Model model) {
        String url = "organization_edit";//工会信息简单添加页面
        if (deptType == 1) {
            url = "dept_edit";//部门信息简单添加页面
        }
        //绑定部门信息至Model对象中
        organizationService.bindDeptDataToModel(id, deptType, model, false);
        return PREFIX + "/tree/" + url;
    }


    /**
     * 删除其节点、子节点、关联的工会信息等
     *
     * @param id （sys_dept表id）
     * @return
     */
    @RequestMapping("/removeOrganizationAndDept")
    @ResponseBody
    public Tip removeOrganizationAndDept(@RequestParam("deptId") Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        organizationService.removeOrganizationAndDept(id);
        return success();
    }

    /**
     * 批量导入工会组织
     *
     * @throws IOException
     * @throws Exception
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public Tip upload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 判断文件是否为空
        if (uploadFile == null)
            return tip(500, "文件为空！", null);
        // 获取上传文件名,包括路径
        String name = uploadFile.getOriginalFilename();
        if (!name.toLowerCase().endsWith(xls) && !name.toLowerCase().endsWith(xlsx)) {
            return tip(500, "上传文件不是excel文件！", null);
        }
        long size = uploadFile.getSize();
        if (ToolUtil.isEmpty(name) && size == 0) {
            return tip(500, "文件为空！", null);
        }
        InputStream is = uploadFile.getInputStream();
        String createUid = getLoginUser().getId();
        String fileUpload = manageProperties.getFileUploadPath();
        new Thread(new Runnable() {
            @Override
            public void run() {
                organizationService.importAndExport(name, is, createUid, fileUpload);
            }
        }).start();
        return tip(200, "导入成功", null);
    }

    @RequestMapping("/exportList")
    @ResponseBody
    public ModelAndView exportList(HttpServletResponse response,@RequestParam(required = false) String unionName, @RequestParam(required = false) String deptNo, @RequestParam(required = false) Integer deptId) {

        List<Map<String, Object>> list = organizationService.getAllOrganizationList(unionName, deptNo, deptId);

        String excelHeader[] ={
                "unit_name:单位名称","unit_org_code:单位法人和其他组织统一社会信用代码","unit_address:单位地址","unit_district_name:单位所在政区","unit_nature_name:单位性质类别",
                "unit_economic_type_name:经济类型","unit_industry_name:单位所属类型","unit_number:职工人数","union_name:工会名称","others_org_code:工会法人和其他组织统一社会信用代码",
                "p_name:上级工会名称","createunion_time:建会日期","union_type_name:工会类型","membership:会员人数","union_leader:工会负责人","union_leader_phone:电话"


        };

        ExcelExportUtil exportUtil = new ExcelExportUtil(excelHeader,"工会组织机构信息表");

        try {
            exportUtil.export(getHttpServletRequest(),response,list,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("/500").addObject("errmsg",e.getMessage());
        }

        return null;
    }

}
