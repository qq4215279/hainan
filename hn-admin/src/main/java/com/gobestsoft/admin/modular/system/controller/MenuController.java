package com.gobestsoft.admin.modular.system.controller;

import com.gobestsoft.common.modular.system.dao.MenuDao;
import com.gobestsoft.common.modular.system.mapper.MenuMapper;
import com.gobestsoft.common.modular.system.model.Menu;
import com.gobestsoft.core.node.ZTreeNode;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.constant.state.MenuStatus;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.moudle.system.service.MenuService;
import com.gobestsoft.mamage.moudle.system.service.RoleService;
import com.gobestsoft.mamage.moudle.system.wrapper.MenuWrapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 * @author gobestsoft
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/system/menu/";

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuDao menuDao;

    @Resource
    MenuService menuService;

    @Autowired
    private RoleService roleService;


    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu";
    }

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping(value = "/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add";
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @RequestMapping(value = "/menu_edit/{menuId}")
    public String menuEdit(@PathVariable Integer menuId, Model model) {
        if (menuId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = this.menuMapper.selectById(menuId);
        model.addAttribute("menu", menu);
        model.addAttribute("pcodeName", ConstantFactory.me().getMenuNameByCode(menu.getPcode()));
        return PREFIX + "menu_edit";
    }


    /**
     * 修该菜单
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Tip edit(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            return new Tip(500, result.getFieldError().getDefaultMessage(), null);
        }
        if (menuService.existsMenuCode(menu.getCode(), menu.getId())) {
            return new Tip(500, "编号已存在", null);
        }
        if (!setLevel(menu)) {
            return fail("父级菜单不存在");
        }
        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.menuMapper.updateById(menu);
        return success();
    }

    /**
     * 获取菜单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {

        List<Map<String, Object>> menus = this.menuDao.selectMenus(menuName, NumberUtils.toInt(level));
        return super.warpObject(new MenuWrapper(menus));
    }

    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Tip add(@Valid Menu menu, BindingResult result) {
        if (result.hasErrors()) {
            return new Tip(500, result.getFieldError().getDefaultMessage(), null);
        }
        if (menuService.existsMenuCode(menu.getCode(), null)) {
            return new Tip(500, "编号已存在", null);
        }

        if (!setLevel(menu)) {
            return fail("父级菜单不存在");
        }
        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.menuMapper.insert(menu);
        return success();
    }

    /**
     * 设置菜单等级
     *
     * @param menu
     */
    private boolean setLevel(Menu menu) {
        if ("0".equals(menu.getPcode())) {
            menu.setLevels(1);
        } else {
            Menu parentMenu = menuMapper.getMenuByCode(menu.getPcode());
            if (parentMenu == null) {
                return false;
            }
            menu.setLevels(parentMenu.getLevels() + 1);
        }
        return true;
    }

    /**
     * 删除菜单
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Tip remove(@RequestParam Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        this.menuService.delMenuContainSubMenus(menuId);
        return success();
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{menuId}")
    @ResponseBody
    public Tip view(@PathVariable Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.menuMapper.selectById(menuId);
        return success();
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuDao.menuTreeList();
        return roleTreeList;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuDao.menuTreeList();
        roleTreeList.add(ZTreeNode.createMenuParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
        return roleService.menuTreeListByRoleId(roleId);
    }

}
