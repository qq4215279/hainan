package com.gobestsoft.mamage.moudle.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.common.modular.system.dao.MenuDao;
import com.gobestsoft.common.modular.system.mapper.MenuMapper;
import com.gobestsoft.common.modular.system.model.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单服务
 *
 * @author gobestsoft
 * @date 2017-05-05 22:20
 */
@Service
public class MenuService {

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuDao menuDao;


    /**
     * 删除菜单
     *
     * @author gobestsoft
     * @Date 2017/5/5 22:20
     */
    public void delMenu(Integer menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuDao.deleteRelationByMenu(menuId);
    }

    /**
     * 删除菜单包含所有子菜单
     *
     * @author gobestsoft
     * @Date 2017/6/13 22:02
     */
    public void delMenuContainSubMenus(Integer menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getId().intValue());
        }
    }


    /**
     * 是否存在code
     *
     * @param code
     * @param id
     * @return
     */
    public boolean existsMenuCode(String code, Integer id) {
        Wrapper wrapper = new EntityWrapper().eq("code", code);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return menuMapper.selectCount(wrapper) > 0;
    }


}
