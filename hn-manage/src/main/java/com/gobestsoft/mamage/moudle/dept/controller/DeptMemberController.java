package com.gobestsoft.mamage.moudle.dept.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.dept.dao.MemberDao;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberTransferLogMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberTransferMapper;
import com.gobestsoft.common.modular.dept.model.*;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.common.modular.system.dao.BlackboardDao;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ExcelExportUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.dept.service.DeptMemberService;
import com.gobestsoft.mamage.moudle.dept.service.PersonInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Controller
@RequestMapping("/dept/member")
public class DeptMemberController extends BaseController {

    private String PREFIX = "/dept/member/";
    private final static String xls = ".xls";
    private final static String xlsx = ".xlsx";

    @Resource
    DeptMemberService memberService;
    @Resource
    PersonInfoService personInfoService;
    @Resource
    DeptMemberTransferMapper deptMemberTransferMapper;
    @Resource
    DeptMemberTransferLogMapper deptMemberTransferLogMapper;
    @Autowired
    MemberDao memberDao;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    DeptMemberMapper deptMemberMapper;
    @Autowired
    BlackboardDao blackboardDao;
    @Autowired
    AppUserDao appUserDao;

    /**
     * 跳转到首页
     *
     * @param model
     * @param deptId 所属工会组织id（sys_dept表id）
     * @return
     */
    @RequestMapping("")
    public String index(Model model, @RequestParam(required = false) Integer deptId) {
        if (deptId == null) {
            model.addAttribute("deptId", getLoginUser().getDeptId());
        } else {
            model.addAttribute("deptId", deptId);
        }
        return PREFIX + "member";
    }

