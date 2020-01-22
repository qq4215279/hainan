package com.gobestsoft.mamage.moudle.legal;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.RoleConstants;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalApplyMapper;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalApproveLogMapper;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalMapper;
import com.gobestsoft.common.modular.legal.model.DeptLegal;
import com.gobestsoft.common.modular.legal.model.DeptLegalApply;
import com.gobestsoft.common.modular.legal.model.DeptLegalApproveLog;
import com.gobestsoft.common.modular.system.mapper.RelationMapper;
import com.gobestsoft.common.modular.system.model.Relation;
import com.gobestsoft.core.util.BeanUtil;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.dept.service.DeptOrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 法人资格首次登记
 */
@Service
@Transactional
public class LegalApplyService {
    @Resource
    DeptLegalApplyMapper deptLegalApplyMapper;
    @Resource
    DeptLegalApproveLogMapper deptLegalApproveLogMapper;
    @Resource
    LegalApplyService legalApplyService;
    @Resource
    DeptOrganizationService deptOrganizationService;
    @Resource
    DeptLegalMapper deptLegalMappper;
    @Resource
    RelationMapper relationMapper;

    /**
     * 多条件查询
     *
     * @param page
     * @param name
     * @param agentName
     * @param uid
     * @return
     */
    public List<Map<String, Object>> selectApplyPageByCondition(Page<Map<String, Object>> page, String name, String deptName, String agentName, String uid) {
        return deptLegalApplyMapper.selectApplyPageByCondition(page, name, null, agentName, uid, DeptLegalApplyMapper.A_TYPE[0]);
    }

    /**
     * 根据deptid获取工会基本信息
     *
     * @param deptId
     * @return
     */
    public DeptOrganization getDeptOrganizationByDeptId(Integer deptId) {
        return deptOrganizationService.getDomainByDeptId(deptId);
    }

    /**
     * 插入方法
     *
     * @param deptLegalApply
     * @param user
     */
    public void insertByDeptLegalApply(DeptLegalApply deptLegalApply, LoginUser user) {
        deptLegalApply.setDeptId(user.getDeptId());
        deptLegalApply.setCreateUid(user.getId());
        deptLegalApply.setCreateTime(DateUtil.getAllTime());
        deptLegalApply.setType(DeptLegalApplyMapper.A_TYPE[0]);
        deptLegalApplyMapper.insert(deptLegalApply);

        if (StringUtils.isNotEmpty(deptLegalApply.getStatus())) {
            //插入审核日志
            insertLogByDeptLegalApply(deptLegalApply, user);
        }
    }


    /**
     * 删除方法
     *
     * @param id
     */
    public void remove(Integer id) {
        deptLegalApplyMapper.deleteById(id);
    }


    /**
     * 更新方法
     *
     * @param deptLegalApply
     */
    public void updateByDeptLegalApply(DeptLegalApply deptLegalApply, LoginUser user) {
        deptLegalApplyMapper.updateById(deptLegalApply);
        if (StringUtils.isNotEmpty(deptLegalApply.getStatus())) {
            //插入审核日志
            insertLogByDeptLegalApply(deptLegalApply, user);
        }
    }


