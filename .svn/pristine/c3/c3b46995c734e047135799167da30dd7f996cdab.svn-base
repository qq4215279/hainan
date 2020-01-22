package com.gobestsoft.api.modular.home.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.api.modular.appuser.model.AppUserDto;
import com.gobestsoft.api.modular.appuser.model.AppUserMember;
import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.home.controller.Service.ProcesService;
import com.gobestsoft.api.modular.home.controller.model.PersonInfoParam;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.dept.mapper.*;
import com.gobestsoft.common.modular.dept.model.*;
import com.gobestsoft.common.modular.mst.dao.MstMemberDao;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.rabbitmq.RabbitSender;
import com.gobestsoft.rabbitmq.processor.MessageProcessor;
import com.sun.java.accessibility.util.TopLevelWindowListener;
import org.apache.catalina.startup.Tool;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duanmu on 2018/10/9.
 */
@RestController
@RequestMapping(value = "/proces")
public class ProcesController extends BaseController {

    /**
     * 组织最大显示数
     */
    private static final int DEPT_COUNT = 20;

    @Autowired
    private DeptMemberApplyMapper deptMemberApplyMapper;

    @Autowired
    private ProcesService procesService;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private DeptMemberTransferMapper deptMemberTransferMapper;

    @Autowired
    private DeptOrganizationMapper deptOrganizationMapper;

    @Autowired
    private DeptMemberMapper deptMemberMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private MstMemberDao mstMemberDao;

    @Autowired
    private AppCheckLogMapper appCheckLogMapper;

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private DeptInviteMapper deptInviteMapper;



    /**
     * 提交入会申请
     *
     * @Param name 姓名
     * @Param sex 性别
     * @Param birthday 生日
     * @Param nation 名族
     * @Param workSituation 就业情况
     * @Param certificateType 证件类型
     * @Param certificateNum 证件号
     * @Param education 学历
     * @Param technologyLevel 技能等级
     * @Param mobile 手机号
     * @Param household 户籍类型
     * @Param domicile 户籍所在地
     * @Param politicalStatus 政治面貌
     * @Param workUnit 工作单位
     * @Param nativePlace 籍贯
     * @Param deptId 组织id
     * @Param unitName 单位名称
     */
    @RequestMapping(value = "/memberApply", method = RequestMethod.POST)
    public BaseResult memberApply(@Valid PersonInfoParam param, BindingResult result, @RequestParam("code") String code){

        if (result.hasErrors()) {
            return fail(result.getFieldError().getDefaultMessage());
        }
        String certificateNum = param.getCertificateNum();
        String name = param.getName();
        if(certificateNum!=null){
            if(certificateNum.contains("410882199007158")&&name.contains("黄梦林")){
                return fail("版本过旧，请更新最新版本");
            }
        }
        // 用户身份验证
        if (ToolUtil.isEmpty(this.getUserId())) {
            return fail("用户信息不存在。");
        }

        //已存在的申请
        int applyCount = deptMemberApplyMapper.selectCount(new EntityWrapper<DeptMemberApply>()
                .eq("auid", this.getUserId())
                .eq("apply_status", DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString()));
        if(applyCount > 0){
            return fail("您的申请正在审批中，请勿重复提交。");
        }

        //根据身份证和姓名判断是否已经是会员
        int memCount = personInfoMapper.selectByNameAndNum(param.getName(), param.getCertificateNum());
        if(memCount > 0){
            return fail("您申请的姓名已是会员，无需再申请。");
        }

        String auid = getUserId();
        try {
            procesService.submitMembership(param, auid, code);
        } catch (DuplicateKeyException e){
            return fail("身份证号重复, 请重新输入。");
        }

        return success();
    }

