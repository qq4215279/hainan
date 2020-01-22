package com.gobestsoft.admin.modular.external;

import com.gobestsoft.admin.config.properties.AdminProperties;
import com.gobestsoft.mamage.basic.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 外部跳转
 * create by gutongwei
 * on 2018/9/27 下午6:02
 */
@Controller
public class ExternalController extends BaseController {

    @Autowired
    private AdminProperties adminProperties;

    @RequestMapping("/php")
    public String php(@RequestParam("url") String url) {
        if (StringUtils.isEmpty(url)) {
            return REDIRECT + "/";
        }
        if (url.indexOf("?") > 0) {
            if (url.endsWith("&")) {
                url = url + "token=" + getToken();
            } else {
                url = url + "&token=" + getToken();
            }
        } else {
            url = url + "?token=" + getToken();
        }
        return REDIRECT + adminProperties.getPhpSite() + url;
    }

    @RequestMapping("/link")
    public String getPhpNew(@RequestParam("href") String href){

        if(StringUtils.isEmpty(href)){
            return REDIRECT + "/";
        } else {
            if(href.startsWith("http://") || href.startsWith("https://")){
                if(href.indexOf("?") > 0){
                    if (href.endsWith("&")) {
                        href = href + "token=" + getToken();
                    } else {
                        href = href + "&token=" + getToken();
                    }
                } else {
                    href = href + "?token=" + getToken();
                }

                return REDIRECT + href;
            }
        }

        if (href.indexOf("?") > 0) {
            if (href.endsWith("&")) {
                href = href + "token=" + getToken();
            } else {
                href = href + "&token=" + getToken();
            }
        } else {
            href = href + "?token=" + getToken();
        }
        return REDIRECT + adminProperties.getPhpSite() + href;

    }

}
