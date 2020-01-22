package com.gobestsoft.mamage.moudle.system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gobestsoft.common.modular.dao.mapper.AppBannerMapper;
import com.gobestsoft.common.modular.dao.model.AppBannerPojo;
import com.gobestsoft.common.modular.homepage.dao.BannerDao;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;


/**
 * 【APP轮播图】服务类
 *
 * @author xiashasha
 * @date 2018-09-03 17:00
 */
@Service
@Transactional
public class AppBannerService {


    @Autowired
    BannerDao bannerDao;
    @Autowired
    AppBannerMapper appBannerMapper;
    @Autowired
    protected ManageProperties manageProperties;
    
    
    /**
     * Banner启用操作
     * @param bannerIds
     */
    public void doOnline(String[] bannerIds) {
    	updateStatusByBannerIds(bannerIds,"0");
    }


    /**
     * Banner禁用操作
     * @param bannerIds
     */
    public void doOffline(String[] bannerIds) {
    	updateStatusByBannerIds(bannerIds,"1");
    }

    
    /**
     * 合并banner的启用、禁用共同的代码
     * @param bannerIds app_banner表id
     * @param status 状态值  0：下线，1：上线
     */
    private void updateStatusByBannerIds(String[] bannerIds,String status){
    	// 循环信息id
        for (String id : bannerIds) {
            Integer bannerId = Integer.valueOf(id);
            if (bannerId == null || bannerId == 0) {
                throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
            }
            // 根据ID 查询当前数据是否存在
            AppBannerPojo appBanner = appBannerMapper.selectById(bannerId);
            if (ToolUtil.isEmpty(appBanner)) {
                throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
            }
            // 附值更新数据
            appBanner.setStatus(Integer.valueOf(status));
            appBannerMapper.updateById(appBanner);
        }
    }

    /**
     * Banner 批量删除操作
     *
     * @param bannerIds
     */
    public void doDelete(String[] bannerIds) {
        // 循环信息id
        for (String id : bannerIds) {
            Integer bannerId = Integer.valueOf(id);
            if (bannerId == null || bannerId == 0) {
            	 throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
            }

            // 根据ID 查询当前数据是否存在
            AppBannerPojo appBanner = appBannerMapper.selectById(bannerId);
            if (ToolUtil.isEmpty(appBanner)) {
            	 throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
            }
            appBannerMapper.deleteById(bannerId);
        }
    }
    


   
}