    /**
     * 插入审核日志
     *
     * @param deptLegalApply
     * @param user
     */
    private void insertLogByDeptLegalApply(DeptLegalApply deptLegalApply, LoginUser user) {
        String flowInfo = "用户：" + deptLegalApply.getDeptName() + "的" + user.getAccount() + "法人资格" + deptLegalApplyMapper.A_TYPE_NAME[0] + "申请";
        String checkOpinion = "操作描述：" + deptLegalApply.getDeptName() + "的" + user.getAccount() + "法人资格" + deptLegalApplyMapper.A_TYPE_NAME[0] + "申请已经提交，等待" + user.getDept().getParentDept().getDeptName() + "的管理员审核";
        DeptLegalApproveLog deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[0], user.getId(), DateUtil.getAllTime(), checkOpinion, user.getDept().getPId(), flowInfo);
        deptLegalApproveLogMapper.insert(deptLegalApproveLog);
    }


    /**
     * 查询方法
     *
     * @param id
     */
    public DeptLegalApply selectById(Integer id) {
        return deptLegalApplyMapper.selectById(id);
    }


    /**
     * 根据工会id，查询出法人资格申请基本信息
     * 可用于法人资质变更、注销、遗失补办
     *
     * @param deptId
     * @return
     */
    public DeptLegalApply selectLegalApplyByDeptId(Integer deptId) {
        String[] types = {"0", "1", "3"};
        List<DeptLegalApply> deptLegalApply = deptLegalApplyMapper.selectList(
                new EntityWrapper<DeptLegalApply>().eq("dept_id", deptId).eq("status", DeptLegalApplyMapper.A_STATUS[2]).in("type", types).orderBy("create_time", false));
        if (deptLegalApply != null && deptLegalApply.size() > 0) {
            return deptLegalApply.get(0);
        } else {
            return new DeptLegalApply();
        }
    }

    /**
     * 绑定新登记信息至Model
     *
     * @param model
     * @param orgId
     */
    public void bindApplyDataToModel(Model model, Integer orgId) {
        model.addAttribute("deptLegalApply", legalApplyService.selectLegalApplyByDeptId(orgId));
    }


    /**
     * 绑定审核日志信息
     *
     * @param model
     * @param id
     */
    public void setLogDataToModel(Model model, Integer id) {
        List<Map<String, Object>> deptLegalApplys = deptLegalApproveLogMapper.selectMapsByApplyId(id);
        deptLegalApplys.forEach(log -> {
            log.put("check_strtime", DateUtil.parseAndFormat(log.get("check_time").toString(), "yyyyMMddHHmmss",
                    "yyyy-MM-dd HH:mm:ss"));
            if (ToolUtil.isEmpty(log.get("fullname"))) {
                log.put("fullname", "");
            }
        });
        model.addAttribute("deptLegalApplys", deptLegalApplys);
    }

    /**
     * 审核页多条件分页查询
     *
     * @param page
     * @param name
     * @param agentName
     * @param orgId
     * @return
     */
    public List<Map<String, Object>> selectApplyCheckPageByCondition(Page<Map<String, Object>> page, String name, String deptName,
                                                                     String agentName, Integer orgId) {
        return deptLegalApplyMapper.selectApplyCheckPageByCondition(page, name, null, agentName, orgId, DeptLegalApplyMapper.A_TYPE[0]);
    }

    /**
     * 审核方法
     *
     * @param id
     * @param logStatus
     * @param checkOpinion
     * @param user
     */

    public void checkSubmit(Integer id, String logStatus, String checkOpinion, LoginUser user) {
        DeptLegalApply deptLegalApply = deptLegalApplyMapper.selectById(id);
        if (DeptLegalApproveLogMapper.A_STATUS[1].equals(logStatus)) {
            // 审核通过
            dealAuditThrough(deptLegalApply, logStatus, checkOpinion, user);
            return;
        }
        //审核拒绝
        dealAuditNotThrough(deptLegalApply, logStatus, checkOpinion, user);
    }

    /**
     * 处理审核拒绝
     */
    private void dealAuditNotThrough(DeptLegalApply deptLegalApply, String logStatus, String checkOpinion, LoginUser user) {
        deptLegalApply.setStatus(DeptLegalApplyMapper.A_STATUS[3]);
        String flowInfo = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核不通过";
        DeptLegalApproveLog deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[2], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, null, flowInfo);
        deptLegalApplyMapper.updateById(deptLegalApply);
        deptLegalApproveLogMapper.insert(deptLegalApproveLog);
    }


    /**
     * 处理审核通过
     *
     * @paramcount
     */
    private void dealAuditThrough(DeptLegalApply deptLegalApply, String logStatus, String checkOpinion, LoginUser user) {
//        Integer count = deptLegalApproveLogMapper.selectCount(new EntityWrapper<DeptLegalApproveLog>().eq("apply_id", deptLegalApply.getId()).in("status", deptLegalApproveLogMapper.A_STATUS[1]));
        DeptLegalApproveLog deptLegalApproveLog = null;

        if(user.getDept().getLevel()==1){
            //最后一级审核
            // 第三级审核：即登记机关审核
            deptLegalApply.setStatus(DeptLegalApplyMapper.A_STATUS[2]);
            String flowInfo_03 = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核通过";
            deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[1], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, null, flowInfo_03);
            // 执行更新 TODO
            // 插入一条法人基本信息至dept_legal表中
            insertDeptLegalByApply(deptLegalApply);
            //去掉法人资格首次申请角色，新增变更、注销、遗失补办登记角色
//                insertRoleByLoginUser(user);

        }else{
            //审核中
            deptLegalApply.setStatus(DeptLegalApplyMapper.A_STATUS[1]);
            String flowInfo_01 = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核通过，等待" + user.getDept().getParentDept().getDeptName() + "审核";
            deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[1], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, user.getDept().getParentDept().getPId(), flowInfo_01);
