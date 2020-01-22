package com.gobestsoft.admin.modular.system.controller;

import com.baidu.ueditor.ActionEnter;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//import com.gobestsoft.core.config.ExtConfig;

/**
 * <p>描述: 上飞党建后台</p>
 * <p>版权所有: 版权所有(C)2017-2017</p>
 * <p>公 司: 建朗科技</p>
 * <p>版本1.0: 2017年9月27日</p>
 *
 * @author
 * @version 1.0
 */
@Controller
@Slf4j
public class UEditorController {

    @Autowired
    private ManageProperties manageProperties;

    @RequestMapping(value = "/static/ueditor/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        System.out.println("ContextPath:" + request.getSession().getServletContext().getContextPath());
        try {
            String exec = new ActionEnter(request, "", manageProperties.getFileUploadPath(), manageProperties.getUeditorConfigFile()).exec();
            log.info("EXEC:" + exec);
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
