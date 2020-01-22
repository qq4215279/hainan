package com.gobestsoft.mamage.moudle.law.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.mapper.SrvLawSupportLogMapper;
import com.gobestsoft.common.modular.dao.mapper.SrvLawSupportMapper;
import com.gobestsoft.common.modular.dao.model.SrvLawSupportLogPojo;
import com.gobestsoft.common.modular.dao.model.SrvLawSupportPojo;
import com.gobestsoft.common.modular.model.LogModel;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LawSupportService extends ManageBasic {

    @Resource
    SrvLawSupportMapper supportMapper;

    @Resource
    SrvLawSupportLogMapper supportLogMapper;

    @Autowired
    SrvLawSupportLogMapper lawSupportLogMapper;

    @Autowired
    DeptDao deptDao;

    @Autowired
    MessageService messageService;

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 根据条件查询法律援助列表
     *
     * @param page
     * @param name
     * @param status
     * @param type
     * @return
     */
    public List<Map<String, Object>> selectByCondition(Page<Map<String, Object>> page, String name, String status, Integer type, String workUnit, String examine, Integer deptId, Integer deptLevel) {
        List<Integer> secondDeptIds = this.deptMapper.selectSecondDeptIds();//市级deptIds
        secondDeptIds.add(1);
        List<String> StringSondeptIds = this.deptMapper.selectSonDeptIdByPid(deptId);
        List<Integer> deptIds = new ArrayList<>();//
        deptIds.add(deptId);
        for (String stringsondeptId : StringSondeptIds) {
            deptIds.add(Integer.parseInt(stringsondeptId));
        }
        for (Integer DId : secondDeptIds) {
            if (ToolUtil.isNotEmpty(deptId) && deptId.equals(DId)) {//deptId是市级
                List<Map<String, Object>> supportList = this.supportMapper.selectByCondition(page, name, status, type, workUnit, examine, deptLevel, null, deptIds);
                supportList.forEach(map -> {
                    int logStatus = (int) map.get("logStatus");
                    int checkleadDeptId = (int) map.get("checkleadDeptId");
                    String simpleName = this.deptMapper.selectSimplenameByDeptId(checkleadDeptId);
                    if (logStatus == 1) {
                        map.put("examine", simpleName + "（待审核）");
                    } else if (logStatus == 2) {
                        map.put("examine", simpleName + "（通过）");
                    } else {
                        String uid = (String) map.get("checkUid");
                        if (ToolUtil.isEmpty(uid)) {
                            map.put("examine", "审核超时");
                        } else {
                            //simpleName = this.deptMapper.selectSimplenameByUid(uid);
                            map.put("examine", simpleName + "（拒绝）");
                        }
                    }

                });
                    return supportList;
            }
        }
        deptIds = null;//不是市级
        List<Map<String, Object>> supportList = this.supportMapper.selectByCondition(page, name, status, type, workUnit, examine, deptLevel, deptId, deptIds);
        supportList.forEach(map -> {
            int logStatus = (int) map.get("logStatus");
            int checkleadDeptId = (int) map.get("checkleadDeptId");
            String simpleName = this.deptMapper.selectSimplenameByDeptId(checkleadDeptId);
            if (logStatus == 1) {
                map.put("logStatus", 0);
                map.put("examine", simpleName + "（待审核）");
            } else if (logStatus == 2) {
                map.put("examine", simpleName + "（通过）");
            } else {
                String uid = (String) map.get("checkUid");
                if (ToolUtil.isEmpty(uid)) {
                    map.put("examine", "审核超时");
                } else {
//                    simpleName = this.deptMapper.selectSimplenameByUid(uid);
                    map.put("examine", simpleName + "（拒绝）");
                }
            }

        });
//        Date date = new Date();// 获取当前时间
        // 对已过期的数据更改状态为拒绝
//		for (Map<String, Object> map : supportList) {
//			// 过期时间
//			if (ToolUtil.isNotEmpty(map.get("expireTime"))) {
//				Date expireTime = DateUtil.parseDateTime(map.get("expireTime").toString());
//				Integer flag = date.compareTo(expireTime);
//				if(flag != -1){ // -1代表当前时间 < 过期时间 比如 当前时间20180912 < 过期时间20180916
//					map.put("status", 3);
//					map.put("logStatus", 3);
//				}
//			}
//		}

            return supportList;
    }

    /**
     * 根据id查询详情
     *
     * @param id
     * @return
     */
    public SrvLawSupportPojo selectById(Integer id) {
        return supportMapper.selectById(id);
    }

    /**
     * 提交审核情况
     * @param support
     */
    public void submit(SrvLawSupportPojo support, String comment, Integer supportLogId) throws IOException {
        Integer deptId = getLoginUser().getDeptId();// 当前组织
        String uid = getLoginUser().getId();// 当前登录人uid
        Integer level = getLoginUser().getDept().getLevel();// 当前等级

        SrvLawSupportLogPojo supportLog = lawSupportLogMapper.selectById(supportLogId);
        supportLog.setStatus(support.getStatus());
        supportLog.setComment(comment);
        supportLog.setCheckUid(uid);
        supportLog.setCheckDate(DateUtil.getAllTime());
        supportLog.setCheckleadDeptId(deptId);//修改市级审核工会id
        lawSupportLogMapper.updateById(supportLog);// 更新日志信息

        SrvLawSupportLogPojo logPojo = new SrvLawSupportLogPojo();
        if (support.getStatus() == 2 && level != 2 && level != 1) { // 审核通过且非市级下增添日志表
            support.setStatus(1);// 待处理
            logPojo.setStatus(1);// 如果非市级，新增的日志状态处于待审核（市级登录时进行审核）
            logPojo.setCheckDeptId(deptDao.selectByOrgId(deptId));
            logPojo.setSupportId(support.getId());
            logPojo.setCreateUserType(1);
            logPojo.setCreateUid(uid);
            logPojo.setCreateTime(DateUtil.getAllTime());
            lawSupportLogMapper.insert(logPojo);// 插入新的日志信息
        }
        supportMapper.updateById(support);//修改主表信息


    }

    /**
     * 查看审核流程
     *
     * @param id
     * @return
     */
    public List<LogModel> log(Integer id) {
        List<LogModel> logs = supportLogMapper.selectListMapById(id);
        logs.forEach(log -> {
            if (log.getAccount() == null) {
                List<Integer> secondDeptIds = this.deptMapper.selectSecondDeptIds();
                secondDeptIds.add(1);
                Integer checkDeptId = log.getCheckDeptId();
                boolean flag = true;
                for (Integer secondDeptId : secondDeptIds) {
                    if (ToolUtil.isNotEmpty(checkDeptId) && secondDeptId.equals(checkDeptId)) {
                        log.setContent("等待" + log.getFullname() + "审核");
                        flag = false;
                    }
                }
                if (flag) {
                    String LogFullname = log.getFullname();
                    String simplename = this.deptMapper.selectSimplenameByLogFullname(LogFullname);
                    System.out.println(simplename);
                    log.setContent("等待" + simplename + "审核");
                }
            } else {
                log.setContent("用户：" + log.getAccount() + "已审核");
                log.setCheckTime(DateUtil.parseAndFormat(log.getCheckTime(), "yyyyMMddHHmmss",
                        "yyyy-MM-dd HH:mm:ss"));
            }
        });
        LogModel result = new LogModel();
        result.setStatus(0);
        result.setFullname("APP用户");
        result.setComment("操作描述：" + logs.get(0).getName() + "已经提交了法律援助申请");
        result.setContent("用户：" + logs.get(0).getName() + "提交法律援助申请");
        result.setCheckTime(DateUtil.parseAndFormat(logs.get(0).getCreateTime(), "yyyyMMddHHmmss",
                "yyyy-MM-dd HH:mm:ss"));
        logs.add(0, result);

        return logs;
    }


    public List<Map> selectSonDept(Integer pid) {
        return supportMapper.selectSonDept(pid);
    }

    /**
     * 转办
     *
     * @param id
     * @param deptId
     * @param transferReason
     * @return
     */
    @Transactional
    public Tip updateTransfer(Integer id, Integer deptId, String transferReason) {
        int update = supportMapper.updateAuditDeptId(id, deptId, transferReason);
        SrvLawSupportPojo srvLawSupportPojo = supportMapper.selectById(id);
        if (update == 0) return new Tip(500, "状态被修改，请刷新列表后重试", null);
        SrvLawSupportLogPojo pojo = new SrvLawSupportLogPojo();
        pojo.setStatus(1);
        pojo.setCreateTime(DateUtil.getAllTime());
        pojo.setCheckDeptId(deptId);
        pojo.setCreateUid(srvLawSupportPojo.getCreateUid());
        pojo.setSupportId(id);
        supportLogMapper.insert(pojo);
        return new Tip(200, null, null);
    }

    /**
     * 给客户发送超时提醒
     */
    @Transactional
    public void sendExpireNotice(int day) {

        boolean hasnext = true;

        while (hasnext) {
            List<Map<String, Object>> list = supportMapper.selectExpireAudit(day);
            if (list != null && list.size() > 0) {

                List<String> idlist = new ArrayList<>();
                List<String> auidlist = new ArrayList<>();

                list.forEach(map -> {

                    idlist.add(map.get("id") + "");
                    auidlist.add(map.get("create_uid") + "");

                });
                supportMapper.updateExpireAudit(idlist);
                supportMapper.updateExpireLog(idlist);

                list.forEach(map -> {
                    int category = 5;
                    int mode = 5;//法律援助
                    int targetId = Integer.valueOf(map.get("id") + "");
                    boolean isPush = true;
                    String title = "法律援助审核超时";
                    String comment = "本级工会超时未审核，请重新提交申请";
                    String[] auids = {map.get("create_uid") + ""};
                    messageService.sendMessageByAuid(category, title, comment, mode, targetId + "", null, isPush, auids);

                });
            } else {
                hasnext = false;
            }
        }


    }

}