    /**
     * 多条件分页查询
     *
     * @param name      会员姓名
     * @param unionName 所属工会组织名称
     * @param deptId    所属工会组织id（sys_dept表id）
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(
            HttpServletRequest request,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String deptNo,
            @RequestParam(required = false) String memberCard,
            @RequestParam(required = false) String unionName,
            @RequestParam(required = false) Integer deptId,
            @RequestParam(required = false) String memberRange) {
        Page<Map<String,Object>> page = defaultPage();

        // unionId为空，表示是直接点击左侧导航栏中的会员菜单进入的该方法
        List<Map<String,Object>> list = new ArrayList<>();
        if (ToolUtil.isEmpty(deptId)) {
            deptId = getLoginUser().getDeptId();
        }

        if(ToolUtil.isEmpty(memberRange)){
            memberRange = "01";
        }

        ObjectMap map = ObjectMap.getInstance();
        map.putRequest("query_sex");
        map.putRequest("query_ethnicGroup");
        map.putRequest("query_workSituation");
        map.putRequest("query_certificateType");
        map.putRequest("query_certificateNum");
        map.putRequest("query_education");
        map.putRequest("query_technologyLevel");
        map.putRequest("query_mobile");
        map.putRequest("query_household");
        map.putRequest("query_domicile");
        map.putRequest("query_memberChange");
        if(ToolUtil.isNotEmpty(request.getParameter("query_memberChangeDate_start"))){
            map.put("query_memberChangeDate_start", DateUtil.parseAndFormat(request.getParameter("query_memberChangeDate_start"), "yyyy-MM-dd", "yyyyMMdd"));
        } else {
            map.putRequest("query_memberChangeDate_start");
        }

        if(ToolUtil.isNotEmpty(request.getParameter("query_memberChangeDate_end"))){
            map.put("query_memberChangeDate_end", DateUtil.parseAndFormat(request.getParameter("query_memberChangeDate_end"), "yyyy-MM-dd", "yyyyMMdd"));
        } else {
            map.putRequest("query_memberChangeDate_end");
        }

        map.putRequest("query_politicalStatus");
        map.putRequest("query_workUnit");
        map.putRequest("query_nativePlace");
        map.putRequest("query_farmer_flag");
        map.putRequest("query_certified_member");

        list = memberService.selectByCondition(page, name, deptNo, memberCard, unionName, deptId, memberRange, map);

        for (Map<String, Object> m : list) {

            Object changeDate = m.get("memberChangeDate");
            if (changeDate != null && !StringUtils.isEmpty(changeDate.toString())) {
                m.put("memberChangeDate", DateUtil.parseAndFormat(m.get("memberChangeDate").toString(), "yyyyMMdd", "yyyy/MM/dd"));
            }

        }
        page.setRecords(list);

        return super.packForBT(page);
    }

    /**
     * 跳转到新增页面
     *
     * @param model
     * @param deptId 所属工会组织id（sys_dept表id）
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model, @RequestParam("deptId") Integer deptId) {
        if (ToolUtil.isEmpty(deptId)) {
            deptId = getLoginUser().getDeptId();
        }
        model.addAttribute("deptId", deptId);
        return PREFIX + "member_add";
    }

    /**
     * 保存方法
     *
     * @param model
     * @param result
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Tip save(@Valid PersonInfo model, @RequestParam("deptId") Integer deptId, BindingResult result) {
        if (result.hasErrors()) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        if (memberService.hasMemberIn(model.getCertificateNum())) {
            return fail("会员身份证已重复");
        }

        memberService.insertMember(model, deptId, getLoginUser());
        return success();
    }

    /**
     * 跳转到编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        DeptMember member = memberService.selectById(id);
        model.addAttribute("deptMember", member);
        if (member != null) {
            PersonInfo personInfo = personInfoService.selectById(member.getPersonId());
            model.addAttribute("personInfo", personInfo);
        }
        return PREFIX + "member_edit";
    }

    /**
     * 更新方法
     *
     * @param result
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Tip update(@Valid PersonInfo person, @RequestParam("memberId") Integer memberId, BindingResult result) {
        if (result.hasErrors() || ToolUtil.isEmpty(person.getId())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        memberService.updateMemberById(person, memberId, getLoginUser().getId());
        return success();
    }

    /**
     * 删除方法
     *
     * @param id
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody
    public Tip remove(@RequestParam("id") Integer id) {

        if (ToolUtil.isEmpty(id) || id == 0) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        String message = this.deleteMemberById(id, 0);
        if(ToolUtil.isNotEmpty(message)){
            return fail(message);
        }
        //更新各组织会员人数
        new Thread(new Runnable() {
            @Override
            public void run() {
                //更新各组织会员人数
                memberDao.updateMemberCountTable1();
                memberDao.updateBindMemberCountTable1();
                memberDao.updateMemberCountTable2();
            }
        }).start();
        return success();
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deletes")
    @ResponseBody
    public Tip deletes(@RequestParam("ids") String ids) {
        if (ToolUtil.isEmpty(ids)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        String[] strIds = ids.split(",");
        for (int i=0; i<strIds.length; i++) {
            Integer id = Integer.valueOf(strIds[i]);

            if (ToolUtil.isEmpty(id) || id == 0) {
                throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
            }

            String message = this.deleteMemberById(id, i);
            if(ToolUtil.isNotEmpty(message)){
                return fail(message);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //更新各组织会员人数
                memberDao.updateMemberCountTable1();
                memberDao.updateBindMemberCountTable1();
                memberDao.updateMemberCountTable2();
            }
        }).start();
        return success();
    }

    private String deleteMemberById(Integer id, int index){
        StringBuffer message = new StringBuffer();
        DeptMember deptMember = memberService.selectById(id);
        if (ToolUtil.isEmpty(deptMember)) {
            message.append("第 "+(index+1)+" 条记录未查询到！！！");
            return message.toString();
        }/*else if(ToolUtil.isNotEmpty(deptMember.getIsBind()) && deptMember.getIsBind()==1){
            message.append("第 "+(index+1)+" 条记录为已认证会员，不能删除！！！");
            return message.toString();
        }*/else{
            memberService.removeMemberAndPerson(id, deptMember.getPersonId());
            return null;
        }
    }

    /**
     * 跳转到详情页面
     *
     * @param id
     * @param model
     * @param pageType 0:默认。1：入会审批。2：转出审批。3：转入审批
     * @return
     */
    @RequestMapping("/detail")
    public String detail(@RequestParam("id") Integer id,
                         @RequestParam(value = "pageType", required = false,defaultValue = "0") int pageType,
                         @RequestParam(value = "targetId", required = false) String targetId,
                         @RequestParam(value = "createTime", required = false) String createTime,
                         Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        DeptMember member = memberService.selectById(id);
        if (member != null) {
            PersonInfo personInfo = personInfoService.selectById(member.getPersonId());
            model.addAttribute("deptMember", personInfo);
        }
        if(StringUtils.isNotEmpty(createTime)){
            EntityWrapper<DeptMemberTransfer> wrapper=new EntityWrapper<>();
            wrapper.eq("member_id",id).eq("create_time",createTime);

            List<DeptMemberTransfer> deptMemberTransfer=deptMemberTransferMapper.selectList(wrapper);
            if(deptMemberTransfer!=null && deptMemberTransfer.size()!=0){
                model.addAttribute("deptMemberTransfer",deptMemberTransfer.get(0));
            }
        }

        model.addAttribute("member", member);
        model.addAttribute("pageType", pageType);
        model.addAttribute("targetId", targetId);

        return PREFIX + "member_detail";
    }

    /**
     * 跳转到批量导入会员信息页面
     */
    @RequestMapping(value = "member_upload")
    public String memberUpload(@RequestParam(value = "deptId", required = false) Integer deptId, Model model) {
        if (deptId == null) {
            model.addAttribute("deptId", getLoginUser().getDeptId());
        } else {
            model.addAttribute("deptId", deptId);

        }
        return PREFIX + "member_upload";
    }

    /**
     * 批量导入会员
     *
     * @throws IOException
     * @throws Exception
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public Tip upload(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam(value = "deptId", required = false) Integer deptId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 判断文件是否为空
        if (ToolUtil.isEmpty(uploadFile)) {
            return tip(500, "文件为空！", null);
        }
        // 获取上传文件名,包括路径
        String name = uploadFile.getOriginalFilename();
        if (!name.toLowerCase().endsWith(xls) && !name.toLowerCase().endsWith(xlsx)) {
            return tip(500, "上传文件不是excel文件！", null);
        }
        long size = uploadFile.getSize();
        if (ToolUtil.isEmpty(name) || size == 0) {
            return tip(500, "文件为空！", null);
        }

        InputStream is = uploadFile.getInputStream();
        String fileUploadPath = manageProperties.getFileUploadPath();
        Integer dID = deptId == null ? getLoginUser().getDeptId() : deptId;
        String createUid = getLoginUser().getId();

        new Thread(new Runnable() {
            @Override
            public void run() {
                memberService.importAndExport(name, fileUploadPath, is, createUid, dID);
            }
        }).start();

        return tip(200, "导入成功", null);

    }

    @RequestMapping("/exportList")
    public ModelAndView exportList(
            HttpServletRequest request,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String deptNo,
            @RequestParam(required = false) String memberCard,
            @RequestParam(required = false) String unionName,
            @RequestParam(required = false) Integer deptId,
            @RequestParam(required = false) String memberRange,
            HttpServletResponse response) {
        ObjectMap map = ObjectMap.getInstance();
        map.putRequest("query_sex");
        map.putRequest("query_ethnicGroup");
        map.putRequest("query_workSituation");
        map.putRequest("query_certificateType");
        map.putRequest("query_certificateNum");
        map.putRequest("query_education");
        map.putRequest("query_technologyLevel");
        map.putRequest("query_mobile");
        map.putRequest("query_household");
        map.putRequest("query_domicile");
        map.putRequest("query_memberChange");
        map.putRequest("query_memberChangeDate_start");
        map.putRequest("query_memberChangeDate_end");
        map.putRequest("query_politicalStatus");
        map.putRequest("query_workUnit");
        map.putRequest("query_nativePlace");
        map.putRequest("query_farmer_flag");
        // unionId为空，表示是直接点击左侧导航栏中的会员菜单进入的该方法
        List<DeptMemberInfoEntity> list = null;
        if (ToolUtil.isEmpty(deptId)) {
            deptId = getLoginUser().getDeptId();
            //查询当前登录用户所属组织下及子组织、子部门下的会员
        }
//        if(getLoginUser().getDeptId()==1&& "02".equals( memberRange)){
//
//            return new ModelAndView("/500").addObject("errmsg", "省总工会不可直接导出下级工会会员数据");
//        }
        list = memberService.getMemberList(name, deptNo, memberCard, unionName, deptId, memberRange, map);
        if (list == null) {
            return new ModelAndView("/500").addObject("errmsg", "没有查询到记录，无法导出");
        }
        if (list.size() > 1048575) {
            return new ModelAndView("/500").addObject("errmsg", "导出条数不能超过1048575");
        }

        try {
            for (DeptMemberInfoEntity m : list) {
                if (m.getBirthday() != null) {
                   try {
                       m.setBirthday(DateUtil.parseAndFormat(m.getBirthday(), "yyyyMMdd", "yyyy/MM/dd"));
                   }catch (Exception e){

                   }
                }
                if (m.getMember_change_date() != null) {
                    m.setMember_change_date(DateUtil.parseAndFormat(m.getMember_change_date(), "yyyyMMdd", "yyyy/MM/dd"));
                }
                if (m.getIs_farmer() != null) {
                    m.setIs_farmer("1".equals(m.getIs_farmer()) ? "是" : "否");
                }

            }
            String[] excelHeader = {"name:姓名", "sex:性别", "birthday:出生日期", "nation:民族", "work_situation:就业状况"
                    , "certificate_type:有效证件类别", "certificate_num:证件号码", "education:学历", "technology_level:技术等级"
                    , "mobile:移动电话", "household:户籍类型", "domicile:户籍所在地", "member_change:会籍变化类型"
                    , "member_change_date:会籍变化日期", "member_change_reason:会籍变化原因", "political_status:政治面貌"
                    , "work_unit:工作单位", "native_place:籍贯", "homeplace:家庭住址", "is_farmer:是否是农民工会员"};
            ExcelExportUtil exportUtil = new ExcelExportUtil(excelHeader, "表格" + DateFormatUtils.format(new Date(), "yyyy_MM_dd"));
            exportUtil.setFileName(getLoginUser().getDeptName() + "会员信息表");
            XSSFWorkbook export = exportUtil.export(request,response, list, ":");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("/500");
        }

        return null;
    }

    /**
     *  check会员是否正在审核中
     */
    @RequestMapping("/checkMemOut")
    @ResponseBody
    public Tip checkMemOut(@RequestParam("memId") Integer memId){

        //判断是否为空
        if (ToolUtil.isEmpty(memId)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        List<DeptMemberTransfer> listInfo = deptMemberTransferMapper.selectList(new EntityWrapper<DeptMemberTransfer>()
                .eq("member_id", memId)
                .orderBy("create_time", false));

        int checkFlg = 0;
        if(listInfo.size() > 0 && ToolUtil.equals("-1", listInfo.get(0).getStatus())){
            checkFlg = 1;
        }

        return new Tip(200, "该会员正在进行转会审核中，请勿重复进行转会操作！", checkFlg);


    }


    /**
     * 会员转会操作
     */
    @RequestMapping("/memreout")
    public String memReturnOut(@RequestParam("id") Integer id, Model model){

        //判断是否为空
        if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        DeptMember member = memberService.selectById(id);

        if (member != null) {
            Dept dept = deptMapper.selectById(member.getDeptId());
            String deptname = dept.getSimplename();
            model.addAttribute("deptname", deptname);

            PersonInfo personInfo = personInfoService.selectById(member.getPersonId());
            model.addAttribute("personInfo", personInfo);
        }

        model.addAttribute("member", member);

        return PREFIX + "member_returnout";
    }


    /**
     * 后台会员转会提交
     */
    @RequestMapping("/memoutsave")
    @ResponseBody
    public Tip memoutSave(@RequestParam("deptId") Integer oldDept,
                          @RequestParam("memId") Integer memId,
                          @RequestParam("reOutId") Integer newDept){

        //判断是否为空
        if (ToolUtil.isEmpty(oldDept) || ToolUtil.isEmpty(memId) || ToolUtil.isEmpty(newDept)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //更新数据
//        String auid = appUserDao.selectAuid(String.valueOf(memId));
//        if(ToolUtil.isEmpty(auid)){
//            throw new BusinessException(BizExceptionEnum.NO_THIS_USER);
//        }
        String datatime = DateUtil.getAllTime();
        DeptMemberTransfer deptMemberTransfer = new DeptMemberTransfer();
        deptMemberTransfer.setMemberId(memId);
        deptMemberTransfer.setTurnOutDeptId(oldDept);
        deptMemberTransfer.setTransferDeptId(newDept);
        deptMemberTransfer.setCreateTime(datatime);
        deptMemberTransfer.setStatus("-1");
        deptMemberTransfer.setReturnFlg(1);
//        deptMemberTransfer.setAuid(auid);
        deptMemberTransferMapper.insert(deptMemberTransfer);

        //转会log表数据作成
        DeptMemberTransferLog deptMemberTransferLog = new DeptMemberTransferLog();
        deptMemberTransferLog.setApplyId(deptMemberTransfer.getId());
        deptMemberTransferLog.setApplyStatus(DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString());
        deptMemberTransferLog.setRecordFlowInfo("提交转会申请");
        deptMemberTransferLog.setContent("请求审核");
        deptMemberTransferLog.setCreateDeptId(oldDept);
        deptMemberTransferLog.setNextCheckDeptId(newDept);
        deptMemberTransferLog.setCreateUid(getLoginUser().getId());
        deptMemberTransferLog.setCreateTime(datatime);
        deptMemberTransferLogMapper.insert(deptMemberTransferLog);

        return new Tip(200, "转会申请提交成功，请耐心等待！", null);

    }

    /**
     * 后台会员转会审核页面
     * @return
     */
    @RequestMapping(value = "/memRehomeCheck")
    public String getRehomeActivity(){
        return PREFIX + "memrehome_check";
    }

    /**
     * 后台会员转会审核页面list
     */
    @RequestMapping("/rehomeCheckList")
    @ResponseBody
    public Object getCheckList(@RequestParam("name") String name, @RequestParam("status") String status){
        Page page = defaultPage();

        LoginUser loginUser = getLoginUser();
        Integer deptId = loginUser.getDeptId();
        Map<String, Object> map = new HashMap<>();
        map.put("quert_deptId", deptId);
        map.put("quert_name", name);
        map.put("quert_status", status);

        List<Map> list = new ArrayList<>();
        list = deptMemberMapper.selectByMemrehomeCheck(page, map);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 后台会员审核通过/不通过
     */
    @RequestMapping("/memoutcheck")
    @ResponseBody
    public Tip memoutApprovel(@RequestParam("id") Integer id,
                               @RequestParam("checkTxt") String checkTxt,
                               @RequestParam("status") String status) {

        if (ToolUtil.isEmpty(id) || ToolUtil.isEmpty(status)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        memberService.updateMemDeptById(id, checkTxt, status);

        String message = "转会成功！";
        if(ToolUtil.equals("0", status)){
            message = "转会申请被拒绝！";
        }
        return new Tip(200, message,null);
    }

    /**
     * 跳转到 实名制数据
     * @param model
     * @return
     */
    @RequestMapping("realData")
    public String realData(Model model) {
        model.addAttribute("list", this.getRealData());
        return PREFIX + "real_data";
    }

    private List<Map<String, Object>> getRealData() {
        return blackboardDao.getCacheRealData();
    }

    /**
     * 下载实名制数据
     */
    @RequestMapping("downloadRealData")
    public ModelAndView downloadRealData(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] excelHeader = {"org_name:单位名称", "org_num:组织数", "mem_num:会员数", "far_num:农民工数", "ver_num:认证会员数", "per_num:认证会员占比"};
            ExcelExportUtil exportUtil = new ExcelExportUtil(excelHeader, DateFormatUtils.format(new Date(), "yyyy年MM月dd")+"各市县产业系统工会实名制数据");
            XSSFWorkbook export = exportUtil.export(request,response, this.getRealData(), ":");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("/500");
        }
        return null;
    }

}
