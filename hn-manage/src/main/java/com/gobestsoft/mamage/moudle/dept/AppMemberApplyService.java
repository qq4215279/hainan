package com.gobestsoft.mamage.moudle.dept;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gobestsoft.common.modular.dao.mapper.AppUserMapper;
import com.gobestsoft.common.modular.dao.model.AppUserPojo;
import com.gobestsoft.common.modular.dept.mapper.*;
import com.gobestsoft.common.modular.dept.model.*;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.mamage.config.member.MemberUtil;
import com.gobestsoft.mamage.moudle.app.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.DictGroupCodeConstants;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.mamage.model.LoginUser;

/**
 * 【会员申请审批】 服务类
 *
 * @author zhangdaowei
 * @date 2018-08-23
 */
@Service
@Slf4j
public class AppMemberApplyService {

    @Resource
    AppUserDao appUserDao;
    @Resource
    DeptMapper deptMapper;
    @Resource
    DeptMemberApplyMapper deptMemberApplyMapper;
    @Resource
    DeptMemberApplyLogMapper deptMemberApplyLogMapper;
    @Resource
    PersonInfoMapper personInfoMapper;
    @Resource
    DeptMemberMapper deptMemberMapper;
    @Resource
    DictMapper dictMapper;
    @Resource
    RedisUtils redisUtils;
    @Resource
    DeptOrganizationMapper deptOrganizationMapper;
    @Resource
    MessageService messageService;
    @Resource
    AppUserMapper appUserMapper;
    @Resource
    DeptMemberTransferMapper deptMemberTransferMapper;
    @Resource
    DeptMemberTransferLogMapper deptMemberTransferLogMapper;
    @Resource
    AppCheckLogMapper appCheckLogMapper;


    /**
     * 转会审核页多条件分页查询
     *
     * @param page
     * @param createTime 申请时间
     * @param status     审批状态
     *                   当前登录用户的deptid
     * @param firstCheck 转会类型时的是否是第一级审核；true:是 /即所属工会审核； false:否 /会员想转入的工会进行审核；
     * @return
     */
    public List<Map<String, Object>> selectByCondition(Page<Map<String, Object>> page, String status, String createTime,
                                                       Integer orgId, String name, boolean firstCheck) {
        //执行转会审核查询
        if (firstCheck) {
            //执行第一级审核；即会员所在工会审核查询
            return deptMemberApplyMapper.selectFirstCheckByCondition( page, status, createTime, orgId, name );
        }
        //执行第二级审核；即会员想转入的工会进行审核查询
        return deptMemberApplyMapper.selectTwoCheckByCondition( page, status, createTime, orgId, name );
    }

    /**
     * 查看详情 绑定入会、转会、办卡的数据至Model
     *
     * @param id
     * @param model
     */
    public void bindMemberApplyDataToModel(Integer id, Model model) {
        DeptMemberApply apply = deptMemberApplyMapper.selectById( id );
        PersonInfo personInfo = personInfoMapper.selectById( apply.getPersonId() );
        // 绑定查看详情用到的字典项
        String[] groupCodes = DictGroupCodeConstants.PERSON_INFO_GROUP_CODES;
        List<Dict> dicts = dictMapper.getDicListByGroupCodes( groupCodes );
        bindDictNameByDicts( dicts, personInfo, groupCodes );

        model.addAttribute( "apply", apply );
        model.addAttribute( "personInfo", personInfo );
    }

    /**
     * 查看流程 绑定审核日志的数据至Model
     *
     * @param id
     * @param model
     */
    public void bindMemberApplyLogDataToModel(Integer id, Model model) {
        List<Map<String, Object>> list = deptMemberApplyLogMapper.selectMapsByApplyId( id );
        list.get( 0 ).put( "fullname", "APP用户" );
        list.forEach( map -> {
            if (map.get( "check_time" ) != null) {
                map.put( "check_time", DateUtil.parseAndFormat( map.get( "check_time" ).toString(), "yyyyMMddHHmmss",
                        "yyyy-MM-dd HH:mm:ss" ) );
            } else {
                map.put( "check_time", "--" );
            }


        } );
        model.addAttribute( "list", list );
    }

    /**
     * 查看流程 转出工会审核日志的数据至Model
     *
     * @param id
     * @param model
     */
    public void deptMemberTransferLogDataToModel(Integer id, Model model) {
        List<Map<String, Object>> list = deptMemberTransferLogMapper.selectMapByApplyId( id );
        list.get( 0 ).put( "fullname", "APP用户" );
        list.forEach( map -> {
            map.put( "create_time", DateUtil.parseAndFormat( map.get( "create_time" ).toString(),
                    "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss" ) );
        } );
        model.addAttribute( "list", list );
    }

