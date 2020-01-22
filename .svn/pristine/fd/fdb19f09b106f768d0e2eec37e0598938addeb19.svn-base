package com.gobestsoft.company.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gobestsoft.common.modular.dao.model.QualificationPojo;
import com.gobestsoft.common.modular.system.dao.BlackboardDao;
import com.gobestsoft.common.modular.system.dao.NoticeDao;
import com.gobestsoft.common.modular.system.dao.QualificationDao;
import com.gobestsoft.common.modular.system.mapper.DictMapper;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.system.service.BlackboardService;
import com.gobestsoft.mamage.moudle.system.service.QualificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * 总览信息
 *
 * @author gobestsoft
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {

    /**
     * lib_economic_type 经济类型字典groupCode
     * lib_industry_type 单位所属行业字典groupCode
     */
    private static final String[] DICGROUPCODE = {"lib_economic_type", "lib_industry_type"};


    @Resource
    QualificationService qualificationService;

    @Resource
    UserMapper userMapper;

    @Resource
    QualificationDao qualificationDao;

    @Autowired
    private BlackboardService blackboardService;

    /**
     * 跳转到首页黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {

        LoginUser loginUser = getLoginUser();

        //未申请建会的需要调整申请界面
        if (loginUser.getDeptId() == null) {
            return REDIRECT+"company/organization/index?type=1";
        }
        model.addAttribute("memberCount",blackboardService.getMemberCountByDeptId(getLoginUser().getDeptId()));
        model.addAttribute("memberCardCount",blackboardService.getMemberCardCountByDeptId(getLoginUser().getDeptId()));
        model.addAttribute("subordinateDeptCount",blackboardService.getSubordinateDeptCount(getLoginUser().getDeptId()));
        //会员人数含下级工会
        model.addAttribute("memberAndSubordinateCount",blackboardService.getMemberCountAndSubordinateByDeptId(getLoginUser().getDeptId()));
        return "blackboard2";
    }

    @RequestMapping("/getchart")
    @ResponseBody
    public List<Map<String, Object>> getChart() {
        return new ArrayList<Map<String, Object>>();
    }

    @RequestMapping("/getlinechart")
    @ResponseBody
    public List<Map<String, Object>> getLintChart() {
//        List<Map<String, Object>> lineinfo = blackboardDao.getLineinfo();
//
//        List<Map<String, Object>> ret = new ArrayList<>();
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 12);
//        for (int i = 0; i < 12; i++) {
//            Map<String, Object> map = new HashMap<>();
//            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
//            String month = cal.get(Calendar.YEAR) + "-" + StringUtils.right("00" + (cal.get(Calendar.MONTH) + 1), 2);
//            map.put("date", month);
//            map.put("cnt", 0);
//            if (ToolUtil.isNotEmpty(lineinfo) && lineinfo.size() > 0)
//                for (int j = 0; j < lineinfo.size(); j++) {
//                    if (ToolUtil.equals(month, lineinfo.get(j).get("date"))) {
//                        map.put("cnt", lineinfo.get(j).get("cnt"));
//                        break;
//                    }
//                }
//            ret.add(map);
//        }

        return null;
    }

    @RequestMapping("/ajaxGetQualification")
    @ResponseBody
    public Tip ajaxGetQualification() {
        LoginUser loginUser = getLoginUser();
        QualificationPojo pojo = qualificationService.getQualificationByUid(loginUser.getId());
        return new Tip(200, null, pojo);
    }

    /**
     * 企业信息保存
     *
     * @return
     */
    @RequestMapping("/ajaxSave")
    @ResponseBody
    public Tip ajaxSave(@Valid QualificationPojo pojo, BindingResult result,
                        @RequestParam("phone") String phone, @RequestParam("email") String email) {
        if (result.hasErrors()) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }

        return insertOrUpdate(pojo, phone, email);
    }

    /**
     * 根据主键id是否存在，判断执行插入或更新操作
     *
     * @return
     */
    public Tip insertOrUpdate(QualificationPojo qualification, String phone, String email) {
        if (ToolUtil.isEmpty(qualification.getId())) {
            //新增数据
            qualification.setUid(getLoginUser().getId());
            qualification.setCreateTime(DateUtil.getAllTime());
            qualification.setCreateUser(getLoginUser().getId());
            qualification.setUpdateUser(getLoginUser().getId());
            qualification.setUpdateTime(DateUtil.getAllTime());
            qualification.setPName(getLoginUser().getDept().getParentDept().getDeptName());
            qualificationDao.insert(qualification);
        } else {
            //更新数据
            QualificationPojo newQualification = qualificationDao.selectById(qualification.getId());
            //根据传入的qualification，将其企业资质的基本参数赋给newQualification
            bindNewQualification(newQualification, qualification);
            //根据当前登录人关联查询工会信息，绑定上级工会名称
            newQualification.setPName(getLoginUser().getDept().getParentDept().getDeptName());
            qualificationDao.updateById(newQualification);
        }
        //绑定用户基本信息，执行更新操作
        User user = userMapper.selectById(getLoginUser().getId());
        user.setPhone(phone);
        user.setEmail(email);
        userMapper.updateById(user);

        return success();
    }

    /**
     * 根据传入的qualification，将其企业资质的基本参数赋给newQualification
     *
     * @param newQualification
     * @param qualification
     */
    private void bindNewQualification(QualificationPojo newQualification, QualificationPojo qualification) {
        newQualification.setEnterpriseName(qualification.getEnterpriseName());//公司名称
        newQualification.setAddress(qualification.getAddress());//地址
        newQualification.setIntroduce(qualification.getIntroduce());//介绍
        newQualification.setEconomicType(qualification.getEconomicType());//经济类型
        newQualification.setIndustryType(qualification.getIndustryType());//所属行业
        newQualification.setEnterpriseNumber(qualification.getEnterpriseNumber());//公司规模
        newQualification.setWebsite(qualification.getWebsite());//网址
        newQualification.setUpdateUser(getLoginUser().getId());
        newQualification.setUpdateTime(DateUtil.getAllTime());
    }

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String uid = getLoginUser().getId();
        return qualificationService.upload(picture, uid);
    }
}