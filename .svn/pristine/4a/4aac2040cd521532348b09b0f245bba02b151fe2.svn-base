package com.gobestsoft.mamage.moudle.srv.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.mapper.SrvStraitenedFirstMapper;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedFirstPojo;
import com.gobestsoft.common.modular.dao.model.SrvStraitenedPojo;
import com.gobestsoft.common.modular.dao.model.StaffSkillPojo;
import com.gobestsoft.common.modular.model.LogModel;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.*;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.app.service.MessageService;
import com.gobestsoft.mamage.moudle.law.service.LawSupportService;
import com.gobestsoft.mamage.moudle.srv.service.SrvStraitenedService;
import com.gobestsoft.mamage.moudle.system.service.DictService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * create by xiashasha
 * on 2018/9/15 上午11:00
 */
@Controller
@RequestMapping("/srv/straitened")
public class SrvStraitenedController extends BaseController {

    private final String PREFIX = "/srv/straitened/";

    @Autowired
    private SrvStraitenedService straitenedService;
    @Autowired
    MessageService messageService;
	@Autowired
	LawSupportService supportService;
	@Autowired
	DictService dictService;

	@Autowired
	SrvStraitenedFirstMapper srvStraitenedFirstMapper;

	@Resource
	RedisUtils redisUtils;
	@RequestMapping(value = "/addSkillNeed")
	@ResponseBody
	public Tip addSkillNeed(@Valid StaffSkillPojo staffSkillPojo, BindingResult result) {
		if (result.hasErrors()) {
			return fail(result.getFieldError().getDefaultMessage());
		}
		staffSkillPojo.setCreateUser(getLoginUser().getId());
		staffSkillPojo.setCreateTime(DateUtil.getAllTime());
		this.straitenedService.addstaffSkill(staffSkillPojo);
		return success();
	}
    /**
     * 跳转到添加职工技能需求
     */
    @RequestMapping(value = "/openSkillNeed")
    public String openAdd(Model model) {
        return PREFIX + "skill_need_add";
    }
	/**
	 * 获取职工技能需求列表
	 * @param name
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/getlist")
	@ResponseBody
	public Object getlist(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String mobile) {
		Page<Map<String, Object>> page = defaultPage();
		List<Map<String, Object>> result = this.straitenedService.getlist(page, name, mobile);
		page.setRecords(result);
		return super.packForBT(page);
	}
	/**
	 * 跳转技能需求管理
	 * @return
	 */
	@RequestMapping("/skillNeed")
	public String skillNeed(){
		return PREFIX + "skill_need";
	}
	/**
	 * 跳转困难帮扶统计
	 * @param model
	 * @return
	 */
    @RequestMapping("/statistics")
	public String kbStatistics(Model model){
		Map<String,Object> map=straitenedService.getSrvTj();
		model.addAttribute("tjData",map);
		return PREFIX + "bf_statistics";
	}
    /**
	 * 跳转困难帮扶终审首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/first_index")
	public String firstIndex(Model model){
		model.addAttribute("total",srvStraitenedFirstMapper.selectTotalCount());
		return PREFIX + "straitened_first";
	}
    
	@RequestMapping("/first_allIndex")
	public String first_alllIndex(Model model){
		return PREFIX + "straitened_firstAll";
	}

    /**
	 * 跳转困难帮扶终审首页
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model){
		return PREFIX + "straitened";
	}

	@RequestMapping("/all")
	public String indexAll(Model model){
		return PREFIX + "straitened_all";
	}

	/**
	 * 获取法律援助初审管理列表
	 * @param name
	 * @param status
	 * @return
	 */
	@RequestMapping("/first_list")
    @ResponseBody
    public Object firstList(
    		@RequestParam(required = false) String name, 
    		@RequestParam(required = false) Integer status,
    		@RequestParam(required = false) String workUnit) {
		Page<Map<String, Object>> page = defaultPage();
		List<Map<String, Object>> result = this.straitenedService.selectFirstByCondition(page, name, status, 0, workUnit, getLoginUser().getDeptId());
		page.setRecords(result);
		return super.packForBT(page);
	}
	@RequestMapping("/first_allList")
	@ResponseBody
	public Object first_allList(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer status,
			@RequestParam(required = false) String workUnit) {
		Page<Map<String, Object>> page = defaultPage();
		List<Map<String, Object>> result = this.straitenedService.selectFirstByCondition(page, name, status, 0, workUnit, null);
		page.setRecords(result);
		return super.packForBT(page);
	}
	
