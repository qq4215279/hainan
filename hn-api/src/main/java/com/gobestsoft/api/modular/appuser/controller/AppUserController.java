package com.gobestsoft.api.modular.appuser.controller;

import com.gobestsoft.api.modular.appuser.model.AppUserDto;
import com.gobestsoft.api.modular.appuser.model.AppUserMember;
import com.gobestsoft.api.modular.appuser.service.AppUserCancellationService;
import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.basic.FileUpload;
import com.gobestsoft.api.modular.cms.service.CourseService;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.dept.mapper.AppCheckLogMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.PersonInfoMapper;
import com.gobestsoft.common.modular.dept.model.AppCheckLog;
import com.gobestsoft.core.reids.RedisConstants;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.EmojiUtil;
import com.gobestsoft.core.util.StringRegularUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.rabbitmq.RabbitSender;
import com.gobestsoft.rabbitmq.processor.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户
 *
 * @author gutongwei
 * <p>
 * 2017年11月27日
 */
@RequestMapping("/user")
@RestController
public class AppUserController extends BaseController {

    @Autowired
    private CourseService courseService;


    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private AppCheckLogMapper appCheckLogMapper;

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Resource
    DeptMemberMapper memberMapper;

    @Resource
    AppUserCancellationService appUserCancellationService;


    /**
     * 获取手机验证码
     *
     * @param mobile 手机
     * @param type   【0：用户登录】【1：用户修改密码】【2：用户注册】【3:修改手机】
     * @return
     */
    @RequestMapping(value = "/getMobileCode", method = RequestMethod.POST)
    public BaseResult getMobileCode(@RequestParam("mobile") String mobile, @RequestParam("type") int type) {
        if (!StringRegularUtil.auth(StringRegularUtil.REGEX_MOBILE, mobile)) {
            return fail(BaseResult.ResultCode.ERROR523);
        }
        if (appUserService.isMoreThanTimes(mobile)) {
            return fail(BaseResult.ResultCode.ERROR523_2);
        }
        //是否存在用户
        if ((type == 0 || type == 1 || type == 3) && !appUserService.authAPPUserExist(mobile)) {
            return fail(BaseResult.ResultCode.ERROR102);
        }
        //是否能申请注册的短信验证码
        if (appUserService.authAPPUserExist(mobile) && type == 2) {
            return baseResult(103, "当前账号已注册", null);
        }
        if (type == 4) {
            //判断用户是否修改了手机号，并且判断修改的手机号是否被被人占用
            AppUserEntity appUserByMobile = appUserService.getAppUserByMobile(mobile);
            if (appUserByMobile != null) {
                //是自己的手机号
                String account = appUserByMobile.getAccount();
                if (account.equals(mobile)) {
                    return baseResult(500, "手机号未变动", null);
                } else {
                    return baseResult(500, "该手机号已被使用", null);
                }
            }

        }

        appUserService.sendMobileCode(mobile);
        return baseResult(200, "发送成功", null);
    }


    /**
     * 注册帐号
     *
     * @param mobile     手机号
     * @param password   密码
     * @param mobileCode 验证码
     * @param deviceInfo 设备信息
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResult register(@RequestParam("mobile") String mobile, @RequestParam("password") String password,
                               @RequestParam("mobileCode") String mobileCode,
                               @RequestParam(value = "deviceInfo", required = false) String deviceInfo) {
        if (!appUserService.authMobileCode(mobile, mobileCode)) {
            return fail(BaseResult.ResultCode.ERROR525);
        }
        if (appUserService.authAPPUserExist(mobile)) {
            return fail(BaseResult.ResultCode.ERROR524);
        }
        return success(appUserService.register(mobile, password, deviceInfo, getPlatform()));
    }

    /**
     * 用户登录
     *
     * @param account    账号即手机号
     * @param password   密码
     * @param registerId 极光推送ID
     * @param deviceInfo 设备信息
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResult login(@RequestParam("account") String account, @RequestParam("password") String password,
                            @RequestParam(value = "registerId", required = false) String registerId,
                            @RequestParam(value = "deviceInfo", required = false) String deviceInfo, HttpServletRequest request) {
        AppUserDto result = appUserService.login(account, password, registerId, deviceInfo, getPlatform());
        if (result == null) {
            return fail(BaseResult.ResultCode.ERROR103);
        }
        return success(result);
    }

    /**
     * 用户使用手机验证码登录
     *
     * @param mobile     账号即手机号
     * @param code       手机验证码
     * @param registerId 极光推送ID
     * @param deviceInfo 设备信息
     * @return
     */
    @RequestMapping(value = "/loginByMobile", method = RequestMethod.POST)
    public BaseResult loginByMobile(@RequestParam("mobile") String mobile, @RequestParam("code") String code,
                                    @RequestParam(value = "registerId", required = false) String registerId,
                                    @RequestParam(value = "deviceInfo", required = false) String deviceInfo) {
        AppUserDto result = appUserService.loginByMobile(mobile, code, registerId, deviceInfo, getPlatform());
        if (result == null) {
            return baseResult(525, "手机号或验证码错误", null);
        }
        return success(result);
    }

