package com.gobestsoft.mamage;

import com.gobestsoft.mamage.basic.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by gutongwei
 * on 2018/8/31 下午9:10
 */
@Controller
@RequestMapping("/we")
public class WeController extends BaseController {

    @RequestMapping("")
    public String we() {
        return "we";
    }
}
