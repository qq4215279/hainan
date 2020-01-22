package com.gobestsoft.mamage.moudle.system.service;

import com.gobestsoft.common.modular.mst.dao.MstOrganizationDao;
import com.gobestsoft.common.modular.mst.model.MstOrganization;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.core.node.ZTreeNode;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.common.constant.CacheConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 部门服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
@Service
@Transactional
public class DeptService {

    @Resource
    DeptMapper deptMapper;

    @Resource
    DeptDao deptDao;

    @Resource
    MstOrganizationDao mstOrganizationDao;

    @Autowired
    protected RedisUtils redisUtils;

    //工会组织树级别 （0：中国海南； 1：50个局级单位；2: 分公司；3: 项目部）
    private final static Integer[] LEVEL = {1, 2, 3};

    /**
     * 插入方法
     * 根据传入的部门信息对象，判断其部门类型，执行插入部门或工会信息操作
     *
     * @param dept
     */
    public Dept insertOrganizationAndDeptByModel(Dept dept) {
        if (dept.getDeptType() == 1) {
            insertDept(dept);//插入部门信息
        } else {
            insertOrganizationAndDept(dept);//插入部门信息和工会信息
        }
        return dept;
    }

    /**
     * 插入一条部门信息
     * 仅往sys_dept表中插入数据
     *
     * @param dept
     */
    private void insertDept(Dept dept) {
        Dept pDept = deptMapper.selectById(dept.getPid());//上级部门信息
        if (dept.getDeptType() == 1) {
            //添加本级部门信息，设置同级部门的pid、pids等字段值
            dept.setLevel(pDept.getLevel());
            dept.setPids(pDept.getPids());
            dept.setPid(pDept.getPid());
        } else {
            //添加子节点工会信息，设置关联部门信息level、pid、pids字段
            dept.setLevel(pDept.getLevel() + 1);
            //根据Dept对象中的pid，设置pids属性
            deptSetPids(dept);
        }
        dept.setPDeptName(pDept.getSimplename());//上级部门名称
        dept.setFullname(dept.getSimplename());//部门全称等于部门简称
        dept.setEstablishDate(DateUtil.format(new Date(), "yyyyMMdd"));//创建日期
        deptMapper.insert(dept);
    }

