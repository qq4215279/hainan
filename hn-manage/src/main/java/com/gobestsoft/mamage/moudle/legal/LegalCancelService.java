package com.gobestsoft.mamage.moudle.legal;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalApplyMapper;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalApproveLogMapper;
import com.gobestsoft.common.modular.legal.mapper.DeptLegalMapper;
import com.gobestsoft.common.modular.legal.model.DeptLegal;
import com.gobestsoft.common.modular.legal.model.DeptLegalApply;
import com.gobestsoft.common.modular.legal.model.DeptLegalApproveLog;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.model.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 法人资格注销
 *
 * @author xiezimeng
 */
@Service
public class LegalCancelService {

    @Resource
    DeptLegalApplyMapper deptLegalApplyMapper;
    @Resource
    DeptLegalApproveLogMapper deptLegalApproveLogMapper;
    @Resource
    LegalApplyService legalApplyService;
    @Resource
    DeptLegalMapper deptLegalMapper;

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
        return deptLegalApplyMapper.selectApplyPageByCondition(page, name, null, agentName, uid, DeptLegalApplyMapper.A_TYPE[2]);
    }

    /**
     * 插入方法
     *
     * @param deptLegalApply
     * @param user
     */
    @Transactional
    public void insertByDeptLegalApply(DeptLegalApply deptLegalApply, LoginUser user) {
        deptLegalApply.setCreateUid(user.getId());
        deptLegalApply.setCreateTime(DateUtil.getAllTime());
        deptLegalApply.setType(DeptLegalApplyMapper.A_TYPE[2]);
        deptLegalApply.setDeptId(user.getDeptId());
        deptLegalApplyMapper.insert(deptLegalApply);
        if (StringUtils.isNotEmpty(deptLegalApply.getStatus())) {
            //插入审核日志
            insertLogByDeptLegalApply(deptLegalApply, user);
        }
    }

    /**
     * 更新方法
     *
     * @param deptLegalApply
     * @param user
     */
    public void updateByDeptLegalApply(DeptLegalApply deptLegalApply, LoginUser user) {
        deptLegalApplyMapper.updateById(deptLegalApply);
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
        List<DeptLegalApproveLog> logs = deptLegalApproveLogMapper.selectList(new EntityWrapper<DeptLegalApproveLog>().eq("apply_id", id));
        if (logs != null && logs.size() > 0) {
            List<Integer> idList = new ArrayList<Integer>();
            for (DeptLegalApproveLog log : logs) {
                idList.add(log.getId());
            }
            deptLegalApproveLogMapper.deleteBatchIds(idList);
        }
        deptLegalApplyMapper.deleteById(id);
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
     * 插入审核日志
     *
     * @param deptLegalApply
     * @param user
     */
    private void insertLogByDeptLegalApply(DeptLegalApply deptLegalApply, LoginUser user) {
        String flowInfo = "用户：" + deptLegalApply.getDeptName() + "的" + user.getAccount() + "法人资格" + deptLegalApplyMapper.A_TYPE_NAME[2] + "申请";
        String checkOpinion = "操作描述：" + deptLegalApply.getDeptName() + "的" + user.getAccount() + "法人资格" + deptLegalApplyMapper.A_TYPE_NAME[2] + "申请已经提交，等待" + user.getDept().getParentDept().getDeptName() + "的管理员审核";
        DeptLegalApproveLog deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[0], user.getId(), DateUtil.getAllTime(), checkOpinion, user.getDept().getPId(), flowInfo);
        deptLegalApproveLogMapper.insert(deptLegalApproveLog);
    }

    /**
     * 绑定注销信息至Model
     *
     * @param model
     * @param orgId
     */
    public void bindApplyDataToModel(Model model, Integer orgId) {
        model.addAttribute("deptLegalApply", legalApplyService.selectLegalApplyByDeptId(orgId));
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
        return deptLegalApplyMapper.selectApplyCheckPageByCondition(page, name, null, agentName, orgId, DeptLegalApplyMapper.A_TYPE[2]);
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
     * 处理审核通过
     *
     * @param
     */
    private void dealAuditThrough(DeptLegalApply deptLegalApply, String logStatus, String checkOpinion, LoginUser user) {
        Integer count = deptLegalApproveLogMapper.selectCount(new EntityWrapper<DeptLegalApproveLog>().eq("apply_id", deptLegalApply.getId()).in("status", deptLegalApproveLogMapper.A_STATUS[1]));
        DeptLegalApproveLog deptLegalApproveLog = null;
        if(user.getDept().getLevel()==1){

            deptLegalApply.setStatus(DeptLegalApplyMapper.A_STATUS[2]);
            String flowInfo_03 = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核通过";
            deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[1], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, null, flowInfo_03);
            // 执行更新 TODO
            // 逻辑删除法人基本信息
            deleteDeptLegalByApply(deptLegalApply);

        }else{

            deptLegalApply.setStatus(DeptLegalApplyMapper.A_STATUS[1]);
            String flowInfo_01 = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核通过，等待" + user.getDept().getParentDept().getDeptName() + "审核";
            deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[1], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, user.getDept().getPId(), flowInfo_01);
        }
//        switch (count) {
//            case 0:
//                // 第一级审核：即本级工会审核
//                deptLegalApply.setStatus(DeptLegalApplyMapper.A_STATUS[1]);
//                String flowInfo_01 = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核通过，等待" + user.getDept().getParentDept().getDeptName() + "审核";
//                deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[1], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, user.getDept().getPId(), flowInfo_01);
//                break;
//            case 1:
//                // 第二级审核：即上级工会审核
//                String flowInfo_02 = "用户：" + user.getDept().getDeptName() + "的" + user.getAccount() + "审核通过，等待" + user.getDept().getDeptName() + "审核";
//                deptLegalApproveLog = new DeptLegalApproveLog(deptLegalApply.getId(), DeptLegalApproveLogMapper.A_STATUS[1], user.getId(), DateUtil.getAllTime(), "审核意见：" + checkOpinion, user.getDept().getPId(), flowInfo_02);
//                break;
//            case 2:
//                // 第三级审核：即登记机关审核
//
//                break;
//            default:
//                break;
//        }
        deptLegalApplyMapper.updateById(deptLegalApply);
        deptLegalApproveLogMapper.insert(deptLegalApproveLog);
    }

    /**
     * 逻辑删除法人基本信息
     *
     * @param deptLegalApply
     */
    private void deleteDeptLegalByApply(DeptLegalApply deptLegalApply) {
        List<DeptLegal> deptLegals = deptLegalMapper.selectDeptLegalByDeptId(deptLegalApply.getDeptId());
        if (deptLegals != null && deptLegals.size() > 0) {
            deptLegals.get(0).setDelFlg("1");//逻辑删除
            deptLegalMapper.updateById(deptLegals.get(0));
        }
        String[] types = {"0", "1", "3"};
        // 逻辑删除法人资格申请、变更、遗失补办的数据
        List<DeptLegalApply> list = deptLegalApplyMapper.selectList(new EntityWrapper<DeptLegalApply>().in("type", types).eq("del_flg", "0"));
        list.forEach(d -> {
            d.setDelFlg("1");
            deptLegalApplyMapper.updateById(d);
        });
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

}

