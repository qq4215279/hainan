package com.gobestsoft.api.modular.srv.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.api.modular.appuser.model.AppUserMember;
import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.basic.FileUpload;
import com.gobestsoft.api.modular.srv.model.LawSupportParam;
import com.gobestsoft.api.modular.srv.model.StraitenedApplyParam;
import com.gobestsoft.api.modular.srv.model.StraitenedDto;
import com.gobestsoft.api.modular.srv.model.StraitenedFirstApplyParam;
import com.gobestsoft.api.modular.srv.service.SrvService;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.mapper.SrvStraitenedFirstMapper;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedFirstPojo;
import com.gobestsoft.common.modular.dept.mapper.DeptOrganizationMapper;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.common.modular.srv.StraitenedEntity;
import com.gobestsoft.core.util.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务大厅
 * create by gutongwei
 * on 2018/6/20 下午2:20
 */
@RestController
@RequestMapping("/srv")
public class SrvController extends BaseController {

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    private SrvService srvService;

    @Autowired
    private SrvStraitenedFirstMapper straitenedFirstMapper;

    @Autowired
    private DeptOrganizationMapper deptOrganizationMapper;

    /**
     * 申请帮扶初审
     *
     * @param name
     * @param certificateNum
     * @param workUnit
     * @param identityFace
     * @param cardNationalEmblem
     * @param commitLetter
     * @param unitProve
     * @param request
     * @return
     */
    @RequestMapping(value = "/straitenedFirst", method = RequestMethod.POST)
    public BaseResult straitenedFirstApply(
                                            @RequestParam(required = false) String id,
                                            @RequestParam("name") String name,
                                           @RequestParam("certificateNum") String certificateNum,
                                           @RequestParam("workUnit") String workUnit,
                                           @RequestParam(value = "identityFace", required = false) MultipartFile identityFace,
                                           @RequestParam(required = false) String identityFacePath,
                                           @RequestParam(value = "cardNationalEmblem", required = false) MultipartFile cardNationalEmblem,
                                           @RequestParam( required = false) String cardNationalEmblemPath,
                                           @RequestParam(value = "commitLetter", required = false) MultipartFile commitLetter,
                                           @RequestParam( required = false) String commitLetterPath,
                                           @RequestParam(value = "unitProve", required = false) MultipartFile unitProve,
                                           @RequestParam(required = false) String unitProvePath,
                                           @RequestParam(required = false) String attachmentsPath,
                                           HttpServletRequest request) {
        if (!isBindMember()) {
            return fail("当前账号未绑定会员");
        }
        StraitenedFirstApplyParam param = new StraitenedFirstApplyParam();
        param.setName(name == null ? "" : name);
        param.setCertificateNum(certificateNum == null ? "" : certificateNum);
        param.setWorkUnit(workUnit == null ? "" : workUnit);
        param.setAttachmentsPath(attachmentsPath);
        param.setId(id);
        try {
            // 身份证人像面
            if (identityFace != null && !identityFace.isEmpty()) {
                FileUpload identityFaceFile = saveFile(UploadConstants.getSrvStraitenedIdentityFace(), identityFace, false);
                param.setIdentityFace(identityFaceFile == null ? "" : identityFaceFile.getSave_path());
            } else if (StringRegularUtil.isURL(identityFacePath)) {
                param.setIdentityFace(WebSiteUtil.getRelativePath(identityFacePath));
            } else {
                return fail("请上传身份证人像面照片");
            }
            // 身份证国徽面
            if (cardNationalEmblem != null && !cardNationalEmblem.isEmpty()) {
                FileUpload cardNationalEmblemFile = saveFile(UploadConstants.getSrvStraitenedCardNationalEmblem(), cardNationalEmblem, false);
                param.setCardNationalEmblem(cardNationalEmblemFile == null ? "" : cardNationalEmblemFile.getSave_path());
            } else if (StringRegularUtil.isURL(cardNationalEmblemPath)) {
                param.setCardNationalEmblem(WebSiteUtil.getRelativePath(cardNationalEmblemPath));
            } else {
                return fail("请上传身份证国徽面照片");
            }


            // 承诺书
            if (commitLetter != null && !commitLetter.isEmpty()) {
                FileUpload commitLetterFile = saveFile(UploadConstants.getSrvStraitenedCommitLetter(), commitLetter, false);
                param.setCommitLetter(commitLetterFile == null ? "" : commitLetterFile.getSave_path());
            } else if (StringRegularUtil.isURL(commitLetterPath)) {
                param.setCommitLetter(WebSiteUtil.getRelativePath(commitLetterPath));
            } else {
                return fail("请上传承诺书照片");
            }

            // 所在单位证明
            if (unitProve != null && !unitProve.isEmpty()) {
                FileUpload unitProveFile = saveFile(UploadConstants.getSrvStraitenedUnitProve(), unitProve, false);
                param.setUnitProve(unitProveFile == null ? "" : unitProveFile.getSave_path());
            } else if (StringRegularUtil.isURL(unitProvePath)) {
                param.setUnitProve(WebSiteUtil.getRelativePath(unitProvePath));
            } else {
                return fail("请上传所在单位证明照片");
            }


            // 附件材料
            List<FileUpload> files = filesUpload(request, UploadConstants.getSrvStraitenedAttachment(), false, "identityFace", "cardNationalEmblem", "commitLetter", "unitProve");

//            StringBuilder attachments = new StringBuilder("");
            JSONArray arr = new JSONArray();
            if(StringUtils.isNotEmpty(param.getAttachmentsPath())){
                arr.addAll(JSONArray.parseObject(param.getAttachmentsPath(),JSONArray.class));
            }
            if(files!=null&&files.size()!=0){
                image2Array(arr, files);
            }
            if(arr.size()==0){
                return fail("至少上传一份材料文件");
            }


            param.setAttachments(arr.toString());
        } catch (IOException e) {
            return fail(BaseResult.ResultCode.ERROR500);
        }

        String flag = srvService.straitenedFirstApply(param, getUserDto());
        if (!SUCCESS.equals(flag)) {
            return fail(flag);
        }
        return baseResult(200, "申请成功", null);
    }