    /**
     * 转会工会审核操作（待转出工会审核）
     *
     * @param id
     * @param checkDesc
     * @param status
     * @param user
     */
    public void updateTransferLogStatusById(Integer id, String checkDesc, String status, LoginUser user) {

        String nowTime = DateUtil.getAllTime();
        DeptMemberTransfer transferLog = deptMemberTransferMapper.selectById( id );
        AppUserEntity entity = appUserDao.getAppUserByAuid( transferLog.getAuid() );
        String nickName = entity.getNickname();

        //获取组织信息
        DeptOrganization organization = deptOrganizationMapper.selectByDeptId( transferLog.getTurnOutDeptId() );

        DeptMemberTransferLog deptMemberTransferLog = new DeptMemberTransferLog();
        deptMemberTransferLog.setApplyId( id );
        deptMemberTransferLog.setApplyStatus( status );
        if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status )) {
            deptMemberTransferLog.setRecordFlowInfo( "APP用户:" + nickName + "转出工会审核通过" );
        } else if (DictCodeConstants.LIB_APPROVE_STATUS_FAIL == Integer.valueOf( status )) {
            deptMemberTransferLog.setRecordFlowInfo( "APP用户:" + nickName + "转出工会审核拒绝" );
        }
        deptMemberTransferLog.setContent( checkDesc );
        deptMemberTransferLog.setCreateDeptId( transferLog.getTurnOutDeptId() );
        deptMemberTransferLog.setNextCheckDeptId( transferLog.getTransferDeptId() );
        deptMemberTransferLog.setCreateUid( user.getId() );
        deptMemberTransferLog.setCreateTime( nowTime );
        deptMemberTransferLogMapper.insert( deptMemberTransferLog );

        //app受理进度
        AppCheckLog appCheckLog = new AppCheckLog();
        appCheckLog.setAuid( transferLog.getAuid() );
        appCheckLog.setType( "2" );
        appCheckLog.setStyleStatus( "1" );
        if (DictCodeConstants.LIB_APPROVE_STATUS_FAIL == Integer.valueOf( status )) {
            appCheckLog.setComment( "审核失败" );
            appCheckLog.setRecordFlowInfo( "经【" + organization.getUnionName() + "】审核，审核失败理由：" + checkDesc );
            appCheckLog.setStatus( "20" );
        } else if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status )) {
            DeptOrganization organizationInfo = deptOrganizationMapper.selectByDeptId( transferLog.getTransferDeptId() );
            appCheckLog.setComment( "审核通过" );
            appCheckLog.setRecordFlowInfo( "转出工会【" + organization.getUnionName() + "】审核通过，由转入工会【" + organizationInfo.getUnionName() + "】进行审核" );
            appCheckLog.setStatus( "30" );
        }
        appCheckLog.setCreateTime( nowTime );
        appCheckLogMapper.insert( appCheckLog );

        //更新会员转会申请表中的申请状态
        DeptMemberTransfer deptMemberTransfer = deptMemberTransferMapper.selectById( deptMemberTransferLog.getApplyId() );
        if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status )) {
            deptMemberTransfer.setOutStatus( DictCodeConstants.LIB_APPROVE_STATUS_PASS.toString() );//申请状态 1-通过
        } else if (DictCodeConstants.LIB_APPROVE_STATUS_FAIL == Integer.valueOf( status )) {
            deptMemberTransfer.setOutStatus( DictCodeConstants.LIB_APPROVE_STATUS_FAIL.toString() );//申请状态 0-拒绝
            deptMemberTransfer.setStatus( DictCodeConstants.LIB_APPROVE_STATUS_FAIL.toString() );//申请状态 0-拒绝
        }
        deptMemberTransferMapper.updateById( deptMemberTransfer );
    }

    /**
     * 转入工会审核操作（待转入工会审核）
     *
     * @param id
     * @param checkDesc
     * @param status
     * @param user
     */
    @Transactional
    public void updateInStatusById(Integer id, String checkDesc, String status, LoginUser user) {

        String nowTime = DateUtil.getAllTime();
        DeptMemberTransfer transfer = deptMemberTransferMapper.selectById( id );

        String auid = transfer.getAuid();
//        AppUserEntity entity = appUserDao.getAppUserByAuid(auid);
//        String nickName = entity.getNickname();

        //获取组织名称
        DeptOrganization organization = deptOrganizationMapper.selectByDeptId( transfer.getTransferDeptId() );

        DeptMemberTransferLog deptMemberTransferLog = new DeptMemberTransferLog();
        deptMemberTransferLog.setApplyId( id );
        deptMemberTransferLog.setApplyStatus( status );
        if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status ).intValue()) {
            deptMemberTransferLog.setRecordFlowInfo( "转入工会审核通过" );
        } else if (DictCodeConstants.LIB_APPROVE_STATUS_FAIL == Integer.valueOf( status ).intValue()) {
            deptMemberTransferLog.setRecordFlowInfo( "转入工会审核拒绝" );
        }
        deptMemberTransferLog.setContent( checkDesc );
        deptMemberTransferLog.setCreateDeptId( transfer.getTurnOutDeptId() );
        deptMemberTransferLog.setNextCheckDeptId( transfer.getTransferDeptId() );
        deptMemberTransferLog.setCreateUid( user.getId() );
        deptMemberTransferLog.setCreateTime( nowTime );
        deptMemberTransferLogMapper.insert( deptMemberTransferLog );

        //app受理进度
        AppCheckLog appCheckLog = new AppCheckLog();
        appCheckLog.setAuid(auid);
        appCheckLog.setType( "2" );
        appCheckLog.setStyleStatus( "1" );
        if (DictCodeConstants.LIB_APPROVE_STATUS_FAIL == Integer.valueOf( status ).intValue()) {
            appCheckLog.setComment( "审核失败" );
            appCheckLog.setRecordFlowInfo( "经【" + organization.getUnionName() + "】审核，审核失败理由：" + checkDesc );
            appCheckLog.setStatus( "20" );
        } else if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status ).intValue()) {
            appCheckLog.setComment( "审核通过" );
            appCheckLog.setRecordFlowInfo( "恭喜你加入【" + organization.getUnionName() + "】" );
            appCheckLog.setStatus( "10" );

            //转会成功提醒
            AppCheckLog appCheckLog2 = new AppCheckLog();
            appCheckLog2.setAuid(auid);
            appCheckLog2.setType( "1" );
            appCheckLog2.setStyleStatus( "1" );
            appCheckLog2.setComment( "转会成功" );
            appCheckLog2.setRecordFlowInfo( "已转入【" + organization.getUnionName() + "】" );
            appCheckLog2.setStatus( "10" );
            appCheckLog2.setCreateTime( nowTime );
            appCheckLogMapper.insert( appCheckLog2 );

        }
        appCheckLog.setCreateTime( nowTime );
        appCheckLogMapper.insert( appCheckLog );


        //审核通过
        if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status ).intValue()) {
            //更新dept_member_transfer表
            transfer.setStatus( "1" );
            deptMemberTransferMapper.updateById( transfer );

            //组织变更
            Integer transferDeptId = transfer.getTransferDeptId();
            DeptMember deptMember = deptMemberMapper.selectById( transfer.getMemberId() );
            deptMember.setDeptId( transferDeptId );
            deptMemberMapper.updateById( deptMember );//内置操作

            //app推送消息
            String[] auids = {auid};    //用到AppMessage表
            messageService.sendMessageByAuid( 1, "转会申请", "您好，您申请的转会已通过", 4, null, null, true, auids );
        }
        //审核拒绝 TODO

    }


    /**
     * 更新用户申请信息状态
     *
     * @param apply
     * @param status
     */
    private void updateStatusByDeptMemberApply(DeptMemberApply apply, String status) {
        apply.setApplyStatus( status );
        //apply.setUpdateUid(uid);
        //apply.setUpdateTime(DateUtil.getAllTime());
        deptMemberApplyMapper.updateById( apply );
    }

    /**
     * 插入审核通过、拒绝日志
     *
     * @param id
     * @param checkDesc
     * @param status
     * @param user
     */
    private void insertDeptMemberApplyLog(Integer id, String checkDesc, String status, LoginUser user,
                                          String checkOpinion, Integer nextCheckOrgId) {
        DeptMemberApplyLog log = new DeptMemberApplyLog();
        log.setApplyId( id );
        log.setApplyStatus( status );
//		log.setContent("审核意见：" + checkDesc);
//		log.setRecordFlowInfo("用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + checkOpinion);
//		log.setCreateOrgId(user.getDeptId());
//		log.setCreateUid(user.getId());
//		log.setCreateTime(DateUtil.getAllTime());
//		if(nextCheckOrgId != null){
//			log.setNextCheckOrgId(nextCheckOrgId);
//		}
        deptMemberApplyLogMapper.insert( log );
    }

    /**
     * 修改会员的所属组织id
     *
     * @param memberId
     * @param deptId
     * @param uid
     */
    private void updateDeptIdByMemberId(Integer memberId, Integer deptId, String uid) {
        DeptMember deptMember = deptMemberMapper.selectById( memberId );
        deptMember.setDeptId( deptId );
        deptMember.setUpdateUser( uid );
        deptMember.setUpdateTime( DateUtil.getAllTime() );
        deptMemberMapper.updateById( deptMember );
    }


    /**
     * 转会实行
     *
     * @param apply
     * @param user
     */
    public void transferMember(DeptMemberApply apply, DeptMemberApplyLog deptMemberApplyLog, LoginUser user) {
        // 修改当前APP用户的会员所属工会ID
        //updateDeptIdByMemberId(apply.getId(),deptMemberApplyLog.getNextCheckOrgId(),user.getId());
        // 当前app用户登录中状态则从新登录
    }

    /**
     * APP用户入会、转会、办卡申请绑定审核日志
     *
     * @param applyId
     * @param appAccount
     * @param appApplyOrgId
     * @param appApplyOrgName
     * @param applyType
     * @param auid
     * @param nextCheckOrgId
     */
    public DeptMemberApplyLog bindFirstApplyLogByDeptMemberApply(Integer applyId, String appAccount, Integer appApplyOrgId,
                                                                 String appApplyOrgName, Integer applyType, String auid, Integer nextCheckOrgId) {
        String applyTypeName = getApplyTypeNameByapplyType( applyType );
        DeptMemberApplyLog log = new DeptMemberApplyLog();
        log.setApplyId( applyId );
        log.setApplyStatus( DictCodeConstants.LIB_APPLY_EXE_TYPE_START );// 发起
//		log.setContent("操作描述：" + appAccount + "已经提交" + applyTypeName + "申请，等待" + appApplyOrgName + "审核");
//		log.setRecordFlowInfo("用户：" + appAccount + applyTypeName + "申请");
//		if(applyType == 2){
//			log.setCreateOrgId(appApplyOrgId);
//		}
//		log.setCreateUid(auid);
//		log.setCreateTime(DateUtil.getAllTime());
//		log.setNextCheckOrgId(nextCheckOrgId);
        return log;
    }

    /**
     * 根据申请类型，获取申请的名称
     *
     * @param applyType
     * @return
     */
    private String getApplyTypeNameByapplyType(Integer applyType) {
        if (applyType == 1) {
            return "入会";
        }
        if (applyType == 2) {
            return "转会";
        }
        if (applyType == 3) {
            return "办卡";
        }
        return "";

    }

    /**
     * 根据获取到的字典项集合绑定字典项名称至PersonInfo对象中
     *
     * @param dicts
     * @param personInfo
     */
    private void bindDictNameByDicts(List<Dict> dicts, PersonInfo personInfo, String[] groupCodes) {
        dicts.forEach( d -> {
            if (groupCodes[0].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getSex() )) {
                personInfo.setSex( d.getName() );
                return;
            }
            if (groupCodes[1].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getNation() )) {
                personInfo.setNation( d.getName() );
                return;
            }
            if (groupCodes[2].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getWorkSituation() )) {
                personInfo.setWorkSituation( d.getName() );
                return;
            }
            if (groupCodes[3].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getCertificateType() )) {
                personInfo.setCertificateType( d.getName() );
                return;
            }
            if (groupCodes[4].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getEducation() )) {
                personInfo.setEducation( d.getName() );
                return;
            }
            if (groupCodes[5].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getTechnologyLevel() )) {
                personInfo.setTechnologyLevel( d.getName() );
                return;
            }
            if (groupCodes[6].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getHousehold() )) {
                personInfo.setHousehold( d.getName() );
                return;
            }
            if (groupCodes[7].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getMemberChange() )) {
                personInfo.setMemberChange( d.getName() );
                return;
            }
            if (groupCodes[8].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getMemberChangeReason() )) {
                personInfo.setMemberChangeReason( d.getName() );
                return;
            }
            if (groupCodes[9].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getPoliticalStatus() )) {
                personInfo.setPoliticalStatus( d.getName() );
                return;
            }
            if (groupCodes[10].equals( d.getGroupCode() ) && StringUtils.isNotEmpty( d.getCode() ) && d.getCode().equals( personInfo.getIsFarmer() )) {
                personInfo.setIsFarmer( d.getName() );
                return;
            }
        } );
    }

    /**
     * 入会审核通过、不通过方法
     *
     * @param id
     * @param checkDesc
     * @param status
     * @param loginUser
     */
    @Transactional
    public void updateCompanyAdmissionApplyStatusById(Integer id, String checkDesc, String status, LoginUser loginUser) {
        String createTime = DateUtil.getAllTime();

        DeptMemberApply apply = deptMemberApplyMapper.selectById( id );
        updateStatusByDeptMemberApply( apply, status );// 更新申请信息审核状态

        //获取人员信息
        PersonInfo personInfo = personInfoMapper.selectById( apply.getPersonId() );
        //获取组织信息
        DeptOrganization organization = deptOrganizationMapper.selectByDeptId( apply.getDeptId() );

        AppUserEntity entity = appUserDao.getAppUserByAuid( apply.getAuid() );
        String nickName = entity.getNickname();

        //入会log表插入
        DeptMemberApplyLog deptMemberApplyLog = new DeptMemberApplyLog();
        deptMemberApplyLog.setApplyId( apply.getId() );
        deptMemberApplyLog.setCheckFlag( 1 );
        deptMemberApplyLog.setApplyStatus( status );
        deptMemberApplyLog.setCreateTime( DateUtil.getAllTime() );
        deptMemberApplyLog.setCheckDeptId( loginUser.getDeptId() );
        if (Integer.valueOf( status ) == 0) {
            deptMemberApplyLog.setRecordFlowInfo( "APP用户:" + nickName + "入会申请被拒绝" );//流程步骤
        } else {
            deptMemberApplyLog.setRecordFlowInfo( "APP用户:" + nickName + "入会申请成功" );//流程步骤
        }
        deptMemberApplyLog.setCheckReason( checkDesc );//审核理由
        deptMemberApplyLog.setCheckTime( createTime );
        deptMemberApplyLog.setCheckUid( loginUser.getId() );
        deptMemberApplyLogMapper.insert( deptMemberApplyLog );

        //app受理进度
        AppCheckLog appCheckLog = new AppCheckLog();
        appCheckLog.setAuid( apply.getAuid() );
        appCheckLog.setType( "1" );
        appCheckLog.setStyleStatus( "1" );
        if (DictCodeConstants.LIB_APPROVE_STATUS_FAIL == Integer.valueOf( status )) {
            appCheckLog.setComment( "审核失败" );
            appCheckLog.setRecordFlowInfo( "经【" + organization.getUnionName() + "】审核，审核失败理由：" + checkDesc );
            appCheckLog.setStatus( "20" );
        } else if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status )) {
            appCheckLog.setComment( "审核通过" );
            appCheckLog.setRecordFlowInfo( "恭喜你加入【" + organization.getUnionName() + "】" );
            appCheckLog.setStatus( "10" );
        }
        appCheckLog.setCreateTime( createTime );
        appCheckLogMapper.insert( appCheckLog );

        //审核通过
        if (DictCodeConstants.LIB_APPROVE_STATUS_PASS == Integer.valueOf( status )) {
            // App用户申请入会审核通过操作
            DeptMember deptMember = new DeptMember();
            deptMember.setDeptId( apply.getDeptId() );
            deptMember.setPersonId( apply.getPersonId() );
            deptMember.setStationCard( MemberUtil.getMemberCardNo( personInfo.getSex(), organization.getUnionName(), organization.getUnitDistrict() ) );
            deptMember.setCreateUser( loginUser.getId() );
            deptMember.setCreateTime( createTime );
            deptMember.setUpdateUser( loginUser.getId() );
            deptMember.setUpdateTime( createTime );
            deptMember.setType( "1" );
            deptMemberMapper.insert( deptMember );

            //更新appUser
            AppUserEntity appUser = appUserDao.getAppUserByAuid( apply.getAuid() );
            appUser.setMember_id( deptMember.getId() );
            appUserDao.updateByAuid( appUser );

            //app推送消息
            String[] auids = {apply.getAuid()};
            messageService.sendMessageByAuid( 1, "入会申请", "您好，您申请的入会已通过", 4, null, null, true, auids );
            return;
        }
    }
}
