package com.gobestsoft.api.modular.cms.controller;

import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.basic.FileUpload;
import com.gobestsoft.api.modular.cms.service.SubmissionService;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.model.MyContributePojo;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我要投稿
 *
 * Created by duanmu on 2018/9/20.
 */
@RestController
@RequestMapping("/submission")
public class SubmissionController extends BaseController{

    @Autowired
    private SubmissionService submissionService;

    /**
     * 我要投稿
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResult addSubmission(HttpServletRequest request,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content){
        if(ToolUtil.isNotEmpty(content)){
            content = EmojiParser.parseToUnicode(content);
        }
        MyContributePojo pojo = new MyContributePojo();
        try {
            List<FileUpload> files=filesUpload(request, UploadConstants.MY_CONTRIBUTE);
            StringBuilder sb = new StringBuilder("");

            files.forEach(f->{
                sb.append(f.getSave_path()+",");
            });

            pojo.setImgPath(sb.substring(0,sb.length()-1));

        } catch (Exception e) {
            pojo.setImgPath("");
        }
        pojo.setAuid(getUserId());
        pojo.setTitle(title);
        pojo.setContent(content);
        submissionService.addSubmission(pojo);
        return success();

    }

    /**
     * 我的投稿
     */
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public BaseResult mineSubmission(){

        List<Map<String, Object>> subMap = new ArrayList<Map<String, Object>>();
        List<MyContributePojo> pojoList = submissionService.mySubmission(getPageBounds(), getUserId());
        for(MyContributePojo pojo : pojoList){
            Map<String, Object> result = new HashMap<>();
            result.put("id", pojo.getId());
            result.put("status", pojo.getStatus());
            String createTime = pojo.getCreateTime();
            if(ToolUtil.isNotEmpty(createTime)){
                createTime = DateUtil.format(DateUtil.parse(createTime, "yyyyMMddHHmmss"), "yyyy-MM-dd HH:mm");
                result.put("createTime", createTime);
            }

            result.put("title", pojo.getTitle());
            if(ToolUtil.isNotEmpty(pojo.getImgPath())){
                String[] str = pojo.getImgPath().split(",");
                result.put("image", WebSiteUtil.getBrowseUrl(str[0]));
            } else {
                result.put("image", "");
            }
            subMap.add(result);
        }

        return success(subMap);
    }

    /**
     * 删除投稿
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public BaseResult delSubmission(@RequestParam("id") Integer id){
        submissionService.delSubmission(id);
        return success();
    }

}
