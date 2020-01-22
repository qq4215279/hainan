package com.gobestsoft.common.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.common.modular.system.model.DeptTreeView;
import com.gobestsoft.core.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 部门dao
 *
 * @author gobestsoft
 * @date 2017年2月17日20:28:58
 */
@Repository
public interface DeptDao {


    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    public List<ZTreeNode> tree();

    public List<ZTreeNode> treebylevelTwo();

    List<ZTreeNode> existsBasicDeptTree();

    String selRecentNo();

    public List<Map<String, Object>> list(@Param("condition") String condition);

    /**
     * 查询当下部门的的下级部门
     *
     * @return
     * @author liushuwei
     * @date 2017年12月5日-下午1:25:30
     */
    public Integer selectGetDeptNo(Dept dept);

    public List<DeptTreeView> selectDeptByPid(@Param("page") Page<DeptTreeView> page, @Param("pDeptId") Integer pDeptId);

    public List<ZTreeNode> belowTree(@Param("pid") Integer pid);

    List<ZTreeNode> belowTree2(@Param("dname") String dname);

    public List<ZTreeNode> belowTreeNoDept(@Param("pid") Integer pid);

    /**
     * 根据登录用户orgId,获取当前工会的tree列表
     *
     * @return
     */
    public List<ZTreeNode> belowTreeByOrgId(@Param("parentId") Integer parentId, @Param("deptId") Integer deptId);

    /**
     * 获取组织数量
     */
    public Integer selectByDept(@Param("parentId") Integer parentId);

    /**
     * 获取会员数量
     */
    public List<Map<String, Object>> selectByMember(List<Integer> parentId);

    /**
     * 根据当前用户的orgId查询上级工会pid
     */
    public Integer selectByOrgId(@Param("orgId") Integer orgId);

    /**
     * 根据 pid 查询下级级工会 会员数 绑定会员数
     */
    public LinkedList<Map<String, Object>> selectByPid(@Param("pid") Integer pid);


    /**
     * 查询所有工会信息
     */
    public List<ZTreeNode> deptTree();

    /**
     * 更新部门编号
     *
     * @return
     */
    public int updateDeptNo();

    /**
     * 查询等级
     *
     * @return
     */
    public Integer getLevelByid(@Param("id") Integer id);

    /**
     * 根据工会名称查询工会信息
     *
     * @param simplename
     * @return
     */
    public Dept selectBySimplename(@Param("simplename") String simplename);

    Dept selectById(@Param("id") int id);

    /**
     * 查询审核工会的pids
     */
    public Map<String, Object> deptByPids(Integer pid);

    /**
     * 新增工会
     */
    public void insertDept(Dept dept);

    /**
     * 根据id,查询工会全称
     *
     * @param id
     * @return
     */
    public String selectFullNameById(@Param("id") Integer id);

    /**
     * 根据sys_dept表id数组，获取对应层级的部门id
     *
     * @param ids
     * @param level
     * @return
     */
    Integer getDeptIdByOrgIdAndLevel(@Param("ids") Integer[] ids, @Param("level") Integer level);

    /**
     * 根据登录用户orgId,获取当前工会的tree列表
     *
     * @param orgId
     * @return
     */
    public List<ZTreeNode> belowTreeByInfo(@Param("orgId") Integer orgId);

    Dept selectDeptsByName(@Param("orgName") String orgName);

}
