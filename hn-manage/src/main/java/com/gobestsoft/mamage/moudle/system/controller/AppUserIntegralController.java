package com.gobestsoft.mamage.moudle.system.controller;

import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.gsp.model.AppIntegralUpdateDto;
import com.gobestsoft.common.modular.gsp.service.AppIntegralService;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 商城后台调用 积分对接接口
 */
@RestController
@RequestMapping("/jifen")
public class AppUserIntegralController extends BaseController {

    @Resource
    private AppIntegralService appIntegralService;
    @Resource
    private AppUserDao appUserDao;

    /**
     * 订单积分校验保存
     * type 0：减扣  1：增加
     * 减扣： 每笔订单只能扣除一次积分
     * 增加： 1.增加积分必须该订单号减扣过积分 2.增加积分数不能大于减扣数  3.每笔订单只能增加一次积分
     * @return
     */
    @PostMapping("/updateInteger")
    public Tip alterIntegral(AppIntegralUpdateDto dto) {
        String msg = appIntegralService.checkParams(dto);
        if(ToolUtil.isNotEmpty(msg)){
            return fail(msg);
        }
        AppUserEntity appUserByAuid = appUserDao.getAppUserByAuid(dto.getAuid());
        if (ToolUtil.isEmpty(appUserByAuid)) {
            return fail("当前用户信息有误");
        }

        msg = appIntegralService.checkDto(dto);
        if(ToolUtil.isNotEmpty(msg)){
            return fail(msg);
        }else {
            appIntegralService.insertIntegral(dto);
            return success(dto);
        }
    }

}