    /**
     * 提交转会申请
     *
     * @param
     */
    @RequestMapping(value = "/transferApply", method = RequestMethod.POST)
    public BaseResult transferApply(@RequestParam("toDeptId") Integer toDeptId,
                                    @RequestParam(value = "transferDesc", required = false) String transferDesc){
        String auid = getUserId();
        // 用户身份验证
        if (ToolUtil.isEmpty(this.getUserId())) {
            return fail("用户信息不存在。");
        }

        if(!isBindMember()){
            return fail("您还不是会员，请先绑定会员信息！");
        }
        AppUserDto appUserDto = getUserDto();
        AppUserMember appUserMember = appUserDto.getMember_info();
        Integer fromDeptId = appUserMember.getDept_id();
        if(ToolUtil.equals(fromDeptId, toDeptId)){
            return fail("您已经是该工会组织会员了。");
        }

        //已存在的申请
        int transferCount = deptMemberTransferMapper.selectCount(new EntityWrapper<DeptMemberTransfer>()
                .eq("auid", this.getUserId())
                .eq("status", DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString()));
        if(transferCount > 0){
            return fail("您的申请正在审批中，请勿重复提交。");
        }

        DeptMemberTransfer deptMemberTransfer = new DeptMemberTransfer();
        deptMemberTransfer.setMemberId(getUserDto().getMember_info().getMember_id());
        deptMemberTransfer.setAuid(auid);
        deptMemberTransfer.setTurnOutDeptId(fromDeptId);
        deptMemberTransfer.setTransferDeptId(toDeptId);
        deptMemberTransfer.setReason(transferDesc);

        procesService.submitMembertranfer(deptMemberTransfer);
        return success();
    }

    /**
     * 获取组织工会信息
     */
    @RequestMapping("/getDept")
    public BaseResult getDeptName(@RequestParam(value = "keyWord", required = false) String keyWord){

        List<Map<String, Object>> deptList = new ArrayList<>();
        RowBounds rowBounds = new RowBounds(0, DEPT_COUNT);
        Wrapper<DeptOrganization> wrapper = new EntityWrapper();
        if(ToolUtil.isNotEmpty(keyWord)){
            wrapper.like("union_name", keyWord);
        }

        List<DeptOrganization> deptOrg = deptOrganizationMapper.selectPage(rowBounds, wrapper);
        for(DeptOrganization deptOrganization : deptOrg){
            Map<String, Object> map = new HashMap<>();
            map.put("deptId", deptOrganization.getDeptId());
            map.put("unionName", deptOrganization.getUnionName());
            map.put("unitName", deptOrganization.getUnitName());
            deptList.add(map);
        }

        return success(deptList);
    }