    /**
     * 修改密码
     *
     * @param password 密码
     * @param mobile   手机号
     * @param code     验证码
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public BaseResult modifyPassword(@RequestParam("password") String password, @RequestParam("mobile") String mobile,
                                     @RequestParam("code") String code) {
        if (!appUserService.authAPPUserExist(mobile)) {
            return fail(BaseResult.ResultCode.ERROR102);
        }
        if (!appUserService.authMobileCode(mobile, code)) {
            return fail(BaseResult.ResultCode.ERROR525);
        }
        appUserService.modifyPassword(password, mobile, code);

        return baseResult(200, "修改成功", null);
    }

    /**
     * 修改手机号
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return
     */
    @RequestMapping(value = "/modifyMobile", method = RequestMethod.POST)
    public BaseResult modifyMobile(@RequestParam("mobile") String mobile,
                                   @RequestParam("code") String code) {
        //验证码是否正确
        if (!appUserService.authMobileCode(mobile, code)) {
            return fail(BaseResult.ResultCode.ERROR525);
        }
        AppUserEntity appUserByMobile = appUserService.getAppUserByMobile(mobile);
        if (appUserByMobile != null) {
            //是自己的手机号
            return baseResult(500, "该手机号已被使用", null);
        }
        AppUserDto userDto = getUserDto();
        appUserService.updateMobile(mobile, userDto.getMobile(), userDto.getAuid(), getToken());
        //更新redis
        userDto.getMember_info().setMobile(mobile);
        userDto.setMobile(mobile);
        redisUtils.set(getToken(), userDto);
        return baseResult(200, "修改成功", null);
    }

    /**
     * 是否能够注销
     * 检验用户
     *
     * @return
     */
    @RequestMapping("validUserState")
    public BaseResult validUserState() {
        AppUserDto userDto = getUserDto();
        if (userDto == null) return new BaseResult(500, "用户未登录", null);

        return appUserService.validUserState(userDto.getAuid());

    }

    /**
     * 注销用户
     *
     * @param mobileCode 验证码
     * @param type       注销类型  字典lib_delete_account
     * @param reason     注销原因
     * @return
     */
    @RequestMapping(value = "/cancellationUser", method = RequestMethod.POST)
    public BaseResult cancellationUser(@RequestParam("mobileCode") String mobileCode,
                                       @RequestParam("type") Integer type,
                                       @RequestParam(value = "reason", required = false) String reason) {
        AppUserDto appUserDto = getUserDto();
        if (!appUserService.authMobileCode(appUserDto.getMobile(), mobileCode)) {
            return fail(BaseResult.ResultCode.ERROR525);
        }
        //获取注销类型
        String dictionarieName = appUserCancellationService.getDictionarieName(type);

        AppUserEntity appUser = appUserDao.getAppUserByAuid(getUserId());
        //保存注销记录
        appUserCancellationService.saveCancellation(getUserId(), appUserDto.getMobile(), appUser.getNickname(), type, EmojiUtil.parseToAliases(reason));

        appUserService.deleteByAuid(getUserId());
        return success();
    }

    /**
     * 修改用户信息
     *
     * @param avatar   头像文件
     * @param nickname 昵称
     * @param realName 真实姓名
     * @param sex      性别
     * @return
     */
    @RequestMapping(value = "/setUserInfo", method = RequestMethod.POST)
    public BaseResult setUserInfo(@RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                  @RequestParam(value = "nickname", required = false) String nickname,
                                  @RequestParam(value = "realName", required = false) String realName,
                                  @RequestParam(value = "sex", required = false) Short sex) {
        Map<String, String> result = new HashMap<>();
        try {
            FileUpload fileUpload = saveFile(UploadConstants.AVATAR, avatar, false);
            result = appUserService.setUserInfo(getUserDto(), fileUpload, nickname,
                    realName, sex);
            appUserService.setTokenInfoByAuId(getUserId());
        } catch (IOException e) {
            e.printStackTrace();
            return fail();
        }
        return success(result);
    }


    /**
     * 用户绑定极光推送号
     *
     * @param registerId
     * @return
     */
    @RequestMapping(value = "/bindRegisterId", method = RequestMethod.POST)
    public BaseResult bindRegisterId(@RequestParam("registerId") String registerId) {
        appUserService.bindAppUserRegisterId(getUserId(), registerId, getPlatform());
        return success();
    }

