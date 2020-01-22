package com.gobestsoft.mamage.moudle.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.MD5Util;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * create by gutongwei
 * on 2018/9/27 上午11:15
 */
@Controller
public class AccountController extends BaseController {

    @Resource
    private UserMapper userMapper;


    private final String PREFIX = "account/";

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd";
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    @ResponseBody
    public Tip changePwd(@RequestParam("oldPwd") String oldPwd,
                         @RequestParam("newPwd") String newPwd,
                         @RequestParam("rePwd") String rePwd) throws BusinessException {
        if (!newPwd.equals(rePwd)) {
            return fail("两次输入密码不一致");
        }
        String userId = getLoginUser().getId();
        User user = userMapper.selectById(userId);
        String oldMd5 = MD5Util.encrypt(oldPwd + user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            if(userService.getIsWeak(newPwd)){
                return fail("新密码要求至少在 8位 以上，由大小写字母和阿拉伯数字组合，不得有两位连续相同。");
            }
            String newMd5 = MD5Util.encrypt(newPwd + user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            cleanLoginUser();
            return success();
        } else {
            return fail("原密码不正确");
        }
    }

    /**
     * 修改当前用户的密码--单页面
     */
    @RequestMapping(value = "/changePwdPage", method = RequestMethod.POST)
    @ResponseBody
    public Tip changePwdPage(@RequestParam("account") String account,
                         @RequestParam("oldPwd") String oldPwd,
                         @RequestParam("newPwd") String newPwd,
                         @RequestParam("userType") Integer userType,
                         @RequestParam("rePwd") String rePwd) throws BusinessException {
        if (!newPwd.equals(rePwd)) {
            return fail("两次输入密码不一致");
        }
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("account", account).eq("user_type", userType).eq("status", 1);
        User user = userMapper.selectList(wrapper).get(0);
        String oldMd5 = MD5Util.encrypt(oldPwd + user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            if(userService.getIsWeak(newPwd)){
                return fail("新密码要求至少在 8位 以上，由大小写字母和阿拉伯数字组合，不得有两位连续相同。");
            }
            String newMd5 = MD5Util.encrypt(newPwd + user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            cleanLoginUser();
            return success();
        } else {
            return fail("原密码不正确");
        }
    }

}