    /**
     * 入会，转会申请状态查询
     * @param applyType
     */
    @RequestMapping("/getApplyStatus")
    public BaseResult getApplyStatus(@RequestParam("applyType") String applyType){

        if (ToolUtil.isEmpty(applyType)) {
            return fail(BaseResult.ResultCode.ERROR500);
        }
        Map<String, Object> map = new HashMap<>();
        AppUserDto appUserDto = getUserDto();
        String auid = appUserDto.getAuid();
        AppUserDto appUserDto1 = appUserService.setTokenInfoByAuId(auid);

        //临时,不给转会
        if("2".equals(applyType)){
            map.put("message", "正在建设中");
            return success(map);
        }

        //入会

        if("1".equals(applyType) || "0".equals(applyType)){

            if(appUserDto1.getVip_level() == 2){
                map.put("status", "1");
            } else {
                List<DeptMemberApply> deptMemberApply = deptMemberApplyMapper.selectList(new EntityWrapper<DeptMemberApply>().eq("auid", auid).orderBy("create_time", false));
                List<AppInviteDept> appInviteDepts=deptInviteMapper.selectList(new EntityWrapper<AppInviteDept>().eq("auid",auid).orderBy("create_time",false));
                if(ToolUtil.isNotEmpty(appInviteDepts)){
                    map.put("message", "您的入会申请正在审核中");
                    map.put("status", "-1");
                    return  success(map);
                }
                if(ToolUtil.isEmpty(deptMemberApply) && ToolUtil.isEmpty(appInviteDepts)){
                    map.put("status", "0");
                    return success(map);
                }
                DeptMemberApply dma = deptMemberApply.get(0);
                if("-1".equals(dma.getApplyStatus())){
                    map.put("message", "您的入会申请正在审核中");
                    map.put("status", "-1");
                } else if("0".equals(dma.getApplyStatus())){
                    map.put("message", "您的入会申请已被拒绝");
                    map.put("status", "2");
                } else if("1".equals(dma.getApplyStatus())){
                    map.put("status", "1");
                }
            }
        }

        //转会
        if("2".equals(applyType)) {
            List<DeptMemberTransfer> deptMemberTransfer = deptMemberTransferMapper.selectList(new EntityWrapper<DeptMemberTransfer>().eq("auid", auid).orderBy("create_time", false));

            if(appUserDto1.getVip_level() == 2){
                if(deptMemberTransfer.size() == 0){
                    map.put("status", "0");
                    return success(map);
                }

                if(deptMemberTransfer != null && deptMemberTransfer.size() > 0){
                    DeptMemberTransfer info = deptMemberTransfer.get(0);
                    if ("-1".equals(info.getStatus())) {
                        map.put("message", "您的转会申请正在审核中");
                        map.put("status", "-1");
                        return success(map);
                    } else if ("0".equals(info.getStatus())) {
                        map.put("message", "您的转会申请已被拒绝");
                        map.put("status", "2");
                        return success(map);
                    } else if ("1".equals(info.getStatus())) {
                        map.put("status", "0");
                        return success(map);
                    }
                }
            }

            List<DeptMemberApply> deptMemberApply = deptMemberApplyMapper.selectList(new EntityWrapper<DeptMemberApply>().eq("auid", auid).orderBy("create_time", false));
            List<AppInviteDept> appInviteDepts=deptInviteMapper.selectList(new EntityWrapper<AppInviteDept>().eq("auid",auid).orderBy("create_time",false));
            if (ToolUtil.isEmpty(deptMemberApply) && ToolUtil.isEmpty(appInviteDepts)) {
                map.put("message", "您还不是工会会员，请先入会再转会");
                return success(map);
            }
            if(ToolUtil.isNotEmpty(appInviteDepts)){
                map.put("message", "您的入会申请正在审核中,请点击入会查看审核流程");
            }else if(ToolUtil.isNotEmpty(deptMemberApply)) {
                DeptMemberApply deptMemberApply1 = deptMemberApply.get(0);
                if (ToolUtil.isEmpty(deptMemberTransfer) && "1".equals(deptMemberApply1.getApplyStatus())) {
                    map.put("status", "0");
                } else if (ToolUtil.isEmpty(deptMemberTransfer) && ("-1".equals(deptMemberApply1.getApplyStatus()))) {
                    map.put("message", "您的入会申请正在审核中,请点击入会查看审核流程");
                } else if (ToolUtil.isEmpty(deptMemberTransfer) && "0".equals(deptMemberApply1.getApplyStatus())) {
                    map.put("message", "您还不是工会会员，请先入会再转会");
                } else {
                    DeptMemberTransfer dmtf = deptMemberTransfer.get(0);
                    if ("-1".equals(dmtf.getStatus())) {
                        map.put("message", "您的转会申请正在审核中");
                        map.put("status", "-1");
                    } else if ("0".equals(dmtf.getStatus())) {
                        map.put("message", "您的转会申请已被拒绝");
                        map.put("status", "2");
                    } else if ("1".equals(dmtf.getStatus())) {
                        map.put("status", "0");
                    }
                }
            }
        }
        return success(map);
    }

