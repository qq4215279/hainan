package com.gobestsoft.mamage.moudle.dept;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.DictGroupCodeConstants;
import com.gobestsoft.common.constant.RoleConstants;
import com.gobestsoft.common.modular.dept.mapper.*;
import com.gobestsoft.common.modular.dept.model.DeptMember;
import com.gobestsoft.common.modular.dept.model.DeptMemberApplyLog;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.common.modular.dept.model.DeptOrganizationLog;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.Relation;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.model.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 申请建会企业业务层
 *
 * @author zhangdaowei
 */
@Service
@Transactional
public class CompanyOrganizationService {

    @Resource
    DeptOrganizationMapper deptOrganizationMapper;
    @Resource
    DeptOrganizationLogMapper deptOrganizationLogMapper;
    @Resource
    DeptMapper deptMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    DeptMemberMapper deptMemberMapper;
    @Resource
    DeptMemberApplyMapper deptMemberApplyMapper;
    @Resource
    DeptMemberApplyLogMapper deptMemberApplyLogMapper;
    @Resource
    AppMemberApplyService appMemberApplyService;
    @Resource
    DictMapper dictMapper;


    /**
     * 根据组织获取阻止你信息
     *
     * @param deptId
     * @return
     */
    public List<DeptOrganization> getDeptOrganizationByDeptId(Integer deptId) {
        return deptOrganizationMapper.selectList(getEntityWrapper().eq("dept_id", deptId));
    }

    /**
     * 申请建会多条件分页查询
     *
     * @param unitName
     * @param unitOrgCode
     * @return
     */
    public List<DeptOrganization> selectBuildPageByUnitNameAndCode(String unitName, String unitOrgCode, String uid) {
        Integer num = this.selectCountByUid(uid);
        if (num == 0) {
            if (StringUtils.isNotEmpty(unitName) && StringUtils.isNotEmpty(unitOrgCode)) {
                return deptOrganizationMapper.selectList(getEntityWrapper().eq("unit_name", unitName).eq("unit_org_code", unitOrgCode));
            }
            return new ArrayList<DeptOrganization>();
        } else {
            return deptOrganizationMapper.selectList(getEntityWrapper().eq("create_user", uid));
        }
    }

    /**
     * 查看企业是否已经申请建会
     *
     * @param uid
     * @return
     */
    public Integer selectCountByUid(String uid) {
        return deptOrganizationMapper.selectCount(getEntityWrapper().eq("create_user", uid));
    }

    /**
     * 根据主键查询工会基本信息
     *
     * @param id
     * @return
     */
    public DeptOrganization selectById(Integer id) {
        DeptOrganization deptOrganization = deptOrganizationMapper.selectById(id);
        if (ToolUtil.isNotEmpty(deptOrganization)) {
            return deptOrganization;
        }
        return new DeptOrganization();
    }

    /**
     * 根据主键查询工会基本信息
     *
     * @param deptId
     * @return
     */
    public DeptOrganization selectByDeptId(Integer deptId) {
        List<DeptOrganization> result = deptOrganizationMapper.selectList(new EntityWrapper().eq("dept_id", deptId));
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return new DeptOrganization();
    }

    /**
     * 申请建会提交
     *
     * @param deptOrganization
     * @param loginUser
     */

    public void saveBuild(DeptOrganization deptOrganization, LoginUser loginUser) {
        if (StringUtils.isEmpty(deptOrganization.getUnionSimpleName())) {
            deptOrganization.setUnionSimpleName(deptOrganization.getUnionName());
        }

        if (ToolUtil.isEmpty(deptOrganization.getId())) {//插入企业信息
            //根据当前用户，插入工会基本信息
            bindDeptOrganizationByLoginUser(deptOrganization, loginUser);
            deptOrganizationMapper.insert(deptOrganization);
            //插入申请建会日志
            insertLogByDeptOrganization(deptOrganization, loginUser, "填写的建会申请已经提交", "的建会申请");
            return;
        }
        //更新企业信息
        //根据当前用户，插入工会基本信息
        bindDeptOrganizationByLoginUser(deptOrganization, loginUser);
        deptOrganizationMapper.updateById(deptOrganization);
        //插入申请建会日志
        insertLogByDeptOrganization(deptOrganization, loginUser, "填写的建会申请已经提交", "的建会申请");
    }