	/**
	 * 获取法律援助终审管理列表
	 * @param name
	 * @param status
	 * @return
	 */
	@RequestMapping("/list")
    @ResponseBody
    public Object list(
    		@RequestParam(required = false) String name, 
    		@RequestParam(required = false) Integer status,
    		@RequestParam(required = false) String workUnit) {
		Page<Map<String, Object>> page = defaultPage();
		List<Map<String, Object>> result = this.straitenedService.selectByCondition(page, name, status, 1, workUnit, getLoginUser().getDeptId());
		page.setRecords(result);
		return super.packForBT(page);
	}
	@RequestMapping("/allList")
    @ResponseBody
    public Object allList() {
		Page<Map<String, Object>> page = defaultPage();
		ObjectMap query = ObjectMap.getInstance();
		query.putRequest("name");
		query.putRequest("status");
		query.putRequest("type");
		query.putRequest("workUnit");
		query.putRequest("cert");
		query.putRequest("straitenedType");
		List<Map<String, Object>> result = this.straitenedService.getStraitenedAll(page, query);
		page.setRecords(result);
		return super.packForBT(page);
	}

	/**
	 * 跳转至初审详情页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/first_detail")
    public String firstDetail(@RequestParam("id") Integer id, @RequestParam("straitenedLogId") Integer straitenedLogId, Model model) {
		if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
		SrvStraitenedFirstPojo straitened = straitenedService.selectFirstById(id);
		straitened.setIdentityFace(WebSiteUtil.getBrowseUrl(straitened.getIdentityFace()));
		straitened.setCardNationalEmblem(WebSiteUtil.getBrowseUrl(straitened.getCardNationalEmblem()));
		straitened.setCommitLetter(WebSiteUtil.getBrowseUrl(straitened.getCommitLetter()));
		straitened.setUnitProve(WebSiteUtil.getBrowseUrl(straitened.getUnitProve()));

		String attachments = straitened.getAttachments();
		if(StringUtils.isNotEmpty(attachments)){
			JSONArray arr = JSONArray.parseObject(attachments,JSONArray.class);
			for(Object o: arr){
				JSONObject obj = (JSONObject) o;
				obj.put("path",WebSiteUtil.getBrowseUrl(obj.getString("path")));
			}
			model.addAttribute("attachments",arr);
		}
		String[] evidence = StringUtils.isEmpty(straitened.getEvidence()) ? new String[]{} : straitened.getEvidence().split(",");
		model.addAttribute("evidence", WebSiteUtil.getBrowseUrl(evidence));
        model.addAttribute("straitened", straitened);
		model.addAttribute("straitenedLogId", straitenedLogId);
		model.addAttribute("isExamine", 0);// 区分是否是审核页面还是详情页面
	    return PREFIX + "straitened_first_detail";
	}
	
	/**
	 * 跳转至终审详情页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
    public String detail(@RequestParam("id") Integer id, @RequestParam("straitenedLogId") Integer straitenedLogId, Model model) {
		if (ToolUtil.isEmpty(id)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
		SrvStraitenedPojo straitened = straitenedService.selectById(id);
		String[] sysAttachments = StringUtils.isEmpty(straitened.getSysAttachments()) ? new String[]{} : straitened.getSysAttachments().split(",");
        model.addAttribute("sysAttachments", WebSiteUtil.getBrowseUrl(sysAttachments));
		model.addAttribute("straitened", straitened);
		model.addAttribute("straitenedLogId", straitenedLogId);
		model.addAttribute("isExamine", 0);// 区分是否是审核页面还是详情页面
		model.addAttribute("level", getLoginUser().getDept().getLevel());// 获取当前等级
	    return PREFIX + "straitened_detail";
	}
	

	/**
	 * 跳转至初审页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/first_examine")
	public String firstExamine(@RequestParam("straitenedLogId") Integer straitenedLogId, @RequestParam("id") Integer id, Model model) {
		if (ToolUtil.isEmpty(straitenedLogId)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
		SrvStraitenedFirstPojo straitened = straitenedService.selectFirstById(id);
		straitened.setIdentityFace(WebSiteUtil.getBrowseUrl(straitened.getIdentityFace()));
		straitened.setCardNationalEmblem(WebSiteUtil.getBrowseUrl(straitened.getCardNationalEmblem()));
		straitened.setCommitLetter(WebSiteUtil.getBrowseUrl(straitened.getCommitLetter()));
		straitened.setUnitProve(WebSiteUtil.getBrowseUrl(straitened.getUnitProve()));
		String attachments = straitened.getAttachments();
		if(StringUtils.isNotEmpty(attachments)){
			JSONArray arr = JSONArray.parseObject(attachments,JSONArray.class);
			for(Object o: arr){
				JSONObject obj = (JSONObject) o;
				obj.put("path",WebSiteUtil.getBrowseUrl(obj.getString("path")));
			}
			model.addAttribute("attachments",arr);
		}
		String[] evidence = StringUtils.isEmpty(straitened.getEvidence()) ? new String[]{} : straitened.getEvidence().split(",");
		model.addAttribute("evidence", WebSiteUtil.getBrowseUrl(evidence));
		model.addAttribute("straitened", straitened);
		model.addAttribute("straitenedLogId", straitenedLogId);
		model.addAttribute("isExamine", 1);// 区分是否是审核页面还是详情页面
	    return PREFIX + "straitened_first_detail";
		
	}
	
	/**
	 * 跳转至终审核页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/examine")
	public String examine(@RequestParam("straitenedLogId") Integer straitenedLogId, @RequestParam("id") Integer id, Model model) {
		if (ToolUtil.isEmpty(straitenedLogId)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        }
		SrvStraitenedPojo straitened = straitenedService.selectById(id);
		String[] sysAttachments = StringUtils.isEmpty(straitened.getSysAttachments()) ? new String[]{} : straitened.getSysAttachments().split(",");
        model.addAttribute("sysAttachments", WebSiteUtil.getBrowseUrl(sysAttachments));
		model.addAttribute("straitened", straitened);
		model.addAttribute("straitenedLogId", straitenedLogId);
		model.addAttribute("isExamine", 1);// 区分是否是审核页面还是详情页面
		model.addAttribute("level", getLoginUser().getDept().getLevel());// 获取当前等级
		model.addAttribute("lib_filing_standard",dictService.getDictionary("lib_filing_standard"));
		model.addAttribute("lib_difficult",dictService.getDictionary("lib_difficult"));
		return PREFIX + "straitened_detail";
		
	}
	
	/**
     * 提交初审核情况
     * @throws IOException
     */
    @RequestMapping(value = "/submit_first/{status}")
    @ResponseBody
    public Tip submitFirst(@RequestParam("id") Integer id,
						   @PathVariable Integer status,
						   @RequestParam(required = false) String comment,
						   @RequestParam("straitenedLogId") Integer straitenedLogId,
						   @RequestParam("straitenedType") String straitenedType,
							@RequestParam("evidence") String evidence
							) throws IOException {
    	SrvStraitenedFirstPojo straitened = straitenedService.selectFirstById(id);
    	// 提交审核情况
    	straitened.setStatus(status);
		straitened.setStraitenedType(straitenedType);
		straitened.setEvidence(evidence);
    	this.straitenedService.submitFirst(straitened, comment, straitenedLogId);

		//		给用户推送消息

		int category=4;
		String title = "困难帮扶";
		title = straitened.getName()+"您好，您"+getNortDate(straitened.getCreateTime(),"yyyy年MM月dd日")+"提交的困难帮扶申请初审";
		if(status==2){
			title+="已通过";
		}
		else if(status==3){
			title+="已拒绝";
		}
		int mode = 6;//困难帮扶
		int targetId = id;
		String jumpUrl = null;
		boolean isPush = true;
		String[] auids= {straitened.getAuid()};
		messageService.sendMessageByAuid(category,title,comment,mode,targetId+"",jumpUrl,isPush,auids);
    	return  success();
    }
	
