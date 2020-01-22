package com.gobestsoft.mamage.moudle.other;

import com.gobestsoft.common.modular.system.model.DictModel;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.system.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * create by gutongwei
 * on 2018/9/15 上午10:47
 */
@Controller
@RequestMapping("/third-party")
public class ThirdPartyController extends BaseController {

    @Resource
    DictService dictService;

    /**
     * 获取登录的用户信息
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/getLoginUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Tip getLoginUserInfo(@RequestParam(value = "token", required = false) String token) {
        if (StringUtils.isEmpty(token)) {
            return fail("请传递用户TOKEN");
        }
        LoginUser loginUser = getLoginUser(token);
        if (loginUser == null) {
            return fail("未查询到登录信息");
        }
        return tip(200, "", loginUser);
    }


    /**
     * 获取字典
     *
     * @param groupCode
     * @return
     */
    @RequestMapping(value = "/getDict")
    @ResponseBody
    public Tip getDict(@RequestParam(value = "groupCode", required = false) String groupCode) {
        if (StringUtils.isEmpty(groupCode)) {
            return fail("字典编码未传递");
        }
        DictModel dictModel = dictService.getDictionary(groupCode);
        if (dictModel == null || dictModel.getDict() == null || dictModel.getDict().size() == 0) {
            return fail("字典不存在");
        }
        return tip(200, "", dictModel.getDict());
    }


}
