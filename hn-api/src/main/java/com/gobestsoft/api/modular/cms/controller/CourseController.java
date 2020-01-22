package com.gobestsoft.api.modular.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.cms.service.CourseService;


/**
 * create by gutongwei
 * on 2018/6/15 上午10:35
 */
@RequestMapping("/course")
@RestController
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    /**
     * 课程列表
     * create by gutongwei
     * on 2018/6/15
     *
     * @param type      【0：在线/普通课程】【1：系列课程】【2：女子学堂】
     * @param isCollect 是否是收藏
     * @return
     */
    @RequestMapping("/list")
    public BaseResult list(@RequestParam(value = "type", required = false) Integer type,
                           @RequestParam(value = "isCollect", required = false) Integer isCollect) {
        return success(courseService.courseList(type, getUserId(), isCollect == null ? false : isCollect == 1 ? true : false, getPageBounds()));
    }

    /**
     * e学堂首页
     *
     * @return
     */
    @RequestMapping(value = "/getHomePage")
    public BaseResult getHomePage() {
        return success(courseService.getHomePage(getUserId(), getToken()));
    }


}