//            if(user.getDeptId().equals(deptId)){
//                // 第一级审核：即本级工会审核
//            }else{
//                // 第二级审核：即上级工会审核
//                String flowInfo_02 = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核通过，等待" + user.getDept().getParentDept().getDeptName() + "审核";
//                deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[1], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, user.getDept().getParentDept().getPId(), flowInfo_02);
//            }
        }
        
        deptLegalApplyMapper.updateById(deptLegalApply);
        deptLegalApproveLogMapper.insert(deptLegalApproveLog);
    }

    /**
     * 法人资格申请通过后，插入一条法人基本信息
     *
     * @param deptLegalApply
     */
    public void insertDeptLegalByApply(DeptLegalApply deptLegalApply) {
        DeptLegal deptLegal = new DeptLegal();
//        deptLegal.setDeptId(deptLegalApply.getDeptId());
//        deptLegal.setName(deptLegalApply.getName());
//        deptLegal.setSex(deptLegalApply.getSex());
//        deptLegal.setNation(deptLegalApply.getNation());
//        deptLegal.setBirthday(deptLegalApply.getBirthday());
//        deptLegal.setEducation(deptLegalApply.getEducation());
//        deptLegal.setPoliticalOutlook(deptLegalApply.getPoliticalOutlook());
//        deptLegal.setNativePlace(deptLegalApply.getNativePlace());
//        deptLegal.setWorkPosition(deptLegalApply.getWorkPosition());
//        deptLegal.setTnureStartDate(deptLegalApply.getTnureStartDate());
//        deptLegal.setPartTimeJob(deptLegalApply.getPartTimeJob());
//        deptLegal.setCertificateNo(deptLegalApply.getCertificateNo());
//        deptLegal.setOtherPosition(deptLegalApply.getOtherPosition());
//        deptLegal.setJoinTime(deptLegalApply.getJoinTime());
        String[] exclude= {"id","delFlg"};
        try {
            BeanUtil.tranferValues(deptLegalApply,deptLegal,exclude);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        deptLegal.setIncomeAccumulativeLastYear(deptLegalApply.getIncomeAccumulativeLastYear());
//
//        deptLegal.setIncomeAnnualDues(deptLegalApply.getIncomeAnnualDues());
//
//        deptLegal.setIncomeTradeUnionFunds(deptLegalApply.getIncomeTradeUnionFunds());
//
//        deptLegal.setIncomeOther(deptLegalApply.getIncomeOther());
//
//        deptLegal.setCapitalTotal(deptLegalApply.getCapitalTotal());
//
//        deptLegal.setCapitalFixedFunds(deptLegalApply.getCapitalFixedFunds());
//
//        deptLegal.setCapitalWorking(deptLegalApply.getCapitalWorking());
//
//        deptLegal.setCapitalWorking(deptLegalApply.getCapitalWorking());
//
//        deptLegal.setCapitalOther(deptLegalApply.getCapitalOther());
//
//        deptLegal.setPlaceTotal(deptLegalApply.getPlaceTotal());
//
//        deptLegal.setPlaceOfficeArea(deptLegal.getPlaceOfficeArea());
//
//        deptLegal.setPlaceActivity(deptLegalApply.getPlaceActivity());
//
//        deptLegal.setPlaceOther(deptLegalApply.getPlaceOther());
//
//        deptLegal.setAbilityToBear(deptLegalApply.getAbilityToBear());
//
//        deptLegal.setDeptName(deptLegalApply.getDeptName());
//
//        deptLegal.getDe
//        private String  unitAddress;
//
//        private String  unitZipCode;
//
//        private String  createunionTime;
//
//        private String  approveNo;
//
//        private String  unitNumber;
//
//        private String  membership;
//
//        private String  cadresNumber;
//
//        private String  chairmanName;
//
//        private String  chairmanSession;
//
//        private String  chairmanMobile;
//
//        private String  cerTime;
//
//        private String  cerNo;
//
//        private String  pDeptFullname;
//
//        private String  assetsHanding;
//
//        private String  debtHanding;
//
//        private String  legalMobile;
//
//        private String  officeTerm;
//
//        private String  certificateNumber;
//
//        private String  personCertificateNumber;
//
//        private String  approveCompany;
        deptLegalMappper.insert(deptLegal);
    }

    /**
     * 去掉法人资格首次申请角色，新增变更、注销、遗失补办登记角色
     *
     * @param user
     */
    private void insertRoleByLoginUser(LoginUser user) {
        List<Integer> ids = new ArrayList<Integer>();
        List<Relation> list = relationMapper.selectList(new EntityWrapper<Relation>().eq("uid", user.getId()).eq("roleid", RoleConstants.MY_COMPANY_ROLE_MENU[0]));
        if (list != null && list.size() > 0) {
            list.forEach(r -> {
                ids.add(r.getId());
            });
        }
        relationMapper.deleteBatchIds(ids);
        Relation relation = new Relation();
        relation.setUid(user.getId());
//        relation.setMenuid(RoleConstants.LEGAL_CHANGE_CANCEL_RECHANGE_REGISTER[0]);
        relationMapper.insert(relation);
    }

    /**
     * 校验能否创建新的法人资格申请
     * @param deptId
     * @return
     */
    public Tip checkCreateLegalApply(Integer deptId){
        String [] status={"0","1","2"};
        List<DeptLegalApply> applylist = deptLegalApplyMapper.selectApplyByStatus(deptId,0, Arrays.asList(status));
        if(applylist==null||applylist.size()==0){
            return new Tip(200,null,null);
        }
        DeptLegalApply deptLegalApply = applylist.get(0);
        if(deptLegalApply.getStatus()==null){
            return new Tip(500,"您有草稿还未处理",null);
        }
        switch (deptLegalApply.getStatus()){
            case "0":
                return new Tip(500,"已经存在待审核的申请，请稍后再试",null);

            case "1":
                return new Tip(500,"已有审核中的申请，请稍后再试",null);

            case "2":
                return new Tip(500,"已有审核成功的法人申请，无法再申请新的法人",null);
            default:
                return new Tip(200,null,null);
        }

    }

    /**
     * 校验能否创建新的其他法人资格申请
     * @param deptId
     * @param type
     * @return
     */
    public Tip checkCreateOtherApply(Integer deptId,Integer type){
        String [] status={"0","1","2"};
        List<DeptLegalApply> applylist = deptLegalApplyMapper.selectApplyByStatus(deptId,type, Arrays.asList(status));
        if(applylist==null||applylist.size()==0){
            return new Tip(200,null,null);
        }
        DeptLegalApply deptLegalApply = applylist.get(0);
        if(deptLegalApply.getStatus()==null){
            return new Tip(500,"您有草稿还未处理",null);
        }
        switch (deptLegalApply.getStatus()){
            case "0":
                return new Tip(500,"已经存在待审核的申请，请稍后再试",null);

            case "1":
                return new Tip(500,"已有审核中的申请，请稍后再试",null);

            default:
                return new Tip(200,null,null);
        }
    }
}
