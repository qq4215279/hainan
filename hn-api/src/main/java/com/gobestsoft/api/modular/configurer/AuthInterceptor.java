package com.gobestsoft.api.modular.configurer;

import com.gobestsoft.api.modular.appuser.service.AppUserService;
import com.gobestsoft.api.modular.basic.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户身份验证 验证token
 *
 * @author gutongwei
 * <p>
 * 2017年11月23日
 */
public class AuthInterceptor extends BaseHandlerInterceptor {

    @Autowired
    private AppUserService appUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (super.preHandle(request, response, handler)) {
            String token = request.getHeader("token");
            if (!StringUtils.isEmpty(token)) {
                if (appUserService.validUserInfoByToken(token)) {
                    return true;
                }
                writeResultCode(response, BaseResult.ResultCode.ERROR100_2);
            } else {
                writeResultCode(response, BaseResult.ResultCode.ERROR100);
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
