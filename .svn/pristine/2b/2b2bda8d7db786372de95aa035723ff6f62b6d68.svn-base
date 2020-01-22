package com.gobestsoft.mamage.constant.factory;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.modular.dao.mapper.CmsCategoryMapper;
import com.gobestsoft.common.modular.dao.mapper.MstDistrictMapper;
import com.gobestsoft.common.modular.dao.model.CmsCategoryPojo;
import com.gobestsoft.common.modular.dao.model.MstDistrictPojo;
import com.gobestsoft.common.modular.system.mapper.*;
import com.gobestsoft.common.modular.system.model.*;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.support.StrKit;
import com.gobestsoft.core.util.NumUtil;
import com.gobestsoft.core.util.SpringContextHolder;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.constant.state.ManagerStatus;
import com.gobestsoft.mamage.constant.state.MenuStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量的生产工厂
 *
 * @author gobestsoft
 * @date 2017年2月13日 下午10:55:21
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {


    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);
    private RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);

    private CmsCategoryMapper cmsCategoryMapper = SpringContextHolder.getBean(CmsCategoryMapper.class);
    private NoticeMapper noticeMapper = SpringContextHolder.getBean(NoticeMapper.class);
    private MstDistrictMapper districtMapper = SpringContextHolder.getBean(MstDistrictMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author gobestsoft
     * @Date 2017/5/9 23:41
     */
    @Override
    public String getUserNameById(String userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author gobestsoft
     * @date 2017年5月16日21:55:371
     */
    @Override
    public String getUserAccountById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }


    @Override
    public Role getSingleRole(Integer roleId) {
        return roleMapper.selectById(roleId);
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    public String getRoleName(String roleIds) {
        Integer[] roles = NumUtil.toIntegerArray(roleIds.split(","));
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            Role roleObj = roleMapper.selectById(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "顶级";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
    public String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    /**
     * 根据当前登录用户的orgid,获取上级工会对象
     */
    @Override
    public Dept getPUnionPojo(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (ToolUtil.isNotEmpty(dept) && dept.getPid() != 0) {
            Dept pdept = deptMapper.selectById(dept.getPid());
            if (ToolUtil.isNotEmpty(pdept)) {
                return pdept;
            }
        }
        return new Dept();
    }

    /**
     * 获取菜单的名称们(多个)
     */
    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = NumUtil.toIntegerArray(menuIds.split(","));
        StringBuilder sb = new StringBuilder();
        for (int menu : menus) {
            Menu menuObj = menuMapper.selectById(menu);
            if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        } else {
            Menu menu = menuMapper.selectById(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        } else if ("0".equals(code)) {
            return "顶级";
        } else {
            Menu param = new Menu();
            param.setCode(code);
            Menu menu = menuMapper.selectOne(param);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取字典名称
     */
    @Override
    public String getDictName(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        } else {
            Dict dict = dictMapper.selectById(dictId);
            if (dict == null) {
                return "";
            } else {
                return dict.getName();
            }
        }
    }

    /**
     * 获取通知标题
     */
    @Override
    public String getNoticeTitle(Integer dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            return "";
        } else {
            Notice notice = noticeMapper.selectById(dictId);
            if (notice == null) {
                return "";
            } else {
                return notice.getTitle();
            }
        }
    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, Integer val) {
        Dict temp = new Dict();
        temp.setName(name);
        Dict dict = dictMapper.selectOne(temp);
        if (dict == null) {
            return "";
        } else {
            Wrapper<Dict> wrapper = new EntityWrapper<>();
            wrapper = wrapper.eq("pid", dict.getId());
            List<Dict> dicts = dictMapper.selectList(wrapper);
            for (Dict item : dicts) {
                if (item.getSort() != null && item.getSort().equals(val)) {
                    return item.getName();
                }
            }
            return "";
        }
    }

    /**
     * 获取性别名称
     */
    @Override
    public String getSexName(Integer sex) {
        return getDictsByName("性别", sex);
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }

    /** 2017-11-30 caoy  modify ---- start */
    /**
     * 查询字典
     */
    @Override
    public List<Dict> findInDict(String groupCode) {
        if (StringUtils.isNotEmpty(groupCode)) {
            if (redisUtils.exists(CacheConstant.getAllDict()+groupCode)) {
                return (List<Dict>) redisUtils.get(CacheConstant.getAllDict()+groupCode);
            }
            EntityWrapper<Dict> wrapper = new EntityWrapper<>();
            List<Dict> dicts = dictMapper.selectList(wrapper.eq("group_code", groupCode).ne("code", "").orderBy("sort"));
            if (dicts != null) {
                redisUtils.set(CacheConstant.getAllDict()+groupCode, dicts);
            }
            return dicts;
        }
        return null;
    }

    @Override
    public Dict findInDictName(String groupCode, String code) {
        if (StringUtils.isEmpty(groupCode) || StringUtils.isEmpty(code)) {
            return null;
        }
        String cacheName = CacheConstant.getDictSingleName(groupCode, code);
        if (redisUtils.exists(cacheName)) {
            return (Dict) redisUtils.get(cacheName);
        }

        Dict param = new Dict();
        param.setGroupCode(groupCode);
        param.setCode(code);
        Dict dict = dictMapper.selectOne(param);
        if (dict == null) {
            return null;
        } else {
            redisUtils.set(cacheName, dict);
            return dict;
        }
    }

    /**
     * 根据categoryId 查询分类（工会资讯）
     *
     * @param categoryId
     * @return
     */
    @Override
    public CmsCategoryPojo getCategoryById(Integer categoryId) {
        if (ToolUtil.isEmpty(categoryId) || categoryId == 0) {
            return null;
        } else {
            CmsCategoryPojo pojo = cmsCategoryMapper.selectById(categoryId);
            return pojo;
        }
    }

    /**
     * 查询所有资讯分类类别 （工会资讯）
     *
     * @return
     */
    @Override
    public List<CmsCategoryPojo> getCategoryList() {
        List<CmsCategoryPojo> pojoList = cmsCategoryMapper.selectList(new EntityWrapper<CmsCategoryPojo>()
                .eq("del_flg", 0).eq("pid", 5));
        return pojoList;
    }

    /** 2017-11-30 caoy  modify ---- end */

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    @Override
    public String getCacheObject(String para) {
        //TODO
//        return LogObjectHolder.me().get().toString();
        return null;
    }

    /**
     * 获取子部门id
     */
    @Override
    public List<Integer> getSubDeptId(Integer deptid) {
        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + deptid + "]%");
        List<Dept> depts = this.deptMapper.selectList(wrapper);

        ArrayList<Integer> deptids = new ArrayList<>();

        if (depts != null && depts.size() > 0) {
            for (Dept dept : depts) {
                deptids.add(dept.getId().intValue());
            }
        }

        return deptids;
    }

    /**
     * 获取所有父部门id
     */
    @Override
    public List<Integer> getParentDeptIds(Integer deptid) {
        Dept dept = deptMapper.selectById(deptid);
        String pids = dept.getPids();
        String[] split = pids.split(",");
        ArrayList<Integer> parentDeptIds = new ArrayList<>();
        for (String s : split) {
            parentDeptIds.add(Integer.valueOf(StrKit.removeSuffix(StrKit.removePrefix(s, "["), "]")));
        }
        return parentDeptIds;
    }

    /**
     * 根据层级获得区域集合
     */
    @Override
    public List<MstDistrictPojo> getDistrictByLevel(Integer level) {
        if (ToolUtil.isEmpty(level)) {
            return null;
        }

        List<MstDistrictPojo> mstDistrictPojoList = districtMapper.selectList(new EntityWrapper<MstDistrictPojo>()
                .eq("level", level));
        return mstDistrictPojoList;
    }

    /**
     * 根据ID获得区域集合
     */
    @Override
    public List<MstDistrictPojo> getDistrictByPid(String pid) {
        if (ToolUtil.isEmpty(pid)) {
            return null;
        }

        List<MstDistrictPojo> mstDistrictPojoList = districtMapper.selectList(new EntityWrapper<MstDistrictPojo>()
                .eq("p_district_code", pid));
        return mstDistrictPojoList;
    }

    /**
     * 根据城市CODE获得区域
     */
    @Override
    public MstDistrictPojo getDistrictByCityCode(String cityCode) {
        if (ToolUtil.isEmpty(cityCode)) {
            return null;
        }

        List<MstDistrictPojo> mstDistrictPojoList = districtMapper.selectList(new EntityWrapper<MstDistrictPojo>()
                .eq("city_code", cityCode).eq("level", 2));
        if (ToolUtil.isEmpty(mstDistrictPojoList)) {
            return null;
        }
        return mstDistrictPojoList.get(0);
    }
}
