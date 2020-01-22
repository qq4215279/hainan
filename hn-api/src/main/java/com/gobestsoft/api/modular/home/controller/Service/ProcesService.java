package com.gobestsoft.api.modular.home.controller.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.api.modular.appuser.service.AppUserService;
import com.gobestsoft.api.modular.basic.RestBasic;
import com.gobestsoft.api.modular.home.controller.model.PersonInfoParam;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.dept.mapper.*;
import com.gobestsoft.common.modular.dept.model.*;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanmu on 2018/10/9.
 */
@Service
@Slf4j
public class ProcesService extends RestBasic {

    @Resource
    private PersonInfoMapper personInfoMapper;

    @Resource
    private DeptMemberApplyMapper deptMemberApplyMapper;

    @Resource
    private DeptMemberApplyLogMapper deptMemberApplyLogMapper;

    @Resource
    private DeptMemberTransferMapper deptMemberTransferMapper;

    @Resource
    private DeptMemberTransferLogMapper deptMemberTransferLogMapper;

    @Resource
    private AppUserDao appUserDao;

    @Resource
    private DeptDao deptDao;

    @Resource
    private AppCheckLogMapper appCheckLogMapper;

    @Resource
    private DeptOrganizationMapper deptOrganizationMapper;

    @Resource
    private DeptInviteMapper deptInviteMapper;

    /**
     * 提交入会申请
     *
     * @Param param
     */
    @Transactional
    public void submitMembership(PersonInfoParam param, String auid, String code){
        //当前时间获取
        String nowtime = DateUtil.getAllTime();
        AppUserEntity entity = appUserDao.getAppUserByAuid(auid);
        String nickName = entity.getNickname();

        //个人信息表添加
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(param.getName());
        personInfo.setSex(param.getSex());
        personInfo.setBirthday(param.getBirthday());
        personInfo.setNation(param.getNation());
        personInfo.setWorkSituation(param.getWorkSituation());
        personInfo.setCertificateType("01");
        personInfo.setCertificateNum(param.getCertificateNum());
        personInfo.setEducation(param.getEducation());
        personInfo.setTechnologyLevel(param.getTechnologyLevel());
        personInfo.setMobile(param.getMobile());
        personInfo.setHousehold(param.getHousehold());
        personInfo.setDomicile(param.getDomicile());
        personInfo.setPoliticalStatus(param.getPoliticalStatus());
        personInfo.setWorkUnit(param.getWorkUnit());
        personInfo.setIsFarmer(param.getIsFarmer());

        personInfoMapper.insert(personInfo);


        DeptOrganization deptOrganization = new DeptOrganization();
        deptOrganization.setUnitName(param.getWorkUnit());
        DeptOrganization deptInfo = deptOrganizationMapper.selectOne(deptOrganization);

        //入会申请流程
        if(ToolUtil.equals("201", code) || (ToolUtil.equals("202", code) && ToolUtil.isNotEmpty(deptInfo))){
            String deptName = deptInfo.getUnionName();
            //入会申请表
            DeptMemberApply deptMemberApply = new DeptMemberApply();
            deptMemberApply.setAuid(auid);
            deptMemberApply.setPersonId(personInfo.getId());
            deptMemberApply.setDeptId(param.getDeptId());//组织id
            deptMemberApply.setUnitName(param.getWorkUnit());
            deptMemberApply.setApplyStatus(DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString());
            deptMemberApply.setCreateTime(nowtime);
            deptMemberApplyMapper.insert(deptMemberApply);

            //审批流程日志
            DeptMemberApplyLog deptMemberApplyLog=new DeptMemberApplyLog();
            deptMemberApplyLog.setApplyId(deptMemberApply.getId());
            deptMemberApplyLog.setApplyStatus(DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString());
            deptMemberApplyLog.setCheckFlag(0);
            deptMemberApplyLog.setRecordFlowInfo("APP用户:" + nickName + "提交入会申请");//流程步骤
            deptMemberApplyLog.setCheckTime(nowtime);
            deptMemberApplyLog.setCheckDeptId(deptInfo.getDeptId());
            deptMemberApplyLog.setCreateTime(nowtime);
            deptMemberApplyLogMapper.insert(deptMemberApplyLog);

            appCheckLogMapper.delCheckLog(auid, "1");
            //app流程查看
            AppCheckLog appCheckLog = new AppCheckLog();
            appCheckLog.setAuid(auid);
            appCheckLog.setStatus("30");
            appCheckLog.setStyleStatus("1");
            appCheckLog.setType("1");
            appCheckLog.setComment("入会申请已提交，等待审核");
            appCheckLog.setRecordFlowInfo("申请已提交至【" + deptName + "】");
            appCheckLog.setCreateTime(nowtime);
            appCheckLogMapper.insert(appCheckLog);

        } else if(ToolUtil.equals("202", code)){
            appCheckLogMapper.delCheckLog(auid, "1");

            //app流程查看
            AppCheckLog appCheckLog = new AppCheckLog();
            appCheckLog.setAuid(auid);
            appCheckLog.setStatus("30");
            appCheckLog.setStyleStatus("3");
            appCheckLog.setType("1");
            appCheckLog.setComment("入会申请已提交");
            appCheckLog.setRecordFlowInfo("您所在的单位【" + param.getWorkUnit() + "】未建立工会，" +
                    "根据《中国工会章程》规定会员二十五人以上，应当成立工会基层委员会。您已成为【临时会员】，" +
                    "邀请公司同事参与建会，人数达到二十五人就可以建立工会，工会成立后会将您转为正式会员享受更多会员福利。");
            appCheckLog.setCreateTime(nowtime);
            appCheckLogMapper.insert(appCheckLog);

//            Integer nowCnt = deptInviteMapper.selectCount(new EntityWrapper<AppInviteDept>().eq("unit_name", param.getWorkUnit()));
//            if(nowCnt == 0){
                //入会邀请
                AppInviteDept appInviteDept = new AppInviteDept();
                appInviteDept.setAuid(auid);
                appInviteDept.setName(param.getName());
                appInviteDept.setUnitName(param.getWorkUnit());
                appInviteDept.setCreateTime(nowtime);
                appInviteDept.setIsDept(0);
                deptInviteMapper.insert(appInviteDept);
//            }

//            if(nowCnt >0){
//                //入会邀请
//                AppInviteDept appInviteDept = new AppInviteDept();
//                appInviteDept.setAuid(auid);
//                appInviteDept.setName(param.getName());
//                appInviteDept.setUnitName(param.getWorkUnit());
//                appInviteDept.setCreateTime(nowtime);
//                appInviteDept.setIsDept(0);
//                deptInviteMapper.insert(appInviteDept);
//
//                //app流程查看
//                AppCheckLog appCheckLog1 = new AppCheckLog();
//                appCheckLog1.setAuid(auid);
//                appCheckLog1.setStatus("30");
//                appCheckLog1.setStyleStatus("2");
//                appCheckLog1.setType("1");
//                appCheckLog1.setComment("邀请成功");
//                appCheckLog1.setInviteNum(nowCnt + 1);
//                appCheckLog.setCreateTime(nowtime);
//                appCheckLogMapper.insert(appCheckLog1);
//
//                if(nowCnt > 25){
//                    //app流程查看
//                    AppCheckLog appCheckLog2 = new AppCheckLog();
//                    appCheckLog2.setAuid(auid);
//                    appCheckLog2.setStatus("30");
//                    appCheckLog2.setStyleStatus("1");
//                    appCheckLog2.setType("1");
//                    appCheckLog2.setComment("已达到建会要求");
//                    appCheckLog.setRecordFlowInfo("建会信息已提交至【省总工会督察组】");
//                    appCheckLog.setCreateTime(nowtime);
//                    appCheckLogMapper.insert(appCheckLog2);
//                }
//            }
        }

    }