	/**
     * 提交终审核情况
     * @throws IOException
     */
    @RequestMapping(value = "/submit/{status}")
    @ResponseBody
    public Tip submit(@RequestParam("id") Integer id, 
    		          @PathVariable Integer status, 
    		          @RequestParam(required = false) String comment, 
    		          @RequestParam(required = false) String srvCategory,
    		          @RequestParam(required = false) String filingStandard,
    		          @RequestParam("straitenedLogId") Integer straitenedLogId,
    		          @RequestParam(required = false) String sysAttachments) throws IOException {
    	SrvStraitenedPojo straitened = straitenedService.selectById(id);
    	// 提交审核情况
    	straitened.setStatus(status);
    	// 困难级别
    	straitened.setSrvCategory(srvCategory);
    	// 建档标准
    	straitened.setFilingStandard(filingStandard);
    	// 后台上传的附件地址返回
    	straitened.setSysAttachments(sysAttachments);
    	this.straitenedService.submit(straitened, comment, straitenedLogId );
    	//给用户推送消息
		int category=4;
		String title = "困难帮扶";
		title = straitened.getName()+"您好，您"+getNortDate(straitened.getCreateTime(),"yyyy年MM月dd日")+"提交的困难帮扶申请终审";
		if(status==2){
			title+="已通过";
		}
		else if(status==3){
			title+="已拒绝";
		}
		int mode = 6;//法律援助
		int targetId = id;
		String jumpUrl = null;
		boolean isPush = true;
		String[] auids= {straitened.getAuid()};
		messageService.sendMessageByAuid(category,title,comment,mode,targetId+"",jumpUrl,isPush,auids);
    	return  success();
    }


