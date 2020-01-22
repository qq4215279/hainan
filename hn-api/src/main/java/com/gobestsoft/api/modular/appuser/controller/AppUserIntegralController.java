package com.gobestsoft.api.modular.appuser.controller;

import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.common.modular.gsp.model.AppIntegralUpdateDto;
import com.gobestsoft.common.modular.gsp.service.AppIntegralService;
import com.gobestsoft.core.util.ToolUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/jifen")
public class AppUserIntegralController extends BaseController {

    @Resource
    private AppIntegralService appIntegralService;

    /**
     * 订单积分校验保存
     * type 0：减扣  1：增加
     * 减扣： 每笔订单只能扣除一次积分
     * 增加： 1.增加积分必须该订单号减扣过积分 2.增加积分数不能大于减扣数  3.每笔订单只能增加一次积分
     * @return
     */
    @PostMapping("/updateInteger")
    public BaseResult alterIntegral(AppIntegralUpdateDto dto) {
        String msg = appIntegralService.checkParams(dto);
        if(ToolUtil.isNotEmpty(msg)){
            return fail(msg);
        }
        if (!ToolUtil.equals(getUserId(), dto.getAuid())) {
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