    /**
     * 验证会员接口
     */
    @RequestMapping("/getMemVipInfo")
    @Transactional
    public BaseResult getMemVipInfo(@RequestParam("name") String name,
                                    @RequestParam("certificateNum") String certificateNum,
                                    @RequestParam(value = "unionName", required = false) String unionName){

        String nowTime = DateUtil.getAllTime();
        Map<String, Object> map = new HashMap<>();
        DeptOrganization deptOrganization = new DeptOrganization();

        //临时,不给入会
        if (true) {
            return new BaseResult(203, "入会功能升级中暂停开放，敬请期待~", null);
        }

        Map<String, Object> resultMapInfo = checkUserMessage(name, certificateNum, getUserId());
        if (ToolUtil.isNotEmpty(resultMapInfo.get("status")) && !ToolUtil.equals("4", resultMapInfo.get("status").toString())) {
            return resultBaseResultByStatus(Integer.parseInt(resultMapInfo.get("status").toString()));
        }

        //判断工会是否存在
        Boolean deptFlg = true;
        List<Dept> depts = new ArrayList<>();
        if(ToolUtil.isEmpty(unionName)){
            deptFlg = false;
        } else {
            depts = deptMapper.selectList(new EntityWrapper<Dept>().eq("fullname", unionName));
            if(depts.size() == 0){
                deptFlg = false;
            }
        }


        List<DeptMember> deptMember = mstMemberDao.selectByIdcardNumber(certificateNum, name);
        if(deptMember.size() == 0){
            if(!deptFlg){
                return new BaseResult(202, null, null);
            } else {
                Dept dept = depts.get(0);
                Integer id = dept.getId();
                deptOrganization.setDeptId(id);
                DeptOrganization deptOrganization1  = deptOrganizationMapper.selectOne(deptOrganization);
                String unName = deptOrganization1.getUnionName();
                String unitName = deptOrganization1.getUnitName();
                int deptId = deptOrganization1.getDeptId();
                map.put("unionName", unName);
                map.put("unitName",unitName);
                map.put("deptId", deptId);
                return new BaseResult(201, null, map);
            }
        } else {
            AppUserDto result = appUserService.certifiedMember(resultMapInfo.get("appUser"), resultMapInfo.get("mstMember"), getToken());
            map.put("result", result);

            appCheckLogMapper.delCheckLog(getUserId(), "1");
            //流程审核表
            AppUserMember appUserMember = result.getMember_info();
            String deptName = appUserMember.getDept_name();
            AppCheckLog appCheckLog = new AppCheckLog();
            appCheckLog.setAuid(getUserId());
            appCheckLog.setStatus("10");
            appCheckLog.setStyleStatus("1");
            appCheckLog.setType("1");
            appCheckLog.setComment("认证成功");
            appCheckLog.setRecordFlowInfo("恭喜你加入【" + deptName + "】");
            appCheckLog.setCreateTime(nowTime);
            appCheckLogMapper.insert(appCheckLog);

            //给用户推送消息
            MessageProcessor messageProcessor = new MessageProcessor();
            messageProcessor.setContent("您好，您的账号会员认证已成功");
            messageProcessor.setAuids(new String[]{getUserId()});
            messageProcessor.setMode(0);
            messageProcessor.setPush(true);
            messageProcessor.setCategory(1);
            messageProcessor.setTargetId(null);
            messageProcessor.setTitle("会员认证成功");
            rabbitSender.apiGiveAdminMessage(messageProcessor);

            //更新用户信息
            appUserService.setTokenInfoByAuId(getUserId());
        }


        return success(map);

    }

    /**
     * 认证会员,错误返回提醒
     *
     * @param resultInt
     * @return
     */
    private BaseResult resultBaseResultByStatus(int resultInt) {
        if (resultInt == 0) {
            return new BaseResult(203, "账号已失效，请重新登录", null);
        }
        if (resultInt == 1) {
            return new BaseResult(203, "当前账号已经是会员", null);
        }

        if (resultInt == 2) {
            return new BaseResult(203, "您输入的信息有误", null);
        }

        if (resultInt == 3) {
            return new BaseResult(203, "输入信息已经被人绑定", null);
        }

        if(resultInt == 5) {
            return new BaseResult(203, "您入会申请的身份证已重复,请确认是否输入正确", null);
        }
        return fail();
    }

    /**
     * 根据用户姓名、身份证、auid参数验证会员是否已经认证会员
     *
     * @param name
     * @param cardNo
     * @param auid
     * @return
     */
    public Map<String, Object> checkUserMessage(String name, String cardNo, String auid) {
        Map<String, Object> map = new HashMap<String, Object>();
        AppUserEntity appUser = appUserDao.getAppUserByAuid(auid);
        if (ToolUtil.isEmpty(appUser)) {
            map.put("status", 0);//当前用户不存在
            return map;
        }

        if (ToolUtil.isNotEmpty(appUser.getMember_id())) {
            map.put("status", 1);//已经认证会员
            return map;
        }

        List<DeptMember> mstNum = mstMemberDao.selectByNumber(cardNo);
        List<DeptMember> mstMember = mstMemberDao.selectByIdcardNumber(cardNo, name);

        if(ToolUtil.isNotEmpty(mstMember)){
            long appUserCount = appUserDao.selectAuidByMemberId(mstMember.get(0).getId());
            if (appUserCount > 0) {
                map.put("status", 3);//姓名、身份证号已经被认证
                return map;
            }
        }

        List<PersonInfo> personInfos = personInfoMapper.selectByCertificateNum(cardNo);
        if(ToolUtil.isEmpty(mstNum)){
            if(personInfos!=null && personInfos.size() > 0 ){
                map.put("status", 5);//身份证号重复
                return map;
            }
        }

        if(ToolUtil.isEmpty(mstNum) || ToolUtil.isNotEmpty(mstMember)){
            map.put("status", 4);
            if(mstMember.size() > 0){
                map.put("mstMember", mstMember.get(0));
            }
            map.put("appUser", appUser);
            return map;
        } else if(ToolUtil.isNotEmpty(mstNum) && ToolUtil.isEmpty(mstMember)){
            map.put("status", 2);//信息输入错误
            return map;
        }

        return map;
    }