    /**
     * 根据部门信息对象，设置pid和pids字段值
     *
     * @param dept
     */
    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("0");
        } else {
            int pid = dept.getPid();
            Dept temp = deptMapper.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "," + pid);
        }
    }

    /**
     * 根据部门信息对象，插入一条工会信息、部门信息
     * 往sys_dept表和mst_organization都插入数据
     *
     * @param dept
     */
    private void insertOrganizationAndDept(Dept dept) {
        MstOrganization mstOrganization = new MstOrganization();
        //绑定工会基本信息
        bindOrganizationByDept(dept, mstOrganization);
        dept.setAddr("");
        //插入sys_dept表一条数据
        insertDept(dept);
        mstOrganization.setDeptId(dept.getId());
        //生成工会编号规则：补零+sys_dept表主键；共六位数
        String unionNumber = getUnionNameById(dept.getId());
        mstOrganization.setUnionNumber(unionNumber);
        mstOrganizationDao.insert(mstOrganization);
    }

    /**
     * 根据部门信息对象，绑定工会信息
     * 仅用于插入部门、工会信息方法
     *
     * @param dept
     */
    private void bindOrganizationByDept(Dept dept, MstOrganization mstOrganization) {
        Dept pdept = deptMapper.selectById(dept.getPid());
        if (ToolUtil.isNotEmpty(pdept)) {
            mstOrganization.setPId(pdept.getId());
            mstOrganization.setPName(pdept.getFullname());
        }
        mstOrganization.setCreateTime(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        //TODO
//		mstOrganization.setCreateUser(ShiroKit.getUser().getName());
        //根据部门信息对象；绑定工会通用基本信息对象
        bindOrganizationByDeptUseUpdate(mstOrganization, dept);
    }

    /**
     * 更新方法
     * 根据传入的部门信息对象判断其是部门还是工会信息更新
     *
     * @param dept
     * @return
     */
    public Dept updateOrganizationAndDeptByModel(Dept dept) {
        if (dept.getDeptType() == 1) {
            //更新部门信息
            updateDept(dept);
        } else {
            //更新工会信息及部门信息
            updateOrganizationAndDept(dept);
        }
        return dept;
    }

    /**
     * 执行更新部门信息操作方法
     * 只更新sys_dept表
     *
     * @param dept
     */
    private void updateDept(Dept dept) {
        Dept oldDept = deptMapper.selectById(dept.getId());//从数据库中查询一条数据
        oldDept.setFullname(dept.getFullname());
        oldDept.setSimplename(dept.getSimplename());
        oldDept.setAddr(dept.getAddr());
        oldDept.setSort(dept.getSort());
        deptMapper.updateById(oldDept);
    }

    /**
     * 执行更新工会、部门信息操作方法
     * 更新sys_dept表和mst_organization表
     *
     * @param dept
     */
    private void updateOrganizationAndDept(Dept dept) {
        //根据当前部门信息对象获取关联的工会组织信息对象
        MstOrganization mstOrganization = mstOrganizationDao.getBeanByDeptId(dept.getId());
        //根据部门信息对象；绑定工会信息对象
        bindOrganizationByDeptUseUpdate(mstOrganization, dept);
        mstOrganizationDao.updateAllColumnById(mstOrganization);
        //更新部门信息
        dept.setAddr("");
        updateDept(dept);
    }

    /**
     * 根据部门信息对象；绑定工会信息对象
     * 工会信息插入、更新方法通用绑定数据方法
     *
     * @param mstOrganization
     * @param dept
     */
    private void bindOrganizationByDeptUseUpdate(MstOrganization mstOrganization, Dept dept) {
        mstOrganization.setSort(dept.getSort());
        mstOrganization.setUnionName(dept.getFullname());
        mstOrganization.setUnionSimpleName(dept.getSimplename());
        mstOrganization.setUnitName(dept.getAddr());//单位名称
        mstOrganization.setUpdateTime(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        //TODO
//		mstOrganization.setUpdateUser(ShiroKit.getUser().getName());
    }

    /**
     * 根据sys_dept表id，创建六位工会编号
     *
     * @param id
     * @return
     */
    public String getUnionNameById(Integer id) {
        if (id < 10) {
            return "00000" + id;
        }
        if (10 <= id && id < 100) {
            return "0000" + id;
        }
        if (100 <= id && id < 1000) {
            return "000" + id;
        }
        if (1000 <= id && id < 10000) {
            return "00" + id;
        }
        if (10000 <= id && id < 100000) {
            return "0" + id;
        }
        return id + "";
    }

    /**
     * 根据sys_dept表id,获取上级组织id
     * 返回pid
     *
     * @param id
     * @return
     */
    public Integer getPidById(Integer id) {
        if (ToolUtil.isEmpty(id)) {
            //TODO
//			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return deptDao.selectByOrgId(id);
    }

    /**
     * 根据sys_dept表id,获取当前组织所属等级
     *
     * @param
     * @return
     */
    public Integer getLevelByid(Integer id) {
        if (ToolUtil.isEmpty(id)) {
            //TODO
//			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return deptDao.getLevelByid(id);
    }

    /**
     * 根据用户orgId,获取局级部门id
     *
     * @param orgId
     * @return
     */
    public Integer getRootDeptIdByOrgId(Integer orgId) {
        Dept dept = deptDao.selectById(orgId);
        if (ToolUtil.isEmpty(dept)) {
            return null;
        }
        if (dept.getLevel() == 1) {
            return orgId;
        }
        String[] pids = dept.getPids().split(",");
        return deptDao.getDeptIdByOrgIdAndLevel(getIntegerArrayByStrings(pids), LEVEL[0]);
    }

    /**
     * 字符串数组转Integer类型数组
     *
     * @param pids
     * @return
     */
    private Integer[] getIntegerArrayByStrings(String[] pids) {
        Integer[] ids = new Integer[pids.length];
        for (int i = 0; i < pids.length; i++) {
            ids[i] = Integer.valueOf(pids[i]);
        }
        return ids;
    }

    /**
     * 获取当前下的组织树
     *
     * @param deptid
     * @return
     */
    public List<ZTreeNode> getZtree(Integer deptid) {
        String cacheName = CacheConstant.getDeptTreeCache(deptid);
        List<ZTreeNode> tree = this.deptDao.belowTree(deptid);
        return tree;
    }


    /**
     * 获取当下部门的tree列表
     *
     * @param pid
     * @return
     */
    public List<ZTreeNode> belowTreeNoDept(Integer pid) {
        String cacheName = CacheConstant.getNoDeptTreeCache(pid);

        if (redisUtils.exists(cacheName)) {
            return (List<ZTreeNode>) redisUtils.get(cacheName);
        }

        List<ZTreeNode> tree = this.deptDao.belowTreeNoDept(pid);
        return tree;
    }

}
