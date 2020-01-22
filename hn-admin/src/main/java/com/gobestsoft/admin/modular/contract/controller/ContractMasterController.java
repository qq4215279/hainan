package com.gobestsoft.admin.modular.contract.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.admin.modular.contract.service.ContractMasterService;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.contract.model.CollectiveContractMaster;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 集体合同
 */
@Controller
@RequestMapping("/contract/master")
@Slf4j
public class ContractMasterController extends BaseController {

    private final String PREFIX = "/contract/master/";

    @Resource
    private ContractMasterService masterService;

    /**
     * 跳转页面 -- index/add/edit
     */
    @RequestMapping("{page}")
    public String page(@PathVariable String page, @RequestParam(required = false)Integer id, Model model){
        if(ToolUtil.isNotEmpty(id)){
            model.addAttribute("pojo",masterService.getDetailById(id));
        }
        return PREFIX + page;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list( @RequestParam(required = false) String name) {
        Page<Map<String, Object>> page = defaultPage();
        List<Map<String, Object>> result = masterService.selectByCondition(page, name);
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 保存
     */
    @RequestMapping("/doSave")
    @ResponseBody
    public Tip addOrEdit(CollectiveContractMaster pojo,@RequestParam("uploadFile") MultipartFile uploadFile) {
        try {
            LoginUser user = getLoginUser();
            String filePath = saveFile(UploadConstants.COLLECTIVE_CONTRACT, uploadFile);
            pojo.setFilePath(filePath);
            masterService.save(pojo,user);
        } catch (Exception e) {
            fail();
        }
        return success();
    }

    /**
     * 保存
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Integer id) {
        try {
            masterService.delete(id);
        }catch (Exception e){
            return fail();
        }
        return success();
    }

}