    /**
     * 绑定工会审核状态和数据来源数据
     *
     * @param deptOrganization
     * @param loginUser
     */
    private void bindDeptOrganizationByLoginUser(DeptOrganization deptOrganization, LoginUser loginUser) {
        deptOrganization.setCreateUser(loginUser.getId());
        deptOrganization.setCreateTime(DateUtil.getAllTime());
        deptOrganization.setUpdateUser(loginUser.getId());
        deptOrganization.setUpdateTime(DateUtil.getAllTime());
        deptOrganization.setStatus(1);//待审核
        deptOrganization.setSource(1);//数据来源（0：系统添加；1：申请建会；）
    }

    /**
     * 插入申请建会日志
     *
     * @param deptOrganization
     * @param loginUser
     */
    private void insertLogByDeptOrganization(DeptOrganization deptOrganization, LoginUser loginUser, String comment, String recordFlowInfo) {
        DeptOrganizationLog log = new DeptOrganizationLog();
        log.setStatus(1);
        log.setRefId(deptOrganization.getId());
        log.setOperationTime(DateUtil.getAllTime());
        log.setOperationUser(loginUser.getId());//操作人uid
        log.setOperationDeptId(deptOrganization.getPId());
        log.setComment("操作描述：" + deptOrganization.getUnionName() + "的" + loginUser.getAccount() + comment + "，等待" + deptOrganization.getPName() + "审核");
        log.setRecordFlowInfo("用户：" + loginUser.getAccount() + recordFlowInfo);
        deptOrganizationLogMapper.insert(log);
    }

    /**
     * 根据uid，查询当前用户创建的工会查询
     *
     * @param uid
     * @return
     */
    public List<DeptOrganization> selectPageByUid(String uid) {
        return deptOrganizationMapper.selectList(getEntityWrapper().eq("create_user", uid));
    }

    /**
     * 获取工会组织的Wrapper对象
     *
     * @return
     */
    private EntityWrapper<DeptOrganization> getEntityWrapper() {
        return new EntityWrapper<DeptOrganization>();
    }

    /**
     * 绑定字典项的名称
     *
     * @param deptOrganization
     */
    public void bindDictNameToDeptOrganization(DeptOrganization deptOrganization) {
        String[] group_codes = DictGroupCodeConstants.ORGANIZATION_GROUP_CODES;
        List<Dict> dicts = dictMapper.getDicListByGroupCodes(group_codes);
        for (Dict d : dicts) {
            if (group_codes[0].equals(d.getGroupCode()) && StringUtils.isNotEmpty(deptOrganization.getUnionType()) && deptOrganization.getUnionType().equals(d.getCode())) {
                deptOrganization.setUnionType(d.getName());
                continue;
            }
            if (group_codes[1].equals(d.getGroupCode()) && StringUtils.isNotEmpty(deptOrganization.getUnitNature()) && deptOrganization.getUnitNature().equals(d.getCode())) {
                deptOrganization.setUnitNature(d.getName());
                continue;
            }
            if (group_codes[2].equals(d.getGroupCode()) && StringUtils.isNotEmpty(deptOrganization.getUnitEconomicType()) && deptOrganization.getUnitEconomicType().equals(d.getCode())) {
                deptOrganization.setUnitEconomicType(d.getName());
                continue;
            }
            if (group_codes[3].equals(d.getGroupCode()) && StringUtils.isNotEmpty(deptOrganization.getUnitIndustry()) && deptOrganization.getUnitIndustry().equals(d.getCode())) {
                deptOrganization.setUnitIndustry(d.getName());
                continue;
            }
        }
    }

