package com.gobestsoft.api.modular.cms.controller;

import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.cms.service.CkyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创客园
 * <p>
 * create by gutongwei
 * on 2018/6/5 下午4:09
 */
@RestController
@RequestMapping("/cky")
public class CkyController extends BaseController {

    @Autowired
    private CkyService ckyService;


    /**
     * 工作室详细
     * create by gutongwei
     * on 2018/6/13
     *
     * @param studioId
     * @return
     */
    @RequestMapping("/studioDetail")
    public BaseResult studioDetail(@RequestParam("studioId") int studioId) {
        return success(ckyService.studioDetail(studioId));
    }

    /**
     * 工作室列表
     * create by gutongwei
     * on 2018/6/14
     *
     * @return
     */
    @RequestMapping("/studios")
    public BaseResult studios() {
        return success(ckyService.studios(getPageBounds()));
    }


}