	/**
	 * 获取详情
	 * @param id
	 * @return
	 */
    @ResponseBody
	@RequestMapping("/getStraitenedDetail")
    public Object getStraitenedDetail(@RequestParam("id") String id){
		Map<String, Object> finalDetail = straitenedService.getFinalDetail(id);
		//新赋值一个变量，避免数据污染
		Map<String, Object> beanMap = finalDetail;
		//固定期限
		if(ToolUtil.isNotEmpty(beanMap.getOrDefault("laborContractEnd",""))){
			beanMap.put("fixedTerm","有");
		}else{
			beanMap.put("fixedTerm","无");
		}
		setDownloadDataToCache(beanMap);
//		finalDetail.put("lib_filing_standard",dictService.getDictionary("lib_filing_standard"));
//		finalDetail.put("lib_difficult",dictService.getDictionary("lib_difficult"));

		return finalDetail;
	}

	/**
	 * 查看审核流程
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/first_log")
	public String logFirst(@RequestParam("id") Integer id, Model model) {
		List<LogModel> log = straitenedService.log(id,0);
		model.addAttribute("logList", log);
		return  "/srv/straitened/look_log";
	}
    
    /**
	 * 查看审核流程
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/log")
	public String log(@RequestParam("id") Integer id, Model model) {
		List<LogModel> log = straitenedService.log(id,1);
		model.addAttribute("logList", log);
		return  "/srv/straitened/look_log";
	}
	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "attch", required = false) MultipartFile file) {
        String packName = UploadConstants.STRAITENED;
        String strDate = DateUtil.getDays();
        String fileExtensionName = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        String fileSavePath = "";
        fileSavePath = manageProperties.getFileUploadPath() + packName + strDate + "/";
        try {
            File p = new File(fileSavePath);
            if (!p.exists()) {
                p.mkdirs();
            }
            file.transferTo(new File(fileSavePath + fileName));
        } catch (Exception e) {
            throw new BusinessException(BizExceptionEnum.UPLOAD_ERROR);
        }
        String result = WebSiteUtil.getBrowseUrl(packName + strDate + "/" + fileName);
        return result;

    }


	@RequestMapping(value="/transfer")
	public String transferWeb(Model model,@RequestParam(value = "id", required = true) Integer id) {

		LoginUser user = getLoginUser();

		Integer deptId = user.getDeptId();

		Integer pid =null;

		String pname = null;

		if(user.getDept().getParentDept()!=null){
			pid = user.getDept().getPId();
			pname = user.getDept().getParentDept().getDeptName();
		}

		model.addAttribute("deptId", deptId);
		model.addAttribute("pid", pid);
		model.addAttribute("pname",pname );

		List<Map> sons = supportService.selectSonDept(deptId);
		model.addAttribute("sons",sons);
		model.addAttribute("id", id);
		String type = getPara("type");
		model.addAttribute("type",type);
		if(pid==null&&(sons==null||sons.size()==0)){
			model.addAttribute("msg", "没有可转办组织，不能进行转办！");
		}

		return PREFIX + "straitened_transfer";
	}

	@ResponseBody
	@RequestMapping("/transferSubmit")
	public Tip transferSubmit(
			@RequestParam(required = true) Integer id
			,@RequestParam(required = false) String transferReason
			,@RequestParam(required = true) Integer deptId
			,@RequestParam(required = true) Integer type){

		if(deptId==null){
			throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
		}

		return straitenedService.updateTransfer(id,deptId,type,transferReason);

	}

	/**
	 * 下载
	 * @param fileName
	 */
	@RequestMapping("/downloadWord")
	@ResponseBody
	public void downloadWord(String fileName,Integer type, Integer id){
		String template = FileUtil.getResoucePath("static"+File.separator+"word"+File.separator+"straitened")+File.separator+ this.getTemplateName(fileName,type);
		switch (type){
			default:
				Map<String, Object> beanMap = (Map<String, Object>) redisUtils.get(CacheConstant.Straitened_Contact+id);
				if(ToolUtil.isEmpty(beanMap)){
					this.getStraitenedDetail(String.valueOf(id));
				}
				WordUtil.downloadWord(template, fileName, beanMap, getHttpServletResponse(), getHttpServletRequest());
				break;
			case 1:
				Map<String, Object> study = (Map<String, Object>) redisUtils.get(CacheConstant.Straitened_Study+id);
				if(ToolUtil.isEmpty(study)){
					Integer pid = straitenedService.getStraitenedIdByStudyId(id);
					this.getStraitenedDetail(String.valueOf(pid));
					study = (Map<String, Object>) redisUtils.get(CacheConstant.Straitened_Study+id);
				}
				WordUtil.downloadWord(template, fileName, study, getHttpServletResponse(), getHttpServletRequest());
				break;
			case 2:
				Map<String, Object> medical = (Map<String, Object>) redisUtils.get(CacheConstant.Straitened_Medical+id);
				if(ToolUtil.isEmpty(medical)){
					Integer pid = straitenedService.getStraitenedIdByMedicalId(id);
					this.getStraitenedDetail(String.valueOf(pid));
					medical = (Map<String, Object>) redisUtils.get(CacheConstant.Straitened_Medical+id);
				}
				WordUtil.downloadWord(template, fileName, medical, getHttpServletResponse(), getHttpServletRequest());
				break;
		}
	}

