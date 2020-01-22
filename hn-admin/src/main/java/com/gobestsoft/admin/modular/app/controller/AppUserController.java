package com.gobestsoft.admin.modular.app.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.cms.model.Article;
import com.gobestsoft.common.modular.dao.model.CmsArticlePojo;
import com.gobestsoft.common.modular.dept.model.DeptMember;
import com.gobestsoft.common.modular.dept.model.PersonInfo;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.constant.factory.ConstantFactory;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.app.service.AppUserService;
import com.gobestsoft.mamage.moudle.cms.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * appuser控制器
 *
 * Created by li on 2018/8/30.
 */
@Controller
@RequestMapping("/appUser")
public class AppUserController extends BaseController{

    private String PREFIX = "/app/appuser/";

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ConstantFactory constantFactory;

    /**
     * 跳转到appuser首页
     */
    @RequestMapping("")
    public String index(Model model){
    	return PREFIX + "appUser";
    }

    /**
     * 跳转到详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/detail")
    public String detail(@RequestParam("auid") String auid,Model model){
    	if (ToolUtil.isEmpty(auid)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	HashMap<String, Object> appUser = appUserService.appUserByAuid(auid);
			model.addAttribute("appUser", appUser);

		return PREFIX+"appUser_detail";
    }

    /**
     * 查询资讯列表list
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false, value = "account") String account,
                        @RequestParam(required = false, value = "begDate") String begDate,
                        @RequestParam(required = false, value = "endDate") String endDate,
                        @RequestParam(required = false, value = "isMember") Integer isMember,
                        @RequestParam(required = false, value = "nickname") String nickname
                        ){
        Page<AppUserEntity> page = defaultPage();
        List<AppUserEntity> result = appUserService.appuUserList(page, account, begDate, endDate, nickname,isMember);
        page.setRecords(result);
        return super.packForBT(page);
    }
}