    /**
     * 查看审核流程
     */
    public List<Map<String, Object>> log(Integer id) {
        List<Map<String, Object>> logs = deptOrganizationLogMapper.selectListMapByRefId(id);
        if(logs.size() > 0){
            DeptOrganization deptOrganization = deptOrganizationMapper.selectById(Integer.valueOf(logs.get(0).get("ref_id").toString()));
            logs.forEach(log -> {
                log.put("statusName", getStatusNameByStatus(Integer.valueOf(log.get("status").toString())));
                log.put("check_date", DateUtil.parseAndFormat(log.get("operation_time").toString(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
                if ("1".equals(log.get("status").toString())) {
                    log.put("union_name", deptOrganization.getUnitName());
                }
            });
        }
        return logs;
    }

    /**
     * 获取审核状态名称
     *
     * @param status
     * @return
     */
    private String getStatusNameByStatus(Integer status) {
        if (status == 1) {
            return "待审核";
        }
        if (status == 2) {
            return "通过";
        }
        if (status == 3) {
            return "不通过";
        }
        return "";
    }

    /**
     * 申请建会审核多条件分页查询
     *
     * @param page
     * @param status
     * @param unitName
     * @param orgId
     * @return
     */
    public List<DeptOrganization> selectAuditOrganizationPage(Page<DeptOrganization> page
            , Integer status, String unitName, Integer orgId) {
        return deptOrganizationMapper.selectAuditOrganizationPage(page, status, unitName, orgId, null);
    }

    /**
     * 申请建会审核通过、审核不通过方法
     *
     * @param id
     * @param status
     * @param reason
     */
    public void saveAuthen(Integer id, Integer status, String reason, LoginUser loginUser) {
        DeptOrganization deptOrganization = deptOrganizationMapper.selectById(id);
        //绑定申请建会审核状态
        deptOrganization.setStatus(status);
        deptOrganization.setUpdateUser(loginUser.getId());
        deptOrganization.setUpdateTime(DateUtil.getAllTime());
        //绑定申请建会审核日志
        String content = (status == 3 ? "审核不通过" : "审核通过");
        DeptOrganizationLog log = new DeptOrganizationLog();
        log.setStatus(status);
        log.setRefId(deptOrganization.getId());
        log.setOperationTime(DateUtil.getAllTime());
        log.setOperationUser(loginUser.getId());//操作人uid
        log.setComment("审核意见：" + reason);
        log.setRecordFlowInfo("用户：" + loginUser.getName() + content);
        if (status == 3) {
            //审核不通过，退回至企业让其重新发起建会申请
            deptOrganizationMapper.updateById(deptOrganization);
            deptOrganizationLogMapper.insert(log);
            return;
        }
        //去掉申请建会菜单角色，新增我的工会菜单角色及会员管理、会员转出审核、法人资格登记角色等
        Integer[] roleMenu = RoleConstants.MY_COMPANY_ROLE_MENU;
        List<Relation> relations = new ArrayList<Relation>();
        String roleId = "0";
        for (Integer roleid : roleMenu) {
            roleId += "," + roleid;
        }
        User user = userMapper.selectById(deptOrganization.getCreateUser());
        Dept dept = null;
        //插入sys_dept表数据
        if (deptOrganization.getDeptId() == null) {
            dept = new Dept();
            //绑定dept数据
            bindDeptByOrganization(dept, deptOrganization);
            deptMapper.insert(dept);
            user.setDeptid(dept.getId());
            deptOrganization.setDeptId(dept.getId());
        } else {
            dept = deptMapper.selectById(deptOrganization.getDeptId());
            dept.setSimplename(deptOrganization.getUnionSimpleName());
            dept.setFullname(deptOrganization.getUnionName());
            deptMapper.updateById(dept);
            user.setDeptid(dept.getId());
        }

        if (dept.getLevel() < 4) {
            for (Integer roleid : RoleConstants.MY_COMPANY_LEVEL_1_2_3_ROLE_MENU) {
                roleId += "," + roleid;
            }
        }

        deptOrganizationMapper.updateById(deptOrganization);
        deptOrganizationLogMapper.insert(log);
        //更新用户的角色id和orgId
        user.setRoleid(roleId);
        userMapper.updateById(user);

        //申请建会审核通过后查询工会下的会员信息是否有预备会员，若有则更新会员信息表的type字段和发起会员入会的申请
        //inSertdeptMemberApplyAndLog(deptOrganization);
    }

    /**
     * 申请建会审核通过后查询工会下的会员信息是否有预备会员，若有则更新会员信息表的type字段和发起会员入会的申请
     *
     * @param deptOrganization
     */
    private void inSertdeptMemberApplyAndLog(DeptOrganization deptOrganization) {
        List<Map<String, Object>> deptMemberApplys = deptMemberApplyMapper.selectListByUnitName(deptOrganization.getUnitName()
                , deptOrganization.getUnionName(), deptOrganization.getUnionSimpleName(), DeptMember.A_TYPE[1]);
        if (ToolUtil.isEmpty(deptMemberApplys)) {
            return;
        }
        List<DeptMemberApplyLog> logs = new ArrayList<DeptMemberApplyLog>();
        deptMemberApplys.forEach(m -> {
            DeptMemberApplyLog deptMemberApplyLog = appMemberApplyService.bindFirstApplyLogByDeptMemberApply(Integer.valueOf(m.get("id").toString())
                    , m.get("name").toString(), null, deptOrganization.getUnionName(), Integer.valueOf(DictCodeConstants.MEMBER_APPLY_TYPE_JOIN), m.get("create_uid").toString()
                    , deptOrganization.getDeptId());
            logs.add(deptMemberApplyLog);
        });
        deptMemberApplyLogMapper.insertList(logs);//插入入会申请日志
        deptMemberMapper.updateTypeByList(deptMemberApplys, DeptMember.A_TYPE[0]);//更新预备会员的type字段
    }

    /**
     * 根据工会信息插入一条Dept数据
     *
     * @param dept
     * @param deptOrganization
     */
    private void bindDeptByOrganization(Dept dept, DeptOrganization deptOrganization) {
        Dept pDept = deptMapper.selectById(deptOrganization.getPId());
        dept.setPid(deptOrganization.getPId());
        dept.setPids(pDept.getPids() + "," + deptOrganization.getPId());
        dept.setSimplename(deptOrganization.getUnionSimpleName());
        dept.setFullname(deptOrganization.getUnionName());
        dept.setLevel(pDept.getLevel() + 1);
        dept.setDeptType(0); //0组织 1部门
        dept.setEstablishDate(DateUtil.getDays());
    }

    /**
     * 完善信息提交
     *
     * @param deptOrganization
     * @param loginUser
     */
    public void reEditSaveBuild(DeptOrganization deptOrganization, LoginUser loginUser) {
        deptOrganization.setPerfectInformation(DeptOrganization.A_PERFECT_INFORMATION[0]);
        deptOrganization.setUpdateTime(DateUtil.getAllTime());
        deptOrganization.setUpdateUser(loginUser.getId());
        deptOrganizationMapper.updateById(deptOrganization);
        //插入完善信息提交待审核的日志
        insertLogByDeptOrganization(deptOrganization, loginUser, "完善的工会信息已经提交", "的工会信息完善");
    }

    /**
     * 完善信息审核多条件分页查询
     *
     * @param page
     * @param unitName
     * @param orgId
     * @return
     */
    public List<DeptOrganization> auditPerFectListPage(Page<DeptOrganization> page,
                                                       String unitName, Integer orgId) {
        return deptOrganizationMapper.selectAuditOrganizationPage(page, null, unitName, orgId, DeptOrganization.A_PERFECT_INFORMATION);
    }

    /**
     * 完善信息审核通过、审核不通过方法
     *
     * @param id
     * @param status
     * @param reason
     * @param loginUser
     */
    public void savePerFectAuthen(Integer id, Integer status, String reason, LoginUser loginUser) {
        DeptOrganization deptOrganization = deptOrganizationMapper.selectById(id);
        if (status == 2) {
            //完善信息通过
            deptOrganization.setPerfectInformation(DeptOrganization.A_PERFECT_INFORMATION[1]);
            deptOrganizationMapper.updateById(deptOrganization);
            insertLogByPerfectInformation(status, deptOrganization, reason, loginUser, "审核通过");
            return;
        }
        deptOrganization.setPerfectInformation(DeptOrganization.A_PERFECT_INFORMATION[2]);
        deptOrganizationMapper.updateById(deptOrganization);
        insertLogByPerfectInformation(status, deptOrganization, reason, loginUser, "审核不通过");
    }

    /**
     * 插入完善信息审核日志
     *
     * @param status
     * @param deptOrganization
     * @param reason
     * @param loginUser
     * @param recordFlowInfo
     */
    private void insertLogByPerfectInformation(Integer status, DeptOrganization deptOrganization, String reason, LoginUser loginUser, String recordFlowInfo) {
        DeptOrganizationLog log = new DeptOrganizationLog();
        log.setStatus(status);
        log.setRefId(deptOrganization.getId());
        log.setOperationTime(DateUtil.getAllTime());
        log.setOperationUser(loginUser.getId());//操作人uid
        log.setComment("审核意见：" + reason);
        log.setRecordFlowInfo("用户：" + loginUser.getAccount() + recordFlowInfo);
        deptOrganizationLogMapper.insert(log);
    }


    /**
     * 工会是否是基层工会
     *
     * @param deptId 工会ID
     * @return
     */
    public boolean isTheBasicDept(int deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (dept != null && dept.getLevel() == 8) {
            return false;
        }
        return true;
    }

    /**
     * 判断法人和其他组织统一社会信用代码是否已经存在
     *
     * @param othersOrgCode
     * @return
     */
    public boolean existsOthersOrgCode(String othersOrgCode, Integer id) {

        Wrapper wrapper = new EntityWrapper().eq("others_org_code", othersOrgCode);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return deptOrganizationMapper.selectCount(wrapper) > 0;
    }


    /**
     * 判断法人和其他组织统一社会信用代码是否已经存在
     *
     * @param othersOrgCode
     * @return
     */
    public boolean existsOthersOrgCodeById(String othersOrgCode, Integer id) {

        Wrapper wrapper = new EntityWrapper().eq("others_org_code", othersOrgCode);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return deptOrganizationMapper.selectCount(wrapper) > 0;
    }

}