    /**
     * 申请帮扶终审
     * create by gutongwei
     * on 2018/6/20 下午2:20
     *
     * @return
     */
    @RequestMapping(value = "/straitenedApply", method = RequestMethod.POST)
    public BaseResult straitenedApply(@RequestBody @Valid StraitenedApplyParam param, BindingResult bindingResult) {
        if (!isBindMember()) {
            return fail("当前账号未绑定会员");
        }
        if (bindingResult.hasErrors()) {
            return baseResult(500, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        if (param.getContacts() == null || param.getContacts().size() <= 0) {
            return fail("请填写至少一位家庭联系人");
        }

        if (param.getStraitenedFirstId() == null) {
            return fail("数据错误！提交申请失败！");
        }
        SrvStraitenedFirstPojo pojo = straitenedFirstMapper.selectById(param.getStraitenedFirstId());
        if (pojo.getStatus() != 2) {// 如果初审的状态不是2通过，无法进行终审
            return fail("数据错误！提交申请失败！");
        }

        if ("01".equals(param.getStraitenedType()) && (param.getMedicals() == null || param.getMedicals().size() == 0)) {
            return fail("请至少填写一位需要医疗救助的人员");
        }

        if ("02".equals(param.getStraitenedType()) && (param.getStudies() == null || param.getStudies().size() == 0)) {
            return fail("请至少填写一位需要助学的子女");
        }

        String flag = srvService.straitenedApply(param, getUserDto());
        if (!SUCCESS.equals(flag)) {
            return fail(flag);
        }
        return baseResult(200, "申请成功", null);
    }


    /**
     * 我的帮扶互助列表
     * create by gutongwei
     * on 2018/6/20
     *
     * @return
     */
    @RequestMapping("/straitenedList")
    public BaseResult straitenedList() {
        List<StraitenedDto> strDto = new ArrayList<>();
        List<StraitenedEntity> entities = srvService.straitenedList(getPageBounds(), getUserId());
        for (StraitenedEntity entity : entities) {
            StraitenedDto dto = new StraitenedDto();
//            dto.setCommitFlg(true);
            dto.setCommitFlg(entity.getCommitFlag());
            dto.setIcon(flwqConfig(3));
            dto.setId(entity.getId());
            dto.setType(entity.getType());
            dto.setStatus(entity.getStatus());
            if (ToolUtil.isNotEmpty(entity.getReason())) {
                dto.setReason(entity.getReason());
            } else {
                dto.setReason("");
            }
            dto.setCreateTime(DateUtil.parseAndFormat(entity.getCreateTime(), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm"));
            if (ToolUtil.isNotEmpty(entity.getStraitenedType())) {
                dto.setStraitenedType(entity.getStraitenedType());
            }
            if (ToolUtil.isNotEmpty(entity.getFirstStraitenedId())) {
                dto.setFirstStraitenedId(entity.getFirstStraitenedId());
            }
            dto.setStraitenedName("帮困救助");
            strDto.add(dto);
        }
        return success(strDto);
    }

    /**
     * 帮扶详细
     * create by gutongwei
     * on 2018/6/20
     *
     * @param id 帮扶id
     * @return
     */
    @RequestMapping("/straitenedDetail")
    public BaseResult straitenedDetail(@RequestParam("id") int id) {
        Map<String, Object> result = srvService.straitenedDetail(id);
        if (result == null) {
            return fail("申请不存在");
        }
        return success(result);
    }

    /**
     * 获取法律援助和困难帮扶所有的字典
     *
     * @return
     */
    @RequestMapping("/parameters")
    public BaseResult parameters() {
        return success(srvService.parameters());
    }


    /**
     * 删除帮扶
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeStraitened", method = RequestMethod.POST)
    public BaseResult removeStraitened(@RequestParam("id") int id) {
        if (srvService.removeStraitened(id) == 2) {
            return fail("当前状态不可删除");
        }
        return baseResult(200, "删除成功", null);
    }


    /**
     * 法律援助申请
     *
     * @param paramJson
     * @param request
     * @return
     */
    @RequestMapping(value = "/law/supportApply", method = RequestMethod.POST)
    public BaseResult lawSupportApply(@RequestParam("paramJson") String paramJson, HttpServletRequest request) {
        /*if (!isBindMember()) {
            return fail("当前账号未绑定会员");
        }*/
        LawSupportParam param = JSON.parseObject(paramJson, LawSupportParam.class);
        Integer type = param.getType();
        if (type == 0) {// 0代表中彩金
            if (param.getContacts() == null || param.getContacts().size() <= 0) {
                return fail("请填写至少一位家庭联系人");
            }
        }

        //证据
        try {
            JSONArray arr = new JSONArray();
            if(StringUtils.isNotEmpty(param.getEvidencePath())){
                EmojiUtil.parseToAliases(param.getEvidencePath());
                arr.addAll(JSONArray.parseObject(param.getEvidencePath(),JSONArray.class));
            }
            List<FileUpload> files = filesUploadKeyInclude(request, UploadConstants.SUPPORT_EVIDENCE, "evidence");
            if(files!=null&&files.size()!=0){
                image2Array(arr, files);
            }
            if(arr.size()==0){
                return fail("至少上传一张证据文件");
            }
            if (arr.size() > 8) {
                return fail("最多上传八张证据文件");
            }
            param.setEvidence(JSONArray.toJSONString(arr));//以json形式保存

        } catch (IOException e) {
            param.setEvidence("");
        }

        //收入
        try {
            JSONArray arr = new JSONArray();
            if(StringUtils.isNotEmpty(param.getIncomePath())){
                EmojiUtil.parseToAliases(param.getIncomePath());
                arr.addAll(JSONArray.parseObject(param.getIncomePath(),JSONArray.class));
            }
            List<FileUpload> files = filesUploadKeyInclude(request, UploadConstants.SUPPORT_INCOME, "income");
            if(files!=null&&files.size()!=0){
                image2Array(arr, files);
            }
            if(arr.size()==0){
                return fail("至少上传一张收入证明");
            }
            if (type == 0) {
                if (arr.size() > 1) {
                    return fail("最多上传一张收入证明");
                }
            }else{
                if (arr.size() > 8) {
                    return fail("最多上传八张收入证明");
                }
            }
            param.setIncome(JSONArray.toJSONString(arr));//以json形式保存

        } catch (IOException e) {
            param.setIncome("");
        }

        //户口本
        try {
            JSONArray arr = new JSONArray();
            if(StringUtils.isNotEmpty(param.getResidenceBookletPath())){
                EmojiUtil.parseToAliases(param.getResidenceBookletPath());
                arr.addAll(JSONArray.parseObject(param.getResidenceBookletPath(),JSONArray.class));
            }
            List<FileUpload> files = filesUploadKeyInclude(request, UploadConstants.SUPPORT_BOOKLET, "residenceBooklet");
            if(files!=null&&files.size()!=0){
                image2Array(arr, files);
            }
            if(arr.size()==0){
                return fail("至少上传一张户口本照片");
            }
            if(arr.size()>8){
                return fail("最多上传八张户口本照片");
            }
            param.setResidenceBooklet(JSONArray.toJSONString(arr));//以json形式保存

        } catch (IOException e) {
            param.setResidenceBooklet("");
        }

        AppUserMember memberInfo = getUserDto().getMember_info();
        Integer deptId = DictCodeConstants.DEPT_LEVEL_PROVINCE;
        if(memberInfo != null){
            deptId = memberInfo.getDept_id();
        }
        String flag = srvService.lawSupportApply(param, getUserId(), deptId);
        if (!SUCCESS.equals(flag)) {
            return fail(flag);
        }
        return baseResult(200, "申请成功", null);
    }

    private void image2Array(JSONArray arr, List<FileUpload> files) {
        files.forEach(f -> {
            JSONObject json = new JSONObject(true);
            json.put("desc", f.getOriginal_name().contains("NONE_DESC") ? "" : f.getOriginal_name().replace("." + FilenameUtils.getExtension(f.getOriginal_name()), ""));
            json.put("path", f.getSave_path());
            arr.add(json);
        });
    }

    /**
     * 困难帮扶条件匹配
     */
    @RequestMapping("/straitenedmatch")
    public BaseResult getMatch(@RequestParam("name") String name
            , @RequestParam("certificateNum") String certificateNum
            , @RequestParam("workUnit") String workUnit) {
        boolean matchFlg = true;
        if (!matchFlg) {
            return baseResult(201, "未符合帮扶条件，", null);
        }
        int count = deptOrganizationMapper.selectCount(new EntityWrapper<DeptOrganization>().eq("unit_name", workUnit));
        if (count == 0) {
            return baseResult(201, "未匹配到您的单位，", null);
        }

        return success();
    }


    /**
     * 获取困难帮扶初审的详情
     *
     * @return
     */
    @RequestMapping("/getFirstDetail")
    public BaseResult getFirstDetail(@RequestParam("id") String id) {

        return success(srvService.getFirsDetail(id));
    }

    /**
     * 获取终审详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/getFinalDetail")
    public BaseResult getFinalDetail(String id) {

        return success(srvService.getFinalDetail(id));

    }


}
