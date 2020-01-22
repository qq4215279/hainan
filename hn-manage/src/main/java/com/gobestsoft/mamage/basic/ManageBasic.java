package com.gobestsoft.mamage.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.core.basic.HttpBasic;
import com.gobestsoft.core.reids.RedisCacheModel;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.FileUtil;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.constant.Const;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.model.LoginUserRole;
import com.gobestsoft.mamage.moudle.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create by gutongwei
 * on 2018/8/3 上午9:19
 */
@Component
@Slf4j
public class ManageBasic extends HttpBasic {

    @Autowired
    protected ManageProperties manageProperties;

    @Autowired
    protected UserService userService;

    @Autowired
    protected RedisUtils redisUtils;

    private final int REMEMBER_TIME = 7 * 24 * 60 * 60;

    private final String SESSION_LOGIN_USER_NAME = "loginUser";

    private final String TOKEN_NAME = "token";


    /**
     * 网址类型
     */
    protected final String COOKIES_WEBSITE_TYPE = "website_type";
    /**
     * 工作平台
     */
    protected final String COOKIES_WEBSITE_WORK = "WORK";
    /**
     * 公司平台
     */
    protected final String COOKIES_WEBSITE_COMPANY = "COMPANY";


    /**
     * 是否登录状态
     *
     * @return
     */
    protected boolean isLogin() {
        if (getLoginUser() != null) {
            return true;
        }
        return false;
    }


    /**
     * 获取登录用户
     *
     * @return
     */
    public LoginUser getLoginUser() {
        LoginUser loginUser = (LoginUser) getSession().getAttribute(SESSION_LOGIN_USER_NAME);
        if (loginUser == null) {
            String token = getCookieValue(TOKEN_NAME);
            if (StringUtils.isNotEmpty(token) && redisUtils.exists(token)) {
                loginUser = (LoginUser) redisUtils.get(token);
            }
        }
        return loginUser;
    }


    /**
     * 获取登录用户
     *
     * @return
     */
    protected LoginUser getLoginUser(String token) {
        LoginUser loginUser = (LoginUser) getSession().getAttribute(SESSION_LOGIN_USER_NAME);
        if (loginUser == null) {
            if (StringUtils.isNotEmpty(token) && redisUtils.exists(token)) {
                loginUser = (LoginUser) redisUtils.get(token);
            }
        }
        return loginUser;
    }

    /**
     * 判断当前用户是否是超级管理员
     */
    public boolean isAdmin() {
        return hasRole(Const.ADMIN_ROLE_ID);
    }


    /**
     * @param roleId 角色id
     * @return 属于该角色：true，否则false
     */
    public boolean hasRole(Integer roleId) {
        Set<LoginUserRole> roles = getLoginUser().getRoles();
        for (LoginUserRole role : roles) {
            if (roleId == role.getRoleId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
     *
     * @param permission 权限
     * @return 属于该角色：true，否则false
     */
    public boolean hasPermission(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        LoginUser loginUser = getLoginUser();
        if (loginUser != null) {
            for (String p : loginUser.getPermissions()) {
                if (p.equals(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取token
     *
     * @return
     */
    protected String getToken() {
        return getCookieValue(TOKEN_NAME);
    }

    /**
     * 获取当前用户的部门数据范围的集合
     */
    public List<Integer> getDeptDataScope() {
        Integer deptId = getLoginUser().getDeptId();
        List<Integer> subDeptIds = ConstantFactory.me().getSubDeptId(deptId);
        subDeptIds.add(deptId);
        return subDeptIds;
    }

    /**
     * 缓存登录用户
     *
     * @param loginUser
     * @param remember
     */
    protected void cacheLoginUser(LoginUser loginUser, boolean remember) {
        String token = UUID.randomUUID().toString().replace("-", "");
        Cookie cookie = new Cookie(TOKEN_NAME, token);
        if (remember) {
            cookie.setMaxAge(REMEMBER_TIME);
        } else {
            cookie.setMaxAge(-1);
        }
        getHttpServletResponse().addCookie(cookie);
        redisUtils.set(token, loginUser);
        getSession().setAttribute(SESSION_LOGIN_USER_NAME, loginUser);
    }

    /**
     * 清除登录
     */
    protected void cleanLoginUser() {
        Arrays.asList(getHttpServletRequest().getCookies()).forEach(c -> {
            if (TOKEN_NAME.equals(c.getName())) {
                c.setValue(null);
                getHttpServletResponse().addCookie(c);
            }
        });
        getSession().setAttribute(SESSION_LOGIN_USER_NAME, null);
    }

    /**
     * 是否是工作平台
     *
     * @return
     */
    protected boolean isWorkSite() {
        AtomicBoolean isWork = new AtomicBoolean(true);
        Arrays.asList(getHttpServletRequest().getCookies()).forEach(c -> {
            if (COOKIES_WEBSITE_TYPE.equals(c.getName()) && COOKIES_WEBSITE_COMPANY.equals(c.getValue())) {
                isWork.set(false);
            }
        });
        return isWork.get();
    }


    /**
     * 读取setting.json 获取配置文件属性
     *
     * @param key
     * @return
     */
    protected Object getSetting(String key) {
        RedisCacheModel cacheModel = (RedisCacheModel) redisUtils.get(CacheConstant.SETTING_CONFIG);
        Map<String,Object> result = null;
        if (cacheModel == null || cacheModel.isExpired()) {
            try {
                File configFile = ResourceUtils.getFile("classpath:setting.json");
                String appConfigJson = FileUtil.getFileContent(configFile);
                result = JSON.parseObject(appConfigJson,Map.class);
                cacheModel = new RedisCacheModel(appConfigJson, (Integer) result.get("cacheSeconds"));
                redisUtils.set(CacheConstant.SETTING_CONFIG, cacheModel);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            result = JSON.parseObject(cacheModel.getData().toString());
        }
        return result.get(key);
    }

}