    /**
     * 提交转会
     */
    @Transactional
    public void submitMembertranfer(DeptMemberTransfer deptMemberTransfer){
        //获取当前时间
        String nowTime = DateUtil.getAllTime();
        AppUserEntity entity = appUserDao.getAppUserByAuid(deptMemberTransfer.getAuid());
        String nickName = entity.getNickname();

        deptMemberTransfer.setCreateTime(nowTime);
        deptMemberTransfer.setStatus(DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString());
        deptMemberTransfer.setOutStatus(DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString());
        deptMemberTransferMapper.insert(deptMemberTransfer);

        //转会log表数据作成
        DeptMemberTransferLog deptMemberTransferLog = new DeptMemberTransferLog();
        deptMemberTransferLog.setApplyId(deptMemberTransfer.getId());
        deptMemberTransferLog.setApplyStatus(DictCodeConstants.LIB_APPROVE_STATUS_WAIT.toString());
        deptMemberTransferLog.setRecordFlowInfo("APP用户:" + nickName + "提交转会申请");
        deptMemberTransferLog.setContent("请求审核");
        deptMemberTransferLog.setCreateDeptId(deptMemberTransfer.getTransferDeptId());
        Integer deptPid = deptDao.selectByOrgId(deptMemberTransfer.getTransferDeptId());
        deptMemberTransferLog.setNextCheckDeptId(deptPid);
        deptMemberTransferLog.setCreateUid(deptMemberTransfer.getAuid());
        deptMemberTransferLog.setCreateTime(nowTime);
        deptMemberTransferLogMapper.insert(deptMemberTransferLog);

        //获取组织名称
        DeptOrganization deptOrganization = new DeptOrganization();
        deptOrganization.setDeptId(deptMemberTransfer.getTurnOutDeptId());
        DeptOrganization deptInfo = deptOrganizationMapper.selectOne(deptOrganization);
        String deptName = deptInfo.getUnionName();
        appCheckLogMapper.delCheckLog(deptMemberTransfer.getAuid(), "2");

        //app流程查看
        AppCheckLog appCheckLog = new AppCheckLog();
        appCheckLog.setAuid(deptMemberTransfer.getAuid());
        appCheckLog.setStatus("30");
        appCheckLog.setStyleStatus("1");
        appCheckLog.setType("2");
        appCheckLog.setComment("转会申请已提交，等待审核");
        appCheckLog.setRecordFlowInfo("申请已提交至【" + deptName + "】");
        appCheckLog.setCreateTime(nowTime);
        appCheckLogMapper.insert(appCheckLog);

    }

    /**
     * 获取受理进度
     */
    @Transactional
    public List<AppCheckLog> getSelectByInfo(String type, String auid){
        List<AppCheckLog> listInfo = appCheckLogMapper.selectList(new EntityWrapper<AppCheckLog>()
                .eq("type", type)
                .eq("auid", auid)
                .eq("del_flg", 0)
                .orderBy("create_time", false));
        return listInfo;
    }
}