	/**
	 * 根据前端的参数 和 beanMap中的type 确定使用的模板
	 * @param fileName
	 * @return
	 */
	private String getTemplateName(String fileName, Integer type) {
		String suffix = WordUtil.DOCX_SUFFIX;
		if (fileName.endsWith(WordUtil.DOC_SUFFIX)) {
			suffix = WordUtil.DOC_SUFFIX;
		}
		String templateName = "straitened";
		if (type == 1){
			templateName = "straitened-study";
		}else if(type == 2){
			templateName = "straitened-medical";
		}
		return templateName + suffix;
	}

	/**
	 * 需要下载的数据放入缓存中
	 * @param beanMap
	 */
	private void setDownloadDataToCache(Map<String, Object> beanMap) {
		Long expireTime = 300L;
		//缓存 困难职工(建档人) 数据
		if(beanMap.containsKey("contacts")){
			//亲属 添加到beanMap
			List<Map<String, Object>> contacts = (List<Map<String, Object>>) beanMap.get("contacts");
			mapAddContact(contacts, 5, beanMap);
			//审核意见 添加到beanMap
			List<LogModel> logModels = (List<LogModel>) beanMap.get("logModels");
			mapAddLogModel(logModels, 3, beanMap);
		}
		redisUtils.set(CacheConstant.Straitened_Contact+beanMap.get("id"), beanMap, expireTime);

		//缓存 助学 数据
		if(beanMap.containsKey("studies")){
			((List<Map<String, Object>>) beanMap.get("studies")).forEach(obj -> {
				fieldsCopy(obj, beanMap);
				redisUtils.set(CacheConstant.Straitened_Study+obj.get("id"), obj, expireTime);
			});
		}

		//缓存 医疗救助 数据
		if(beanMap.containsKey("medicals")){
			((List<Map<String, Object>>) beanMap.get("medicals")).forEach(obj -> {
				fieldsCopy(obj, beanMap);
				redisUtils.set(CacheConstant.Straitened_Medical +obj.get("id"), obj, expireTime);
			});
		}
	}

