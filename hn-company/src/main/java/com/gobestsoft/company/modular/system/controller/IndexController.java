package com.gobestsoft.company.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.system.dao.MenuDao;
import com.gobestsoft.core.node.MenuNode;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.page.PageInfoBT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * create by gutongwei
 * on 2018/7/27 下午4:13
 * 首页代码
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    MenuDao menuDao;

    /**
     * 工作平台首页
     *
     * @return
     */
    @RequestMapping(value = {"", "/work"})
    public String index() {
        addCookie(COOKIES_WEBSITE_TYPE, COOKIES_WEBSITE_WORK);
        if (!isLogin()) {
            return REDIRECT + "/login";
        }
        return REDIRECT + "/welcome";
    }

    /**
     * 公司平台首页
     *
     * @return
     */
    @RequestMapping("/company")
    public String company() {
        addCookie(COOKIES_WEBSITE_TYPE, COOKIES_WEBSITE_COMPANY);
        if (!isLogin()) {
            return REDIRECT + "/company/login";
        }
        return REDIRECT + "/welcome";
    }

    /**
     * 母版页
     *
     * @return
     */
    @RequestMapping("/layout")
    public String layout() {
        return "layout/_layout";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        if (!isLogin()) {
            return REDIRECT + "/login";
        }

        String uid = getLoginUser().getId();
        if (uid == null) {
            addSessionAttribute(LOGIN_TIP, "该用户不存在，无法登陆");
            return REDIRECT + "/login";
        }
        //获取菜单列表
        List<Integer> roleList = getLoginUser().getRoleIds();
        if (roleList == null || roleList.size() == 0) {
            model.addAttribute("titles", new ArrayList<>());
        }else{
            List<MenuNode> menus = menuDao.getMenusByRoleIdsAndPlatform(roleList,"2");
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            model.addAttribute("titles", titles);
        }

        //获取用户头像
        model.addAttribute("user", getLoginUser());
        return "index";
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = {"/logout"})
    public String logout() {
        cleanLoginUser();
        if (!isWorkSite()) {
            return REDIRECT + "/company/login";
        }
        return REDIRECT + "/login";
    }


    /**
     * 错误页
     *
     * @return
     */
    @RequestMapping("/wrong")
    public String wrong() {
        return "500";
    }


    /**
     * 404页面
     *
     * @return
     */
    @RequestMapping("/notFind")
    public String notFound() {
        return "404";
    }

    //    案例
    @RequestMapping("/demo")
    public String demo1() {
        return "demo";
    }

    @RequestMapping("/demoSearch")
    public String demo() {
        return "demoSearch";
    }


    @RequestMapping("/getDemoSearchList")
    @ResponseBody
    public PageInfoBT getDemoSearchList() {
        Page page = defaultPage();
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < page.getSize(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "name" + i);
            map.put("age", "" + i);
            map.put("address", "address_" + i);
            result.add(map);
        }
        page.setRecords(result);
        page.setTotal(200);
        return super.packForBT(page);
    }

    @RequestMapping("/demoForm")
    public String demoForm() {
        return "demoForm";
    }

}
