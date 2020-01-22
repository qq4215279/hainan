package com.gobestsoft.common.modular.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.system.model.Menu;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * 根据code获取菜单
     *
     * @param code
     * @return
     */
    Menu getMenuByCode(@Param("code") String code);

}