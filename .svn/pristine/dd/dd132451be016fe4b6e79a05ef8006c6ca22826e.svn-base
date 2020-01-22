package com.gobestsoft.company.modular.system.controller;

import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.core.node.ZTreeNode;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.system.service.DeptService;
import com.gobestsoft.mamage.moudle.system.wrapper.DeptWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author gobestsoft
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Resource
    DeptDao deptDao;

    @Resource
    DeptMapper deptMapper;

    @Resource
    DeptService deptService;


    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add";
    }

    /**
     * 跳转到修改部门
     */
//    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptMapper.selectById(deptId);
        Dept pDept = deptMapper.selectById(dept.getPid());
        model.addAttribute(dept);
        model.addAttribute("pName", pDept.getSimplename());
        return PREFIX + "dept_edit";
    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree(@RequestParam(value = "existBasicDept", required = false) Integer existBasicDept) {
        List<ZTreeNode> tree = null;
        if (existBasicDept != null && existBasicDept == 1) {
            tree = this.deptDao.existsBasicDeptTree();
        } else {
            tree = this.deptDao.tree();
        }
        return tree;
    }

    /**
     * 通过名字获取tree列表
     */
    @RequestMapping(value = "/treebyName")
    @ResponseBody
    public List<ZTreeNode> treeByName(@RequestParam("keyword") String keyword){
        Dept dept = new Dept();
        dept.setSimplename(keyword);
        Dept deptInfo = deptMapper.selectOne(dept);
        Integer deptid = deptInfo.getId();
        return deptService.getZtree(deptid);
    }

    /**
     * 获取当下部门的tree列表
     */
    @RequestMapping(value = "/belowTree")
    @ResponseBody
    public List<ZTreeNode> belowTree() {
        Integer deptid = getLoginUser().getDeptId();
        List<ZTreeNode> tree = this.deptDao.belowTree(deptid);
        return tree;
    }

    /**
     * 获取当下部门的tree列表
     */
    @RequestMapping(value = "/belowTreeNoDept")
    @ResponseBody
    public List<ZTreeNode> belowTreeNoDept() {
        Integer deptid = getLoginUser().getDeptId();
        Integer pid = this.deptDao.selectByOrgId(deptid);
        List<ZTreeNode> tree = this.deptDao.belowTreeNoDept(pid);
        return tree;
    }

    /**
     * 根据登录用户orgId,获取当前工会的tree列表
     *
     * @return
     */
    @RequestMapping(value = "/belowTreeByOrgId")
    @ResponseBody
    public List<ZTreeNode> belowTreeByOrgId(@RequestParam(required = false) Integer parentId) {
        List<ZTreeNode> tree = new ArrayList<>();

        tree = deptDao.belowTreeByOrgId(parentId,getLoginUser().getDept().getLevel()==1?1:getLoginUser().getDeptId());
        if(ToolUtil.isEmpty(tree)){
            return null;
        }

        List<Integer> idlist = new ArrayList<>();
        tree.forEach(t->{
            idlist.add(t.getId());
            Integer count = deptDao.selectByDept(t.getId());
            if(t.getId()==1){
                t.setIsOpen(true);
            }
            if(count==0){
                t.setIsParent(false);
                t.setIsOpen(false);
            }
            t.setName(t.getName()+"(组织数:"+count+")");
        });
        List<Map<String, Object>> list = deptDao.selectByMember(idlist);


        for(ZTreeNode t:tree){
            int mcnt=0;
            int id = t.getId();
            for(Map m:list){
                int dept_id = Integer.valueOf(m.get("dept_id") + "");

                if(id==dept_id){

                    mcnt = Integer.valueOf(m.get("cnt")+"");
                    break;
                }
            }
            t.setName(t.getName()+"(会员数:"+mcnt+")");
        }

        return tree;
    }

    /**
     * 新增部门
     */
//    @BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Dept dept) throws BusinessException {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);
        Dept pDept = deptMapper.selectById(dept.getPid());
        if (pDept.getSpecialFlg() != null && pDept.getSpecialFlg() == 1) {
            dept.setLevel(pDept.getLevel() + 2);
        } else {
            dept.setLevel(pDept.getLevel() + 1);
        }
        dept.setPDeptNo(pDept.getDeptNo());
        dept.setPDeptName(pDept.getSimplename());

        Object newDept = this.deptMapper.insert(dept);

        // 更新部门编号
        this.deptDao.updateDeptNo();
        return newDept;
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.deptDao.list(condition);
        return super.warpObject(new DeptWrapper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
//    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptMapper.selectById(deptId);
    }

    /**
     * 修改部门
     */
//    @BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/update")
//    @Permission
    @ResponseBody
    public Object update(Dept dept) throws BusinessException {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        deptSetPids(dept);
//        Dept pDept = deptMapper.selectById(dept.getPid());
//        dept.setLevel(pDept.getLevel()+1);
//        dept.setPDeptNo(pDept.getDeptNo());
//        dept.setPDeptName(pDept.getSimplename());
        deptMapper.updateById(dept);
        return success();
    }

    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptMapper.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }

    /**
     * 部门信息、工会信息的简便数据的插入保存
     *
     * @param dept
     * @return
     */
//    @BussinessLog(value = "添加部门或工会", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "easyAdd")
    @ResponseBody
    public Dept easyAdd(Dept dept) throws BusinessException {
        if (ToolUtil.isEmpty(dept) && ToolUtil.isEmpty(dept.getSimplename()) && ToolUtil.isEmpty(dept.getPid())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return deptService.insertOrganizationAndDeptByModel(dept);
    }

    /**
     * 根据前台传入的部门类型来执行当前是编辑部门信息还是工会信息的更新操作
     *
     * @param dept
     * @return
     */
//    @BussinessLog(value = "编辑部门或工会", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "easyEdit")
    @ResponseBody
    public Dept easyEdit(Dept dept) throws BusinessException {
        if (ToolUtil.isEmpty(dept) && ToolUtil.isEmpty(dept.getSimplename()) && ToolUtil.isEmpty(dept.getPid())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return deptService.updateOrganizationAndDeptByModel(dept);
    }


}