    /**
     * 根据Token获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/getUserInfoByToken", method = RequestMethod.GET)
    public BaseResult getUserInfoByToken() {
        return success(getUserDto());
    }

    /**
     *
     * 根据Token获取用户信息（传参数）
     */
    @RequestMapping(value = "/getUserIdByToken", method = RequestMethod.GET)
    public BaseResult getUserIdByToken(@RequestParam("token") String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }

        return success(appUserService.getUserInfoByToken(token));
    }

    /**
     * 认证会员
     *
     * @param name   姓名
     * @param cardNo 身份证号码
     * @return
     */
    @RequestMapping(value = "/certifiedMember", method = RequestMethod.POST)
    public BaseResult certifiedMember(
            @RequestParam("name") String name,
            @RequestParam("cardNo") String cardNo) {

        String nowTime = DateUtil.getAllTime();
        if (ToolUtil.isEmpty(name) || ToolUtil.isEmpty(cardNo)) {
            return fail("请求参数为空");
        }
        Map<String, Object> resultMap = appUserService.checkUserMessage(name, cardNo, getUserId());
        if (ToolUtil.isNotEmpty(resultMap.get("status"))) {
            return resultBaseResultByStatus(Integer.parseInt(resultMap.get("status").toString()));
        }
        if (ToolUtil.isEmpty(resultMap.get("appUser")) || ToolUtil.isEmpty(resultMap.get("mstMember"))) {
            return fail("信息输入有误，未找到会员信息，不能认证会员");
        }
        AppUserDto result = appUserService.certifiedMember(resultMap.get("appUser"), resultMap.get("mstMember"), getToken());

        appCheckLogMapper.delCheckLog(getUserId(), "1");
        //流程审核表
        AppUserMember appUserMember = result.getMember_info();
        String deptName = appUserMember.getDept_name();
        AppCheckLog appCheckLog = new AppCheckLog();
        appCheckLog.setAuid(getUserId());
        appCheckLog.setStatus("10");
        appCheckLog.setStyleStatus("1");
        appCheckLog.setType("1");
        appCheckLog.setComment("认证成功");
        appCheckLog.setRecordFlowInfo("恭喜你加入【" + deptName + "】");
        appCheckLog.setCreateTime(nowTime);
        appCheckLogMapper.insert(appCheckLog);

        //给用户推送消息
        MessageProcessor messageProcessor = new MessageProcessor();
        //messageProcessor.setJumpUrl("http://www.baidu.com");
        messageProcessor.setContent("您好，您的账号会员认证已成功");
        messageProcessor.setAuids(new String[]{getUserId()});
        messageProcessor.setMode(0);
        messageProcessor.setPush(true);
        messageProcessor.setCategory(1);
        messageProcessor.setTargetId(null);
        messageProcessor.setTitle("会员认证成功");
        rabbitSender.apiGiveAdminMessage(messageProcessor);

        return success(result);
    }


    /**
     * 认证会员,错误返回提醒
     *
     * @param resultInt
     * @return
     */
    private BaseResult resultBaseResultByStatus(int resultInt) {
        if (resultInt == 0) {
            return fail("账号已失效，请重新登录");
        }
        if (resultInt == 1) {
            return fail("当前账号已经是会员");
        }
        if (resultInt == 2) {
            return fail("会员基本信息不存在");
        }
        if (resultInt == 3) {
            return fail("输入信息已经被人绑定");
        }
        return fail();
    }


    /**
     * 获取消息类型类别
     *
     * @return
     */
    @RequestMapping("/messageCategory")
    public BaseResult messageCategory() {

        return success(appUserService.getUserMessageCategory(getUserId()));
    }


    /**
     * 用户消息列表
     * create by gutongwei
     * 2018/6/12
     *
     * @return
     */
    @RequestMapping("/messages")
    public BaseResult messages(@RequestParam("categoryId") Integer categoryId) {
        if (categoryId == null) {
            return fail("请选择消息类型");
        }

        appUserService.updateMessageReadByCat(getUserId(), categoryId);
        return success(appUserService.getMessages(categoryId, getUserId(), getPageBounds()));
    }

    /**
     * 消息已读
     * create by gutongwei
     * 2018/6/12
     *
     * @param messageId
     * @return
     */
    @RequestMapping(value = "/readMessage", method = RequestMethod.POST)
    public BaseResult readMessage(@RequestParam("messageId") int messageId) {
        appUserService.readMessage(getUserId(), messageId);
        return success();
    }

    /**
     * 消息删除
     * create by gutongwei
     * 2018/6/12
     *
     * @param messageId
     * @return
     */
    @RequestMapping(value = "/delMessage", method = RequestMethod.POST)
    public BaseResult delMessage(@RequestParam("messageId") int messageId) {
        appUserService.delMessage(getUserId(), messageId);
        return baseResult(200, "删除成功", null);
    }

    /**
     * 获取用户积分总数
     * create by gutongwei
     * 2018/6/22
     *
     * @return
     */
    @RequestMapping("/integral")
    public BaseResult integral() {
        Map<String, Object> result = new HashMap<>();
        result.put("integral", appUserService.getUserIntegral(getUserId()));
        result.put("has_new", appUserService.hasNewIntegral(getUserId()));
        return success(result);
    }

    /**
     * 积分任务
     * create by gutongwei
     * 2018/6/21
     *
     * @return
     */
    @RequestMapping("/integralTask")
    public BaseResult integralTask() {
        return success(appUserService.integralTask(getUserId()));
    }

    /**
     * 完成积分任务
     * create by gutongwei
     * 2018/6/21
     *
     * @param taskId 任务ID
     *               '1', '每日签到'
     *               '2', '身份信息绑定'
     *               '3', '上传头像'
     *               '4', '资讯浏览'
     *               '5', '资讯点赞'
     *               '6', '资讯收藏'
     *               '7', '资讯分享'
     *               '8', '账号注册'
     *               '9', '生日签到'
     *               '10', '连续签到7天'
     * @return
     */
    @RequestMapping(value = "/completeIntegralTask", method = RequestMethod.POST)
    public BaseResult completeIntegralTask(@RequestParam("taskId") int taskId) {
        try {
            appUserService.completeTaskGiveIntegral(getUserDto(), taskId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail("操作失败");
        }
        return success();
    }

    /**
     * 积分消费
     * create by gutongwei
     * 2018/6/21
     *
     * @param value    消费积分值
     * @param targetId 目标ID
     * @param type     消费类型
     * @return
     */
    @RequestMapping(value = "/addInteger", method = RequestMethod.POST)
    public BaseResult addInteger(@RequestParam("value") int value, @RequestParam("targetId") String targetId,
                                 @RequestParam("type") int type) {
        appUserService.addInteger(value, targetId, type, getUserId());
        return success();
    }


    /**
     * 获取用户积分列表
     * create by gutongwei
     * 2018/6/21
     *
     * @return
     */
    @RequestMapping("/getUserIntegralList")
    public BaseResult getUserIntegralList(@RequestParam(value = "type", required = false) Integer type,
                                          @RequestParam(value = "taskId", required = false) Integer taskId) {
        return success(appUserService.getUserIntegralList(getUserId(), type, taskId, getPageBounds()));
    }

    /**
     * 获取用户未读消息数量
     * create by gutongwei
     * 2018/6/21
     *
     * @return
     */
    @RequestMapping("/newMessageCount")
    public BaseResult newMessageCount() {
        return success(appUserService.newMessageCount(getUserId()));
    }

    @RequestMapping("/testRabbitMessage")
    public BaseResult testRabbitMessage() {
        appUserService.sendMessageByAuid(getUserId(), 1, "测试标题", "测试标题的内容", 0, "", "", false);
        return success();
    }


    /**
     * 用户退出
     * 删除缓存及极光
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public BaseResult logout() {
        return success();
    }


    @RequestMapping("/sendMsgTest")
    public BaseResult sendMsg() {
        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.setJumpUrl("http://www.baidu.com");
        messageProcessor.setTitle("标题");
        messageProcessor.setContent("内容荣");
        messageProcessor.setAuids(new String[]{"65d9bbe5902e431fbc1e72395841bef3"});
        messageProcessor.setMode(0);
        messageProcessor.setPush(false);
        messageProcessor.setCategory(0);
        messageProcessor.setTargetId(null);
        rabbitSender.apiGiveAdminMessage(messageProcessor);
        return success();
    }

    /**
     * 更新当前消息类型的消息为已读
     *
     * @param cat_id
     * @return
     */
    @RequestMapping("/readAll")
    public BaseResult readAll(@RequestParam(required = true) int cat_id) {

        AppUserDto userDto = getUserDto();
        if (userDto == null) {
            return fail("请重新登录");
        }
        appUserService.updateMessageReadByCat(userDto.getAuid(), cat_id);
        return success();
    }

    /**
     * 打开app刷新点击量
     *
     * @return
     */
    @RequestMapping("/openApp")
    public BaseResult openApp() {
        redisUtils.incr(RedisConstants.APP_OPEN_COUNT);
        return success();
    }

    /**
     * 根据卡号返回用户信息
     *
     * @return
     */
    @RequestMapping("/getUserInfoByCardNo")
    public BaseResult getUserInfoByCardNo(@RequestParam(required = true) String stationCard) {

        return success(appUserService.getUserInfoByCardNo(stationCard));
    }


}