	private void mapAddContact(List<Map<String, Object>> objs, int length, Map<String, Object> beanMap) {

		for (int i = 0; i < (objs).size(); i++) {
			beanMap.put("name" + i, (objs).get(i).getOrDefault("name", ""));
			beanMap.put("relationName" + i, (objs).get(i).getOrDefault("relationName", ""));
			beanMap.put("sexName" + i, (objs).get(i).getOrDefault("sexName", ""));
			beanMap.put("politicalStatusName" + i, (objs).get(i).getOrDefault("politicalStatusName", ""));
			beanMap.put("educationName" + i, (objs).get(i).getOrDefault("educationName", ""));
			beanMap.put("certificateNum" + i, (objs).get(i).getOrDefault("certificateNum", ""));
			beanMap.put("age" + i, (objs).get(i).getOrDefault("age", ""));
			beanMap.put("medicalInsuranceName" + i, (objs).get(i).getOrDefault("medicalInsuranceName", ""));
			beanMap.put("healthName" + i, (objs).get(i).getOrDefault("healthName", ""));
			beanMap.put("monthlyIncome" + i, (objs).get(i).getOrDefault("monthlyIncome", ""));
			beanMap.put("identityName" + i, (objs).get(i).getOrDefault("identityName", ""));
			beanMap.put("unit" + i, (objs).get(i).getOrDefault("unit", ""));
		}
		for (int i = length-1; i > (objs).size() - 1; i--) {
			beanMap.put("name" + i, "");
			beanMap.put("relationName" + i, "");
			beanMap.put("sexName" + i, "");
			beanMap.put("politicalStatusName" + i, "");
			beanMap.put("educationName" + i, "");
			beanMap.put("certificateNum" + i, "");
			beanMap.put("age" + i, "");
			beanMap.put("medicalInsuranceName" + i, "");
			beanMap.put("healthName" + i, "");
			beanMap.put("monthlyIncome" + i, "");
			beanMap.put("identityName" + i, "");
			beanMap.put("unit" + i, "");
		}

	}

	private void mapAddLogModel(List<LogModel> objs, int length, Map<String, Object> beanMap) {

		for (int i = 0; i < objs.size(); i++) {
			String unit = objs.get(i).getFullname();
			String comment = objs.get(i).getComment();
			if (ToolUtil.isNotEmpty(unit) && ToolUtil.isNotEmpty(comment)) {
				String status = "通过";
				if (objs.get(i).getStatus() != 2) {
					status = "拒绝";
				}
				beanMap.put("logModel" + i, "经 " + unit + " 审核： " + status + ", 原因：" + comment);
			}
		}
		for (int i = length-1; i > objs.size() - 1; i--) {
			beanMap.put("logModel" + i, "");
		}

	}

	/**
	 * 复制map中的 key-value
	 * @param obj 预期map
	 * @param beanMap 源map
	 */
	private void fieldsCopy(Map<String, Object> obj,Map<String, Object> beanMap){
		obj.put("createTime",beanMap.get("createTime"));
		obj.put("filingName",beanMap.get("name"));
		obj.put("filingSexName",beanMap.get("sexName"));
		obj.put("filingAge",beanMap.get("age"));
		obj.put("filingCertificateNum",beanMap.get("certificateNum"));
		obj.put("filingWorkUnit",beanMap.get("workUnit"));
		obj.put("filingIndustryTypeName",beanMap.get("industryTypeName"));
		obj.put("filingMonthlyIncome",beanMap.get("monthlyIncome"));
		obj.put("filingMemberCardNo",beanMap.get("memberCardNo"));
		obj.put("filingMobile",beanMap.get("mobile"));
		obj.put("filingLaborContractStart",beanMap.get("laborContractStart"));
		obj.put("filingLaborContractEnd",beanMap.get("laborContractEnd"));
		obj.put("filingFixedTerm",beanMap.get("fixedTerm"));
		obj.put("filingReason",beanMap.get("reason"));
		//是否农名工
		String isFarmer = "否";
		if(beanMap.containsKey("memberInfo")){
			Map<String, Object> memberInfo = (Map<String, Object>) beanMap.get("memberInfo");
			if(ToolUtil.isNotEmpty(memberInfo.containsKey("isFarmer")) && "1".equals(memberInfo.get("isFarmer"))){
				isFarmer = "是";
			}
		}
		obj.put("filingIsFarmer",isFarmer);
		//是否低保户
		obj.put("filingIsBasicLive", "");
	}
}