    /**
     * 受理进度查看
     * @param type 1: 入会  2：转会
     */
    @RequestMapping("/acceptInfo")
    public BaseResult getAcceptInfo(@RequestParam("type") String type){

        List<Map<String, Object>> listInfo = new ArrayList<>();
        List<AppCheckLog> appCheckLogs = procesService.getSelectByInfo(type, getUserId());
        if(ToolUtil.isEmpty(appCheckLogs)){
            AppUserDto appUserDto = getUserDto();
            String deptName = appUserDto.getMember_info().getDept_name();
            Map<String, Object> map = new HashMap<>();
            map.put("createTime", "");
            map.put("comment", "认证成功");
            map.put("recordFlowInfo", "恭喜你加入【" + deptName + "】工会");
            map.put("colorStatus", "10");
            map.put("styleStatus", "1");
            listInfo.add(map);
        } else{

            for(AppCheckLog appCheckLog: appCheckLogs){
               List<AppInviteDept> appInviteDept=deptInviteMapper.selectList(new EntityWrapper<AppInviteDept>().eq("auid",appCheckLogs.get(0).getAuid()).orderBy("create_time",true));
               Map<String, Object> map = new HashMap<>();
                map.put("createTime", DateUtil.parseAndFormat(appCheckLog.getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
                map.put("comment", appCheckLog.getComment());
                map.put("recordFlowInfo", appCheckLog.getRecordFlowInfo());
                map.put("colorStatus", appCheckLog.getStatus());
                map.put("styleStatus", appCheckLog.getStyleStatus());
                map.put("nowNum", appCheckLog.getInviteNum());
                map.put("maxNum", 25);
                if(appCheckLog.getStyleStatus().equals("3")){
                    map.put("name",appInviteDept.get(0).getName());
                    map.put("workUnit",appInviteDept.get(0).getUnitName());
                }
                map.put("qrCode","http://ghypt.hnszgh.org:9081/hn-api/show-page/download");
                listInfo.add(map);

                if(appCheckLog.getStyleStatus().equals("3")){//状态样式:邀请入会
                    List<AppInviteDept> appInviteDepts = deptInviteMapper.selectList(new EntityWrapper<AppInviteDept>().eq("unit_name", appInviteDept.get(0).getUnitName()).orderBy("create_time",false));
                   //邀请入会人数未达到25人,只提示邀请成功
                    if(appInviteDepts.size()>1){
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("createTime",DateUtil.parseAndFormat(appInviteDepts.get(0).getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
                        map2.put("comment", "邀请成功");
                        map2.put("colorStatus", 30);
                        map2.put("styleStatus", 2);
                        map2.put("nowNum", appInviteDepts.size());
                        map2.put("maxNum", 25);
                        listInfo.add(0,map2);

                     //邀请入会人数达到25人,提示建会
                    if(appInviteDepts.size()>=25){
                        Map<String, Object> map3 = new HashMap<>();
                        map3.put("createTime", DateUtil.parseAndFormat( appInviteDepts.get(0).getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
                        map3.put("comment", "已达到建会要求");
                        map3.put("recordFlowInfo","建会信息已提交至【省总工会督察组】");
                        map3.put("colorStatus", "30");
                        map3.put("styleStatus", 1);
                        listInfo.add(0,map3);
                        }
                    }
                }
            }
        }

        return success(listInfo);
    }

    /**
     * 获取入会邀请单位
     */
    @RequestMapping("/getUnit")
    public BaseResult getUnit(@RequestParam(value = "keyWord", required = false) String keyWord){

        List<Map<String, Object>> deptList = new ArrayList<>();
        RowBounds rowBounds = new RowBounds(0, DEPT_COUNT);
        Wrapper<AppInviteDept> wrapper = new EntityWrapper();
        if(ToolUtil.isNotEmpty(keyWord)){
            wrapper.like("unit_name", keyWord);
        }

        List<AppInviteDept> list = deptInviteMapper.getUnitName(keyWord, rowBounds);
        for(AppInviteDept appInviteDept : list){
            Map<String, Object> map = new HashMap<>();
            map.put("unitName", appInviteDept.getUnitName());
            deptList.add(map);
        }

        return success(deptList);
    }


}